package com.adenon.sp.kernel.event.message.rpc;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.adenon.sp.kernel.event.message.AbstractMessage;
import com.adenon.sp.kernel.event.message.CoreMessages;
import com.adenon.sp.kernel.event.message.MessageKind;
import com.adenon.sp.kernel.event.message.MessageType;
import com.adenon.sp.kernel.execution.IExecution;


public class RPCMessage extends AbstractMessage implements Serializable {

    public static int         EVENT_MESSAGE_ID = 1;
    private static final long serialVersionUID = 1L;
    protected String          serviceName;
    protected String          methodName;
    protected Object[]        parameters;
    protected Object          returnValue;
    private final Object      notifyObject     = new Object();
    //
    private RPCType           rpcType;
    private transient Method  method;
    private IExecution        returnInf;
    private boolean           exceptionOccured = false;
    private Exception         exception;

    public RPCMessage() {
        super(MessageType.BEGIN);
        this.messageKind = MessageKind.RPC;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return this.parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Object getReturnValue() {
        return this.returnValue;
    }

    public void setReturnValue(Object retVal) {
        this.returnValue = retVal;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public RPCType getRpcType() {
        return this.rpcType;
    }

    public void setRpcType(RPCType rpcType) {
        this.rpcType = rpcType;
    }

    @Override
    public CoreMessages getId() {
        return CoreMessages.RPC_MESSAGE;
    }

    public void setReturn(IExecution returnInf) {
        this.returnInf = returnInf;
    }

    public IExecution getReturn() {
        return this.returnInf;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[RPC MSG # Srv: '")
           .append(this.serviceName)
           .append("' ReqId: ")
           .append(" MethodName: '")
           .append(this.methodName)
           .append("' RPCType: ")
           .append(this.rpcType);
        if (this.parameters != null) {
            str.append(" ParamCount : ").append(this.parameters.length);
        }
        str.append("]");
        return str.toString();
    }

    public Object getNotifyObject() {
        return this.notifyObject;
    }

    public boolean isExceptionOccured() {
        return this.exceptionOccured;
    }

    public void setExceptionOccured(boolean exceptionOccured) {
        this.exceptionOccured = exceptionOccured;
    }

    public Exception getException() {
        return this.exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
