/**
 *   _   _ _     _         ____         __ _
 *  | | | (_)___| | ____ _/ ___|  ___  / _| |_
 *  | |_| | / __| |/ / _` \___ \ / _ \| |_| __|
 *  |  _  | \__ \   < (_| |___) | (_) |  _| |_
 *  |_| |_|_|___/_|\_\__,_|____/ \___/|_|  \__|
 *
 *  Copyright © 2020 HiskaSoft
 *  http://www.hiskasoft.com/licenses/LICENSE-2.0
 */
package com.hiska.result;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = false, exclude = {})
public class JsonData {
   private static Jsonb jsonb = JsonbBuilder.create();

   public static void setConfig(JsonbConfig config) {
      jsonb = JsonbBuilder.create(config);
   }

   private String value;

   public JsonData() {
   }

   public <T> void setObject(T object) {
      value = jsonb.toJson(object);
   }

   public <T> T getObject(Class<T> aClass) {
      return jsonb.fromJson(value, aClass);
   }

   public static JsonData ofValue(String value) {
      JsonData jd = new JsonData();
      jd.setValue(value);
      return jd;
   }

   public static JsonData ofObject(Object value) {
      JsonData jd = new JsonData();
      jd.setObject(value);
      return jd;
   }
}
