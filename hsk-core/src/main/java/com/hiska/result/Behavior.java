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

import lombok.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Willyams Yujra
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Behavior implements Serializable {
   /**
    * Type Behavior
    */
   private String type;
   /**
    * Action
    */
   private String action;
   /**
    * Parameters
    */
   private final Map<String, String> params = new HashMap<>();

   public void addParam(String name, String value) {
      params.put(name, value);
   }

   public boolean isEquals(String type, String action) {
      return action != null && type != null && action.equals(this.action) && type.equals(this.type);
   }

   public boolean isEquals(String action) {
      return action != null && action.equals(this.action);
   }

   public static Behavior create(String action) {
      return new Behavior("CASE", action);
   }

   public static Behavior create(String type, String action) {
      return new Behavior(type, action);
   }
}
