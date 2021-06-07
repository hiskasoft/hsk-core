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
package com.hiska.result.ext;

import java.io.StringReader;
import javax.json.*;
import javax.persistence.*;

/**
 * @author Willyams Yujra
 */
@Converter(autoApply = true)
public class JsonObjectConverter implements AttributeConverter<JsonObject, String> {
   @Override
   public String convertToDatabaseColumn(JsonObject jo) {
      return jo == null ? null : jo.toString();
   }

   @Override
   public JsonObject convertToEntityAttribute(String value) {
      JsonObject jo = Json.createObjectBuilder().build();
      if (value != null) {
         try (JsonReader jsonReader = Json.createReader(new StringReader(value))) {
            jo = jsonReader.readObject();
         }
      }
      return jo;
   }
}
