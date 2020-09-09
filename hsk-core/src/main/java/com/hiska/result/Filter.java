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
 * Filter Element
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Filter<T> {
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
      notNull("is not null", 0);

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

   public Filter() {
   }

   public Filter(T value) {
      this(value, Expr.eq);
   }

   public Filter(T value, Expr expr) {
      this.value = value;
      this.expr = expr;
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

   public boolean isIgnore() {
      return expr != null && expr.ignore();
   }

   public static <T> Filter<T> create(T value) {
      return new Filter<>(value);
   }
}
