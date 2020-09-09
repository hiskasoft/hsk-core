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

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Willyams Yujra
 */
@Converter
public class BooleanConverter implements AttributeConverter<Boolean, String> {
   @Override
   public String convertToDatabaseColumn(Boolean param) {
      return param != null && param ? "T" : "F";
   }

   @Override
   public Boolean convertToEntityAttribute(String valor) {
      return valor != null && "T".equals(valor);
   }
}
