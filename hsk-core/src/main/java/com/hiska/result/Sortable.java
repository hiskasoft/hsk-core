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
import javax.xml.bind.annotation.*;
import lombok.*;

/**
 * Pagination Filter
 */
@Data
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Sortable implements Serializable {
   public enum Type {
      asc,
      desc,
      none
   }

   /**
    * Name Sort
    */
   private String name;
   /**
    * Sort Mode
    */
   private Type type;

   public static Sortable create(Sortable value) {
      return new Sortable(value);
   }

   public Sortable() {
   }

   public Sortable(Sortable other) {
      if (other != null) {
         name = other.name;
         type = other.type;
      }
   }

   public boolean isAsc() {
      return type == Type.asc;
   }

   public boolean isDesc() {
      return type == Type.desc;
   }

   public boolean isNone() {
      return type == Type.none;
   }

   public void sort(String value) {
      if (value != null && value.equals(name)) {
         name = value;
         type = type == Type.asc ? Type.desc : type == Type.desc ? Type.none : Type.asc;
      } else if (name != null) {
         name = value;
         type = Type.asc;
      } else {
         name = null;
         type = Type.none;
      }
   }

   public boolean isValid() {
      return type != null && type != Type.none && name != null && !name.isEmpty();
   }

   public String getTypeString() {
      return type != null ? type.name() : Type.asc.name();
   }
}
