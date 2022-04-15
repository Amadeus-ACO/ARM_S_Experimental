package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Config {
    public static JsonObject load(String name) {
        try {
            JsonReader reader = new JsonReader(new FileReader("resources/" + name + ".json"));
            JsonObject config = new Gson().fromJson(reader, JsonObject.class);
            return config;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
