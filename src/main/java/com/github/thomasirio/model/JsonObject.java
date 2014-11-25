package com.github.thomasirio.model;

import com.google.gson.Gson;

/**
 * Created by pcthomas on 30/10/2014.
 */

public abstract class JsonObject extends Model {

  public String toJson() {
    Gson gson = new Gson();

    return gson.toJson(this);
  }

  public static<Child extends Model> Child fromJson(Class<Child> childClass, String json) {
    Gson gson = new Gson();

    return gson.fromJson(json, childClass);
  }

}
