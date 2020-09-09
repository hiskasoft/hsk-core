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
package com.hiska.faces.converter;

import com.hiska.faces.ContextUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.hiska.result.Param;

public class ParamConverter implements Converter {
   @Override
   public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
      return Param.create(value, "JSF: " + value);
   }

   @Override
   public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
      if (object instanceof Param) {
         Param param = (Param) object;
         return param.getValue();
      }
      return object == null ? null : object.toString();
   }
}
