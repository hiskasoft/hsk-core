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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Willyams Yujra
 */
@Converter(autoApply = true)
public class ParamListConverter implements AttributeConverter<List<Param>, String> {
   @Override
   public String convertToDatabaseColumn(List<Param> paramList) {
      StringBuilder result = new StringBuilder();
      if (paramList != null) {
         paramList.stream()
               .filter(it -> it != null)
               .map(it -> it.getValue())
               .filter(it -> it != null)
               .forEach(v -> {
                  result.append(v).append(";");
               });
      }
      return result.toString();
   }

   @Override
   public List<Param> convertToEntityAttribute(String value) {
      List<Param> result = new ArrayList<>();
      if (value != null) {
         Stream.of(value.split(";"))
               .filter(it -> it != null)
               .filter(it -> !it.isEmpty())
               .map(it -> Param.of(it, "DB_" + it))
               .forEach(result::add);
      }
      return result;
   }
}
