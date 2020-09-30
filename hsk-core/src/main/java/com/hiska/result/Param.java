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
import java.util.Objects;
import lombok.*;

/**
 * Param Data
 */
@Getter
@Setter
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
      Param param = new Param();
      param.value = valor;
      param.label = label;
      param.description = description;
      return param;
   }

   public static Param create(Param other) {
      Param param = new Param();
      param.value = other.getValue();
      param.label = other.getLabel();
      param.description = other.getDescription();
      return param;
   }

   public static Param create(Option other) {
      Param param = new Param();
      param.value = other.getValue();
      param.label = other.getLabel();
      param.description = other.getDescription();
      return param;
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

   @Override
   public boolean equals(Object o) {
      if (o instanceof Param) {
         Param param = (Param) o;
         return value != null && value.equals(param.getValue());
      } else if (o instanceof Option) {
         Option option = (Option) o;
         return value != null && value.equals(option.getValue());
      } else if (o instanceof String) {
         String string = (String) o;
         return value != null && value.equals(string);
      }
      return false;
   }

   @Override
   public int hashCode() {
      int hash = 5;
      hash = 61 * hash + Objects.hashCode(this.value);
      return hash;
   }

   public static final Param NONE = create("NONE");
}
