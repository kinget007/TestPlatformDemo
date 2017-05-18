package com.king.yyl.service.utils.verify.check;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.king.yyl.domain.apis.custom.Check;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class AssertHandler{
    private final Logger log = LoggerFactory.getLogger(BodyAssert.class);


    public List<Check> runAssertTry(List<Check> checks,String responseBody) {

        try {
            BodyAssert tool = new BodyAssert(responseBody);
            for (Check check : checks) {
                tool.doAssert(check);
            }
        } catch (Exception e) {
            log.debug("Body is not json");
        }
        return checks;
    }

    private class BodyAssert {

        Object body;

        public BodyAssert(String body) {
            this.body = Configuration.defaultConfiguration().jsonProvider().parse(body);
        }

        @SuppressWarnings("unchecked")
        public void doAssert(Check check) {
            String propertyName = check.getField();
            if (propertyName.startsWith("[")) {
                propertyName = "$" + propertyName;
            } else {
                propertyName = "$." + propertyName;
            }

            boolean success = false;

            try {
                Object actualValue = JsonPath.read(body, propertyName);
                check.setActualValue(actualValue.toString());
                if (actualValue instanceof Number) {
                    success = evaluate(check.getExpectValue(), check.getCheckMethod(), (Number) actualValue);
                } else if (actualValue instanceof String) {
                    success = evaluate(check.getExpectValue(), check.getCheckMethod(), (String) actualValue);
                } else if (actualValue instanceof Map) {
                    JSONObject json = new JSONObject((Map<String, ?>) actualValue);
                    check.setActualValue(json.toString());
                    success = evaluate(check.getExpectValue(), check.getCheckMethod(), json);
                } else if (actualValue instanceof List) {
                    success = evaluate(check.getExpectValue(), check.getCheckMethod(), (List<Object>) actualValue);
                }
            } catch (Exception e) {
                log.debug("propery not found");
            }

            if(success){
                check.setStatus("OK");
            }else{
                check.setStatus("KO");
            }


        }

        private boolean evaluate(String expectedValue, String comparator, String actualValue) {
            boolean result = false;

            switch (comparator) {
                case "=":
                    result = actualValue.equals(expectedValue);
                    break;
                case "!=":
                    result = !actualValue.equals(expectedValue);
                    break;
                case "Contains":
                    result = actualValue.contains(expectedValue);
                    break;
                case "! Contains":
                    result = !actualValue.contains(expectedValue);
                    break;
            }

            return result;
        }

        private boolean evaluate(String expectedValue, String comparator, Number actualValue) {
            boolean result = false;

            double expected = Double.parseDouble(expectedValue);
            double actual = actualValue.doubleValue();

            switch (comparator) {
                case "=":
                    result = actual == expected;
                    break;
                case "!=":
                    result = actual != expected;
                    break;
                case "<":
                    result = actual < expected;
                    break;
                case "<=":
                    result = actual <= expected;
                    break;
                case ">":
                    result = actual > expected;
                    break;
                case ">=":
                    result = actual >= expected;
                    break;
            }

            return result;
        }

        private boolean evaluate(String expectedValue, String comparator, JSONObject actualValue) {
            boolean result = false;

            switch (comparator) {
                case "Contains Key":
                    result = actualValue.containsKey(expectedValue);
                    break;
                case "! Contains Key":
                    result = !actualValue.containsKey(expectedValue);
                    break;
                case "Contains Value":
                    result = actualValue.containsValue(expectedValue);
                    ;
                    break;
                case "! Contains Value":
                    result = !actualValue.containsValue(expectedValue);
                    break;

            }

            return result;
        }

        private boolean evaluate(String expectedValue, String comparator, List<Object> actualValue) {
            boolean result = false;

            switch (comparator) {
                case "Contains Value":
                    result = actualValue.contains(expectedValue);
                    break;
                case "! Contains Value":
                    result = !actualValue.contains(expectedValue);
                    break;

            }
            return result;
        }
    }


}

