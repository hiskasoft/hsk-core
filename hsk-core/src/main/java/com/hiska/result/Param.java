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

/**
 * Trust Data
 */
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(of = {"value"})
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
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

   @Override
   public String toString() {
      return value + ": " + label;
   }
}
