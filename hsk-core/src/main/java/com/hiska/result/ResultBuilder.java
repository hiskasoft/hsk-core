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
   public static Result successResult(String message) {
      Result result = new Result();
      result.setSuccess(true);
      result.message(message);
      return result;
   }

   public static Result errorResult(String message) {
      Result result = new Result();
      result.setError(true);
      result.message(message);
      return result;
   }

   public static Result notImplementResult() {
      Result result = new Result();
      result.message("APP-9001: Metodo no implementado");
      return result;
   }

   public static ResultItem notImplementResultItem() {
      ResultItem result = new ResultItem();
      result.message("APP-9001: Metodo no implementado");
      return result;
   }

   public static ResultPage notImplementResultPage() {
      ResultPage result = new ResultPage();
      result.message("APP-9001: Metodo no implementado");
      return result;
   }

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

   public ResultBuilder outcome(String outcome) {
      result.setOutcome(outcome);
      return this;
   }

   public ResultBuilder error() {
      result.setError(true);
      return this;
   }

   public ResultBuilder error(boolean error) {
      result.setError(error);
      return this;
   }

   public ResultBuilder success() {
      result.setSuccess(true);
      return this;
   }

   public ResultBuilder success(boolean success) {
      result.setSuccess(success);
      return this;
   }

   public ResultBuilder message(Message message) {
      result.addMessage(message);
      return this;
   }

   public Result get() {
      return result;
   }

   public <T> ResultPage<T> get(List<T> value, Pagination pagination) {
      ResultPage resultPage = new ResultPage<>(result);
      resultPage.setPagination(pagination);
      resultPage.setValue(value);
      return resultPage;
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

   public ResultException getResultException() {
      return new ResultException(result);
   }
}
