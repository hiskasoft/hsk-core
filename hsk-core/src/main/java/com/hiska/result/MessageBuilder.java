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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

/**
 * @author Willyams Yujra
 */
public class MessageBuilder {
   private static final Logger LOGGER = Logger.getLogger(MessageBuilder.class.getName());

   public static Message createMessageInfo(String description) {
      return new Message(Message.Level.info, description);
   }

   public static Message createMessageError(String description) {
      return new Message(Message.Level.error, description);
   }

   public static Message createMessageFatal(Throwable root) {
      return MessageBuilder.create("HSK-2000: Exception no controlada")
            .exception(root)
            .get();
   }

   private final Message message;

   public static MessageBuilder create() {
      return new MessageBuilder();
   }

   public static MessageBuilder create(String text) {
      return new MessageBuilder().text(text);
   }

   private MessageBuilder() {
      this.message = new Message();
   }

   public MessageBuilder(Message internal) {
      this.message = internal;
   }

   public MessageBuilder title(String value) {
      message.setTitle(value);
      return this;
   }

   public MessageBuilder level(Message.Level value) {
      message.setLevel(value);
      return this;
   }

   public MessageBuilder info() {
      message.setLevel(Message.Level.info);
      return this;
   }

   public MessageBuilder error() {
      message.setLevel(Message.Level.error);
      return this;
   }

   public MessageBuilder fatal() {
      message.setLevel(Message.Level.fatal);
      return this;
   }

   public MessageBuilder warn() {
      message.setLevel(Message.Level.warn);
      return this;
   }

   public MessageBuilder description(String value) {
      message.setDescription(value);
      return this;
   }

   public MessageBuilder action(String value) {
      message.setAction(value);
      return this;
   }

   public MessageBuilder cause(String value) {
      message.addCause(value);
      return this;
   }

   public MessageBuilder cause(boolean sw, String value) {
      message.addCause(sw, value);
      return this;
   }

   private static final Pattern TXT_CODE = Pattern.compile("([A-Z]{3,5}-[0-9]{3,5}):(.*)", Pattern.DOTALL);

   /**
    * Text Message in format XXX-####: Text
    * XXX is a PREFIX
    * #### is a Number
    * 0### is INFO
    * 1### is INFO
    * 2### is WARN
    * 3### is ERROR
    * 9### is FATAL
    * 
    * @param  text
    * @return
    */
   public MessageBuilder text(String text) {
      if (text != null) {
         Matcher matcher = TXT_CODE.matcher(text);
         if (matcher.find()) {
            String code = matcher.group(1).trim();
            String desc = matcher.group(2).trim();
            message.setCode(code);
            message.setDescription(desc);
            if (code.contains("-0") || code.contains("-1")) {
               message.setLevel(Message.Level.info);
            } else if (code.contains("-2")) {
               message.setLevel(Message.Level.warn);
            } else if (code.contains("-3")) {
               message.setLevel(Message.Level.error);
            } else if (code.contains("-9")) {
               message.setLevel(Message.Level.fatal);
            }
         } else {
            message.setDescription(text);
         }
      }
      return this;
   }

   public MessageBuilder exception(Throwable root) {
      if (root != null) {
         message.addTrace("Type: " + root.getClass().getName());
         message.addTrace("Message: " + root.getMessage());
         for (StackTraceElement it : root.getStackTrace()) {
            String line = format(it);
            if (line != null) {
               message.addTrace(" -" + line);
            }
         }
         exception(root.getCause());
      }
      return this;
   }

   private String format(StackTraceElement ste) {
      if (ste.getFileName() == null || ste.getLineNumber() == -2 || !ste.getClassName().startsWith("com.hiska")) {
         return null;
      }
      return ste.getClassName() + "::" + ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
   }

   public MessageBuilder code(String value) {
      message.setCode(value);
      return this;
   }

   public MessageBuilder set(Consumer<Message> caller) {
      caller.accept(message);
      return this;
   }

   public Message get() {
      return message;
   }

   public boolean isCauseEmpty() {
      return message.isCauseEmpty();
   }

   public boolean isTraceEmpty() {
      return message.isTraceEmpty();
   }

   private final static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
   private final static Validator validator = factory.getValidator();

   public MessageBuilder required(String name, Object bean) {
      message.addCause(bean == null, "El parametro '" + name + "' es requerido");
      return this;
   }

   public MessageBuilder validateRequired(String name, Object bean, String... ignores) {
      return validateRequired(name, bean, Default.class, ignores);
   }

   public MessageBuilder validateRequired(String name, Object bean, Class group, String... ignores) {
      message.addCause(bean == null, "El parametro '" + name + "' es requerido");
      return bean == null ? this : validate(bean, group, ignores);
   }

   public MessageBuilder validate(Object bean, String... ignores) {
      return validate(bean, javax.validation.groups.Default.class, ignores);
   }

   public <T> MessageBuilder validate(T bean, Class group, String... ignores) {
      List<String> names = ignores == null ? Collections.emptyList() : Arrays.asList(ignores);
      Set<ConstraintViolation<T>> violations;
      violations = validator.validate(bean, group);
      for (ConstraintViolation<T> violation : violations) {
         String name = violation.getPropertyPath().toString();
         message.addCause(!names.contains(name), violation.getMessage());
      }
      return this;
   }

   public <T> ResultItem<T> asResultItem() {
      return asResultItem(null);
   }

   public <T> ResultItem<T> asResultItem(T value) {
      ResultItem<T> result = new ResultItem();
      result.addMessage(message);
      result.setValue(value);
      return result;
   }
}
