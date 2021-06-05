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

import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class Auditor implements Serializable {
   private Entry created;
   private Entry updated;
   private Entry deleted;
   private Long version;

   @Data
   @ToString
   public static class Entry {
      private Date at;
      private String by;
   }
}
