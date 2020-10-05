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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Willyams Yujra
 */
@Converter(autoApply = true)
public class ListConverter implements AttributeConverter<List<String>, String> {
   @Override
   public String convertToDatabaseColumn(List<String> paramList) {
      StringBuilder result = new StringBuilder();
      if (paramList != null) {
         paramList.forEach(v -> {
            result.append(v).append(";");
         });
      }
      return result.toString();
   }

   @Override
   public List<String> convertToEntityAttribute(String value) {
      List<String> result = new ArrayList<>();
      if (value != null) {
         String[] values = value.split(";");
         for (String it : values) {
            if (!it.isEmpty()) {
               result.add(it);
            }
         }
      }
      return result;
   }
}
