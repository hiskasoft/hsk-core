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
package com.hiska.faces.bean;

import java.util.LinkedList;

/**
 * @author Willyams Yujra
 */
public class LimitedQueue<E> extends LinkedList<E> {
   private final int limit;

   public LimitedQueue(int limit) {
      this.limit = limit;
   }

   @Override
   public boolean add(E o) {
      boolean added = contains(o);
      if (added) {
         return false;
      }
      super.addFirst(o);
      while (size() > limit) {
         super.remove();
      }
      return added;
   }
}
