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
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;

/**
 * Message data
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Message implements Serializable {
   /**
    * Level Message
    */
   public static enum Level {
      info,
      warn,
      error,
      fatal
   }

   /**
    * Level Message
    */
   private Level level = Level.info;
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
   /**
    * Hide
    */
   private boolean hide;

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

   @JsonbTransient
   public boolean isCauseEmpty() {
      return causes.isEmpty();
   }

   @JsonbTransient
   public boolean isCauseExist() {
      return !causes.isEmpty();
   }

   public void addTrace(String value) {
      traces.add(value);
   }

   @JsonbTransient
   public boolean isTraceEmpty() {
      return traces.isEmpty();
   }

   @JsonbTransient
   public boolean isTraceExist() {
      return !traces.isEmpty();
   }

   public void accept(Message other) {
      level = other.level;
      code = other.code;
      title = other.title;
      description = other.description;
      causes.clear();
      causes.addAll(other.causes);
      traces.clear();
      traces.addAll(other.traces);
   }
}
