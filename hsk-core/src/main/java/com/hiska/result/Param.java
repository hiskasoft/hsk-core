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
import java.util.Objects;
import javax.xml.bind.annotation.*;
import lombok.*;

/**
 * Param Data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Param implements Serializable {
   private String value;
   private String label;
   private boolean disabled;
   private String description;

   public static Param of(Object valor) {
      return valor == null ? null : of(valor.toString(), "DEFAULT_" + valor);
   }

   public static Param of(String valor, String label) {
      return new Param(valor, label, false, "DEFAULT_" + valor);
   }

   public static Param of(String valor, String label, String description) {
      Param param = new Param();
      param.value = valor;
      param.label = label;
      param.description = description;
      return param;
   }

   public static Param of(String valor, String label, boolean disabled, String description) {
      Param param = new Param();
      param.value = valor;
      param.label = label;
      param.disabled = disabled;
      param.description = description;
      return param;
   }

   public static Param of(Param other) {
      Param param = new Param();
      param.value = other.value;
      param.label = other.label;
      param.disabled = other.disabled;
      param.description = other.description;
      return param;
   }

   public static Param of(Option other) {
      Param param = new Param();
      param.value = other.getValue();
      param.label = other.getLabel();
      param.disabled = other.isDisabled();
      param.description = other.getDescription();
      return param;
   }

   public static String asValue(Param param) {
      return param == null ? null : param.getValue();
   }

   public static String asLabel(Param param) {
      return param == null ? null : param.getLabel();
   }

   public static String asDescription(Param param) {
      return param == null ? null : param.getDescription();
   }

   public static boolean isEquals(Param param, String str) {
      String value = param == null ? null : param.getValue();
      return str != null && str.equals(value);
   }

   public static boolean isNotIn(Param param, String... strs) {
      String value = param == null ? null : param.getValue();
      for (String str : strs) {
         if (str != null && str.equals(value)) {
            return false;
         }
      }
      return true;
   }

   public static boolean isIn(Param param, String... strs) {
      String value = param == null ? null : param.getValue();
      for (String str : strs) {
         if (str != null && str.equals(value)) {
            return true;
         }
      }
      return false;
   }

   public static boolean isNotInEnabled(Param param, String... strs) {
      return isNotIn(param, strs) && !param.disabled;
   }

   public static boolean isInEnabled(Param param, String... strs) {
      return isIn(param, strs) && !param.disabled;
   }

   public boolean isEquals(String str) {
      return str != null && str.equals(value);
   }

   public boolean isIn(String... strs) {
      for (String str : strs) {
         if (str != null && str.equals(value)) {
            return true;
         }
      }
      return false;
   }

   public boolean isNotIn(String... strs) {
      for (String str : strs) {
         if (str != null && str.equals(value)) {
            return false;
         }
      }
      return true;
   }

   @Override
   public String toString() {
      return "[" + value + "] " + label;
   }

   @Override
   public boolean equals(Object o) {
      if (o instanceof Param) {
         Param param = (Param) o;
         return value != null && value.equals(param.value);
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
      hash = 61 * hash + Objects.hashCode(value);
      hash = 61 * hash + Objects.hashCode("PARAM:" + value);
      return hash;
   }

   public void accept(Param other) {
      value = other.value;
      label = other.label;
      disabled = other.disabled;
      description = other.description;
   }

   public static final Param NONE = of("NONE");
   public static final List<Param> NONES = List.of(NONE);
}
