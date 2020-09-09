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
package com.hiska.result.definition;

import com.hiska.result.Filter;
import com.hiska.result.FilterElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@lombok.Getter
@lombok.ToString
public class FilterDefinition implements Definition<Filter> {
   private final String ref;
   private final String[] name;
   private final boolean isParam;
   private final Method method;

   public FilterDefinition(Field field, FilterElement element) {
      ref = assertParamName(element.ref(), field.getName());
      name = assertAttrNames(element.name(), field.getName());
      isParam = element.isParam();
      method = Common.assertGetter(field, field.getDeclaringClass());
   }

   private static final Map<Class, List<FilterDefinition>> CACHE = new HashMap<>();

   public static List<FilterDefinition> get(Class<?> aClass) {
      List<FilterDefinition> items = CACHE.get(aClass);
      if (items == null) {
         items = new ArrayList<>();
         CACHE.put(aClass, items);
         for (Field field : Common.assertAllField(aClass)) {
            FilterElement element = field.getAnnotation(FilterElement.class);
            if (element != null) {
               FilterDefinition item = new FilterDefinition(field, element);
               items.add(item);
            }
         }
      }
      return items;
   }

   private static String assertParamName(String param, String paramDefault) {
      if ("#default".equals(param) || param == null || param.isEmpty()) {
         param = paramDefault;
      }
      return param;
   }

   private static String[] assertAttrNames(String[] name, String nameDefault) {
      String resp[] = new String[name.length];
      for (int i = 0; i < name.length; i++) {
         resp[i] = name[i];
         if ("#default".equals(resp[i])) {
            resp[i] = nameDefault;
         }
      }
      return resp;
   }
}
