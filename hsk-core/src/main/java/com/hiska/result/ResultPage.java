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
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Result Filter Response for Request Filter
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultPage<T> extends Result implements Serializable {

    /**
     * Pagination Result
     */
    private Pagination pagination;
    /**
     * List of Object
     */
    private List<T> value;

    public ResultPage() {
    }

    public ResultPage(final List<T> value) {
        this.value = value;
    }

    public ResultPage(final List<T> value, final Result other) {
        super(other);
        this.value = value;
    }

    public ResultPage(final List<T> value, final Pagination pagination, final Result other) {
        super(other);
        this.value = value;
        this.pagination = pagination;
    }

    public ResultPage(final Result other) {
        super(other);
    }

    public <R> ResultPage<R> mapper(Function<T, R> mapper) {
        List<R> list = null;
        if (value != null) {
            list = value.stream().map(mapper).collect(toList());
        }
        return new ResultPage<>(list, pagination, this);
    }
}
