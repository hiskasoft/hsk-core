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
import lombok.*;

/**
 * @author Willyams Yujra
 */
@Getter
@Setter
@ToString(exclude = {"content"})
public class Document implements Serializable {
   @ToString
   public static enum Type {
      TXT("text/plain", true),
      CSV("text/csv", true),
      XML("text/xml", true),
      HTM("text/htm", true),
      HTML("text/html", true),
      XHTML("text/xhtml", true),
      PNG("image/png", false),
      JPG("image/jpge", false),
      PDF("application/pdf", false),
      ODT("application/vnd.oasis.opendocument.text", false),
      ODS("application/vnd.oasis.opendocument.spreadsheet", false),
      XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", false),
      DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", false);

      @Getter
      private final String mimeType;
      @Getter
      private final boolean plain;

      Type(String mimeType, boolean plain) {
         this.mimeType = mimeType;
         this.plain = plain;
      }

      public boolean stringEquals(String value) {
         return name().equalsIgnoreCase(value) || mimeType.equalsIgnoreCase(value);
      }

      public static Type valueOfString(String value) {
         for (Type t : Type.values()) {
            if (t.stringEquals(value)) {
               return t;
            }
         }
         return null;
      }
   }

   private String fileName;
   private String encode;
   private Type type;
   private byte[] content;

   public boolean isContentPlain() {
      return type != null && type.isPlain();
   }

   /**
    * ISO Content MimeType
    * 
    * @return
    */
   public String getContentMimeType() {
      return type != null ? type.getMimeType() : "";
   }

   /**
    * Length content
    * 
    * @return
    */
   public int getContentLength() {
      return content == null ? 0 : content.length;
   }

   /**
    * Detect MimeType Support
    * 
    * @param value
    */
   public void setTypeString(String value) {
      type = Type.valueOfString(value);
   }
}
