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
package com.hiska.faces.bean;

import com.hiska.faces.application.FacesResultMessage;
import com.hiska.result.Pagination;
import com.hiska.result.Filter;
import com.hiska.result.Message;
import com.hiska.result.Resource;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * @author Willyams Yujra
 */
@RequestScoped
@ManagedBean(name = "_help")
@lombok.Getter
@lombok.Setter
@lombok.ToString
@SuppressWarnings("unchecked")
public class HelpBean {
   private static final Logger LOGGER = Logger.getLogger(HelpBean.class.getName());
   private static final List<SelectItem> STRING = asSelectItem(Filter.Expr.none, Filter.Expr.eq, Filter.Expr.neq, Filter.Expr.like);
   private static final List<SelectItem> NUMBER = asSelectItem(Filter.Expr.none, Filter.Expr.eq, Filter.Expr.neq, Filter.Expr.bw);
   private static final List<SelectItem> DATE = asSelectItem(Filter.Expr.none, Filter.Expr.eq, Filter.Expr.neq, Filter.Expr.bw);
   private static final List<SelectItem> NULL = asSelectItem(Filter.Expr.isNull, Filter.Expr.notNull);
   private static final List<SelectItem> INEQ = asSelectItem(Filter.Expr.lt, Filter.Expr.lte, Filter.Expr.gt, Filter.Expr.gte);

   private static List<SelectItem> asSelectItem(Filter.Expr... es) {
      List<SelectItem> values = new ArrayList<>();
      for (Filter.Expr e : es) {
         values.add(new SelectItem(e.name(), e.oper()));
      }
      return values;
   }

   public Filter createFilterValue(Filter value) {
      return value == null ? new Filter() : value;
   }

   public Object createItemsValue(String convert, Object items, boolean withNull, boolean withIneq) {
      if (items == null) {
         items = new ArrayList<>();
         List<SelectItem> values = (ArrayList) items;
         convert = convert == null ? "String" : convert.replace("javax.faces.", "");
         if ("date".equalsIgnoreCase(convert) || "time".equalsIgnoreCase(convert)) {
            values.addAll(DATE);
         } else if ("integer".equalsIgnoreCase(convert) || "long".equalsIgnoreCase(convert) || "double".equalsIgnoreCase(convert) || "float".equalsIgnoreCase(convert)) {
            values.addAll(NUMBER);
         } else {
            values.addAll(STRING);
         }
         if (withNull) {
            values.addAll(NULL);
         }
         if (withIneq) {
            values.addAll(INEQ);
         }
      }
      return items;
   }

   public void noneListener() {
      LOGGER.log(Level.FINE, "noneListener");
   }

   public int[] getPages(Pagination pagination, int size) {
      return getPages(pagination.getIndex(), pagination.getLength(), size);
   }

   public int[] getPages(int index, int limit, int size) {
      if (0 < limit && limit < size) {
         size = limit;
      }
      int d1 = (size - 1) / 2;
      int d2 = size - d1;
      int start = index - d1;
      int end = index + d2;
      if (start < 1) {
         start = 1;
      } else if (0 < limit && limit < end) {
         start = limit - size + 1;
      }
      int indexs[] = new int[size];
      for (int i = 0; i < indexs.length; i++) {
         indexs[i] = start + i;
      }
      return indexs;
   }

//    public void sortFunction(Pagination pagination) {
//        setSortFunction(pagination);
//    }
//    public void setSortFunction(Pagination pagination) {
//        DataTable dt = (DataTable) ContextUtil.getCurrentComponent();
//        pagination.setColumnSort(dt.getSortField(), dt.getSortOrder());
//    }
   public void callListener(Runtime... runner) {
      // runner
   }

   public void setEquals(Filter filter) {
      filter.setExpr(Filter.Expr.eq);
   }

   public void changeValue(Filter filter) {
      if (filter != null) {
         Object value = filter.getValue();
         filter.setExpr(value == null ? Filter.Expr.none : Filter.Expr.eq);
      }
   }

   public Message assertError(Object o) {
      LOGGER.log(Level.FINE, "Assert Error: {0}", o);
      Message message;
      if (o instanceof Message) {
         message = (Message) o;
      } else // if(o == null)
      {
         message = new Message();
         message.setCode("000");
         message.setTitle("Error no reportado");
         message.setDescription("Error no reportado o no codificado en las excepciones");
         message.addCause("No se reporto ningun error a visualizar.");
         message.addCause("La exception no esta codificada.");
         message.addCause("Acceso directo a la pagina de error.");
         message.setAction("Consulte con el adminstrador del sistema.");
         message.setLevel(Message.Level.warn);
      }
      return message;
   }

   private Resource recurso;

   public String hasAccessViewId(String viewId, Resource root, List<String> roles) {
      LOGGER.log(Level.FINE, "Verificando acceso: viewId:{0}  roles:{1} recursos:{2}", new Object[]{viewId, roles, root});
      if (root == null || roles == null) {
         return "ERROR";
      }
      if (viewId == null || viewId.isEmpty()) {
         return "ERROR";
      }
      recurso = root.findRecursoByRuta(viewId);
      if (recurso == null) {
         return "NOT_RESOURCE";
      }
      String rol = recurso.getRol();
      if ("NONE".equals(rol)) {
         return "ALLOW_ACCESS";
      }
      boolean exist = roles.contains(rol);
      return exist ? "ALLOW_ACCESS" : "DENY_ACCESS";
   }

   public Resource getRecurso() {
      if (recurso == null) {
         recurso = new Resource("???", "???", "???", null);
      }
      return recurso;
   }

   public List<FacesResultMessage> getFacesResultMessage() {
      return getFacesResultMessage(FacesContext.getCurrentInstance());
   }

   public List<FacesResultMessage> getFacesResultMessage(FacesContext context) {
      List<FacesResultMessage> list = new ArrayList<>();
      for (FacesMessage fm : context.getMessageList()) {
         FacesResultMessage item = fm instanceof FacesResultMessage ? (FacesResultMessage) fm : new FacesResultMessage(fm);
         list.add(item);
      }
      return list;
   }

   private final String MESSAGE_HISTORY = "MESSAGE_HISTORY";

   public List<FacesResultMessage> getFacesResultMessageHistory() {
      return getFacesResultMessageHistory(FacesContext.getCurrentInstance());
   }

   public List<FacesResultMessage> getFacesResultMessageHistory(FacesContext context) {
      List<FacesResultMessage> history = (List<FacesResultMessage>) context.getExternalContext().getSessionMap().get(MESSAGE_HISTORY);
      if (history == null) {
         history = new LimitedQueue(10);
         context.getExternalContext().getSessionMap().put(MESSAGE_HISTORY, history);
      }
      for (FacesResultMessage item : getFacesResultMessage()) {
         history.add(item);
      }
      return new ArrayList<>(history);
   }
}
