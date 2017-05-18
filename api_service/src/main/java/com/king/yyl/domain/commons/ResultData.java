package com.king.yyl.domain.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.king.yyl.service.utils.swagger.core.util.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 16/10/9.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class ResultData implements java.io.Serializable {
//
//    private static final long serialVersionUID = 1L;

public class ResultData{

    private int resultCode;
    private Map<String, Object> resultData;
    private String resultMsg;


    public ResultData(){}

    public ResultData(int resultCode,Map<String, Object> resultData,String resultMsg){
        this.resultCode = resultCode;
        this.resultData = resultData;
        this.resultMsg = resultMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Map<String, Object> getResultData() {
        return resultData;
    }

    public void setResultData(Map<String, Object> resultData) {
        this.resultData = resultData;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }


    public static ResultData getErrorInstance(Object object,String msg){
        ResultData resultData = new ResultData();
        resultData.setResultCode(11000);
        resultData.setResultMsg("操作失败:" + msg);
        Map<String,Object> map = new HashMap<>();
        map.put("data",object);
        resultData.setResultData(map);

        return resultData;
    }

    public static ResultData getSuccessInstance(Object object,String msg){
        ResultData resultData = new ResultData();
        resultData.setResultCode(10000);
        resultData.setResultMsg("操作成功:" + msg);
        Map<String,Object> map = new HashMap<>();
        map.put("data",object);
        resultData.setResultData(map);

        return resultData;
    }

    @Override
    public String toString() {
        String result = super.toString();
        try {
            result = Json.pretty(Json.mapper().writeValueAsString(this));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }


}
