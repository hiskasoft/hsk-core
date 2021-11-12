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
    static class Entry<T> {

        private final String param;
        private final String paramA;
        private final String paramB;
        private final String[] names;
        private final Filter<T> filter;
        private final boolean convertToParam;
    }

    private String source;
    private Pagination pagination;
    private Sortable sortable;
    private String alias = "o";
    private final List<Entry<?>> entries = new ArrayList<>();

    protected FilterBuilderImpl() {
        source = "DUAL";
    }

    protected FilterBuilderImpl(String name) {
        this.source = name;
    }

    @Override
    public FilterBuilder<T> condition(final Object oFilter) {
        if (oFilter != null) {
            Class cFilter = oFilter.getClass();
            List<FilterDefinition> items = FilterDefinition.get(cFilter);
            items.stream()
                    .forEach(item -> {
                        Filter filter = item.invokeGetter(oFilter);
                        appendEntry(item.getParam(), item.getName(), filter, item.isConvertToParam());
                    });
            Pagination oPagination = PaginationDefinition.getInstance(oFilter);
            pagination(oPagination);
        }
        return this;
    }

    @Override
    public FilterBuilder<T> appendEntry(final String param, final String[] names, final Filter filter, boolean convertToParam) {
        if (filter != null && !filter.isIgnore()) {
            Entry entry = Entry.builder()
                    .param(param)
                    .paramA(param + "_A")
                    .paramB(param + "_B")
                    .names(names)
                    .filter(filter)
                    .convertToParam(convertToParam)
                    .build();
            entries.add(entry);
        }
        return this;
    }

    @Override
    public FilterBuilder<T> pagination(final Pagination value) {
        pagination = new Pagination(value);
        return this;
    }

    @Override
    public FilterBuilder<T> sortable(final Sortable value) {
        sortable = new Sortable(value);
        return this;
    }

    @Override
    public String createQuery() {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT ").append(alias)
                .append("\n  FROM ").append(source).append(" ").append(alias);
        appendWhere(queryString);
        if (sortable != null && sortable.isValid()) {
            queryString.append("\nORDER BY ")
                    .append(alias)
                    .append(".")
                    .append(sortable.getName())
                    .append(" ")
                    .append(sortable.getTypeString());
        }
        return queryString.toString();
    }

    @Override
    public String createQueryCount() {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT COUNT(1)\n  FROM ").append(source).append(" ").append(alias);
        appendWhere(queryString);
        return queryString.toString();
    }

    private void appendWhere(StringBuilder queryString) {
        if (!entries.isEmpty()) {
            StringBuilder whereString = new StringBuilder();
            for (Entry entry : entries) {
                Filter filter = entry.filter;
                Filter.Operator oper = filter.getOper();
                if (oper.ignore()) {
                    continue;
                }
                whereString.append("(");
                for (String name : entry.names) {
                    whereString.append("\n   ")
                            .append(alias).append(".").append(name)
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
        if (pagination != null) {
            int index = pagination.getIndex() - 1;
            index = index * pagination.getSize();
            query.setFirstResult(index);
            query.setMaxResults(pagination.getSize());
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
            if (pagination != null) {
                if (pagination.getLength() == -1) {
                    int count = getCount(em).intValue();
                    int size = pagination.getSize();
                    int length = count / size;
                    if (length * size != count) {
                        length++;
                    }
                    pagination.setCount(count);
                    pagination.setLength(length);
                }
                if (pagination.getIndex() > pagination.getLength()) {
                    pagination.setIndex(pagination.getLength());
                }
            }
            List<T> list = getList(em);
            result.setPagination(pagination);
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
        for (Entry entry : entries) {
            Filter filter = entry.filter;
            Filter.Operator oper = filter.getOper();
            if (oper == null || oper.params() <= 0) {
                continue;
            }
            Object value = filter.getValue();
            Object other = filter.getOther();
            List values = filter.getValues();
            if (entry.convertToParam) {
                value = Param.of(value);
                other = Param.of(other);
            }
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
                    if (entry.convertToParam) {
                        valueIt = Param.of(valueIt);
                    }
                    query.setParameter(entry.param + "_" + i, valueIt);
                }
            }
        }
    }

    private static final Logger LOGGER = Logger.getLogger(FilterBuilderImpl.class.getName());
}
