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
package com.hiska.faces.application;

import java.util.PropertyResourceBundle;

/**
 * @author Willyams Yujra
 */
public class ELI18NBundle {
   private final PropertyResourceBundle bundle;
   private String name = "";

   public ELI18NBundle(PropertyResourceBundle bundle) {
      this.bundle = bundle;
   }

   public void addName(String name) {
      if (this.name.length() > 1) {
         this.name += ".";
      }
      this.name += name;
   }

   public String getName() {
      return name;
   }

   @Override
   public String toString() {
      String value = "???" + name + "???";
      try {
         value = bundle.getString(name);
      } catch (Exception e) {
         e = null;
      }
      return value;
   }
}
