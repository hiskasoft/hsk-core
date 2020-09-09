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

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;

/**
 * Result List Response for Request List
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString(callSuper = true)
public class ResultList<T> extends Result {
   /**
    * List of Object
    */
   private List<T> value;

   public ResultList() {
   }

   public ResultList(List<T> value) {
      this.value = value;
   }

   public ResultList(List<T> value, Result other) {
      super(other);
      this.value = value;
   }

   public ResultList(Result other) {
      super(other);
   }

   public <R extends Serializable> ResultList<R> mapper(Function<T, R> mapper) {
      List<R> list = null;
      if (value != null) {
         list = value.stream().map(mapper).collect(toList());
      }
      return new ResultList<>(list, this);
   }

   public void ifSuccess(Consumer<List<T>> caller) {
      if (isSuccess()) {
         caller.accept(value);
      }
   }
}
