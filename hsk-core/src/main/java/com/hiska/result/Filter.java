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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import lombok.Data;
import lombok.ToString;

/**
 * Filter Element
 */
@Data
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Filter<T> implements Serializable {
   public static enum Expr {
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

      private final String oper;
      private final int params;

      Expr(String oper, int params) {
         this.oper = oper;
         this.params = params;
      }

      public int params() {
         return params;
      }

      public String oper() {
         return oper;
      }

      public boolean ignore() {
         return params == -1;
      }
   }

   /**
    * Expression Filter
    */
   private Expr expr;
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

   public Filter() {
   }

   public Filter(Expr expr) {
      this.expr = expr;
   }

   public Filter(T value) {
      this(value, Expr.eq);
   }

   public Filter(T value, Expr expr) {
      this.value = value;
      this.expr = expr;
   }

   public Filter(T value, T other) {
      this.value = value;
      this.other = other;
      this.expr = Expr.bw;
   }

   public Filter(List<T> values) {
      this.values = values;
      this.expr = Expr.in;
   }

   public Expr getExpr() {
      if (expr == null) {
         expr = Expr.none;
      }
      return expr;
   }
//    public boolean isWithParam() {
//        return expr != null && expr.isWithParam();
//    }

   public boolean isWithValue() {
      return expr != null && expr.params() >= 1;
   }

   public boolean isWithOther() {
      return expr != null && expr.params() == 2;
   }

   public boolean isWithList() {
      return expr != null && expr.params() == 99;
   }

   public boolean isIgnore() {
      return expr != null && expr.ignore();
   }

   public int getSizeValues() {
      return values == null ? 0 : values.size();
   }

   public static <T> Filter<T> of(T value) {
      return new Filter<>(value);
   }

   public static <T> Filter<T> of(T value, T other) {
      return new Filter<>(value, other);
   }

   public static <T> Filter<T> of(List<T> values) {
      return new Filter<>(values);
   }

   public static Filter IS_NULL = new Filter(Expr.isNull);
   public static Filter NOT_NULL = new Filter(Expr.notNull);
}
