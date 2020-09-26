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
import lombok.*;

/**
 * Param Data
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"value"})
@NoArgsConstructor
@AllArgsConstructor
public class Param implements Serializable {
   private String value;
   private String label;
   private String description;

   public static Param create(Object valor) {
      return valor == null ? null : create(valor.toString(), "DEFAULT_" + valor);
   }

   public static Param create(String valor, String label) {
      return new Param(valor, label, "DEFAULT_" + valor);
   }

   public static Param create(String valor, String label, String description) {
      return new Param(valor, label, description);
   }

   public static boolean isEquals(String str, Param param) {
      String old = param == null ? null : param.getValue();
      return str != null && str.equals(old);
   }

   public boolean isEquals(String str) {
      return str != null && str.equals(value);
   }

   @Override
   public String toString() {
      return "[" + value + "] " + label;
   }

   public static final Param NONE = create("NONE");
}
