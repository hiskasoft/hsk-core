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

import java.io.Serializable;
import java.util.List;

/**
 * Option Data
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Option implements Serializable {
   private String value;
   private String label;
   private String description;
   private List<Option> children;

   public static Option create(String value) {
      return value == null ? null : create(value, "DEFAULT: " + value);
   }

   public static Option create(String value, String label) {
      return create(value, label, null);
   }

   public static Option create(String value, String label, String description) {
      Option option = new Option();
      option.value = value;
      option.label = label;
      option.description = description;
      return option;
   }
}
