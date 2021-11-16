package com.hiska.jaxrs.ext;

import com.hiska.jaxrs.HashString;
import javax.ws.rs.ext.ParamConverter;

public class HashStringConverter implements ParamConverter<HashString>  {

    private final DESConvert convert = DESConvert.getInstance();

    @Override
    public HashString fromString(String param) {
        if (param == null) {
            return null;
        }
        String value = convert.forceDecode(param, s -> s);
        return new HashString(value);
    }

    @Override
    public String toString(HashString param) {
        if (param == null) {
            return null;
        }
        String value = param.getValue();
        return convert.forceEncode(value, s -> s);
    }
}
