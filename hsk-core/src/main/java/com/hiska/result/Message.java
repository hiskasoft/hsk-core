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
import java.util.*;
import javax.xml.bind.annotation.*;
import lombok.*;

/**
 * Message data
 */
@Data
@ToString
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable {
   /**
    * Level Message
    */
   public static enum Level {
      NONE,
      INFO,
      SUCCESS,
      WARNING,
      ERROR,
      FATAL;
   }

   /**
    * Mode Message
    */
   public static enum Mode {
      HIDE,
      ALERT,
      FLASH;
   }

   /**
    * Level Message
    */
   private Mode mode;
   /**
    * Level Message
    */
   private Level level;
   /**
    * Code Message
    */
   private String code;
   /**
    * Title Message
    */
   private String title;
   /**
    * Description Message
    */
   private String description;
   /**
    * Causes Message
    */
   private final List<String> causes = new ArrayList<>();
   /**
    * Action Message
    */
   private String action;
   /**
    * Trace Error
    */
   private final List<String> traces = new ArrayList<>();

   public Message() {
   }

   public Message(Level level, String description) {
      this.level = level;
      this.description = description;
   }

   public void addCause(boolean when, String value) {
      if (when) {
         causes.add(value);
      }
   }

   public void addCause(String value) {
      causes.add(value);
   }

   public boolean isCauseEmpty() {
      return causes.isEmpty();
   }

   public boolean isCauseExist() {
      return !causes.isEmpty();
   }

   public void addTrace(String value) {
      traces.add(value);
   }

   public boolean isTraceEmpty() {
      return traces.isEmpty();
   }

   public boolean isTraceExist() {
      return !traces.isEmpty();
   }

   public void accept(Message other) {
      mode = other.mode;
      level = other.level;
      code = other.code;
      title = other.title;
      description = other.description;
      causes.clear();
      causes.addAll(other.causes);
      traces.clear();
      traces.addAll(other.traces);
   }

   public static Level typeOf(String code) {
      if (code.startsWith("HTTP-1")) {
         return Message.Level.INFO;
      } else if (code.startsWith("HTTP-2")) {
         return Message.Level.SUCCESS;
      } else if (code.startsWith("HTTP-3")) {
         return Message.Level.WARNING;
      } else if (code.startsWith("HTTP-4")) {
         return Message.Level.ERROR;
      } else if (code.startsWith("HTTP-5")) {
         return Message.Level.FATAL;
      } else if (code.contains("-0")) {
         return Message.Level.NONE;
      } else if (code.contains("-1")) {
         return Message.Level.INFO;
      } else if (code.contains("-2")) {
         return Message.Level.SUCCESS;
      } else if (code.contains("-3")) {
         return Message.Level.WARNING;
      } else if (code.contains("-4")) {
         return Message.Level.ERROR;
      } else if (code.contains("-5")) {
         return Message.Level.FATAL;
      } else {
         return Message.Level.NONE;
      }
   }
}
