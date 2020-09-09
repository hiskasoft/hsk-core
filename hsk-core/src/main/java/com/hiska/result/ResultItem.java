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
package com.hiska.result;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Result Item Response for Request Find
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString(callSuper = true)
public class ResultItem<T> extends Result {
   /**
    * Object
    */
   private T value;

   public ResultItem() {
   }

   public ResultItem(T value) {
      this.value = value;
   }

   public ResultItem(T value, Result other) {
      super(other);
      this.value = value;
   }

   public <R> ResultItem<R> mapper(Function<T, R> mapper) {
      R other = value == null ? null : mapper.apply(value);
      return new ResultItem(other, this);
   }

   public void ifSuccess(Consumer<T> caller) {
      if (isSuccess()) {
         caller.accept(value);
      }
   }
}
