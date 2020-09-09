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

import java.util.List;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;

/**
 * Result Filter Response for Request Filter
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString(callSuper = true)
public class ResultFilter<T> extends Result {
   /**
    * Pagination Result
    */
   private Pagination pagination;
   /**
    * List of Object
    */
   private List<T> value;

   public ResultFilter() {
   }

   public ResultFilter(final List<T> value) {
      this.value = value;
   }

   public ResultFilter(final List<T> value, final Result other) {
      super(other);
      this.value = value;
   }

   public ResultFilter(final List<T> value, final Pagination pagination, final Result other) {
      super(other);
      this.value = value;
      this.pagination = pagination;
   }

   public ResultFilter(final Result other) {
      super(other);
   }

   public <R> ResultFilter<R> mapper(Function<T, R> mapper) {
      List<R> list = null;
      if (value != null) {
         list = value.stream().map(mapper).collect(toList());
      }
      return new ResultFilter<>(list, pagination, this);
   }
}
