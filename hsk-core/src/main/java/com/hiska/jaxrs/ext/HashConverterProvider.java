package com.hiska.jaxrs.ext;

import com.hiska.jaxrs.HashInteger;
import com.hiska.jaxrs.HashString;
import com.hiska.jaxrs.HashLong;
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
