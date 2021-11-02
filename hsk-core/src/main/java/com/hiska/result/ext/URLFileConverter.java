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

import com.hiska.result.URLFile;
import javax.persistence.*;

/**
 * @author Willyams Yujra
 */
@Converter(autoApply = true)
public class URLFileConverter implements AttributeConverter<URLFile, String> {
   @Override
   public String convertToDatabaseColumn(URLFile urlFile) {
      return urlFile == null || urlFile == URLFile.NONE ? null : urlFile.getUrl();
   }

   @Override
   public URLFile convertToEntityAttribute(String value) {
      return value == null || value.isEmpty() ? null : URLFile.of(value);
   }
}
