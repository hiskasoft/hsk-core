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

import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.bind.annotation.JsonbTransient;

/**
 * Basic Result Response for Request
 */
@Getter
@Setter
@ToString
public class Result implements Serializable {
   public static final String SUCCESS = "success";
   public static final String ERROR = "error";
   /**
    * String Flag attribute for outcome result
    */
   private String outcome = SUCCESS;
   /**
    * List of Message
    */
   private final List<Message> messages;
   /**
    * Date Response
    */
   private Date at = new Date();

   public Result() {
      messages = new ArrayList<>();
   }

   public Result(final Result other) {
      outcome = other.outcome;
      messages = new ArrayList<>(other.messages);
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

   /**
    * Clear all messages
    */
   public void clearMessage() {
      messages.clear();
   }

   /**
    * Outcome SUCCESS Clear all messages
    */
   public void reset() {
      outcome = SUCCESS;
      messages.clear();
   }

   /**
    * Create a simple Result Object
    *
    * @return
    */
   public Result asResult() {
      return new Result(this);
   }

   /**
    * Create and add a new Message
    *
    * @return
    */
   public MessageBuilder message() {
      return MessageBuilder.create()
            .set(messages::add);
   }

   /**
    * Create and add a new Message Text Message in format XXX-####: Text
    * <p>
    * XXX is a PREFIX
    * </p>
    * <p>
    * #### is a Number
    * </p>
    * <p>
    * 0### is INFO
    * </p>
    * <p>
    * 1### is INFO
    * </p>
    * <p>
    * 2### is WARN
    * </p>
    * <p>
    * 3### is ERROR
    * </p>
    * <p>
    * 9### is FATAL
    * </p>
    *
    * @param  text
    * @return
    */
   public MessageBuilder message(String text) {
      return MessageBuilder.create(text)
            .set(messages::add);
   }

   public void ifSuccess(Runnable caller) {
      if (isSuccess()) {
         caller.run();
      }
   }

   public void ifError(Runnable caller) {
      if (!isSuccess()) {
         caller.run();
      }
   }

   @JsonbTransient
   public boolean isSuccess() {
      return SUCCESS.equalsIgnoreCase(outcome);
   }

   @JsonbTransient
   public boolean isError() {
      return ERROR.equalsIgnoreCase(outcome);
   }

   public void setSuccess(boolean value) {
      outcome = value ? SUCCESS : ERROR;
   }

   public void setError(boolean value) {
      outcome = value ? ERROR : SUCCESS;
   }

   public boolean isOutcome(String name) {
      return name != null && name.equalsIgnoreCase(outcome);
   }

   /**
    * Append Messages and Behaviors to Result Message is included Behavior is
    * included
    *
    * @param result
    */
   public void append(Result result) {
      outcome = result.outcome;
      addAllMessage(result.messages);
   }

   /**
    * Accept Messages and Behaviors to Result Message is replaced Behavior is
    * replaced
    *
    * @param result
    */
   public void accept(Result result) {
      outcome = result.outcome;
      messages.clear();
      addAllMessage(result.messages);
   }

   /**
    * Throw a new ResultException if not success
    *
    * @param message
    */
   public void throwException(String message) {
      if (!isSuccess()) {
         throw new ResultException(message, this);
      }
   }

   private static final Logger LOGGER = Logger.getLogger(Result.class.getName());

   /**
    * Create Log Result
    */
   public void log() {
      log("==");
   }

   /**
    * Create Log Result
    *
    * @param title title log
    */
   public void log(String title) {
      LOGGER.log(Level.INFO, "Result:{0}|Outcome={1}|Messages={2}|This={4}", new Object[]{title, outcome, messages, this});
   }
}
