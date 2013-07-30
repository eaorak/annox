package com.adenon.sp.administration.persist.mongo;

import org.bson.BSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.util.JSON;

public class JsonConverter {

    private Gson gson;

    public JsonConverter() {
        this.gson = new GsonBuilder().setExclusionStrategies(new GsonExcluder()).create();
    }

    public BSONObject objToBson(Object obj) {
        String json = this.gson.toJson(obj);
        return (BSONObject) JSON.parse(json);
    }

    public <T> T bsonToObj(Object obj,
                           Class<T> clazz) {
        String json = JSON.serialize(obj);
        return this.gson.fromJson(json, clazz);
    }

}
