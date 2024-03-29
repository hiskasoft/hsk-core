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
import java.util.List;
import javax.xml.bind.annotation.*;
import lombok.*;

/**
 * Filter Element
 */
@Data
@ToString
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class FilterEntry<T> implements Serializable {
   public static enum Operator {
      none("", -1),
      eq("=", 1),
      neq("!=", 1),
      lt("<", 1),
      lte("<=", 1),
      gt(">", 1),
      gte(">=", 1),
      like("like", 1),
      bw("between", 2),
      isNull("is null", 0),
      notNull("is not null", 0),
      in("in", 99);

      private final String value;
      private final int params;

      Operator(String value, int params) {
         this.value = value;
         this.params = params;
      }

      public int params() {
         return params;
      }

      public String alias() {
         return value;
      }

      public boolean ignore() {
         return params == -1;
      }
   }

   /**
    * Expression Filter
    */
   private Operator oper;
   /**
    * Value Filter
    */
   private T value;
   /**
    * Other Value filter
    */
   private T other;
   /**
    * Other Values filter
    */
   private List<T> values;

   public FilterEntry() {
   }

   public FilterEntry(Operator oper) {
      this.oper = oper;
   }

   public FilterEntry(T value) {
      this(value, Operator.eq);
   }

   public FilterEntry(T value, Operator oper) {
      this.value = value;
      this.oper = oper;
   }

   public FilterEntry(T value, T other) {
      this.value = value;
      this.other = other;
      this.oper = Operator.bw;
   }

   public FilterEntry(List<T> values) {
      this.values = values;
      this.oper = Operator.in;
   }

   public Operator getOper() {
      if (oper == null) {
         oper = Operator.none;
      }
      return oper;
   }
//    public boolean isWithParam() {
//        return expr != null && expr.isWithParam();
//    }

   public boolean isWithValue() {
      return oper != null && oper.params() >= 1;
   }

   public boolean isWithOther() {
      return oper != null && oper.params() == 2;
   }

   public boolean isWithList() {
      return oper != null && oper.params() == 99;
   }

   public boolean isIgnore() {
      return oper != null && oper.ignore();
   }

   public int getSizeValues() {
      return values == null ? 0 : values.size();
   }

   public static <T> FilterEntry<T> of(T value) {
      return new FilterEntry<>(value);
   }

   public static <T> FilterEntry<T> of(T value, T other) {
      return new FilterEntry<>(value, other);
   }

   public static <T> FilterEntry<T> of(List<T> values) {
      return new FilterEntry<>(values);
   }

   public static FilterEntry IS_NULL = new FilterEntry(Operator.isNull);
   public static FilterEntry NOT_NULL = new FilterEntry(Operator.notNull);
}
