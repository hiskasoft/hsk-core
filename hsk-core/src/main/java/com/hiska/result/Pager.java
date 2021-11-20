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
import javax.xml.bind.annotation.*;
import lombok.*;

/**
 * Pager Filter
 */
@Data
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Pager implements Serializable {
   /**
    * Size Block Result
    */
   private int size = 15;
   /**
    * Page Index
    */
   private int index = 1;
   /**
    * Max Pages for page index
    */
   private int length = -1;
   /**
    * Count Result
    */
   private int count = 0;
   /**
    * From Index
    */
   private int indexFrom = 0;
   /**
    * To Index
    */
   private int indexTo = 0;

   public static Pager create(Pager value) {
      return new Pager(value);
   }

   public Pager() {
   }

   public Pager(Pager other) {
      if (other != null) {
         size = other.size;
         index = other.index;
         length = other.length;
         count = other.count;
      }
   }

   public int getIndexFrom() {
      int aux = (index - 1) * size + 1;
      indexFrom = count == 0 ? 0 : aux;
      return indexFrom;
   }

   public int getIndexTo() {
      int aux = index * size;
      indexTo = count == 0 ? 0 : aux < count ? aux : count;
      return indexTo;
   }

   public void clean() {
      count = 0;
      length = -1;
      index = 1;
      size = 10;
      indexFrom = 0;
      indexTo = 0;
   }

   public void reload() {
      count = 0;
      length = -1;
      indexFrom = 0;
      indexTo = 0;
   }

   public void setIndex(int value) {
      index = value <= 0 ? 1 : value;
   }

   public void setCount(int value) {
      count = value < 0 ? 0 : value;
   }

   public void setLength(int value) {
      length = value < 0 ? 0 : value;
   }

   public void setSize(int value) {
      size = value < 5 ? 5 : value;
   }
}
