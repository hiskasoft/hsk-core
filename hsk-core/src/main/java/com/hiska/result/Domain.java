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
@EqualsAndHashCode(of = {"value"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Domain implements Serializable {
   private String code;
   private String classifier;
   private String value;

   public static Domain create(String code) {
      return code == null ? null : create(code, "NONE");
   }

   public static Domain create(String code, String classifier) {
      return new Domain(code, classifier, null);
   }
}
