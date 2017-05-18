package com.king.yyl.domain.apis.custom;

/**
 * Created by apple on 16/10/18.
 */
public class Check {

    protected String checkMethod;

    protected String field;

    protected String targetField;

    protected String expectValue;

    protected String status;

    protected String actualValue;

    public Check(){}

    public Check(String field, String targetField, String expect){
        this.field = field;
        this.targetField = targetField;
        this.expectValue = expect;

    }

    public String getTargetField() {
        return targetField;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getExpectValue() {
        return expectValue;
    }

    public void setExpectValue(String expectValue) {
        this.expectValue = expectValue;
    }

    public String getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(String checkMethod) {
        this.checkMethod = checkMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + ((targetField == null) ? 0 : targetField.hashCode());
        result = prime * result + ((expectValue == null) ? 0 : expectValue.hashCode());
        result = prime * result + ((checkMethod == null) ? 0 : checkMethod.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Check other = (Check) obj;
        if (field == null) {
            if (other.field != null) {
                return false;
            }
        } else if (!field.equals(other.field)) {
            return false;
        }
        if (targetField == null) {
            if (other.targetField != null) {
                return false;
            }
        } else if (!targetField.equals(other.targetField)) {
            return false;
        }
        if (expectValue == null) {
            if (other.expectValue != null) {
                return false;
            }
        } else if (!expectValue.equals(other.expectValue)) {
            return false;
        }

        if (checkMethod == null) {
            if (other.checkMethod != null) {
                return false;
            }
        } else if (!checkMethod.equals(other.checkMethod)) {
            return false;
        }

        return true;
    }
}
