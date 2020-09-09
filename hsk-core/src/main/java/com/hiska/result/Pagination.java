/**
 *   _   _ _     _         ____         __ _
 *  | | | (_)___| | ____ _/ ___|  ___  / _| |_
 *  | |_| | / __| |/ / _` \___ \ / _ \| |_| __|
 *  |  _  | \__ \   < (_| |___) | (_) |  _| |_
 *  |_| |_|_|___/_|\_\__,_|____/ \___/|_|  \__|
 *
 *  Copyright © ${project.inceptionYear} HiskaSoft
 *  http://www.hiskasoft.com/licenses/LICENSE-2.0
 */
package com.hiska.result;

/**
 * Pagination Filter
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Pagination {
   public enum Sort {
      asc,
      desc,
      none
   }

   /**
    * Size Block Result
    */
   private int size = 10;
   /**
    * Index
    */
   private int index = 1;
   /**
    * Max Length for index
    */
   private int length = -1;
   /**
    * Count Result
    */
   private int count = 0;
   /**
    * Sort Attribute
    */
   private String attr;
   /**
    * Sort Mode
    */
   private Sort sort;

   public static Pagination create(Pagination value) {
      return new Pagination(value);
   }

   public Pagination() {
   }

   public Pagination(Pagination other) {
      if (other != null) {
         size = other.size;
         index = other.index;
         length = other.length;
         attr = other.attr;
         sort = other.sort;
      }
   }

   public int getIndexFrom() {
      int aux = (index - 1) * size + 1;
      return count == 0 ? 0 : aux;
   }

   public int getIndexTo() {
      int aux = index * size;
      return count == 0 ? 0 : aux < count ? aux : count;
   }

   public boolean hasSort() {
      return sort != null && sort != Sort.none && attr != null && !attr.isEmpty();
   }

   public void clean() {
      count = 0;
      length = -1;
      index = 1;
      size = 10;
   }

   public void reload() {
      count = 0;
      length = -1;
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

   public boolean isSortAsc() {
      return sort == Sort.asc;
   }

   public boolean isSortDesc() {
      return sort == Sort.desc;
   }

   public boolean isSortNone() {
      return sort == Sort.none;
   }

   public void sort(String name) {
      if (name != null && name.equals(attr)) {
         attr = name;
         sort = sort == Sort.asc ? Sort.desc : sort == Sort.desc ? Sort.none : Sort.asc;
      } else if (name != null) {
         attr = name;
         sort = Sort.asc;
      } else {
         attr = null;
         sort = Sort.none;
      }
   }
}
