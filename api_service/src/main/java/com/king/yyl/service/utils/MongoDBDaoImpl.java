package com.king.yyl.service.utils;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yilongyu on 16/10/26.
 */
public class MongoDBDaoImpl {

    /**
     * MongoClient的实例代表数据库连接池，是线程安全的，可以被多线程共享，客户端在多线程条件下仅维持一个实例即可
     * Mongo是非线程安全的，目前mongodb API中已经建议用MongoClient替代Mongo
     */
    private MongoClient mongoClient = null;

    /**
     * 构造方法
     *
     * @param ip   127.0.0.1
     * @param port 27107
     */

    private MongoDBDaoImpl(String ip, int port) {
        if (mongoClient == null) {
            MongoClientOptions.Builder build = new MongoClientOptions.Builder();
            /*
             * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟
             * 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception
             * 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败
             */
            build.maxWaitTime(1000 * 60 * 2);
            build.connectTimeout(1000 * 60 * 1);    //与数据库建立连接的timeout设置为1分钟
            build.socketTimeout(0);// 套接字超时时间，0无限制
            build.connectionsPerHost(300);   //连接池设置为300个连接,默认为100
            build.threadsAllowedToBlockForConnectionMultiplier(5000);// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
            build.writeConcern(WriteConcern.ACKNOWLEDGED);

            MongoClientOptions myOptions = build.build();
            try {
                //数据库连接实例
                mongoClient = new MongoClient(new ServerAddress(ip, port), myOptions);
            } catch (MongoException e) {
                e.printStackTrace();
            }

        }
    }

    private static MongoDBDaoImpl mongoDBDaoImpl = null;

    /**
     * 获取实例
     *
     * @param ip   127.0.0.1
     * @param port 27107
     * @return
     */
    public synchronized static MongoDBDaoImpl getInstance(String ip, int port) {
        if (mongoDBDaoImpl == null) {
            mongoDBDaoImpl = new MongoDBDaoImpl(ip, port);
        }
        return mongoDBDaoImpl;
    }

    public synchronized static MongoDBDaoImpl getInstance() {
        if (mongoDBDaoImpl == null) {
            mongoDBDaoImpl = new MongoDBDaoImpl("localhost", 27017);
        }
        return mongoDBDaoImpl;
    }


    public boolean isExits(String dbName, String collectionName, Map<String, Object> filterMap) {
        if (filterMap != null) {
            FindIterable<Document> docs = mongoClient.getDatabase(dbName).getCollection(collectionName).find(new Document(filterMap));

            Document doc = docs.first();
            if (doc != null) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public boolean insert(String dbName, String collectionName, Map<String, Object> insertMap) {
        if (insertMap != null) {
            mongoClient.getDatabase(dbName).getCollection(collectionName).insertOne(new Document(insertMap));
            return true;
        }
        return false;
    }

    public boolean deleteById(String dbName, String collectionName, String _id) {
        ObjectId objectId = new ObjectId(_id);
        Bson filter = Filters.eq("_id", objectId);

        DeleteResult deleteResult = getDatabase(dbName).getCollection(collectionName).deleteOne(filter);
        long deletedCount = deleteResult.getDeletedCount();

        return deletedCount > 0 ? true : false;
    }


    public boolean delete(String dbName, String collectionName, Map<String, Object> map) {
        if (map != null) {
            DeleteResult result = mongoClient.getDatabase(dbName).getCollection(collectionName).deleteMany(new Document(map));
            long deletedCount = result.getDeletedCount();
            return deletedCount > 0 ? true : false;
        }
        return false;
    }


    public boolean updateOne(String dbName, String collectionName, Map<String, Object> filterMap, Map<String, Object> updateMap) {
        if (filterMap != null && filterMap.size() > 0 && updateMap != null) {
            UpdateResult result = mongoClient.getDatabase(dbName).getCollection(collectionName).updateOne(new Document(filterMap), new Document("$set", new Document(updateMap)));
            long modifiedCount = result.getModifiedCount();
            return modifiedCount > 0 ? true : false;
        }

        return false;
    }

    public boolean updateById(String dbName, String collectionName, String _id, Document updateDoc) {
        ObjectId objectId = new ObjectId(_id);
        Bson filter = Filters.eq("_id", objectId);

        UpdateResult result = getDatabase(dbName).getCollection(collectionName).updateOne(filter, new Document("$set", updateDoc));
        long modifiedCount = result.getModifiedCount();

        return modifiedCount > 0 ? true : false;
    }

    public List<Document> find(String dbName, String collectionName, Bson filter) {
        List<Document> resultList = new ArrayList<Document>();
        if (filter != null) {
            FindIterable<Document> docs = mongoClient.getDatabase(dbName).getCollection(collectionName).find(filter);
            docs.forEach(new Block<Document>() {

                public void apply(Document document) {
                    resultList.add(document);
                }
            });
        }

        return resultList;
    }

    public Document findById(String dbName, String collectionName, String _id) {
        ObjectId objectId = new ObjectId(_id);

        Document doc = getDatabase(dbName).getCollection(collectionName).find(Filters.eq("_id", objectId)).first();
        return doc;
    }


    /**
     * 分页查询
     *
     * @param dbName
     * @param collectionName
     * @param filter
     * @param pageIndex      从1开始
     * @param pageSize
     * @return
     */
    public List<Document> findByPage(String dbName, String collectionName, Bson filter, int pageIndex, int pageSize) {
        Bson orderBy = new BasicDBObject("_id", 1);

        List<Document> resultList = new ArrayList<Document>();
        FindIterable<Document> docs = getDatabase(dbName).getCollection(collectionName).find(filter).sort(orderBy).skip((pageIndex - 1) * pageSize).limit(pageSize);
        docs.forEach(new Block<Document>() {
            public void apply(Document document) {
                resultList.add(document);
            }
        });

        return resultList;
    }

    public MongoCollection getCollection(String dbName, String collectionName) {
        return new Jongo(mongoClient.getDB(dbName)).getCollection(collectionName);
    }


    public MongoDatabase getDatabase(String dbName) {
        return mongoClient.getDatabase(dbName);
    }

    public long getCount(String dbName, String collectionName) {
        return getDatabase(dbName).getCollection(collectionName).count();
    }

    /**
     * 查询dbName下的所有表名
     *
     * @param dbName
     * @return
     */
    public List<String> getAllCollections(String dbName) {
        MongoIterable<String> cols = getDatabase(dbName).listCollectionNames();
        List<String> _list = new ArrayList<String>();
        for (String s : cols) {
            _list.add(s);
        }
        return _list;
    }

    /**
     * 获取所有数据库名称列表
     *
     * @return
     */
    public MongoIterable<String> getAllDatabaseName() {
        MongoIterable<String> s = mongoClient.listDatabaseNames();
        return s;
    }

    /**
     * 删除一个数据库
     *
     * @param dbName
     */
    public void dropDatabase(String dbName) {
        getDatabase(dbName).drop();
    }

    /**
     * 删除collection
     *
     * @param dbName
     * @param collectionName
     */
    public void dropCollection(String dbName, String collectionName) {
        getDatabase(dbName).getCollection(collectionName).drop();
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}
