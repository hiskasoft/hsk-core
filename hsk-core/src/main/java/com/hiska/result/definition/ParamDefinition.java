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
package com.hiska.result.definition;

import com.hiska.result.Param;
import com.hiska.result.ParamElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@lombok.Getter
@lombok.ToString
public class ParamDefinition implements Definition<Param> {
   private String domain;
   private final String classifier;
   private final boolean integrity;
   private Method method;

   public ParamDefinition(Class aClass, Field field, ParamElement element) {
      domain = assertDomainName(element.domain(), aClass);
      classifier = assertClassifierName(element.classifier(), field);
      integrity = element.integrity();
      method = Common.assertGetter(field, field.getDeclaringClass());
   }

   private static final Map<String, List<ParamDefinition>> CACHE = new HashMap<>();

   public static List<ParamDefinition> get(Class<?> aClass) {
      List<ParamDefinition> items = CACHE.get(aClass.getName());
      if (items == null) {
         items = new ArrayList<>();
         CACHE.put(aClass.getName(), items);
         for (Field field : Common.assertAllField(aClass)) {
            ParamElement element = field.getAnnotation(ParamElement.class);
            if (element != null) {
               ParamDefinition item = new ParamDefinition(aClass, field, element);
               items.add(item);
            }
         }
      }
      return items;
   }

   private String assertDomainName(String domain, Class aClass) {
      if (Common.isDefaultValue(domain)) {
         domain = Common.getTableName(aClass);
      }
      return domain;
   }

   private String assertClassifierName(String classifier, Field field) {
      if (Common.isDefaultValue(classifier)) {
         classifier = Common.getColumnName(field);
      }
      return classifier;
   }
}
