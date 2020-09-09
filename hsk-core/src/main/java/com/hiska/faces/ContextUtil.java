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
package com.hiska.faces;

import javax.el.*;
import javax.faces.application.*;
import javax.faces.component.UIComponent;
import javax.faces.context.*;
//import org.primefaces.component.datatable.DataTable;

/**
 * @author Willyams Yujra
 */
@SuppressWarnings("unchecked")
public class ContextUtil {
   public static Object getRequestParameter(String name) {
      FacesContext context = FacesContext.getCurrentInstance();
      return getRequestParameter(name, context);
   }

   public static Object getRequestParameter(String name, FacesContext context) {
      return context.getExternalContext().getRequestMap().get(name);
   }

   public static void setRequestParameter(String name, Object value) {
      FacesContext context = FacesContext.getCurrentInstance();
      setRequestParameter(name, value, context);
   }

   public static void setRequestParameter(String name, Object value, FacesContext context) {
      context.getExternalContext().getRequestMap().put(name, value);
   }
//   public static PerfilBean getPerfilBean(FacesContext context) {
//      return (PerfilBean) getBean("_perfil", context);
//   }
//
//   public static PerfilBean getPerfilBean() {
//      return (PerfilBean) getBean("_perfil");
//   }
//
//   public static ConfigBean getConfigBean(FacesContext context) {
//      return (ConfigBean) getBean("_config", context);
//   }
//
//   public static ConfigBean getConfigBean() {
//      return (ConfigBean) getBean("_config");
//   }

   public static Object getBeanOrDefault(String beanName, Object other) {
      Object value = getBean(beanName);
      return value == null ? other : value;
   }

   public static Object getBeanOrDefault(String beanName, Object other, FacesContext context) {
      Object value = getBean(beanName, context);
      return value == null ? other : value;
   }

   public static Object getBean(String beanName) {
      FacesContext context = FacesContext.getCurrentInstance();
      return getBean(beanName, context);
   }

   public static Object getBean(String beanName, FacesContext context) {
      ELContext elctx = context.getELContext();
      Application jsfApp = context.getApplication();
      ExpressionFactory exprFactory = jsfApp.getExpressionFactory();
      return exprFactory.createValueExpression(elctx, "#{" + beanName + "}", Object.class).getValue(elctx);
   }

   public static void setBean(String beanName, Object value) {
      FacesContext context = FacesContext.getCurrentInstance();
      setBean(beanName, value, context);
   }

   public static void setBean(String beanName, Object value, FacesContext context) {
      ELContext elctx = context.getELContext();
      Application jsfApp = context.getApplication();
      ExpressionFactory exprFactory = jsfApp.getExpressionFactory();
      exprFactory.createValueExpression(elctx, "#{" + beanName + "}", Object.class).setValue(elctx, value);
   }

   public static Boolean parseBoolean(Object value) {
      if (value instanceof String) {
         return Boolean.parseBoolean((String) value);
      } else if (value instanceof Boolean) {
         return (Boolean) value;
      } else if (value != null) {
         return Boolean.parseBoolean(value.toString());
      }
      return false;
   }

   public static Integer parseInteger(Object value) {
      if (value instanceof String) {
         return Integer.parseInt((String) value);
      } else if (value instanceof Integer) {
         return (Integer) value;
      } else if (value != null) {
         return Integer.parseInt(value.toString());
      }
      return null;
   }

   public static Long parseLong(Object value) {
      if (value instanceof String) {
         return Long.parseLong((String) value);
      } else if (value instanceof Long) {
         return (Long) value;
      } else if (value != null) {
         return Long.parseLong(value.toString());
      }
      return null;
   }

   public static UIComponent getCurrentComponent() {
      return getCurrentComponent(FacesContext.getCurrentInstance());
   }

   public static UIComponent getCurrentComponent(FacesContext context) {
      return UIComponent.getCurrentComponent(context);
   }
//   public static void setSortEventToConfig(Config config) {
//      DataTable dt = (DataTable) getCurrentComponent();
//      config.setColumnSort(dt.getSortField(), dt.getSortOrder());
//   }
}
