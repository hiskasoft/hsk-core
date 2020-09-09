/**
 *   _   _ _     _         ____         __ _
 *  | | | (_)___| | ____ _/ ___|  ___  / _| |_
 *  | |_| | / __| |/ / _` \___ \ / _ \| |_| __|
 *  |  _  | \__ \   < (_| |___) | (_) |  _| |_
 *  |_| |_|_|___/_|\_\__,_|____/ \___/|_|  \__|
 *
 *  Copyright © ${project.inceptionYear} HiskaSoft
 *  http://www.hiskasoft.com/licenses/LICENSE-2.0
 */
package com.hiska.result;

/**
 * Trust Data
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
@lombok.EqualsAndHashCode(of = {"value"})
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class Domain {
   private String code;
   private String classifier;
   private String value;

   public static Domain create(String code) {
      return code == null ? null : create(code, "NONE");
   }

   public static Domain create(String code, String classifier) {
      return new Domain(code, classifier, null);
   }
}
