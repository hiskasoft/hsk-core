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
package com.hiska.faces.converter;

import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@SuppressWarnings("unchecked")
public class ObjectConverter implements Converter {
   private final String LILS_OBJECT = "list-object";

   @Override
   public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
      if (string == null || string.startsWith(component.getClientId() + ":") == false) {
         return null;
      }
      string = string.substring(component.getClientId().length() + 1);
      int i = Integer.valueOf(string);
      List<Object> list = (List<Object>) component.getAttributes().get(LILS_OBJECT);
      if (list == null || list.isEmpty() || list.size() < i) {
         return null;
      }
      return list.get(i);
   }

   @Override
   public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
      if (object == null) {
         return null;
      }
      List<Object> list = (List<Object>) component.getAttributes().get(LILS_OBJECT);
      if (list == null) {
         list = new ArrayList<>();
         component.getAttributes().put(LILS_OBJECT, list);
      }
      int index = list.indexOf(object);
      if (index == -1) {
         list.add(object);
         return component.getClientId() + ":" + (list.size() - 1);
      } else {
         return component.getClientId() + ":" + index;
      }
   }
}
