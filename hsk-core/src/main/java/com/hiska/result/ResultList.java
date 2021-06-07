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
import java.util.function.Consumer;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Result List Response for Request List
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultList<T> extends Result implements Serializable {

    /**
     * List of Object
     */
    private List<T> value;

    public ResultList() {
    }

    public ResultList(List<T> value) {
        this.value = value;
    }

    public ResultList(List<T> value, Result other) {
        super(other);
        this.value = value;
    }

    public ResultList(Result other) {
        super(other);
    }

    public <R extends Serializable> ResultList<R> mapper(Function<T, R> mapper) {
        List<R> list = null;
        if (value != null) {
            list = value.stream().map(mapper).collect(toList());
        }
        return new ResultList<>(list, this);
    }

    public void ifSuccess(Consumer<List<T>> caller) {
        if (isSuccess()) {
            caller.accept(value);
        }
    }
}
