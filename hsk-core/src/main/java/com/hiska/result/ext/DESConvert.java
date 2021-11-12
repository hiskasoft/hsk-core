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

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.Function;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESConvert {
   private static DESConvert instance;

   public static DESConvert getInstance() {
      if (instance == null) {
         instance = new DESConvert("cY97Os1bwi8ksGl");
      }
      return instance;
   }

   private Charset UTF8 = Charset.forName("UTF-8");
   private SecretKey SECRET_KEY;

   private DESConvert(String seed) {
      try {
         seed = seed + System.getProperty("SEED_KEY", ":cY97Os1bwi+o1cArTcm/eQ==");
         MessageDigest md = MessageDigest.getInstance("MD5");
         byte[] digestOfPassword = md.digest(seed.getBytes(UTF8));
         byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
         SECRET_KEY = new SecretKeySpec(keyBytes, "DESede");
      } catch (NoSuchAlgorithmException ex) {
         throw new RuntimeException("Error StartDESBase64", ex);
      }
   }

   public <T> String encode(T original, Function<T, String> toString) throws Exception {
      if (original == null) {
         return null;
      }
      try {
         String value = toString.apply(original);
         Cipher cipher = Cipher.getInstance("DESede");
         cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
         byte[] valueBytes = value.getBytes(UTF8);
         valueBytes = cipher.doFinal(valueBytes);
         return Base64.getEncoder().encodeToString(valueBytes);
      } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
         throw new Exception("Error al ENCRYPT_MODE", ex);
      }
   }

   public <T> T decode(String value, Function<String, T> toObject) throws Exception {
      if (value == null) {
         return null;
      }
      try {
         Cipher decipher = Cipher.getInstance("DESede");
         decipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
         byte[] valueBytes = Base64.getDecoder().decode(value);
         valueBytes = decipher.doFinal(valueBytes);
         String valueText = new String(valueBytes, UTF8);
         return toObject.apply(valueText);
      } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
         throw new Exception("Error al DECRYPT_MODE", ex);
      }
   }

   public <T> T forceDecode(String value, Function<String, T> toObject) {
      try {
         return decode(value, toObject);
      } catch (Exception e) {
         return null;
      }
   }

   public <T> String forceEncode(T original, Function<T, String> toString) {
      try {
         return encode(original, toString);
      } catch (Exception e) {
         return null;
      }
   }
}