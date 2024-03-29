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

import java.util.*;
import java.util.stream.Stream;
import javax.persistence.*;

/**
 * @author Willyams Yujra
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
   @Override
   public String convertToDatabaseColumn(List<String> paramList) {
      StringBuilder result = new StringBuilder();
      if (paramList != null) {
         paramList.stream()
               .filter(it -> it != null)
               .filter(it -> !it.isEmpty())
               .forEach(it -> {
                  result.append(it).append(";");
               });
      }
      return result.toString();
   }

   @Override
   public List<String> convertToEntityAttribute(String value) {
      List<String> result = new ArrayList<>();
      if (value != null) {
         Stream.of(value.split(";"))
               .filter(it -> it != null)
               .filter(it -> !it.isEmpty())
               .forEach(result::add);
      }
      return result;
   }
}
