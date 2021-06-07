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

import com.hiska.result.Option;
import com.hiska.result.Param;
import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ParamConverter implements Converter {
   private static final String FAMILY_OUTPUT = "javax.faces.Output";

   @Override
   public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
      Param param = null;
      Map<String, Object> items = getItems(component);
      Object real = items.get(value);
      if (real instanceof Option) {
         Option option = (Option) real;
         param = Param.of(value, option.getLabel(), option.getDescription());
      } else if (real instanceof Param) {
         param = (Param) real;
      } else if (value != null && !value.isEmpty()) {
         param = Param.of(value, "JSF_" + value);
      }
      return param;
   }

   @Override
   public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
      String value = null;
      if (FAMILY_OUTPUT.equals(component.getFamily())) {
         if (object instanceof Option) {
            Option option = (Option) object;
            value = option.getLabel();
         } else if (object instanceof Param) {
            Param param = (Param) object;
            value = param.getLabel();
         } else if (object != null) {
            value = object.toString();
         }
         return value;
      }
      Map<String, Object> items = getItems(component);
      if (object instanceof Option) {
         Option option = (Option) object;
         items.put(option.getValue(), option);
         value = option.getValue();
      } else if (object instanceof Param) {
         Param param = (Param) object;
         items.put(param.getValue(), param);
         value = param.getValue();
      } else if (object != null) {
         value = object.toString();
      }
      return value;
   }

   private Map<String, Object> getItems(UIComponent component) {
      Map<String, Object> items = (Map) component.getAttributes().get(ITEM_LIST);
      if (items == null) {
         items = new HashMap<>();
         component.getAttributes().put(ITEM_LIST, items);
      }
      return items;
   }

   public static final String ITEM_LIST = "==items==";
}
