package com.hiska.jaxrs.ext;

import com.hiska.jaxrs.HashInteger;
import javax.ws.rs.ext.ParamConverter;

public class HashIntegerConverter implements ParamConverter<HashInteger>  {

    private final DESConvert convert = DESConvert.getInstance();

    @Override
    public HashInteger fromString(String param) {
        if (param == null) {
            return null;
        }
        Integer value = convert.forceDecode(param, Integer::decode);
        return new HashInteger(value);
    }

    @Override
    public String toString(HashInteger param) {
        if (param == null) {
            return null;
        }
        Integer value = param.getValue();
        return convert.forceEncode(value, String::valueOf);
    }
}
