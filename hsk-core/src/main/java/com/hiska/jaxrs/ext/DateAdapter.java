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
package com.hiska.jaxrs.ext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {
   private static Logger LOGGER = Logger.getLogger(DateAdapter.class.getName());
   private final SimpleDateFormat format;

   public static class ISO extends DateAdapter {
      public ISO() {
         // ISO 8601 Extended format
         super("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      }
   }

   public static class DATE extends DateAdapter {
      public DATE() {
         super("dd/MM/yyyy");
      }
   }

   public static class DATETIME extends DateAdapter {
      public DATETIME() {
         super("dd/MM/yyyy HH:mm:ss");
      }
   }

   public static class TIME extends DateAdapter {
      public TIME() {
         super("HH:mm:ss");
      }
   }

   public DateAdapter() {
      this("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
   }

   public DateAdapter(String text) {
      format = new SimpleDateFormat(text);
      format.setTimeZone(TimeZone.getTimeZone("GMT"));
   }

   @Override
   public String marshal(Date d) throws Exception {
      try {
         return format.format(d);
      } catch (Exception e) {
         LOGGER.log(Level.WARNING, "Failed to format date", e);
         return null;
      }
   }

   @Override
   public Date unmarshal(String d) throws Exception {
      if (d == null) {
         return null;
      }
      try {
         return format.parse(d);
      } catch (ParseException e) {
         LOGGER.log(Level.WARNING, "Failed to parse string {0}", d);
         return null;
      }
   }
}
