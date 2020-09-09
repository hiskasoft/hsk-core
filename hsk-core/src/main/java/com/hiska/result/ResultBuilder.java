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

public class ResultBuilder {
   public static Result createResult(Message message) {
      Result result = new Result();
      result.addMessage(message);
      return result;
   }

   public static <T> ResultItem<T> createResultItem(T value) {
      return new ResultItem<>(value);
   }

   public static ResultBuilder create() {
      return new ResultBuilder();
   }

   public static ResultBuilder create(Message message) {
      return new ResultBuilder().message(message);
   }

   private final Result result;

   private ResultBuilder() {
      result = new Result();
   }

   public ResultBuilder error() {
      result.setSuccess(false);
      return this;
   }

   public ResultBuilder success() {
      result.setSuccess(true);
      return this;
   }

   public ResultBuilder message(Message message) {
      result.addMessage(message);
      return this;
   }
//   public ResultBuilder behavior(Behavior behavior) {
//      result.addBehavior(behavior);
//      return this;
//   }

   public Result get() {
      return result;
   }

   public <T> ResultFilter<T> get(List<T> value, Pagination pagination) {
      ResultFilter resultFilter = new ResultFilter<>(result);
      resultFilter.setPagination(pagination);
      resultFilter.setValue(value);
      return resultFilter;
   }

   public <T> ResultList<T> get(List<T> value) {
      ResultList resultList = new ResultList<>(result);
      resultList.setValue(value);
      return resultList;
   }

   public <T> ResultItem<T> get(T value) {
      ResultItem resultItem = new ResultItem<>(result);
      resultItem.setValue(value);
      return resultItem;
   }
}
