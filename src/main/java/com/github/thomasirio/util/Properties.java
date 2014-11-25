package com.github.thomasirio.util;

/**
 * Created by pcnicole on 30/10/2014.
 */

public class Properties {

    //variabili di ambiente
  public static final String DB_USERNAME = "DB_USERNAME";
  public static final String DB_PASSWORD = "DB_PASSWORD";
  public static final String DB_URL = "DB_URL";
  public static final String TEST_ENV = "TEST_ENV";

  public static String get(String variable) {
  //prende dal sistema una variabile d'ambiente->getenv
    return System.getenv(variable);
  }

}
