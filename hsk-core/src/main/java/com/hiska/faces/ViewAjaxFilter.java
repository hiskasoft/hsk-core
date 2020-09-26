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
package com.hiska.faces;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * @author Willyams Yujra
 */
@WebFilter(filterName = "ViewAjaxFilter", urlPatterns = {"/*"})
public class ViewAjaxFilter implements Filter {
   // private String includeRender = "";
   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
      // includeRender =
      // filterConfig.getServletContext().getInitParameter("com.hiska.faces.partial.render");
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      doHttpFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
   }

   private final String PARTIAL_RENDER = "javax.faces.partial.render";
   private final String PARTIAL_AJAX = "javax.faces.partial.ajax";
   private final String HEADER_AJAX = "partial/ajax";

   public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      String facesRequest = request.getHeader("Faces-Request");
      boolean isFacesRequest = HEADER_AJAX.equals(facesRequest);
      String contentType = request.getHeader("Content-Type");
      boolean isMultipartFormData = contentType != null && contentType.startsWith("multipart/form-data");
      if (isMultipartFormData) {
         Collection<Part> parts = request.getParts();
         for (Part cur : parts) {
            if (PARTIAL_AJAX.equals(cur.getName())) {
               isFacesRequest = true;
               break;
            }
         }
      }
      if (isFacesRequest) {
         RequestWrapper wrapper = new RequestWrapper(request);
         String facesRender = wrapper.getParameter(PARTIAL_RENDER);
         facesRender = facesRender == null ? "" : facesRender;
         facesRender = facesRender + " message-flash config:message-history";
         wrapper.setParameter(PARTIAL_RENDER, facesRender);
         chain.doFilter(wrapper, response);
      } else {
         chain.doFilter(request, response);
      }
   }

   @Override
   public void destroy() {
   }

   class RequestWrapper extends HttpServletRequestWrapper {
      private final Hashtable<String, Object> localParams;

      public RequestWrapper(HttpServletRequest request) {
         super(request);
         localParams = new Hashtable<>(request.getParameterMap());
      }

      public void setParameter(String name, String value) {
         localParams.put(name, value);
      }

      public void setParameter(String name, String[] values) {
         localParams.put(name, values);
      }

      @Override
      public String getParameter(String name) {
         Object val = localParams.get(name);
         if (val instanceof String) {
            return (String) val;
         }
         if (val instanceof String[]) {
            String[] values = (String[]) val;
            return values[0];
         }
         return (val == null ? null : val.toString());
      }

      @Override
      public String[] getParameterValues(String name) {
         Object val = localParams.get(name);
         if (val instanceof String) {
            return new String[]{val.toString()};
         }
         if (val instanceof String[]) {
            return (String[]) val;
         }
         return (val == null ? null : new String[]{val.toString()});
      }

      @Override
      public Enumeration getParameterNames() {
         return localParams.keys();
      }

      @Override
      public Map getParameterMap() {
         return localParams;
      }
   }
}
