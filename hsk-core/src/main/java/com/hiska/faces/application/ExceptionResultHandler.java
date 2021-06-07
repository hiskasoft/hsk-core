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
package com.hiska.faces.application;

import com.hiska.faces.MessageUtil;
import com.hiska.result.Message;
import com.hiska.result.MessageBuilder;
import java.util.Iterator;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

/**
 * @author Willyams Yujra
 */
@SuppressWarnings("deprecation")
public class ExceptionResultHandler extends ExceptionHandlerWrapper {

    private final ExceptionHandler delegate;

    public ExceptionResultHandler(ExceptionHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return delegate;
    }

    @Override
    public void handle() throws FacesException {
        ExceptionHandlerWrapper a = (ExceptionHandlerWrapper) delegate;
        delegate.handle();
        final Iterator<ExceptionQueuedEvent> queue = getUnhandledExceptionQueuedEvents().iterator();
        while (queue.hasNext()) {
            ExceptionQueuedEvent item = queue.next();
            System.out.println("--->" + item);
            ExceptionQueuedEventContext exceptionQueuedEventContext = (ExceptionQueuedEventContext) item.getSource();
            try {
                Throwable root = exceptionQueuedEventContext.getException();
                Throwable cause = root.getCause();
                String text = root.getMessage();
                text = text == null ? "" : text;
                if (root instanceof FacesException && text.contains("actionListener=") && cause instanceof ELException) {
                    Message message = MessageBuilder.createMessageFatal(cause.getCause());
                    MessageUtil.processMessage(message);
                } else {
                    Message message = MessageBuilder.createMessageFatal(root);
                    MessageUtil.processMessage(message);
                }
            } finally {
                queue.remove();
            }
        }
    }
}
