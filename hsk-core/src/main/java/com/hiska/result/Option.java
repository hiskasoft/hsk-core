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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Option Data
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Option implements Serializable {
   private String value;
   private String label;
   private String description;
   private List<Option> children;

   public static Option of(String value) {
      return value == null ? null : of(value, "DEFAULT: " + value);
   }

   public static Option of(String value, String label) {
      return of(value, label, null);
   }

   public static Option of(String value, String label, String description) {
      Option option = new Option();
      option.value = value;
      option.label = label;
      option.description = description;
      return option;
   }

   public static Option of(Option other) {
      Option option = new Option();
      option.value = other.getValue();
      option.label = other.getLabel();
      option.description = other.getDescription();
      return option;
   }

   public static Option of(Param other) {
      Option option = new Option();
      option.value = other.getValue();
      option.label = other.getLabel();
      option.description = other.getDescription();
      return option;
   }

   public static boolean isEquals(Option option, String str) {
      String old = option == null ? null : option.getValue();
      return str != null && str.equals(old);
   }

   public boolean isEquals(String str) {
      return str != null && str.equals(value);
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

   public static final Option NONE = of("NONE");
}
