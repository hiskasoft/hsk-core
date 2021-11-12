package com.hiska.result.ext;

import com.hiska.result.*;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class HashConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType,
            Annotation[] annotations) {
        if (rawType.equals(HashString.class)) {
            return (ParamConverter<T>) new HashStringConverter();
        } else if (rawType.equals(HashLong.class)) {
            return (ParamConverter<T>) new HashLongConverter();
        } else if (rawType.equals(HashInteger.class)) {
            return (ParamConverter<T>) new HashIntegerConverter();
        }
        return null;
    }

}
