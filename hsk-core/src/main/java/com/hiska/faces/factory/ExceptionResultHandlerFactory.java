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
package com.hiska.faces.factory;

import com.hiska.faces.application.ExceptionResultHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * @author Willyams Yujra
 */
public class ExceptionResultHandlerFactory extends ExceptionHandlerFactory {

    private final ExceptionHandlerFactory delegate;

    public ExceptionResultHandlerFactory(ExceptionHandlerFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new ExceptionResultHandler(delegate.getExceptionHandler());
    }
}
