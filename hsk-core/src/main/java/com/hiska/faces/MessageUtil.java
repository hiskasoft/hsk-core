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
package com.hiska.faces;

import com.hiska.faces.application.FacesResultMessage;
import com.hiska.result.Message;
import com.hiska.result.Result;
import java.io.PrintStream;
import java.util.Collection;
import java.util.regex.Pattern;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * @author Willyams Yujra
 */
public final class MessageUtil {

    public static void addMessage(Severity severity, String summary, String detail) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        ELContext elctx = context.getELContext();
        Application jsfApp = context.getApplication();
        ExpressionFactory exprFactory = jsfApp.getExpressionFactory();
        if (summary != null && summary.startsWith("#{")) {
            summary = (String) exprFactory.createValueExpression(elctx, summary, String.class).getValue(elctx);
        }
        if (detail != null && detail.startsWith("#{")) {
            detail = (String) exprFactory.createValueExpression(elctx, detail, String.class).getValue(elctx);
        }
        context.addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public static void addMessage(String id, FacesMessage message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(id, message);
    }

    private static void processException(String summary, Throwable e) {
        Throwable root = e;
        int nivel = 0;
        while (root != null) {
            nivel++;
            printTrace(root, "^(bo|central)", System.err);
            // if (root instanceof MessageException) {
            // MessageException v = (MessageException) root;
            // v.changeSummary(summary);
            // addMessage(null, new FacesMessageException(v));
            // return;
            // }
            root = root.getCause();
        }
        addMessage(FacesMessage.SEVERITY_FATAL, "Fatal: ", "Vea el log de errores, para identificar la causa de nivel: " + nivel);
    }

    public static void printTrace(Throwable root, String expresion, PrintStream out) {
        Pattern p = Pattern.compile(expresion);
        out.println("Exception: " + root.getMessage());
        for (StackTraceElement te : root.getStackTrace()) {
            if (p.matcher(te.getClassName()).find()) {
                out.println("\t->" + te);
            }
        }
    }

    public static void warn(String detail, Exception e) {
        addMessage(FacesMessage.SEVERITY_WARN, "Advertencia: ", detail);
        processException("Causa: ", e);
    }

    public static void warn(String string) {
        addMessage(FacesMessage.SEVERITY_WARN, "Advertencia: ", string);
    }

    public static void info(String string, Exception e) {
        addMessage(FacesMessage.SEVERITY_INFO, "Info: ", string);
        processException("Causa: ", e);
    }

    public static void info(String string) {
        addMessage(FacesMessage.SEVERITY_INFO, "Info: ", string);
    }

    public static void error(String string, Exception e) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Error: ", string);
        processException("Causa: ", e);
    }

    public static void error(String string) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Error: ", string);
    }

    public static void exception(Exception e) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e.getMessage());
        processException("Causa: ", e);
    }

    public static void processResult(Result result) {
        if (result != null) {
            processMessages(result.getMessages());
        }
    }

    public static void processMessages(Collection<Message> messages) {
        if (messages != null) {
            messages.forEach(MessageUtil::processMessage);
        }
    }

    public static void processMessage(Message message) {
        if (message != null) {
            FacesResultMessage frm = new FacesResultMessage(message);
            addMessage(null, frm);
        }
    }
}
