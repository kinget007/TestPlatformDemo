package com.king.yyl.web.rest;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.king.yyl.domain.apis.custom.*;
import com.king.yyl.domain.apis.moudle.ScenarioDocSimplify;
import com.king.yyl.domain.apis.moudle.ScenarioTagName;
import com.king.yyl.domain.apis.swagger.HttpMethod;
import com.king.yyl.domain.apis.swagger.Operation;
import com.king.yyl.domain.apis.swagger.Path;
import com.king.yyl.domain.apis.swagger.parameters.Parameter;
import com.king.yyl.domain.commons.ResultData;
import com.king.yyl.service.apis.ApiDocDao;
import com.king.yyl.service.apis.ApiDocImplMongo;
import com.king.yyl.service.scenarios.ScenarioDao;
import com.king.yyl.service.scenarios.ScenarioImplMongo;
import com.king.yyl.service.utils.JacksonFactory;
import com.king.yyl.service.utils.swagger.core.util.Json;
import com.king.yyl.service.utils.verify.check.AssertHandler;
import com.king.yyl.service.utils.verify.request.SendHttpRequest;
import net.sf.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yilongyu on 2016/10/26.
 */

@RestController
@RequestMapping("/api")
public class ScenarioResource{


    final static ScenarioDao scenarioDao = new ScenarioImplMongo();

    @GetMapping("/scenario")
    public ResponseEntity<ResultData> getScenariosByEmailOne(@RequestParam("emailUser") String emailUser) {
        ScenarioDoc scenarioDoc = scenarioDao.selectScenarioDocByEmail(emailUser).get(0);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(scenarioDoc, "getScenariosByEmailOne"));
    }


    @GetMapping("/scenario/{scenarioId}")
    public ResponseEntity<ResultData> getScenariosByScenarioId(@PathVariable("scenarioId") String scenarioId) {
        ScenarioDoc scenarioDoc = scenarioDao.selectScenarioDocByScenarioId(scenarioId);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(scenarioDoc, "getScenariosByEmailOne"));
    }

    @GetMapping("/scenarios")
    public ResponseEntity<ResultData> getScenariosByEmail(@RequestParam("emailUser") String emailUser) {
        List<ScenarioDoc> scenarioDocList = scenarioDao.selectScenarioDocByEmail(emailUser);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(scenarioDocList, "getScenariosByEmail"));
    }

    @GetMapping(value = "/scenarios/tagName/{tagName}")
    public ResponseEntity<ResultData> getScenariosByEmailAndTagName(@PathVariable("tagName") String tagName,@RequestParam("emailUser") String emailUser) {
        List<ScenarioDoc> scenarioDocList = scenarioDao.selectScenarioDocByEmailAndTagName(emailUser, tagName);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(scenarioDocList, "getScenariosByEmailAndTagName"));
    }

    @GetMapping("/scenarios/scenarioName/{scenarioName}")
    public ResponseEntity<ResultData> getScenariosByEmailAndTitle(@PathVariable("scenarioName") String scenarioName,@RequestParam("emailUser") String emailUser) {
        List<ScenarioDoc> scenarioDocList = scenarioDao.selectScenarioDocByEmailAndScenarioName(emailUser, scenarioName);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(scenarioDocList, "getScenariosByEmailAndTitle"));
    }

    @GetMapping("/scenarios/{tagName}/{scenarioName}")
    public ResponseEntity<ResultData> getScenariosByEmailTagNameAndTitle(@PathVariable("tagName") String tagName, @PathVariable("scenarioName") String scenarioName,@RequestParam("emailUser") String emailUser) {

        ScenarioDoc scenarioDoc = scenarioDao.selectScenarioDocByEmailTagNameAndScenarioName(emailUser, tagName, scenarioName);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(scenarioDoc, "getScenariosByEmailTagNameAndTitle"));
    }

    @PostMapping("/scenario")
    public ResponseEntity<ResultData> addScenario(@RequestParam("emailUser") String emailUser,@RequestBody JsonNode scenarioNode) {
        String tagName = scenarioNode.get("tagName").toString().replace("\"", "");
        String scenarioName = scenarioNode.get("scenarioName").toString().replace("\"", "");
        String title = scenarioNode.get("title").toString().replace("\"", "");
        String version = scenarioNode.get("version").toString().replace("\"", "");


        Boolean result = scenarioDao.createScenarioDocWithEmailTagNameAndScenarioName(emailUser, tagName, scenarioName, title, version);

        return ResponseEntity.ok().body(ResultData.getSuccessInstance(result, "addScenario"));
    }

    @GetMapping("/scenarios/infos")
    public ResponseEntity<ResultData> getScenarioInfosByEmail(@RequestParam("emailUser") String emailUser) {
        List<ScenarioTagName> scenarioTagNameList = scenarioDao.selectTagNameAndScenarioNamesByEmail(emailUser);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(scenarioTagNameList, "getScenarioInfosByEmail"));
    }

    @GetMapping("/simplescenarios")
    public ResponseEntity<ResultData> getScenarioDocSimplifysByEmail(@RequestParam("emailUser") String emailUser) {
        List<ScenarioDocSimplify> scenarioDocSimplifyList = scenarioDao.selectScenarioDocSimplifyByEmail(emailUser);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(scenarioDocSimplifyList, "getScenarioDocSimplifysByEmail"));
    }

    @GetMapping("/scenario/{scenarioId}/apis")
    public ResponseEntity<ResultData> getApisByScenarioId(@PathVariable("scenarioId") String scenarioId) {
        Map<String, List<ApiListInfo>> map = new HashMap<>();

        ScenarioDoc scenarioDoc = scenarioDao.selectScenarioDocByScenarioId(scenarioId);

        if (!scenarioDoc.equals(new ScenarioDoc())) {
            Map<String, ScenarioApiInfo> dependsOn = scenarioDoc.getDependsOn();

            List<ApiListInfo> apiListInfo_in = new ArrayList<>();

            List<ApiListInfo> apiListInfoScenarioDoc = scenarioDao.selectApisByScenarioId(scenarioId);

            for (Map.Entry<String, ScenarioApiInfo> dependsOnMap : dependsOn.entrySet()) {

                for (int k = 0; k < apiListInfoScenarioDoc.size(); k++) {
                    if (UtilServices.compareApiListInfoAndScenarioApi(dependsOnMap.getValue(), apiListInfoScenarioDoc.get(k))) {
                        apiListInfo_in.add(apiListInfoScenarioDoc.get(k));
                    }
                }
            }

            map.put("in", apiListInfo_in);
        }

        ApiDocDao apiDocDao = new ApiDocImplMongo();

        List<ApiListInfo> apiListInfoList_out = apiDocDao.selectApisByTitleAndVersion(scenarioDoc.getSwagger().getInfo().getTitle(), scenarioDoc.getSwagger().getInfo().getVersion());

        map.put("out", apiListInfoList_out);

        return ResponseEntity.ok().body(ResultData.getSuccessInstance(map, "getApisByScenarioId"));
    }

    @PutMapping("/scenario/{scenarioId}/apis")
    public ResponseEntity<ResultData> updateScenario(@PathVariable("scenarioId") String scenarioId,@RequestBody JsonNode scenarioApiListInfosNode) {

        List<ApiListInfo> apiListInfos = new ArrayList<>();

        List<Object> apiList = new ArrayList<>();
        try {
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(JacksonFactory.getMapperInstance(false).writeValueAsString(scenarioApiListInfosNode));
            String str = com.alibaba.fastjson.JSONObject.toJSONString(jsonObject);
            jsonObject = JSON.parseObject(str);
            apiList = Json.mapper().readValue(jsonObject.get("apiListInfos").toString(), List.class);


//            apiList = Json.mapper().readValue(JacksonFactory.getMapperInstance(false).writeValueAsString(scenarioApiListInfosNode.get("apiListInfos").toString()), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < apiList.size(); i++) {
            try {

                ApiListInfo apiListInfo = Json.mapper().readValue(JSONObject.fromObject(apiList.get(i)).toString(), ApiListInfo.class);
//                ApiListInfo apiListInfo = Json.mapper().readValue(toJson(apiList.get(i)).toString(), ApiListInfo.class);
                apiListInfos.add(apiListInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ScenarioDoc scenarioDoc = scenarioDao.selectScenarioDocByScenarioId(scenarioId);

        Map<String, ScenarioApiInfo> dependsOnOld = new HashMap<>();

        if (scenarioDoc.getDependsOn() != null) {
            dependsOnOld = scenarioDoc.getDependsOn();
        }

        Map<String, ScenarioApiInfo> dependsOnNew = new HashMap<>();

        Map<String, Path> pathsOld = new HashMap<>();

        if (scenarioDoc.getSwagger().getPaths() != null) {
            pathsOld = scenarioDoc.getSwagger().getPaths();
        }


        Map<String, Path> pathsNew = new HashMap<>();

        if (apiListInfos.size() > 0) {
            for (int j = 0; j < apiListInfos.size(); j++) {
                Boolean flag = true;
                ScenarioApiInfo scenarioApiInfo = new ScenarioApiInfo();
                for (Map.Entry<String, ScenarioApiInfo> dependsOn : dependsOnOld.entrySet()) {
                    if (UtilServices.compareApiListInfoAndScenarioApi(dependsOn.getValue(), apiListInfos.get(j))) {
                        scenarioApiInfo = dependsOn.getValue();
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    scenarioApiInfo.setVersion(apiListInfos.get(j).getVersion());
                    scenarioApiInfo.setHost(apiListInfos.get(j).getHost());
                    scenarioApiInfo.setHttpMethod(apiListInfos.get(j).getHttpMethod());
                    scenarioApiInfo.setTitle(apiListInfos.get(j).getTitle());
                    scenarioApiInfo.setBasePath(apiListInfos.get(j).getBasePath());
                    scenarioApiInfo.setPathUrl(apiListInfos.get(j).getUrlPath());
                    scenarioApiInfo.setChecks(new ArrayList<>());

                    List<Parameter> parameters = apiListInfos.get(j).getOperation().getParameters();
                    List<FieldValue> fieldValues = new ArrayList<>();
                    for (int l = 0; l < parameters.size(); l++) {
                        FieldValue fieldValue = new FieldValue();
                        fieldValue.setName(parameters.get(l).getName());
                        fieldValue.setIn(parameters.get(l).getIn());
                        fieldValue.setValue("");
                        fieldValues.add(fieldValue);
                    }

                    scenarioApiInfo.setFieldValues(fieldValues);
                    Map<String, String> map = new HashMap<>();
                    scenarioApiInfo.setHeaders(map);

                }
                dependsOnNew.put(j + "", scenarioApiInfo);

                String pathNew = apiListInfos.get(j).getUrlPath();
                Operation operationNew = apiListInfos.get(j).getOperation();
                HttpMethod httpMethodNew = apiListInfos.get(j).getHttpMethod();
                Path path = new Path();

                if (pathsNew.containsKey(pathNew)) {
                    path = pathsNew.get(pathNew);
                }

                if (pathsOld.containsKey(pathNew)) {

                    Map<HttpMethod, Operation> operationMapOld = pathsOld.get(pathNew).getOperationMap();

                    if (operationMapOld.containsKey(httpMethodNew)) {

                        path = path.set(httpMethodNew.toString().toLowerCase(), operationMapOld.get(httpMethodNew));

                    } else {
                        path = path.set(httpMethodNew.toString().toLowerCase(), operationNew);
                    }

                } else {
                    path = path.set(httpMethodNew.toString().toLowerCase(), operationNew);
                }
                pathsNew.put(pathNew, path);
            }
        }
        scenarioDoc.getSwagger().setPaths(pathsNew);
        scenarioDoc.setDependsOn(dependsOnNew);
        Boolean flag = scenarioDao.upateScenarioDoc(scenarioDoc);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(flag, "updateScenario"));
    }

    @PutMapping("/scenario/{scenarioId}/dependon")
    public ResponseEntity<ResultData> updateScenarioByDependsOn(@PathVariable("scenarioId") String scenarioId,@RequestBody JsonNode scenarioApiListInfosNode) {
        List<Check> checks = new ArrayList<>();
        List<FieldValue> fieldValues = new ArrayList<>();
        List<Object> checksObject = new ArrayList<>();
        List<Object> fieldValuesObject = new ArrayList<>();
        Map<String, String> headers = new HashMap<>();

        String orderNum_js = scenarioApiListInfosNode.get("orderNum").toString().replace("\"", "");
        String scheme = scenarioApiListInfosNode.get("scheme").toString().replace("\"", "");
        String host = scenarioApiListInfosNode.get("host").toString().replace("\"", "");
        String urlPath = scenarioApiListInfosNode.get("urlPath").toString().replace("\"", "");

        try {
            headers = Json.mapper().readValue(scenarioApiListInfosNode.get("headers").toString(), Map.class);
            checksObject = Json.mapper().readValue(scenarioApiListInfosNode.get("checks").toString(), List.class);
            fieldValuesObject = Json.mapper().readValue(scenarioApiListInfosNode.get("params").toString(), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < checksObject.size(); i++) {
            try {

                Check check = Json.mapper().readValue(JSONObject.fromObject(checksObject.get(i)).toString().toString(), Check.class);

//                Check check = Json.mapper().readValue(toJson(checksObject.get(i)).toString(), Check.class);
                if (!checks.contains(check)) {
                    checks.add(check);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < fieldValuesObject.size(); i++) {
            try {
                FieldValue fieldValue = Json.mapper().readValue(JSONObject.fromObject(fieldValuesObject.get(i)).toString(), FieldValue.class);
//                FieldValue fieldValue = Json.mapper().readValue(toJson(fieldValuesObject.get(i)).toString(), FieldValue.class);
                fieldValues.add(fieldValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ScenarioDoc scenarioDoc = scenarioDao.selectScenarioDocByScenarioId(scenarioId);
        scenarioDoc.getDependsOn().get(orderNum_js).setChecks(checks);
        scenarioDoc.getDependsOn().get(orderNum_js).setHeaders(headers);
        scenarioDoc.getDependsOn().get(orderNum_js).setFieldValues(fieldValues);
        scenarioDoc.getDependsOn().get(orderNum_js).setHost(host);
        scenarioDoc.getDependsOn().get(orderNum_js).setScheme(scheme);
        scenarioDoc.getDependsOn().get(orderNum_js).setPathUrl(urlPath.replace(scenarioDoc.getDependsOn().get(orderNum_js).getBasePath(), ""));

        scenarioDao.upateScenarioDoc(scenarioDoc);

        Map<String, ScenarioApiInfo> dependsOn = scenarioDoc.getDependsOn();
        Map<Integer, ScenarioApiInfo> dependsOnResult = new HashMap<>();

        Map<String, String> saveParams = new HashMap<>();

//        String sessionId = "";

        for (int i = 0; i <= Integer.parseInt(orderNum_js); i++) {
            ScenarioApiInfo scenarioApiInfo = scenarioDoc.getDependsOn().get(i + "");

            List<FieldValue> fieldValueList = scenarioApiInfo.getFieldValues();

            for (int m = 0; m < fieldValueList.size(); m++) {
                if (saveParams.containsKey(fieldValueList.get(m).getValue())) {
                    fieldValueList.get(m).setValue(saveParams.get(fieldValueList.get(m).getValue()));
                }
            }
            scenarioApiInfo.setFieldValues(fieldValueList);

            SendHttpRequest sendHttpRequest = new SendHttpRequest(scenarioApiInfo);

//            if (!sessionId.equals("")) {
//                sendHttpRequest.setSessionId(sessionId);
//            }

            sendHttpRequest.sendHttpRequest();

//            sessionId = sendHttpRequest.getResponse().getSessionId();

            scenarioApiInfo.setResponse(sendHttpRequest.getResponse().asString());

            AssertHandler assertHandler = new AssertHandler();
            List<Check> checks2 = assertHandler.runAssertTry(scenarioApiInfo.getChecks(),sendHttpRequest.getResponse().asString());

//            for (int k = 0; k < scenarioApiInfo.getChecks().size(); k++) {
//                CheckRuleImpl checkRule = new CheckRuleImpl(scenarioApiInfo.getChecks().get(k));
//                checkRule.getResult(sendHttpRequest.getResponse().asString());
//                if (checkRule.getCheck().getCheckMethod().equals(CheckMethod.Save)) {
//                    saveParams.put(checkRule.getCheck().getTargetField(), checkRule.getCheck().getExpectValue());
//                }
//
//            }

            scenarioApiInfo.setChecks(checks2);
            dependsOn.put(i + "", scenarioApiInfo);
            dependsOnResult.put(i, scenarioApiInfo);
        }
        scenarioDoc.setDependsOn(dependsOn);

        return ResponseEntity.ok().body(ResultData.getSuccessInstance(dependsOnResult, "updateScenarioByDependsOnAndSendRequest"));
    }

    @GetMapping("/scenario/{scenarioId}/sendrequest")
    public ResponseEntity<ResultData> sendRequestByScenarioId(@PathVariable("scenarioId") String scenarioId) {

        ScenarioDoc scenarioDoc = scenarioDao.selectScenarioDocByScenarioId(scenarioId);
        Map<String, String> saveParams = new HashMap<>();
        Map<String, ScenarioApiInfo> dependsOn = scenarioDoc.getDependsOn();
//        String sessionId = "";


        for (Map.Entry<String, ScenarioApiInfo> dependOn : scenarioDoc.getDependsOn().entrySet()) {
            ScenarioApiInfo scenarioApiInfo = dependOn.getValue();
            ScenarioApiInfo scenarioApiInfo_send = scenarioApiInfo;

            List<Check> checkList = new ArrayList<>();
            List<FieldValue> fieldValueList = scenarioApiInfo.getFieldValues();

            for (int m = 0; m < fieldValueList.size(); m++) {
                if (saveParams.containsKey(fieldValueList.get(m).getValue())) {
                    fieldValueList.get(m).setValue(saveParams.get(fieldValueList.get(m).getValue()));
                }
            }
            scenarioApiInfo_send.setFieldValues(fieldValueList);

            SendHttpRequest sendHttpRequest = new SendHttpRequest(scenarioApiInfo_send);
//            if (!sessionId.equals("")) {
//                sendHttpRequest.setSessionId(sessionId);
//            }
            sendHttpRequest.sendHttpRequest();

//            System.out.println(sendHttpRequest.getResponse().asString().length());
//            if(sendHttpRequest.getResponse().asString().length()!=0){
//                sessionId = sendHttpRequest.getResponse().getSessionId();
//                if(sessionId.equals(null)){
//                    sessionId = "";
//                }
//            }

            scenarioApiInfo.setResponse(sendHttpRequest.getResponse().asString());

            AssertHandler assertHandler = new AssertHandler();

            List<Check> checks = assertHandler.runAssertTry(scenarioApiInfo.getChecks(),sendHttpRequest.getResponse().asString());


//            for (int k = 0; k < scenarioApiInfo.getChecks().size(); k++) {
//                CheckRuleImpl checkRule = new CheckRuleImpl(scenarioApiInfo.getChecks().get(k));
//                checkRule.getResult(sendHttpRequest.getResponse().asString());
//                checkList.add(checkRule.getCheck());
//
//                if (checkRule.getCheck().getCheckMethod().equals(CheckMethod.Save)) {
//                    saveParams.put(checkRule.getCheck().getTargetField(), checkRule.getCheck().getExpectValue());
//                }
//
//            }

            scenarioApiInfo.setChecks(checks);
            dependsOn.put(dependOn.getKey(), scenarioApiInfo);
        }
        scenarioDoc.setDependsOn(dependsOn);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(scenarioDoc.getDependsOn(), "sendRequestByScenarioId"));
    }

}
