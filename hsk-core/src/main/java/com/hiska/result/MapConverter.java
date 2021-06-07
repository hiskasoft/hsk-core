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

import java.util.HashMap;
import java.util.Map;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Willyams Yujra
 */
@Converter(autoApply = true)
public class MapConverter implements AttributeConverter<Map<String, String>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, String> map) {
        StringBuilder result = new StringBuilder();
        if (map != null) {
            map.forEach((k, v) -> {
                if (v != null) {
                    result.append(k)
                            .append(":")
                            .append(v)
                            .append(";");
                }
            });
        }
        return result.toString();
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String value) {
        Map map = new HashMap();
        if (value != null) {
            String[] values = value.split(";");
            for (String it : values) {
                if (!it.isEmpty()) {
                    String[] its = it.split(":", 2);
                    if (its.length == 2) {
                        map.put(its[0], its[1]);
                    }
                }
            }
        }
        return map;
    }
}
