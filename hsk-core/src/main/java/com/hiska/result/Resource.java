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
package com.hiska.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@lombok.Getter
@lombok.Setter
@lombok.ToString
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class Resource implements Serializable {
   private String path;
   private String description;
   private String rol;
   private List<Resource> items;

   public void addItem(Resource it) {
      if (items == null) {
         items = new ArrayList<>();
      }
      items.add(it);
   }

   public void addAllItem(List<Resource> its) {
      if (items == null) {
         items = new ArrayList<>();
      }
      items.addAll(its);
   }

   public Map<String, Resource> getItemAsMap() {
      if (items == null) {
         return Collections.emptyMap();
      }
      return items.stream().collect(Collectors.toMap(it -> it.path, it -> it));
   }

   public Resource findRecursoByRuta(String value) {
      if (value == null || value.isEmpty()) {
         return null;
      }
      if (value.equals(path)) {
         return this;
      }
      if (items == null) {
         return null;
      }
      for (Resource item : items) {
         if (item != null) {
            item = item.findRecursoByRuta(value);
            if (item != null) {
               return item;
            }
         }
      }
      return null;
   }
}
