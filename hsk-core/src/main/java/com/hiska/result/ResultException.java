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

import javax.ejb.ApplicationException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Willyams Yujra
 */
@Getter
@Setter
@ToString
@ApplicationException(rollback = true)
public class ResultException extends RuntimeException {
   private Result result;

   public ResultException() {
   }

   public ResultException(Throwable cause) {
      super(cause);
   }

   public ResultException(Result result) {
      this.result = result;
   }

   public ResultException(String message, Result result) {
      super(message);
      this.result = result;
   }

   public ResultException(Result result, Throwable cause) {
      super(cause);
      this.result = result;
   }
}
