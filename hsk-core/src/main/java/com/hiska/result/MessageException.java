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

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MessageException extends Exception {

    private final MessageBuilder builder;

    public MessageException(Message internal) {
        super(internal.toString());
        this.builder = MessageBuilder.create(internal);
    }

    public MessageException(MessageBuilder internal) {
        this.builder = internal;
    }

    public MessageException(String message) {
        this(message, null);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
        this.builder = MessageBuilder.create(message)
                .exception(cause);
    }

    public Message get() {
        return builder.get();
    }

    public ResultException asResultException() {
        return ResultBuilder.create(builder.get())
                .asException();
    }
}
