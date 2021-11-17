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

import com.hiska.result.JsonData;
import javax.persistence.*;

@Converter(autoApply = true)
public class JsonDataConverter implements AttributeConverter<JsonData, String> {
   @Override
   public String convertToDatabaseColumn(JsonData json) {
      if (json != null) {
         return json.getValue();
      }
      return null;
   }

   @Override
   public JsonData convertToEntityAttribute(String valor) {
      JsonData jd = new JsonData();
      jd.setValue(valor);
      return jd;
   }
}
