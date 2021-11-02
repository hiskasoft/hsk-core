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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.annotation.*;
import lombok.*;

/**
 * URL File
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class URLFile implements Serializable {
   private String url;
   private String action;
   private byte[] content;
   private String contentType;
   private static final Pattern DATA = Pattern.compile("^data:([^;]*);([^,]*),(.*)$", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

   public static URLFile of(Object value) {
      URLFile urlFile = new URLFile();
      if (value instanceof String) {
         String string = (String) value;
         final Matcher matcher = DATA.matcher(string);
         if (matcher.find()) {
            urlFile.contentType = matcher.group(1);
            String base = matcher.group(2);
            String data = matcher.group(3);
            if ("BASE64".equalsIgnoreCase(base)) {
               urlFile.content = Base64.getDecoder().decode(data);
            } else {
               System.err.println("Not Support base: " + base);
            }
         } else {
            urlFile.url = string;
         }
      } else if (value instanceof File) {
         try {
            File file = (File) value;
            Path path = file.toPath();
            urlFile.content = Files.readAllBytes(path);
            urlFile.contentType = Files.probeContentType(path);
         } catch (IOException e) {
            urlFile.url = "ERROR";
            urlFile.contentType = "ERROR";
         }
      } else if (value instanceof URL) {
         try {
            URL url = (URL) value;
            URLConnection con = url.openConnection();
            urlFile.contentType = con.getContentType();
            urlFile.content = con.getInputStream().readAllBytes();
         } catch (IOException e) {
            urlFile.url = "ERROR";
            urlFile.contentType = "ERROR";
         }
      } else if (value instanceof byte[]) {
         urlFile.content = (byte[]) value;
         urlFile.contentType = "none";
      }
      return urlFile;
   }

   public static URLFile of(String value, byte[] content, String contentType) {
      URLFile urlFile = new URLFile();
      urlFile.url = value;
      urlFile.content = content;
      urlFile.contentType = contentType;
      return urlFile;
   }

   public static URLFile of(URLFile other) {
      URLFile urlFile = new URLFile();
      urlFile.url = other.getUrl();
      urlFile.content = other.getContent();
      urlFile.contentType = other.getContentType();
      return urlFile;
   }

   public static URLFile of(Option other) {
      URLFile urlFile = new URLFile();
      urlFile.url = other.getValue();
      // urlFile.content = other.getContent();
      // urlFile.contentType = other.getContentType();
      return urlFile;
   }

   public static boolean isEquals(URLFile urlFile, String url) {
      String value = urlFile == null ? null : urlFile.getUrl();
      return url != null && url.equals(value);
   }

   public static boolean isNotIn(URLFile urlFile, String... urls) {
      String value = urlFile == null ? null : urlFile.getUrl();
      for (String str : urls) {
         if (str != null && str.equals(value)) {
            return false;
         }
      }
      return true;
   }

   public static boolean isIn(URLFile urlFile, String... urls) {
      String value = urlFile == null ? null : urlFile.getUrl();
      for (String str : urls) {
         if (str != null && str.equals(value)) {
            return true;
         }
      }
      return false;
   }

   public boolean isEquals(String url) {
      return url != null && url.equals(this.url);
   }

   public boolean isIn(String... urls) {
      for (String str : urls) {
         if (str != null && str.equals(url)) {
            return true;
         }
      }
      return false;
   }

   public boolean isNotIn(String... urls) {
      for (String str : urls) {
         if (str != null && str.equals(url)) {
            return false;
         }
      }
      return true;
   }

   @Override
   public String toString() {
      return "URL:" + url;
   }

   @Override
   public boolean equals(Object o) {
      if (o instanceof URLFile) {
         URLFile param = (URLFile) o;
         return url != null && url.equals(param.url);
      } else if (o instanceof Option) {
         Option option = (Option) o;
         return url != null && url.equals(option.getValue());
      } else if (o instanceof String) {
         String string = (String) o;
         return url != null && url.equals(string);
      }
      return false;
   }

   @Override
   public int hashCode() {
      int hash = 5;
      hash = 61 * hash + Objects.hashCode(url);
      hash = 61 * hash + Objects.hashCode("URL:" + url);
      return hash;
   }

   public void accept(URLFile other) {
      url = other.url;
      content = other.content;
      contentType = other.contentType;
   }

   public static final URLFile NONE = of("NONE", null, "NONE");
}
