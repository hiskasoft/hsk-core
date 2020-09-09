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

import com.hiska.faces.cc.Menu;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "_config")
@lombok.Getter
@lombok.Setter
@lombok.ToString
@SuppressWarnings("unchecked")
public class ConfigBean {
   private String title = "Sistema de Atencion de Servicios";
   private String template = "/resources/template/coreui.xhtml";
   private String styleClass = "dev";
   private List<Menu> menus;
   private Date today = new Date();
   private String url = "https://hiskasoft.com";

   public void addMenu(Menu value) {
      if (menus == null) {
         menus = new ArrayList<>();
      }
      menus.add(value);
   }
}
