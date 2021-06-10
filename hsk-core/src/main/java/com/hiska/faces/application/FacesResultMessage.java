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
package com.hiska.faces.application;

import com.hiska.result.Message;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.faces.application.FacesMessage;

/**
 * @author Willyams Yujra
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class FacesResultMessage extends FacesMessage {
   private transient String title;
   private transient String code;
   private transient String description;
   private transient List<String> causes;
   private transient List<String> traces;
   private transient String action;
   private transient String date;
   private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   public FacesResultMessage() {
   }

   public FacesResultMessage(FacesMessage other) {
      if (other instanceof FacesResultMessage) {
         FacesResultMessage message = (FacesResultMessage) other;
         title = message.getTitle();
         code = message.getCode();
         description = message.getDescription();
         causes = message.getCauses();
         traces = message.getTraces();
         action = message.getAction();
         date = message.getDate();
         setSeverity(message.getSeverity());
      } else {
         description = other.getDetail();
         setSeverity(other.getSeverity());
      }
   }

   public FacesResultMessage(Message message) {
      title = message.getTitle();
      code = message.getCode();
      description = message.getDescription();
      causes = message.getCauses();
      traces = message.getTraces();
      action = message.getAction();
      date = format.format(new Date());
      Message.Level level = message.getLevel();
      Severity severity = level == Message.Level.FATAL ? FacesMessage.SEVERITY_FATAL
            : level == Message.Level.ERROR ? FacesMessage.SEVERITY_ERROR
                  : level == Message.Level.SUCCESS ? FacesMessage.SEVERITY_INFO
                        : level == Message.Level.INFO ? FacesMessage.SEVERITY_INFO
                              : level == Message.Level.WARNING ? FacesMessage.SEVERITY_WARN : null;
      setSeverity(severity);
   }

   @Override
   public String getSummary() {
      return title == null ? "Servidor:" : title;
   }

   @Override
   public String getDetail() {
      return (code == null ? "" : code) + ": " + description;
   }

   public boolean isDescriptionEmpty() {
      return description != null;
   }

   public boolean isCodeEmpty() {
      return code != null;
   }

   public boolean isActionEmpty() {
      return action != null;
   }

   public boolean isCauseEmpty() {
      return causes != null && !causes.isEmpty();
   }

   public boolean isTraceEmpty() {
      return traces != null && !traces.isEmpty();
   }

   public String getIconClass() {
      Severity severity = getSeverity();
      if (severity == FacesMessage.SEVERITY_INFO) {
         return "fa fa-info-circle";
      } else if (severity == FacesMessage.SEVERITY_WARN) {
         return "fa fa-exclamation-triangle";
      } else if (severity == FacesMessage.SEVERITY_ERROR) {
         return "fa fa-times-circle";
      } else if (severity == FacesMessage.SEVERITY_FATAL) {
         return "fa fa-bug";
      }
      return "fa fa-check";
   }

   public String getLevelClass() {
      Severity severity = getSeverity();
      if (severity == FacesMessage.SEVERITY_INFO) {
         return "info";
      } else if (severity == FacesMessage.SEVERITY_WARN) {
         return "warn";
      } else if (severity == FacesMessage.SEVERITY_ERROR) {
         return "danger";
      } else if (severity == FacesMessage.SEVERITY_FATAL) {
         return "dark";
      }
      return "primary";
   }
}
