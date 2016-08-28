package com.jerechua.huey;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.Writer;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.stream.JsonReader;

final class HueProperties {

  // TODO: Make this a flag.
  private static final String DEFAULT_PROPERTIES_PATH = "../properties.json";

  // The JSON serializer to use.
  private static final Gson gson = new GsonBuilder()
     .enableComplexMapKeySerialization()
     .serializeNulls()
     .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
     .setPrettyPrinting()
     .setVersion(1.0)
     .create();

  private final String appName;
  private String userId;

  private HueProperties(String appName, String userId) {
    this.appName = appName;
    this.userId = userId;
  }

  /** Only updates user if user is empty. */
  public void updateUserId(String userId) {
    if (hasUserId()) {
      if (!this.userId.equals(userId)) {
        System.out.println("User IDs do not match!!!");
      }
      return;
    }
    this.userId = userId;
    try {
      writeConfig(this);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getUserId() {
    return userId;
  }

  public boolean hasUserId() {
    return userId != null && !userId.equals("");
  }

  public String getAppName() {
    return appName;
  }

  public String toString() {
    return gson.toJson(this);
  }

  public static HueProperties createOrLoad(String appName) throws IOException {
    File propertiesJson = new File(DEFAULT_PROPERTIES_PATH);
    if (!propertiesJson.exists()) {
      writeConfig(new HueProperties(appName, ""));
    }
    return readConfig(propertiesJson);
  }

  private static HueProperties readConfig(File file) throws IOException {
    try(JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
      return gson.fromJson(reader, HueProperties.class);
    }
  }

  private static void writeConfig(HueProperties properties) throws IOException {

    try(Writer writer =
        new OutputStreamWriter(new FileOutputStream(DEFAULT_PROPERTIES_PATH) , "UTF-8")) {
      gson.toJson(properties, writer);
    }
  }
}