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

import com.hiska.result.Filter;
import com.hiska.result.*;
import com.hiska.result.definition.*;
import java.util.*;
import java.util.logging.*;
import javax.persistence.*;
import lombok.Builder;

/**
 * @author Willyams Yujra
 */
public class FilterBuilderImpl<T> implements FilterBuilder<T> {
   @Builder
   static class RuleEntry<T> {
      private final String param;
      private final String paramA;
      private final String paramB;
      private final String[] names;
      private final Filter<T> filter;
   }

   private String source;
   private Pager pager;
   private Order order;
   private String name = "o";
   private final List<RuleEntry<?>> entries = new ArrayList<>();

   protected FilterBuilderImpl() {
      source = "DUAL";
   }

   protected FilterBuilderImpl(String name) {
      this.source = name;
   }

   @Override
   public FilterBuilder<T> rules(final Object oFilter) {
      if (oFilter != null) {
         Class cFilter = oFilter.getClass();
         List<FilterDefinition> items = FilterDefinition.get(cFilter);
         items.stream()
               .forEach(item -> {
                  Filter filter = item.invokeGetter(oFilter);
                  appendRule(item.getParam(), item.getName(), filter);
               });
         Pager oPager = PaginationDefinition.getInstance(oFilter);
         pager(oPager);
      }
      return this;
   }

   @Override
   public FilterBuilder<T> appendRule(final String param, final String[] names, final Filter filter) {
      if (filter != null && !filter.isIgnore()) {
         RuleEntry entry = RuleEntry.builder()
               .param(param)
               .paramA(param + "_A")
               .paramB(param + "_B")
               .names(names)
               .filter(filter)
               .build();
         entries.add(entry);
      }
      return this;
   }

   @Override
   public FilterBuilder<T> pager(final Pager value) {
      pager = new Pager(value);
      return this;
   }

   @Override
   public FilterBuilder<T> order(final Order value) {
      order = new Order(value);
      return this;
   }

   @Override
   public String createQuery() {
      StringBuilder queryString = new StringBuilder();
      queryString.append("SELECT ").append(name)
            .append("\n  FROM ").append(source).append(" ").append(name);
      appendWhere(queryString);
      if (order != null && order.isValid()) {
         queryString.append("\nORDER BY ")
               .append(name)
               .append(".")
               .append(order.getName())
               .append(" ")
               .append(order.getTypeString());
      }
      return queryString.toString();
   }

   @Override
   public String createQueryCount() {
      StringBuilder queryString = new StringBuilder();
      queryString.append("SELECT COUNT(1)\n  FROM ").append(source).append(" ").append(name);
      appendWhere(queryString);
      return queryString.toString();
   }

   private void appendWhere(StringBuilder queryString) {
      if (!entries.isEmpty()) {
         StringBuilder whereString = new StringBuilder();
         for (RuleEntry entry : entries) {
            Filter filter = entry.filter;
            Filter.Operator oper = filter.getOper();
            if (oper.ignore()) {
               continue;
            }
            whereString.append("(");
            for (String name : entry.names) {
               whereString.append("\n   ")
                     .append(name).append(".").append(name)
                     .append(" ").append(oper.alias());
               if (oper.params() == 1) {
                  whereString.append(" :").append(entry.param);
               } else if (oper.params() == 2) {
                  whereString.append(" :").append(entry.paramA);
                  whereString.append(" AND :").append(entry.paramB);
               } else if (oper.params() == 99) {
                  whereString.append(" (");
                  int size = filter.getSizeValues();
                  for (int i = 0; i < size; i++) {
                     whereString.append(" :")
                           .append(entry.param)
                           .append("_")
                           .append(i)
                           .append(" ,");
                  }
                  if (size > 0) {
                     whereString.setLength(whereString.length() - 1);// remove
                     // last ,
                  }
                  whereString.append(")");
               }
               whereString.append("\n  OR ");
            }
            whereString.setLength(whereString.length() - 6); // remove last OR
            whereString.append("\n ) AND ");
         }
         if (whereString.length() != 0) {
            whereString.setLength(whereString.length() - 5); // remove last AND
            queryString.append("\n WHERE ");
            queryString.append(whereString);
         }
      }
   }

   @Override
   public Number getCount(EntityManager em) {
      String queryString = createQueryCount();
      Query query = em.createQuery(queryString);
      appendParameter(query);
      return (Number) query.getSingleResult();
   }

   @Override
   public List<T> getList(EntityManager em) {
      String queryString = createQuery();
      Query query = em.createQuery(queryString);
      appendParameter(query);
      if (pager != null) {
         int index = pager.getIndex() - 1;
         index = index * pager.getSize();
         query.setFirstResult(index);
         query.setMaxResults(pager.getSize());
      }
      return query.getResultList();
   }

   @Override
   public ResultList<T> getResultList(EntityManager em) {
      List<T> list = getList(em);
      return new ResultList(list);
   }

   @Override
   public ResultPage<T> getResultPage(EntityManager em) {
      ResultPage<T> result = new ResultPage();
      try {
         if (pager != null) {
            if (pager.getLength() == -1) {
               int count = getCount(em).intValue();
               int size = pager.getSize();
               int length = count / size;
               if (length * size != count) {
                  length++;
               }
               pager.setCount(count);
               pager.setLength(length);
            }
            if (pager.getIndex() > pager.getLength()) {
               pager.setIndex(pager.getLength());
            }
         }
         List<T> list = getList(em);
         result.setPagination(pager);
         result.setValue(list);
      } catch (Exception e) {
         LOGGER.log(Level.SEVERE, "EXCEPTION QUERY", e);
         result.setError(true);
         result.message("HSK-2001: Error al ejecutar la consulta")
               .exception(e)
               .action("Contactese con el administrador de sistemas");
      }
      return result;
   }

   private void appendParameter(Query query) {
      for (RuleEntry entry : entries) {
         Filter filter = entry.filter;
         Filter.Operator oper = filter.getOper();
         if (oper == null || oper.params() <= 0) {
            continue;
         }
         Object value = filter.getValue();
         Object other = filter.getOther();
         List values = filter.getValues();
         if (oper.params() == 1) {
            if (oper == Filter.Operator.like) {
               query.setParameter(entry.param, "%" + value + "%");
            } else {
               query.setParameter(entry.param, value);
            }
         } else if (oper.params() == 2) {
            query.setParameter(entry.paramA, value);
            query.setParameter(entry.paramB, other);
         } else if (oper.params() == 99) {
            int size = filter.getSizeValues();
            for (int i = 0; i < size; i++) {
               Object valueIt = values.get(i);
               query.setParameter(entry.param + "_" + i, valueIt);
            }
         }
      }
   }

   private static final Logger LOGGER = Logger.getLogger(FilterBuilderImpl.class.getName());
}
