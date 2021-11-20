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

import com.hiska.result.*;
import com.hiska.result.definition.Common;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Filter Builder
 */
public interface FilterBuilder<T> {
   public static <T> FilterBuilder<T> create(Class<T> aEntity) {
      String name = Common.getEntityName(aEntity);
      return new FilterBuilderImpl(name);
   }

   public static <T> FilterBuilder<T> create(String name) {
      return new FilterBuilderImpl(name);
   }

   public static <T> FilterBuilder<T> create(Class<T> aEntity, FilterWrap oFilter) {
      String name = Common.getEntityName(aEntity);
      FilterBuilder<T> builder = new FilterBuilderImpl(name);
      builder.where(oFilter.getWhere());
      builder.pager(oFilter.getPager());
      builder.order(oFilter.getOrder());
      return builder;
   }

   public FilterBuilder<T> where(Object oFilter);

   public FilterBuilder<T> appendRule(String param, String[] names, Filter filter);

   public FilterBuilder<T> pager(Pager pager);

   public FilterBuilder<T> order(Order order);

   public String createQuery();

   public String createQueryCount();

   public Number getCount(EntityManager em);

   public List<T> getList(EntityManager em);

   public ResultList<T> getResultList(EntityManager em);

   public ResultPage<T> getResultPage(EntityManager em);
}
