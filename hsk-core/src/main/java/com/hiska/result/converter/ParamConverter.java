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
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Willyams Yujra
 */
@Converter(autoApply = true)
public class ParamConverter implements AttributeConverter<Param, String> {
   @Override
   public String convertToDatabaseColumn(Param param) {
      return param == null ? null : param.getValue();
   }

   @Override
   public Param convertToEntityAttribute(String valor) {
      return valor == null ? null : new Param(valor, "DB: " + valor, "DB: " + valor);
   }
}
