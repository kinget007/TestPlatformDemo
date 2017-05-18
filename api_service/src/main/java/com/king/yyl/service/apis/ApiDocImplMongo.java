package com.king.yyl.service.apis;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.WriteResult;
import com.king.yyl.service.utils.swagger.parser.SwaggerParser;
import com.king.yyl.domain.apis.custom.ApiDoc;
import com.king.yyl.domain.apis.custom.ApiListInfo;
import com.king.yyl.domain.apis.moudle.InfoVersions;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import org.jongo.Aggregate;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import com.king.yyl.web.rest.UtilServices;
import com.king.yyl.service.utils.MongoDBDaoImpl;
import com.king.yyl.service.utils.swagger.core.util.Json;

import java.io.IOException;
import java.util.*;

/**
 * Created by apple on 16/10/18.
 */
public class ApiDocImplMongo implements ApiDocDao {

    private final MongoDBDaoImpl mongoDBDao = MongoDBDaoImpl.getInstance();

    private final MongoCollection apidocs = mongoDBDao.getCollection("apiservice", "apidocs");

    @Override
    public List<ApiDoc> selectApiDocAll() {
        List<ApiDoc> apiDocList = new ArrayList<>();
        MongoCursor<Object> apiDocsMongoCursor = apidocs.find().as(Object.class);
        while (apiDocsMongoCursor.hasNext()) {
            apiDocList.add(UtilServices.verifyData(apiDocsMongoCursor.next()));
        }
        return apiDocList;
    }

    @Override
    public List<ApiDoc> selectApiDocByTitle(String title) {
        List<ApiDoc> apiDocList = new ArrayList<>();
        MongoCursor<Object> apiDocsMongoCursor = apidocs.find("{swagger.info.title:#}", title).as(Object.class);

        while (apiDocsMongoCursor.hasNext()) {
            apiDocList.add(UtilServices.verifyData(apiDocsMongoCursor.next()));
        }
        return apiDocList;
    }

    @Override
    public List<ApiDoc> selectApiDocByVersion(String version) {
        List<ApiDoc> apiDocList = new ArrayList<>();
        MongoCursor<Object> apiDocsMongoCursor = apidocs.find("{swagger.info.version:#}", version).as(Object.class);
        while (apiDocsMongoCursor.hasNext()) {
            apiDocList.add(UtilServices.verifyData(apiDocsMongoCursor.next()));
        }
        return apiDocList;
    }

    @Override
    public ApiDoc selectApiDocByTitleAndVersion(String title, String version) {
        Object apiInfoJson = apidocs.findOne("{swagger.info.title:#,swagger.info.version:#}", title, version).as(Object.class);
        return UtilServices.verifyData(apiInfoJson);
    }

    @Override
    public List<InfoVersions> selectInfosAll() {
        List<InfoVersions> infoList = new ArrayList<>();
        Aggregate.ResultsIterator<InfoVersions> infoVersionsResultsIterator = apidocs.aggregate("{ $group : { _id : '$swagger.info.title' ,versions:{$push:'$swagger.info.version'}} }").as(InfoVersions.class);
        while (infoVersionsResultsIterator.hasNext()) {
            infoList.add(infoVersionsResultsIterator.next());
        }
        return infoList;
    }

    @Override
    public ApiDoc selectApiDocOne() {
        Object apiInfoJson = apidocs.findOne().as(Object.class);
        return UtilServices.verifyData(apiInfoJson);
    }

    @Override
    public List<ApiListInfo> selectApisAll() {
        return UtilServices.apiDocToApiListInfo(this.selectApiDocAll());
    }

    @Override
    public List<ApiListInfo> selectApisByTitleAndVersion(String title, String version) {
        List<ApiDoc> apiDocList = new ArrayList<>();
        apiDocList.add(this.selectApiDocByTitleAndVersion(title, version));
        return UtilServices.apiDocToApiListInfo(apiDocList);
    }

    @Override
    public List<ApiListInfo> selectApisByVersion(String version) {
        return UtilServices.apiDocToApiListInfo(this.selectApiDocByVersion(version));
    }

    @Override
    public List<ApiListInfo> selectApisByTitle(String title) {
        return UtilServices.apiDocToApiListInfo(this.selectApiDocByTitle(title));
    }

    @Override
    public Boolean insertApiDoc(JSONObject apiInfo) {
        ApiDoc apiDoc = new ApiDoc();

        try {
            JsonNode jsonNode = Json.mapper().readTree(apiInfo.toString().replace("\"$ref\"", "\"@ref\""));
            SwaggerParser swaggerParser = new SwaggerParser();
            apiDoc.setSwagger(swaggerParser.read(jsonNode));

            WriteResult writeResult = apidocs.insert(apiDoc);
//            System.out.println(JSONObject.fromObject(writeResult).toString());
            String title = jsonNode.get("info").get("title").toString().replace("\"", "");
            String version = jsonNode.get("info").get("version").toString().replace("\"", "");
            apiDoc = this.selectApiDocByTitleAndVersion(title, version);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (apiDoc.equals(new ApiDoc())) {
            return false;
        } else {
            return true;

        }
    }

    @Override
    public Boolean removeApiDoc(ApiDoc apiInfo) {
        WriteResult writeResult = apidocs.remove(new ObjectId(apiInfo.get_id()));
        return null;
    }

    @Override
    public Boolean updateApiDoc(ApiDoc apiInfo) {
        WriteResult writeResult = apidocs.save(apiInfo);

        return null;
    }


}
