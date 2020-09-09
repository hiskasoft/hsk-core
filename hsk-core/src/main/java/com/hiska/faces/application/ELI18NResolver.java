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
package com.hiska.faces.application;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.PropertyResourceBundle;
import javax.el.ELContext;
import javax.el.ELResolver;

/**
 * @author Willyams Yujra
 */
public class ELI18NResolver extends ELResolver {
   @Override
   public Object getValue(ELContext context, Object object, Object property) {
      context.setPropertyResolved(false);
      if (object instanceof PropertyResourceBundle) {
         PropertyResourceBundle bundle = (PropertyResourceBundle) object;
         ELI18NBundle i18nBundle = new ELI18NBundle(bundle);
         String name = (String) property;
         i18nBundle.addName(name);
         context.setPropertyResolved(true);
         return i18nBundle;
      } else if (object instanceof ELI18NBundle) {
         ELI18NBundle i18nBundle = (ELI18NBundle) object;
         String name = (String) property;
         i18nBundle.addName(name);
         context.setPropertyResolved(true);
         return i18nBundle;
      }
      return null;
   }

   @Override
   public Class<?> getType(ELContext context, Object object, Object property) {
      context.setPropertyResolved(false);
      return null;
   }

   @Override
   public void setValue(ELContext context, Object object, Object property, Object value) {
      context.setPropertyResolved(false);
   }

   @Override
   public boolean isReadOnly(ELContext context, Object object, Object property) {
      context.setPropertyResolved(false);
      return true;
   }

   @Override
   public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object object) {
      context.setPropertyResolved(false);
      return null;
   }

   @Override
   public Class<?> getCommonPropertyType(ELContext context, Object object) {
      context.setPropertyResolved(false);
      return null;
   }
}
