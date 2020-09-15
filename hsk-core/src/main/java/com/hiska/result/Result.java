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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Basic Result Response for Request
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Result implements Serializable {
   /**
    * Flag attribute for error result
    */
   private boolean success = true;
   /**
    * List of Message
    */
   private List<Message> messages = new ArrayList<>();
   /**
    * List of Behavior
    */
   private List<Behavior> behaviors = new ArrayList<>();

   public Result() {
   }

   public Result(final Result other) {
      success = other.success;
      messages.addAll(other.messages);
   }

   public void addMessage(final Message message) {
      if (message != null) {
         messages.add(message);
      }
   }

   public void addAllMessage(final Collection<Message> allMessage) {
      if (allMessage != null) {
         allMessage.forEach(this::addMessage);
      }
   }

   public void clearMessage() {
      messages.clear();
   }

   public void addBehavior(final Behavior behavior) {
      if (behavior != null) {
         behaviors.add(behavior);
      }
   }

   public void addAllBehavior(final Collection<Behavior> allBehavior) {
      if (allBehavior != null) {
         allBehavior.forEach(this::addBehavior);
      }
   }

   public void clearBehavior() {
      behaviors.clear();
   }

   public Result asResult() {
      return new Result(this);
   }

   public MessageBuilder message() {
      return MessageBuilder.create()
            .set(messages::add);
   }

   public MessageBuilder message(String text) {
      return MessageBuilder.create(text)
            .set(messages::add);
   }

   public void ifSuccess(Runnable caller) {
      if (success) {
         caller.run();
      }
   }

   public void ifError(Runnable caller) {
      if (!success) {
         caller.run();
      }
   }

   public boolean isError() {
      return !success;
   }

   public void append(Result result) {
      success = result.success;
      addAllMessage(result.messages);
      addAllBehavior(result.behaviors);
   }

   public void accept(Result result) {
      success = result.success;
      messages.clear();
      behaviors.clear();
      addAllMessage(result.messages);
      addAllBehavior(result.behaviors);
   }

   public void throwException(String message) {
      if (success == false) {
         throw new ResultException(message, this);
      }
   }
}
