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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiska.faces.cc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Willyams Yujra
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class Menu implements Comparable<Menu> {
   private Long id;
   private Integer order;
   private String name;
   private String description;
   private String icon;
   private String rol;
   private String path;
   private String target;
   private List<Menu> items;

   public void addItem(Menu item) {
      if (items == null) {
         items = new ArrayList<>();
      }
      item.path = path;
      items.add(item);
   }

   public void removeItem(Menu item) {
      if (items != null) {
         items.remove(item);
      }
   }

   public void clearItem() {
      if (items != null) {
         items.clear();
      }
   }

   public Map<String, Menu> getItemAsMap() {
      if (items == null) {
         return Collections.emptyMap();
      }
      return items.stream().collect(Collectors.toMap(it -> it.target, it -> it));
   }

   public void setPath(String path) {
      this.path = path;
      if (items != null) {
         items.forEach(it -> it.path = path);
      }
   }

   public String getPathTarget() {
      if (!isIsLink()) {
         return "#";
      }
      return path != null ? path + target : target;
   }

   public boolean isIsLink() {
      return items == null || items.isEmpty();
   }

   public String getType() {
      return isIsLink() ? "link" : "group";
   }

   @Override
   public int compareTo(Menu t) {
      return order - t.order;
   }
}
