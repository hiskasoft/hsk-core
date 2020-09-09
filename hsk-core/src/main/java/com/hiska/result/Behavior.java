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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Willyams Yujra
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Behavior {
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
}
