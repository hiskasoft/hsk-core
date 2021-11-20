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

import com.hiska.result.Pager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@lombok.Getter
@lombok.ToString
public class PaginationDefinition implements Definition<Pager> {
   private Method getter;
   private Method setter;
//    public PaginationDefinition(Field field, FilterElement element) {
//        ref = assertParamName(element.ref(), field.getName());
//        name = assertAttrNames(element.name(), field.getName());
//        isParam = element.isParam();
//        method = Common.assertGetter(field, field.getDeclaringClass());
//    }
   private static final Map<Class, PaginationDefinition> CACHE = new HashMap<>();

   public static PaginationDefinition get(Class aClass) {
      PaginationDefinition item = CACHE.get(aClass);
      if (item == null) {
         item = new PaginationDefinition();
         CACHE.put(aClass, item);
         for (Field field : Common.assertAllField(aClass)) {
            if (field.getType().isAssignableFrom(Pager.class)) {
               item.getter = Common.assertGetter(field, aClass);
               item.setter = Common.assertSetter(field, aClass, Pager.class);
               break;
            }
         }
      }
      return item;
   }

   public static Pager getInstance(Object aInstance) {
      if (aInstance == null) {
         return new Pager();
      } else if (aInstance instanceof Pager) {
         Pager other = (Pager) aInstance;
         return new Pager(other);
      } else {
         PaginationDefinition pd = get(aInstance.getClass());
         return pd.invokeGetter(aInstance);
      }
   }
}
