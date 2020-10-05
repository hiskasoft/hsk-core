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
package com.hiska.result.converter;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Willyams Yujra
 */
@Converter(autoApply = true)
public class JsonArrayConverter implements AttributeConverter<JsonArray, String> {
   @Override
   public String convertToDatabaseColumn(JsonArray ja) {
      return ja == null ? null : ja.toString();
   }

   @Override
   public JsonArray convertToEntityAttribute(String value) {
      JsonArray ja = Json.createArrayBuilder().build();
      if (value != null) {
         try (JsonReader jsonReader = Json.createReader(new StringReader(value))) {
            ja = jsonReader.readArray();
         }
      }
      return ja;
   }
}
