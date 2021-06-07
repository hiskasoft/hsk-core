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
package com.hiska.result.definition;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Willyams Yujra
 */
public interface Definition<T> {
   public Logger logger = Logger.getLogger(Definition.class.getName());

   public Method getGetter();

   public Method getSetter();

   public default T invokeGetter(Object aInstance) {
      try {
         Method getter = getGetter();
         return (T) getter.invoke(aInstance);
      } catch (Exception e) {
         logger.log(Level.SEVERE, "Error invokeGetter: {0}", e.getMessage());
      }
      return null;
   }

   public default void invokeSetter(Object aInstance, T value) {
      try {
         Method setter = getSetter();
         setter.invoke(aInstance, value);
      } catch (Exception e) {
         logger.log(Level.SEVERE, "Error invokeSetter: {0}", e.getMessage());
      }
   }
}
