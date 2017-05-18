package com.king.yyl.web.rest;

import com.king.yyl.domain.apis.custom.ApiDoc;
import com.king.yyl.domain.apis.custom.ApiListInfo;
import com.king.yyl.domain.apis.custom.ScenarioApiInfo;
import com.king.yyl.domain.apis.custom.ScenarioDoc;
import com.king.yyl.domain.apis.swagger.HttpMethod;
import com.king.yyl.domain.apis.swagger.Operation;
import com.king.yyl.domain.apis.swagger.Path;
import com.king.yyl.domain.apis.swagger.Swagger;
import com.king.yyl.domain.commons.ResultData;
import com.king.yyl.service.utils.JacksonFactory;
import com.king.yyl.service.utils.swagger.core.util.Json;
import com.king.yyl.service.utils.swagger.parser.SwaggerParser;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by apple on 16/10/11.
 */
public class UtilServices {

    public static List<ApiListInfo> apiDocToApiListInfo(List<ApiDoc> apiDocList){
        List<ApiListInfo> apiListInfoList = new ArrayList<>();
        for(int i=0;i<apiDocList.size();i++){
            ApiDoc apiDoc = apiDocList.get(i);

            if(!apiDoc.equals(new ApiDoc())){
                apiListInfoList.addAll(swaggerToApiListInfo(apiDoc.getSwagger()));

            }
        }

        return apiListInfoList;
    }


    public static List<ApiListInfo> swaggerToApiListInfo(Swagger swagger){
//        try {
//            swagger = JacksonFactory.getMapperInstance(false).readValue(Json.pretty(JSONObject.fromObject(swagger).toString()),Swagger.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        List<ApiListInfo> apiListInfoList = new ArrayList<>();
        Map<String,Path> paths =  swagger.getPaths();
        for (Map.Entry<String, Path> entry : paths.entrySet()) {
            String urlPath = entry.getKey();

            Map<HttpMethod,Operation> httpMethodOperationMap = entry.getValue().getOperationMap();

            for (Map.Entry<HttpMethod, Operation> OperationMap : httpMethodOperationMap.entrySet()) {
                ApiListInfo apiListInfo = new ApiListInfo();
                apiListInfo.setTitle(swagger.getInfo().getTitle());
                apiListInfo.setVersion(swagger.getInfo().getVersion());
                apiListInfo.setHost(swagger.getHost());
                apiListInfo.setBasePath(swagger.getBasePath());
                apiListInfo.setUrlPath(urlPath);
                apiListInfo.setHttpMethod(OperationMap.getKey());
                apiListInfo.setOperation(OperationMap.getValue());
                apiListInfo.setDefinitions(swagger.getDefinitions());
                apiListInfo.setScheme(swagger.getSchemes());
                apiListInfoList.add(apiListInfo);
            }
        }
        return apiListInfoList;

    }

    public static ResultData verifyDataOne(Object apiDocJson){

        ResultData resultData = new ResultData();

        if(apiDocJson != null){
//            JSONObject jsonObject = new JSONObject(toJson(apiDocJson).toString());

            JSONObject jsonObject = JSONObject.fromObject(apiDocJson);

            Swagger swagger = new SwaggerParser().parse(jsonObject.get("swagger").toString().replace("\"@ref\"","\"$ref\""));

            ObjectId objectId = null;

            try {

                objectId = Json.mapper().readValue(jsonObject.get("_id").toString(),ObjectId.class);

            } catch (IOException e) {

                e.printStackTrace();

            }

            ApiDoc apiDoc = new ApiDoc();

            apiDoc.set_id(objectId.toString());

            apiDoc.setSwagger(swagger);

            Map<String,Object> map = new HashMap<>();

            resultData.setResultCode(10000);

            map.put("data",apiDoc);

            resultData.setResultMsg("查询成功");

            resultData.setResultData(map);
        }

        return resultData;

    }

    public static ApiDoc verifyData(Object apiDocJson){

        ApiDoc apiDoc = new ApiDoc();
        if(apiDocJson != null){
            JSONObject jsonObject = JSONObject.fromObject(apiDocJson);
            Swagger swagger = new SwaggerParser().parse(jsonObject.get("swagger").toString().replace("\"@ref\"","\"$ref\""));

            ObjectId objectId = null;
            try {
                objectId = Json.mapper().readValue(jsonObject.get("_id").toString(),ObjectId.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            apiDoc.set_id(objectId.toString());
            apiDoc.setSwagger(swagger);
        }

        return apiDoc;
    }

    public static ScenarioDoc verifyDataScenarioDoc(Object apiDocJson){
        ScenarioDoc scenarioDoc = new ScenarioDoc();

        if(apiDocJson != null){
            JSONObject jsonObject = JSONObject.fromObject(apiDocJson);

            String tagName = jsonObject.get("tagName").toString();
            String scenarioName = jsonObject.get("scenarioName").toString();

            Swagger swagger = new SwaggerParser().parse(jsonObject.get("swagger").toString().replace("\"@ref\"","\"$ref\""));
            ObjectId objectId = new ObjectId();

            Map<String,ScenarioApiInfo> dependsOn = new HashMap<>();

            try {
                Map<String,Object> dependsOnObject = Json.mapper().readValue(jsonObject.get("dependsOn").toString(),Map.class);
                for (Map.Entry<String,Object> OperationMap : dependsOnObject.entrySet()) {
                    ScenarioApiInfo scenarioApiInfo = JacksonFactory.getMapperInstance(false).readValue(Json.pretty(OperationMap.getValue()).toString(),ScenarioApiInfo.class);
                    dependsOn.put(OperationMap.getKey(),scenarioApiInfo);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                objectId = Json.mapper().readValue(jsonObject.get("_id").toString(),ObjectId.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            scenarioDoc.set_id(objectId.toString());
            scenarioDoc.setSwagger(swagger);
            scenarioDoc.setTagName(tagName);
            scenarioDoc.setScenarioName(scenarioName);
            scenarioDoc.setDependsOn(dependsOn);
        }
        return scenarioDoc;
    }

    public static boolean compareApiListInfoAndScenarioApi(ScenarioApiInfo scenarioApiInfo,ApiListInfo apiListInfo){
        if(!scenarioApiInfo.getTitle().equals(apiListInfo.getTitle())){
            return false;
        }

        if(!scenarioApiInfo.getHttpMethod().equals(apiListInfo.getHttpMethod())){
            return false;
        }

        if(!scenarioApiInfo.getPathUrl().equals(apiListInfo.getUrlPath())){
            return false;
        }

        if(!scenarioApiInfo.getVersion().equals(apiListInfo.getVersion())){
            return false;
        }

        if(!scenarioApiInfo.getBasePath().equals(apiListInfo.getBasePath())){
            return false;
        }

//        if(!scenarioApiInfo.getHost().equals(apiListInfo.getHost())){
//            return false;
//        }

        return true;
    }
}
