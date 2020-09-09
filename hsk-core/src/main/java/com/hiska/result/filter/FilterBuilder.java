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
package com.hiska.result.filter;

import com.hiska.result.ResultFilter;
import java.util.List;
import com.hiska.result.Pagination;
import com.hiska.result.Filter;
import com.hiska.result.definition.Common;
import com.hiska.result.definition.PaginationDefinition;
import com.hiska.result.definition.FilterDefinition;
import javax.persistence.EntityManager;

/**
 * Filter Builder
 */
public interface FilterBuilder<T> {
   public static <T> FilterBuilder<T> create() {
      return new FilterBuilderImpl();
   }

   public static <T> FilterBuilder<T> create(Class<T> aEntity) {
      String name = Common.getEntityName(aEntity);
      return create(name, null);
   }

   public static <T> FilterBuilder<T> create(String name) {
      return create(name, null);
   }

   public static <T> FilterBuilder<T> create(Class<T> aEntity, Object oFilter) {
      String name = Common.getEntityName(aEntity);
      return create(name, oFilter);
   }

   public static <T> FilterBuilder<T> create(String name, Object oFilter) {
      FilterBuilder<T> builder = new FilterBuilderImpl(name);
      if (oFilter != null) {
         Class cFilter = oFilter.getClass();
         List<FilterDefinition> items = FilterDefinition.get(cFilter);
         items.stream()
               .forEach(item -> {
                  Filter filter = item.invokeMethod(oFilter);
                  builder.appendEntry(item.getRef(), item.getName(), filter, item.isParam());
               });
         Pagination pagination = PaginationDefinition.getInstance(oFilter);
         builder.pagination(pagination);
      }
      return builder;
   }

   public FilterBuilder<T> appendEntry(String param, String[] names, Filter filter, boolean isParam);

   public FilterBuilder<T> pagination(Pagination pagination);

   public String createQuery();

   public String createQueryCount();

   public Number getResultCount(EntityManager em);

   public List<T> getResultList(EntityManager em);

   public ResultFilter<T> getResultFilter(EntityManager em);
}
