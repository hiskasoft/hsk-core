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

import com.sun.faces.io.FastStringWriter;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import static com.sun.faces.renderkit.RenderKitUtils.getSelectItems;
import static com.sun.faces.renderkit.RenderKitUtils.renderOnchange;
import static com.sun.faces.renderkit.RenderKitUtils.renderPassThruAttributes;
import static com.sun.faces.renderkit.RenderKitUtils.renderXHTMLStyleBooleanAttributes;
import com.sun.faces.renderkit.SelectItemsIterator;
import com.sun.faces.renderkit.html_basic.ListboxRenderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

/**
 * @author Willyams Yujra
 */
public class SpanListboxRenderer extends ListboxRenderer {
   private static final Attribute[] OUTPUT_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.OUTPUTTEXT);

   @Override
   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      boolean renderAsOutput = RenderkitHelp.isRenderAsOutput(context, component);
      if (!renderAsOutput) {
         super.encodeEnd(context, component);
      } else {
         ResponseWriter writer = context.getResponseWriter();
         writer.startElement("span", component);
         writeIdAttributeIfNecessary(context, writer, component);
         RenderkitHelp.writeStyleClassAttributeIfNecessary(context, writer, component);
         SelectItemsIterator<SelectItem> items = getSelectItems(context, component);
         FastStringWriter bufferedWriter = new FastStringWriter(128);
         context.setResponseWriter(writer.cloneWithWriter(bufferedWriter));
         renderOptions(context, component, items);
         context.setResponseWriter(writer);
         renderPassThruAttributes(context, writer, component, OUTPUT_ATTRIBUTES, getNonOnChangeBehaviors(component));
         renderXHTMLStyleBooleanAttributes(writer, component);
         renderOnchange(context, component, false);
         writer.write(bufferedWriter.toString());
         writer.endElement("span");
      }
   }

   @Override
   protected boolean renderOption(FacesContext context, UIComponent component, UIComponent selectComponent, Converter<?> converter, SelectItem curItem,
         Object currentSelections, Object[] submittedValues, OptionComponentInfo optionInfo) throws IOException {
      boolean renderAsOutput = RenderkitHelp.isRenderAsOutput(context, component);
      if (!renderAsOutput) {
         return super.renderOption(context, component, component, converter, curItem, currentSelections, submittedValues, optionInfo);
      }
      Object valuesArray;
      Object itemValue;
      String valueString = getFormattedValue(context, component, curItem.getValue(), converter);
      boolean containsValue;
      if (submittedValues != null) {
         containsValue = containsaValue(submittedValues);
         if (containsValue) {
            valuesArray = submittedValues;
            itemValue = valueString;
         } else {
            valuesArray = currentSelections;
            itemValue = curItem.getValue();
         }
      } else {
         valuesArray = currentSelections;
         itemValue = curItem.getValue();
      }
      boolean isSelected = isSelected(context, component, itemValue, valuesArray, converter);
      if (optionInfo.isHideNoSelection() && curItem.isNoSelectionOption() && currentSelections != null && !isSelected) {
         return false;
      }
      ResponseWriter writer = context.getResponseWriter();
      assert (writer != null);
      if (!isSelected) {
         return false;
      }
      writer.writeText("\t", component, null);
      writer.startElement("span", (null != selectComponent) ? selectComponent : component);
      // writer.writeAttribute("value", valueString, "value");
      String labelClass;
      if (optionInfo.isDisabled() || curItem.isDisabled()) {
         labelClass = optionInfo.getDisabledClass();
      } else {
         labelClass = optionInfo.getEnabledClass();
      }
      if (labelClass != null) {
         writer.writeAttribute("class", labelClass, "labelClass");
      }
      String label = curItem.getLabel();
      if (label == null) {
         label = valueString;
      }
      writer.writeText(label, component, "label");
      writer.endElement("span");
      writer.writeText("\n", component, null);
      return true;
   }
}
