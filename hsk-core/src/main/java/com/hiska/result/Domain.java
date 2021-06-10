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
package com.hiska.result;

import java.io.Serializable;
import javax.xml.bind.annotation.*;
import lombok.*;

/**
 * Trust Data
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Domain implements Serializable {
   public static enum Type {
      LIST,
      TREE
   }

   public static final String NONE = "NONE";
   private String name;
   private String classifier;
   private Type type;

   public static Domain of(String code) {
      return code == null ? null : of(code, NONE);
   }

   public static Domain of(String code, Type type) {
      return code == null ? null : of(code, NONE, type);
   }

   public static Domain of(String code, String classifier) {
      return new Domain(code, classifier, Type.LIST);
   }

   public static Domain of(String code, String classifier, Type type) {
      return new Domain(code, classifier, type);
   }
}
