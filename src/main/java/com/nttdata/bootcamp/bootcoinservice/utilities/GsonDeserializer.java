package com.nttdata.bootcamp.bootcoinservice.utilities;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;

public class GsonDeserializer<T> implements Deserializer<T> {

    private final Gson gson;
    private final Class<T> targetType;

    public GsonDeserializer(Class<T> targetType) {
        this.gson = new Gson();
        this.targetType = targetType;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return gson.fromJson(new String(data), targetType);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON data: " + e.getMessage(), e);
        }
    }
}


