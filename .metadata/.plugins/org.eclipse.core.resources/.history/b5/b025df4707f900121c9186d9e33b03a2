package com.adenon.sp.kernel.error;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

public class ErrorObject implements IError, Serializable {

    private static final long serialVersionUID = 1L;
    private final IBaseError  baseError;
    private final String      cause;
    private String            trace            = "N/A";

    public static IError create(IBaseError error,
                                String cause) {
        ErrorObject errorObject = new ErrorObject(error, cause);
        return errorObject;
    }

    public static IError create(IBaseError error,
                                Throwable t) {
        ErrorObject errorObject = new ErrorObject(error, t);
        return errorObject;
    }

    private ErrorObject(IBaseError baseError,
                        String cause) {
        this.baseError = baseError;
        this.cause = cause;
    }

    private ErrorObject(IBaseError baseError,
                        Throwable t) {
        this.baseError = baseError;
        this.cause = "Exception: " + t.getMessage();
        this.trace = this.getTraceOf(t);
    }

    private String getTraceOf(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        sw.flush();
        pw.flush();
        return pw.toString();
    }


    @Override
    public String cause() {
        return this.cause;
    }

    @Override
    public int code() {
        return this.baseError.code();
    }

    @Override
    public String description() {
        return this.baseError.description();
    }

    @Override
    public ErrorActions action() {
        return this.baseError.action();
    }

    @Override
    public String trace() {
        return this.trace;
    }

    @Override
    public String toString() {
        return "ErrorObject [baseError=" + this.baseError + ", cause=" + this.cause + "]";
    }


}
