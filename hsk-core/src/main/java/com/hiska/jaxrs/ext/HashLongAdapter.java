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

import com.hiska.jaxrs.ext.DESConvert;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class HashLongAdapter extends XmlAdapter<String, Long> {
   private final DESConvert convert = DESConvert.getInstance();

   @Override
   public Long unmarshal(String v) throws Exception {
      if (v == null || v.isEmpty()) {
         return null;
      }
      return convert.forceDecode(v, Long::parseLong);
   }

   @Override
   public String marshal(Long v) throws Exception {
      if (v == null) {
         return null;
      }
      return convert.forceEncode(v, String::valueOf);
   }
}