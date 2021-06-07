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
import java.util.function.*;
import javax.xml.bind.annotation.*;
import lombok.*;

/**
 * Result Item Response for Request Find
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultItem<T> extends Result implements Serializable {
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
