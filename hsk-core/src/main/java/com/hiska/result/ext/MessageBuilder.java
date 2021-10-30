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
package com.hiska.result.ext;

import com.hiska.result.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.*;
import javax.persistence.OptimisticLockException;
import javax.validation.*;
import javax.validation.groups.Default;
import lombok.ToString;

/**
 * @author Willyams Yujra
 */
@ToString
public class MessageBuilder {
   public static Message createMessageInfo(String description) {
      return new Message(Message.Level.INFO, description);
   }

   public static Message createMessageSuccess(String description) {
      return new Message(Message.Level.SUCCESS, description);
   }

   public static Message createMessageWarning(String description) {
      return new Message(Message.Level.WARNING, description);
   }

   public static Message createMessageError(String description) {
      return new Message(Message.Level.ERROR, description);
   }

   public static Message createMessageFatal(String description) {
      return new Message(Message.Level.FATAL, description);
   }

   public static Message createMessageFatal(Throwable root) {
      return MessageBuilder.create("APP-5000: Exception no controlada")
            .description("Ha ocurrido una excepcion no controlada en el servidor")
            .exception(root)
            .get();
   }

   private final Message message;

   public static MessageBuilder create() {
      return new MessageBuilder();
   }

   public static MessageBuilder create(Message internal) {
      return new MessageBuilder(internal);
   }

   public static MessageBuilder create(String text) {
      return new MessageBuilder().text(text);
   }

   public static MessageBuilder createPong(String name) {
      return new MessageBuilder().text("APP-2000: Pong")
            .description("Pong service name: " + name);
   }

   private MessageBuilder() {
      message = new Message();
   }

   public MessageBuilder(Message internal) {
      message = internal;
   }

   public MessageBuilder accept(MessageException e) {
      message.accept(e.get());
      return this;
   }

   public MessageBuilder accept(Message m) {
      message.accept(m);
      return this;
   }

   public MessageBuilder title(String value) {
      message.setTitle(value);
      return this;
   }

   public MessageBuilder code(String value) {
      message.setCode(value);
      return this;
   }

   public MessageBuilder codeLevel(String value) {
      message.setCode(value);
      Message.Level level = Message.typeOf(value);
      message.setLevel(level);
      return this;
   }

   public MessageBuilder level(Message.Level value) {
      message.setLevel(value);
      return this;
   }

   public MessageBuilder info() {
      message.setLevel(Message.Level.INFO);
      return this;
   }

   public MessageBuilder success() {
      message.setLevel(Message.Level.SUCCESS);
      return this;
   }

   public MessageBuilder warning() {
      message.setLevel(Message.Level.WARNING);
      return this;
   }

   public MessageBuilder error() {
      message.setLevel(Message.Level.ERROR);
      return this;
   }

   public MessageBuilder fatal() {
      message.setLevel(Message.Level.FATAL);
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

   public MessageBuilder cause(Collection<String> value) {
      message.addAllCause(value);
      return this;
   }

   public MessageBuilder modeHide() {
      message.setMode(Message.Mode.HIDE);
      return this;
   }

   public MessageBuilder modeAlert() {
      message.setMode(Message.Mode.ALERT);
      return this;
   }

   public MessageBuilder modeFlash() {
      message.setMode(Message.Mode.FLASH);
      return this;
   }

   public MessageBuilder mode(Message.Mode mode) {
      message.setMode(mode);
      return this;
   }

   public MessageBuilder trace(String value) {
      message.addTrace(value);
      return this;
   }

   public MessageBuilder trace(String name, Object value) {
      String string = formatName(name) + " : " + (value == null ? "NULL" : value.toString());
      message.addTrace(string);
      return this;
   }

   public MessageBuilder trace(Collection<String> value) {
      message.addAllTrace(value);
      return this;
   }

   private String formatName(String name) {
      if (name != null && name.length() < 10) {
         name = (name + "          ").substring(0, 10);
      }
      return name;
   }

   private static final Pattern CODE_MESSAGE = Pattern.compile("([A-Z]{2,5}-[0-9]{3,5}):(.*)", Pattern.DOTALL);

   /**
    * Text Message in format XXX-####: Text
    * <p>
    * XXX is a PREFIX
    * </p>
    * <p>
    * #### is a Number
    * </p>
    * <p>
    * 0### is NONE
    * </p>
    * <p>
    * 1### is INFO
    * </p>
    * <p>
    * 2### is SUCCESS
    * </p>
    * <p>
    * 3### is WARNING
    * </p>
    * <p>
    * 4### is ERROR
    * </p>
    * <p>
    * 5### is FATAL
    * </p>
    *
    * @param  text
    * @return
    */
   public MessageBuilder text(String text) {
      if (text != null) {
         Matcher matcher = CODE_MESSAGE.matcher(text);
         if (matcher.find()) {
            String code = matcher.group(1).trim();
            String desc = matcher.group(2).trim();
            Message.Level level = Message.typeOf(code);
            message.setMode(Message.Mode.FLASH);
            message.setLevel(level);
            message.setCode(code);
            message.setTitle(desc);
         } else {
            message.setTitle(text);
         }
      }
      return this;
   }

   public MessageBuilder exception(Throwable root) {
      if (root != null) {
         if (root instanceof OptimisticLockException) {
            message.addCause("Un registro fue modificado o eliminado por otra transaccion");
         }
         trace("Type", root.getClass().getName());
         trace("Message", root.getMessage());
         for (StackTraceElement it : root.getStackTrace()) {
            String line = format(it);
            if (line != null) {
               trace(" -" + line);
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

   public MessageBuilder set(Consumer<Message> caller) {
      caller.accept(message);
      return this;
   }

   public Message get() {
      return message;
   }

   public MessageException asException() {
      return new MessageException(message);
   }

   public void throwException() throws MessageException {
      if (message.isCauseExist()) {
         throw new MessageException(message);
      }
   }

   public boolean isCauseEmpty() {
      return message.isCauseEmpty();
   }

   public boolean isCauseExist() {
      return message.isCauseExist();
   }

   public boolean isTraceEmpty() {
      return message.isTraceEmpty();
   }

   public boolean isTraceExist() {
      return message.isTraceExist();
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

   public ResultBuilder asResultBuilder() {
      return ResultBuilder.create(message);
   }

   public Result asResult() {
      Result result = new Result();
      result.addMessage(message);
      result.setOutcome(getOutcome());
      return result;
   }

   public <T> ResultItem<T> asResultItem() {
      return asResultItem(null);
   }

   public <T> ResultItem<T> asResultItem(T value) {
      ResultItem<T> result = new ResultItem();
      result.addMessage(message);
      result.setOutcome(getOutcome());
      result.setValue(value);
      return result;
   }

   public <T> ResultList<T> asResultList() {
      return asResultList(null);
   }

   public <T> ResultList<T> asResultList(List<T> value) {
      ResultList<T> result = new ResultList();
      result.addMessage(message);
      result.setOutcome(getOutcome());
      result.setValue(value);
      return result;
   }

   public ResultException asResultException() {
      return new ResultException(asResult());
   }

   private String getOutcome() {
      Message.Level level = message.getLevel();
      return level == Message.Level.SUCCESS ? "success" : "error";
   }
}
