package com.king.yyl.service.scenarios;

import com.mongodb.WriteResult;
import com.king.yyl.domain.apis.custom.ApiDoc;
import com.king.yyl.domain.apis.custom.ApiListInfo;
import com.king.yyl.domain.apis.custom.ScenarioApiInfo;
import com.king.yyl.domain.apis.custom.ScenarioDoc;
import com.king.yyl.domain.apis.moudle.ScenarioDocSimplify;
import com.king.yyl.domain.apis.moudle.ScenarioTagName;
import com.king.yyl.domain.apis.swagger.*;
import org.bson.types.ObjectId;
import org.jongo.Aggregate;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import com.king.yyl.web.rest.UtilServices;
import com.king.yyl.service.apis.ApiDocDao;
import com.king.yyl.service.apis.ApiDocImplMongo;
import com.king.yyl.service.utils.MongoDBDaoImpl;

import java.util.*;


/**
 * Created by apple on 16/10/17.
 */
public class ScenarioImplMongo implements ScenarioDao {

    private final MongoDBDaoImpl mongoDBDao = MongoDBDaoImpl.getInstance();

    private final MongoCollection scenarioInfos = mongoDBDao.getCollection("apiservice", "scenarioinfos");

    @Override
    public Boolean createScenarioDocWithEmailTagNameAndScenarioName(String email, String tagName, String scenarioName,String title,String version) {

        ScenarioDoc scenarioDocExist = selectScenarioDocByEmailTagNameAndScenarioName(email, tagName, scenarioName);

        ApiDocDao apiDocDao = new ApiDocImplMongo();

        ApiDoc apiDoc = apiDocDao.selectApiDocByTitleAndVersion(title,version);

        if (scenarioDocExist.equals(new ScenarioDoc())) {
            ScenarioDoc scenarioDoc = new ScenarioDoc();
            scenarioDoc.setTagName(tagName);
            scenarioDoc.setScenarioName(scenarioName);

            Swagger swagger = apiDoc.getSwagger();

            if(apiDoc.getSwagger().getInfo().getContact() == null){
                Contact contact = new Contact();
                contact.setEmail(email);
                swagger.getInfo().setContact(contact);
            }else{
                swagger.getInfo().getContact().setEmail(email);
            }

            Map<String,Path> map = new HashMap<>();
            swagger.setPaths(map);

            scenarioDoc.setSwagger(swagger);

            Map<String,ScenarioApiInfo> dependsOn = new HashMap<>();
            scenarioDoc.setDependsOn(dependsOn);

            scenarioInfos.save(scenarioDoc);

            ScenarioDoc scenarioDb = selectScenarioDocByEmailTagNameAndScenarioName(email, tagName, scenarioName);

            if (scenarioDb.equals(new ScenarioDoc())) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<ScenarioDoc> selectScenarioDocByEmailAndTagName(String email, String tagName) {
        List<ScenarioDoc> scenarioDocList = new ArrayList<>();
        MongoCursor<Object> scenarioDocMongoCursor = scenarioInfos.find("{swagger.info.contact.email:#,tagName:#}", email, tagName).as(Object.class);
        while (scenarioDocMongoCursor.hasNext()) {
            scenarioDocList.add(UtilServices.verifyDataScenarioDoc(scenarioDocMongoCursor.next()));
        }
        return scenarioDocList;
    }

    @Override
    public List<ScenarioDoc> selectScenarioDocByEmailAndScenarioName(String email, String scenarioName) {
        List<ScenarioDoc> scenarioDocList = new ArrayList<>();
        MongoCursor<Object> scenarioDocMongoCursor = scenarioInfos.find("{swagger.info.contact.email:#,scenarioName:#}", email, scenarioName).as(Object.class);
        while (scenarioDocMongoCursor.hasNext()) {
            scenarioDocList.add(UtilServices.verifyDataScenarioDoc(scenarioDocMongoCursor.next()));
        }
        return scenarioDocList;
    }

    @Override
    public List<ScenarioDoc> selectScenarioDocByEmail(String email) {
        List<ScenarioDoc> scenarioDocList = new ArrayList<>();
        MongoCursor<Object> scenarioDocMongoCursor = scenarioInfos.find("{swagger.info.contact.email:#}", email).as(Object.class);
        while (scenarioDocMongoCursor.hasNext()) {
            scenarioDocList.add(UtilServices.verifyDataScenarioDoc(scenarioDocMongoCursor.next()));
        }
        return scenarioDocList;
    }

    @Override
    public ScenarioDoc selectScenarioDocByEmailTagNameAndScenarioName(String email, String tagName, String scenarioName) {
        ScenarioDoc scenarioDoc = new ScenarioDoc();
        MongoCursor<Object> scenarioDocMongoCursor = scenarioInfos.find("{swagger.info.contact.email:#,tagName:#,scenarioName:#}", email, tagName, scenarioName).as(Object.class);
        if (scenarioDocMongoCursor.count() > 0) {
            scenarioDoc = UtilServices.verifyDataScenarioDoc(scenarioDocMongoCursor.next());
        }
        return scenarioDoc;
    }

    @Override
    public ScenarioDoc selectScenarioDocByScenarioId(String idScenario) {
        ScenarioDoc scenarioDoc = new ScenarioDoc();
        Object scenarioDocDb = scenarioInfos.findOne("{_id:#}", new ObjectId(idScenario)).as(Object.class);

        if (scenarioDocDb != null) {
            scenarioDoc = UtilServices.verifyDataScenarioDoc(scenarioDocDb);
        }
        return scenarioDoc;
    }

    @Override
    public Boolean upateScenarioDoc(ScenarioDoc scenarioDoc) {

        WriteResult writeResult = scenarioInfos.save(scenarioDoc);

        if (writeResult.isUpdateOfExisting()) {
            return true;
        }
        return false;
    }

    @Override
    public List<ScenarioTagName> selectTagNameAndScenarioNamesByEmail(String email) {
        List<ScenarioTagName> infoList = new ArrayList<>();
        Aggregate.ResultsIterator<ScenarioTagName> infoVersionsResultsIterator = scenarioInfos.aggregate("{$match:{swagger.info.contact.email: #}}", email).and("{ $group : { _id : '$tagName' ,scenarioNames:{$push:'$scenarioName'}} }").as(ScenarioTagName.class);
        while (infoVersionsResultsIterator.hasNext()) {
            infoList.add(infoVersionsResultsIterator.next());
        }
        return infoList;
    }

    @Override
    public List<ApiListInfo> selectApisByScenarioId(String scenarioId) {
        ScenarioDoc scenarioDoc = selectScenarioDocByScenarioId(scenarioId);
        Swagger swagger = scenarioDoc.getSwagger();
        List<ApiListInfo> apiListInfoList = new ArrayList<>();
        if(swagger.getPaths() != null){
            apiListInfoList.addAll(UtilServices.swaggerToApiListInfo(swagger));
        }
        return apiListInfoList;
    }

    @Override
    public List<ScenarioDocSimplify> selectScenarioDocSimplifyByEmail(String email) {
        List<ScenarioDocSimplify> scenarioDocSimplifies = new ArrayList<>();
        MongoCursor<Object> scenarioDocMongoCursor = scenarioInfos.find("{swagger.info.contact.email:#}", email).as(Object.class);
        while (scenarioDocMongoCursor.hasNext()) {
            ScenarioDocSimplify scenarioDocSimplify = new ScenarioDocSimplify();
            ScenarioDoc scenarioDoc = UtilServices.verifyDataScenarioDoc(scenarioDocMongoCursor.next());
            scenarioDocSimplify.set_id(scenarioDoc.get_id());
            scenarioDocSimplify.setScenarioName(scenarioDoc.getScenarioName());
            scenarioDocSimplify.setTagName(scenarioDoc.getTagName());

            scenarioDocSimplifies.add(scenarioDocSimplify);
        }
        return scenarioDocSimplifies;
    }

    //    friends.find().projection("{lastname: 1, address: 1}").as(Friend.class);

    //    @Override
//    public List<ScenarioTagName> selectTagScenarioNames(String UserId) {
//        List<ScenarioTagName> infoList = new ArrayList<>();
//        Aggregate.ResultsIterator<ScenarioTagName> infoVersionsResultsIterator = scenarioInfos.aggregate("{$match:{idUser: #}}", new ObjectId(UserId)).and("{ $group : { _id : '$tagName' ,scenarioNames:{$push:'$scenarioName'}} }").as(ScenarioTagName.class);
//        Iterator<ScenarioTagName> iterator = infoVersionsResultsIterator.iterator();
//        while (iterator.hasNext()) {
//            infoList.add(iterator.next());
//        }
//        return infoList;
//    }
//
//    @Override
//    public ScenarioDoc addScenarioTagName(String UserId, String tag, String scenarioName) {
//        ScenarioDoc scenarioDocDb = selectScenarioByUserIdTagScenarioName(UserId, tag, scenarioName);
//        if (scenarioDocDb == null) {
//            ScenarioDoc scenarioDoc = new ScenarioDoc();
//            scenarioDoc.setIdUser(UserId);
//            scenarioDoc.setTagName(tag);
//            scenarioDoc.setScenarioName(scenarioName);
//            scenarioInfos.insert(scenarioDoc);
//
//            return selectScenarioByUserIdTagScenarioName(UserId, tag, scenarioName);
//        } else {
//            return new ScenarioDoc();
//
//        }
//
//    }
//
//    @Override
//    public ScenarioDoc addScenarioDoc(ScenarioDoc scenarioDoc) {
//        scenarioInfos.insert(scenarioDoc);
//        return null;
//    }
//
//    @Override
//    public List<ScenarioDoc> selectScenarioByUserId(String UserId) {
//
//        List<ScenarioDoc> scenarioDocList = new ArrayList<>();
//        MongoCursor<ScenarioDoc> scenarioDocs = scenarioInfos.find("{idUser:#}", new ObjectId("57393b7bdce2eb27e1b61086")).as(ScenarioDoc.class);
//        Iterator<ScenarioDoc> scenarioDocIterator = scenarioDocs.iterator();
//        while (scenarioDocIterator.hasNext()) {
//            scenarioDocList.add(scenarioDocIterator.next());
//        }
//
//        return scenarioDocList;
//    }
//
//    @Override
//    public ScenarioDoc selectScenarioByUserIdTagScenarioName(String userId, String tagName, String scenarioName) {
//
//        ScenarioDoc scenarioDoc = scenarioInfos.findOne("{idUser:#,tagName:#,scenarioName:#}", new ObjectId(userId), tagName, scenarioName).as(ScenarioDoc.class);
//
//        return scenarioDoc;
//    }
//
//    @Override
//    public ScenarioDoc selectScenarioDocByScenarioId(String idScenario) {
//        ScenarioDoc scenarioDoc = scenarioInfos.findOne(new ObjectId(idScenario)).as(ScenarioDoc.class);
//        return scenarioDoc;
//    }
//
//    @Override
//    public WriteResult upateScenario(ScenarioDoc scenarioDoc) {
//
//        WriteResult writeResult = scenarioInfos.save(scenarioDoc);
//
//        return writeResult;
//    }
}

//    public static void main(String[] args){
//        ScenarioImplMongo scenarioImplMongo = new ScenarioImplMongo();
//        List<ScenarioDoc> scenarioDocList = scenarioImplMongo.selectScenarioByUserId("57393b7bdce2eb27e1b61086");
//
//        System.out.println(toJson(scenarioDocList));

///** add
//        ScenarioImplMongo scenarioImplMongo = new ScenarioImplMongo();
//
//        ApiDocImplMongo apiDocImplMongo = new ApiDocImplMongo();
//
//        List<ScenarioApi> scenarioApiList = apiDocImplMongo.selectScenarioApis("Uber API","1.0.0");
//        scenarioImplMongo.addScenarioTagName("57393b7bdce2eb27e1b61086","testTag2","testScenarioName2");
//        ScenarioDoc scenarioDoc = scenarioImplMongo.selectScenarioByUserIdTagScenarioName("57393b7bdce2eb27e1b61086","testTag2","testScenarioName2");
//        scenarioDoc.setScenarioApis(scenarioApiList);
//        System.out.println(toJson(scenarioImplMongo.upateScenario(scenarioDoc)));

//  */

/**
 * MongoDBDaoImpl mongoDBDao = MongoDBDaoImpl.getInstance("127.0.0.1", 27017);
 * <p>
 * MongoCollection scenarioInfos = mongoDBDao.getCollection("apirobot", "scenarioinfos");
 * <p>
 * List<ScenarioDoc> scenarioDocList = new ArrayList<>();
 * MongoCursor<ScenarioDoc> scenarioDocs = scenarioInfos.find("{idUser:#}",new ObjectId("57393b7bdce2eb27e1b61086")).as(ScenarioDoc.class);
 * Iterator<ScenarioDoc> scenarioDocIterator =  scenarioDocs.iterator();
 * while(scenarioDocIterator.hasNext()){
 * scenarioDocList.add(scenarioDocIterator.next());
 * }
 * //
 * System.out.println(toJson(scenarioDocList));
 * <p>
 * <p>
 * //
 * //        try {
 * //            JSONObject jsonObject = new JSONObject(toJson(objectList.get(0)).toString());
 * //            jsonObject.get("_id");
 * //            jsonObject.get("idUser");
 * //            jsonObject.get("tagName");
 * //            jsonObject.get("scenarioName");
 * //            jsonObject.get("dependsOn");
 * //            com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonObject.get("scenarioApis").toString());
 * //            System.out.println("jsonArray0   "+toJson(jsonArray.get(0)));
 * //
 * //
 * //        try {
 * //
 * //            String json = "{\"summary\":\"Price Estimates\",\"httpMethod\":\"GET\",\"version\":\"1.0.0\",\"urlPath\":\"/estimates/price\",\"bodyParameters\":[],\"pathParameters\":[],\"apiDocId\":null,\"checks\":{},\"basePath\":\"/v1\",\"cookieParameters\":[],\"queryParameters\":[{\"vendorExtensions\":{},\"in\":\"query\",\"name\":\"start_latitude\",\"format\":\"double\",\"description\":\"Latitude component of start location.\",\"type\":\"number\",\"required\":true},{\"vendorExtensions\":{},\"in\":\"query\",\"name\":\"start_longitude\",\"format\":\"double\",\"description\":\"Longitude component of start location.\",\"type\":\"number\",\"required\":true},{\"vendorExtensions\":{},\"in\":\"query\",\"name\":\"end_latitude\",\"format\":\"double\",\"description\":\"Latitude component of end location.\",\"type\":\"number\",\"required\":true},{\"vendorExtensions\":{},\"in\":\"query\",\"name\":\"end_longitude\",\"format\":\"double\",\"description\":\"Longitude component of end location.\",\"type\":\"number\",\"required\":true}],\"headerParameters\":[],\"host\":\"api.uber.com\",\"formParameters\":[]}";
 * //            ScenarioApi scenarioDoc = JacksonFactory.getMapperInstance(false).readValue(toJson(jsonArray.get(0)).toString(),ScenarioApi.class);
 * //            ScenarioApi scenarioDoc = JacksonFactory.getMapperInstance(false).readValue(json,ScenarioApi.class);
 * //            System.out.println(toJson(scenarioDoc));
 * //
 * //        } catch (IOException e) {
 * //            e.printStackTrace();
 * //        }
 * //
 * //            JSONObject jsonObjectScenarioApi = new JSONObject(toJson(jsonArray.get(0)).toString());
 * //            jsonObjectScenarioApi.get("basePath");
 * //            jsonObjectScenarioApi.get("host");
 * //            jsonObjectScenarioApi.get("urlPath");
 * //            jsonObjectScenarioApi.get("httpMethod");
 * //        try {
 * //            ScenarioApi scenarioApi = Json.mapper().readValue(jsonArray.get(0).toString(),ScenarioApi.class);
 * //        } catch (IOException e) {
 * //            e.printStackTrace();
 * //        }
 * //
 * //            Operation operation = services.utils.swagger.core.util.Json.mapper().readValue(toJson(jsonObjectScenarioApi.get("operation")).toString(),Operation.class);
 * //
 * //            System.out.println(toJson(operation));
 * //
 * //
 * //        } catch (IOException e) {
 * //            e.printStackTrace();
 * //        }
 * //
 */

//        while(objectDocs.hasNext()){
//            objectList.add(objectDocs.next());
//        }
//
//
//
//        System.out.println(toJson(objectList));

//        System.out.println(scenarioDocList);


//        List<ScenarioApi> scenarioApiList = new ArrayList<>();
//
//        Map<String,Object> dependsOn = new HashMap<>();
//
//        Operation operation = new Operation();
//
//        Proxy proxy = new Proxy();
//        proxy.setHost("");
//        proxy.setPort(80);
//
//        List<Check> checkRuleList = new ArrayList<>();
//        Check checkRule = new Check();
//        checkRule.setField("");
//        checkRule.setFieldTarget("");
//        checkRule.setCheck(CheckMethod.Contain);
//        checkRule.setExpect(new Object());
//        checkRule.setResult(true);
//        checkRuleList.add(checkRule);
//
//        ScenarioApi scenarioApi = new ScenarioApi();
//        scenarioApi.setHost("");
//        scenarioApi.setBasePath("");
//        scenarioApi.setUrlPath("");
//        scenarioApi.setOperation(operation);
//        scenarioApi.setOrderNum(1);
//        scenarioApi.setProxy(proxy);
//        scenarioApi.setRules(checkRuleList);
//        scenarioApiList.add(scenarioApi);
//
//
//
//        ScenarioDoc scenarioDoc = new ScenarioDoc();
//        scenarioDoc.setTagName("TestNameA");
//        scenarioDoc.setScenarioName("TestScenarioNameA");
//        scenarioDoc.setIdUser("57393b7bdce2eb27e1b61086");
//        scenarioDoc.setDependsOn(dependsOn);
//        scenarioDoc.setScenarioApis(scenarioApiList);


//    }
//}


//    private final MongoDBDaoImpl mongoDBDao = MongoDBDaoImpl.getInstance("127.0.0.1", 27017);
//
//    private final MongoCollection scenarioInfos = mongoDBDao.getCollection("apimanage", "scenarioinfo");
//
//    @Override
//    public List<ScenarioMapper> selectScenarioByUserId(ObjectId UserId) {
//        List<ScenarioMapper> scenarioInfoList = new ArrayList<>();
//        Aggregate.ResultsIterator<ScenarioMapper> scenarioInfoResultsIterator = scenarioInfos.aggregate("{$match:{idUser: #}}",UserId).and("{ $group : { _id : '$tag' ,scenarioInfos:{$push:'$$ROOT'}} }").as(ScenarioMapper.class);
//        Iterator<ScenarioMapper> iterator = scenarioInfoResultsIterator.iterator();
//        while(iterator.hasNext()){
//            scenarioInfoList.add(iterator.next());
//        }
//
//        return scenarioInfoList;
//    }

//    @Override
//    public List<ScenarioInfo> selectScenarioByUserIdScenarioName(ObjectId UserId,String tag,String scenarioName) {
//        List<ScenarioInfo> scenarioInfoList = new ArrayList<>();
//        Aggregate.ResultsIterator<ScenarioInfo> scenarioInfoResultsIterator = scenarioInfos.aggregate("{$match:{idUser: #}}",UserId).and("{$match:{tag: #}}",tag).and("{$match:{scenarioName: #}}",scenarioName).as(ScenarioInfo.class);
//        Iterator<ScenarioInfo> iterator = scenarioInfoResultsIterator.iterator();
//        while(iterator.hasNext()){
//            scenarioInfoList.add(iterator.next());
//        }
//        return scenarioInfoList;
//    }

//    @Override
//    public ScenarioInfo addScenario(String UserId, String tag, String scenarioName) {
//        ScenarioInfo scenarioInfo = new ScenarioInfo();
//        scenarioInfo.setIdUser(UserId);
//        scenarioInfo.setScenarioName(scenarioName);
//        scenarioInfo.setTag(tag);
//        scenarioInfo.setScenarioApis(new ArrayList<>());
//
//        try{
//            WriteResult writeResult = scenarioInfos.insert(scenarioInfo);
//
//        }catch (Exception e){
//            System.out.println("添加出错");
//        }
//
//        return scenarioInfo;
//    }
//
//    @Override
//    public Boolean upateScenario(ObjectId idScenario, ScenarioInfo scenarioInfo) {
//        Boolean result = true;
//
//        try{
//            WriteResult writeResult = scenarioInfos.update(idScenario).with(scenarioInfo);
//        }catch (Exception e){
//            result = false;
//            System.out.println("更新出错");
//        }
//
//        return result;
//    }
//
//    @Override
//    public ScenarioInfo selectScenarioByScenarioId(ObjectId idScenario) {
//        ScenarioInfo scenarioInfo = scenarioInfos.findOne(idScenario).as(ScenarioInfo.class);
//        return scenarioInfo;
//    }
//    @Override
//    public List<TagScenario> selectTagScenarioAll() {
//        List<TagScenario> listTagScenario = new ArrayList<>();
//        Aggregate.ResultsIterator<TagScenario> tagScenarios = taskinfos.aggregate("{ $group : { _id : '$tag' ,scenarios:{$addToSet:'$scenario'}} }").as(TagScenario.class);
//        Iterator<TagScenario> iterator = tagScenarios.iterator();
//        while(iterator.hasNext()){
//            listTagScenario.add(iterator.next());
//        }
//        return listTagScenario;
//    }
//
//    @Override
//    public List<TagScenario> selectTagScenarioByUserId(ObjectId objectId) {
//        List<TagScenario> tagScenario = new ArrayList<>();
//        Aggregate.ResultsIterator<TagScenario> tagScenarios = taskinfos.aggregate("{$match:{idUser: #}}",objectId).and("{ $group : { _id : '$tag' ,scenarios:{$addToSet:'$scenario'}} }").as(TagScenario.class);
//        Iterator<TagScenario> iterator = tagScenarios.iterator();
//        while(iterator.hasNext()){
//            tagScenario.add(iterator.next());
//        }
//        return tagScenario;
//    }
//
//    @Override
//    public List<Scenario> selectScenarioByUserId(ObjectId userId) {
//        List<Scenario> listScenario = new ArrayList();
//        Aggregate.ResultsIterator<Scenario> scenarios = taskinfos.aggregate("{$match:{idUser: #}}",userId).and("{ $group : { _id : {tag:'$tag',scenario:'$scenario'},scenarioInfos:{$push:'$$ROOT'},count:{$sum:1} }}").as(Scenario.class);
//        //iterate over it
//        Iterator<Scenario> iteratorScenario = scenarios.iterator();
//        while(iteratorScenario.hasNext()){
//            listScenario.add(iteratorScenario.next());
//        }
//        return listScenario;
//    }
//
//    @Override
//    public List<Scenario> selectScenarioByUserIdTagSceario(ObjectId userId, Scenario.Tag tag) {
//        List<Scenario> scenarios = new ArrayList();
//        Aggregate.ResultsIterator<Scenario> scenarioWithTag = taskinfos.aggregate("{$match:{idUser: #}}",userId).and("{ $group : { _id : {tag:'$tag',scenario:'$scenario'},scenarioInfos:{$push:'$$ROOT'},count:{$sum:1} }}").and("{$match:{_id: #}}",tag).as(Scenario.class);
//        //iterate over it
//
//        Iterator<Scenario> iteratorScenario = scenarioWithTag.iterator();
//        while(iteratorScenario.hasNext()){
//            scenarios.add(iteratorScenario.next());
//        }
//        return scenarios;
//    }
