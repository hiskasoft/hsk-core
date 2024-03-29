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
package com.hiska.jaxrs.ext;

import com.hiska.jaxrs.HashLong;
import javax.ws.rs.ext.ParamConverter;

public class HashLongConverter implements ParamConverter<HashLong> {
   private final DESConvert convert = DESConvert.getInstance();

   @Override
   public HashLong fromString(String param) {
      if (param == null) {
         return null;
      }
      Long value = convert.forceDecode(param, Long::parseLong);
      return new HashLong(value);
   }

   @Override
   public String toString(HashLong param) {
      if (param == null) {
         return null;
      }
      Long value = param.getValue();
      return convert.forceEncode(value, String::valueOf);
   }
}
