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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

public class Common {
   protected static List<Field> assertAllField(Class aClass) {
      List<Field> fields = new ArrayList<>();
      while (aClass != Object.class && aClass != null) {
         fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
         aClass = aClass.getSuperclass();
      }
      return fields;
   }

   protected static Method assertGetter(Field field, Class aClass) {
      Method method;
      try {
         String nameMethod = "get" + field.getName().toUpperCase().charAt(0) + field.getName().substring(1);
         method = aClass.getMethod(nameMethod);
      } catch (NoSuchMethodException | SecurityException e) {
         method = null;
      }
      return method;
   }

   public static boolean isDefaultValue(String value) {
      return "#default".equals(value) || value == null || value.isEmpty();
   }

   public static String getEntityName(Class aClass) {
      Entity e = (Entity) aClass.getAnnotation(Entity.class);
      if (e != null && !isDefaultValue(e.name())) {
         return e.name();
      }
      return aClass.getSimpleName();
   }

   static String getTableName(Class aClass) {
      String value = aClass.getSimpleName();
      Table table = (Table) aClass.getAnnotation(Table.class);
      if (table != null) {
         if (isDefaultValue(table.name())) {
            value = table.name();
         }
         if (!isDefaultValue(table.schema())) {
            value = table.schema() + ":" + value;
         }
      }
      value = value.toUpperCase();
      return value;
   }

   static String getColumnName(Field aField) {
      String value = aField.getName();
      Column column = (Column) aField.getAnnotation(Column.class);
      if (column != null) {
         if (isDefaultValue(column.name())) {
            value = column.name();
         }
      }
      value = value.toUpperCase();
      return value;
   }
}
