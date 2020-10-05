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

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.TextRenderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * @author Willyams Yujra
 */
public class TextSpanRenderer extends TextRenderer {
   private static final Attribute[] OUTPUT_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.OUTPUTTEXT);

   // ------------------------------------------------------- Protected Methods
   @Override
   protected void getEndTextToRender(FacesContext context,
         UIComponent component,
         String currentValue)
         throws IOException {
      boolean isSpan = isSpan(context, component);
      if (!isSpan) {
         super.getEndTextToRender(context, component, currentValue);
         return;
      }
      ResponseWriter writer = context.getResponseWriter();
      assert (writer != null);
      writer.startElement("span", component);
      writeIdAttributeIfNecessary(context, writer, component);
      writeStyleClassAttributeIfNecessary(context, writer, component);
      // style is rendered as a passthru attribute
      RenderKitUtils.renderPassThruAttributes(context,
            writer,
            component,
            OUTPUT_ATTRIBUTES);
      if (currentValue != null) {
         Object val = component.getAttributes().get("escape");
         if ((val != null) && Boolean.valueOf(val.toString())) {
            writer.writeText(currentValue, component, "value");
         } else {
            writer.write(currentValue);
         }
      }
      writer.endElement("span");
   }

   public boolean isSpan(FacesContext context, UIComponent component) throws IOException {
      return (Boolean) component.getAttributes().get("disabled") || (Boolean) component.getAttributes().get("readonly");
   }

   private void writeStyleClassAttributeIfNecessary(FacesContext context, ResponseWriter writer, UIComponent component) throws IOException {
      String styleClass = (String) component.getAttributes().get("styleClass");
      if (styleClass != null) {
         writer.writeAttribute("class", styleClass, "styleClass");
      }
   }
}
