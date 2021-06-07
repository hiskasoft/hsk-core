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

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * @author Willyams Yujra
 */
public class DateConverter implements Converter {

    private final SimpleDateFormat allows[] = {
        new SimpleDateFormat("dd/MM/yyyy"),
        new SimpleDateFormat("dd-MM-yyyy")
    };

    @Override
    public Object getAsObject(FacesContext contect, UIComponent component, String value) {
        for (SimpleDateFormat p : allows) {
            try {
                return p.parse(value);
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext contect, UIComponent component, Object object) {
        if (object instanceof Date) {
            Date date = (Date) object;
            return allows[0].format(date);
        }
        return null;
    }
}
