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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiska.result.definition;

import java.lang.reflect.Method;

/**
 * @author willyams yujra
 */
public interface Definition<T> {
   public Method getMethod();

   public default T invokeMethod(Object aInstance) {
      Method method = getMethod();
      if (method == null) {
         return null;
      }
      try {
         return (T) method.invoke(aInstance);
      } catch (Exception e) {
         e = null;
      }
      return null;
   }
}
