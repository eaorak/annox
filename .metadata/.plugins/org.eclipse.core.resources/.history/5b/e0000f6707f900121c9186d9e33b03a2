package com.adenon.sp.administration.persist.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.adenon.sp.administration.jmx.BeanInfo;
import com.adenon.sp.administration.persist.BeanFields;
import com.adenon.sp.administration.persist.IPersistence;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;


public class MongoPersistence implements IPersistence {

    private static String       DB_HOST   = "127.0.0.1";
    private static String       DB_NAME   = "spdb";
    private static String       COLL_NAME = "configuration";
    //
    private final Mongo         mongo;
    private final DB            db;
    private final DBCollection  collection;
    private final JsonConverter converter = new JsonConverter();

    public MongoPersistence() throws UnknownHostException,
                             MongoException {
        this.mongo = new Mongo(DB_HOST);
        this.db = this.mongo.getDB(DB_NAME);
        this.collection = this.db.getCollection(COLL_NAME);
    }

    public DB getDb() {
        return this.db;
    }

    public DBCollection getCollection() {
        return this.collection;
    }

    @Override
    public void persist(BeanInfo info) {
        if (!info.beanConfig().persist()) {
            return;
        }
        Object bean = info.bean();
        DBCollection collection = this.getCollection();
        String objectName = info.objectName().toString();
        // Query
        BasicDBObject query = new BasicDBObject();
        query.put(BeanFields.OBJECT_NAME.id(), objectName);
        // Bean
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put(BeanFields.OBJECT_NAME.id(), objectName);
        dbObject.put(BeanFields.TYPE.id(), bean.getClass().getName());
        dbObject.putAll(this.converter.objToBson(bean));
        collection.update(query, dbObject, true, false);
    }

    @Override
    public <T> List<T> get(Class<T> beanClass) {
        DBCollection collection = this.getCollection();
        BasicDBObject query = new BasicDBObject();
        query.put(BeanFields.TYPE.id(), beanClass.getName());
        DBCursor cur = collection.find(query);
        List<T> list = new ArrayList<T>();
        while (cur.hasNext()) {
            DBObject dbObj = cur.next();
            list.add(this.converter.bsonToObj(dbObj, beanClass));
        }
        return list;
    }

    @Override
    public void remove(BeanInfo info) {
        if (!info.beanConfig().persist()) {
            return;
        }
        DBCollection collection = this.getCollection();
        BasicDBObject query = new BasicDBObject();
        query.put(BeanFields.TYPE.id(), info.bean().getClass().getName());
        query.put(BeanFields.OBJECT_NAME.id(), info.objectName().toString());
        WriteResult remove = collection.remove(query);
        String error = remove.getError();
        if (error != null) {
            throw new RuntimeException("Error occured while deleting bean [" + info + "] : " + error);
        }
    }

    @Override
    public boolean isConnected() {
        return true;
    }

}
