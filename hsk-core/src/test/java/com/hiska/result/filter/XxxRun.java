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

import com.hiska.result.FilterBuilder;
import com.hiska.result.Filter;
import com.hiska.result.Param;
import java.util.Arrays;

/**
* @author Willyams Yujra
*/
public class XxxRun {
   public static void main(String[] args) {
      XxxFilter f1;
      String jpsql;
      f1 = new XxxFilter();
      jpsql = FilterBuilder.create(XxxEntity.class)
            .filter(f1)
            .createQuery();
      space();
      System.out.println(jpsql);
      f1 = new XxxFilter();
      f1.setId(Filter.of(1L));
      f1.setCol(Filter.of("xxx"));
      f1.setColName(Filter.of("xxx"));
      f1.setCode(Filter.of(Param.of("01"), Param.of("01")));
      f1.setRef(Filter.of(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)));
      jpsql = FilterBuilder.create(XxxEntity.class)
            .filter(f1)
            .createQuery();
      space();
      System.out.println(jpsql);
   }

   private static void space() {
      System.out.println("-----------------------");
   }
}
