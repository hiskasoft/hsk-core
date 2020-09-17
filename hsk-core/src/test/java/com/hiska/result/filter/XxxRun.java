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
package com.hiska.result.filter;

import com.hiska.result.Filter;

/**
 * @author yracnet
 */
public class XxxRun {
   public static void main(String[] args) {
      XxxFilter f1;
      String jpsql;
      f1 = new XxxFilter();
      jpsql = FilterBuilder.create(XxxEntity.class, f1)
            .createQuery();
      space();
      System.out.println(jpsql);
      f1 = new XxxFilter();
      f1.setId(Filter.create(1L));
      f1.setCol(Filter.create("xxx"));
      f1.setColName(Filter.create("xxx"));
      f1.setCode(Filter.create("xxx"));
      jpsql = FilterBuilder.create(XxxEntity.class, f1)
            .createQuery();
      space();
      System.out.println(jpsql);
   }

   private static void space() {
      System.out.println("-----------------------");
   }
}
