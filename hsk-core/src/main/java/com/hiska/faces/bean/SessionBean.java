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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Willyams Yujra
 */
@SessionScoped
@ManagedBean(name = "_session")
@lombok.Getter
@lombok.Setter
@lombok.ToString
@SuppressWarnings("unchecked")
public class SessionBean {
   /**
    * Creates a new instance of SessionBean
    */
   public SessionBean() {
   }
}