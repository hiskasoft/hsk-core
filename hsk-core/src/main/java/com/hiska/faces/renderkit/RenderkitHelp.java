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
package com.hiska.faces.renderkit;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * @author Willyams Yujra
 */
public class RenderkitHelp {
   public static final String BASE = "com.hiska.faces.";
   public static final String DISABLED_CONVERT_TO_OUTPUT = BASE + "DISABLED_CONVERT_TO_OUTPUT";
   public static final String MESSAGE_TO_SPAN = BASE + "MESSAGE_TO_SPAN";

   public static boolean isMessageToSpan(FacesContext context) throws IOException {
      String value = context.getExternalContext().getInitParameter(MESSAGE_TO_SPAN);
      return value != null && Boolean.valueOf(value);
   }

   public static boolean isDisabledConvertToOutput(FacesContext context) throws IOException {
      String value = context.getExternalContext().getInitParameter(DISABLED_CONVERT_TO_OUTPUT);
      return value != null && Boolean.valueOf(value);
   }

   public static boolean isComponentDisabled(UIComponent component) throws IOException {
      return Boolean.valueOf(String.valueOf(component.getAttributes().get("disabled"))) || Boolean.valueOf(String.valueOf(component.getAttributes().get("readonly")));
   }

   public static boolean isRenderAsOutput(FacesContext context, UIComponent component) throws IOException {
      return isDisabledConvertToOutput(context) && isComponentDisabled(component);
   }

   public static void assertAttribute(UIComponent component, String name, String defaultValue) {
      String value = (String) component.getAttributes().get(name);
      if (value == null || value.isEmpty()) {
         component.getAttributes().put(name, defaultValue);
      }
   }

   public static void writeStyleClassAttributeIfNecessary(FacesContext context, ResponseWriter writer, UIComponent component) throws IOException {
      String styleClass = (String) component.getAttributes().get("styleClass");
      if (styleClass != null) {
         writer.writeAttribute("class", styleClass, "styleClass");
      }
   }
}
