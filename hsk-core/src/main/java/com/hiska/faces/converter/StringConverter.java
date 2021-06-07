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
package com.hiska.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * @author Willyams Yujra
 */
public class StringConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext contect, UIComponent component, String value) {
        System.out.println("getAsObject--->" + this + " : " + value);
        return value;
    }

    @Override
    public String getAsString(FacesContext contect, UIComponent component, Object object) {
        System.out.println("getAsString--->" + this + " : " + object);
        return object != null ? object.toString() : null;
    }
}
