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

import com.hiska.result.Param;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Willyams Yujra
 */
@Converter(autoApply = true)
public class ParamListConverter implements AttributeConverter<List<Param>, String> {
   @Override
   public String convertToDatabaseColumn(List<Param> paramList) {
      String result = "";
      if (paramList != null) {
         result = paramList.stream()
               .map(it -> it.getValue())
               .collect(Collectors.joining(";"));
      }
      return result;
   }

   @Override
   public List<Param> convertToEntityAttribute(String value) {
      List<Param> result = new ArrayList<>();
      if (value != null) {
         String[] values = value.split(";");
         for (String it : values) {
            Param p = Param.create(it, "DB_" + it);
            result.add(p);
         }
      }
      return result;
   }
}
