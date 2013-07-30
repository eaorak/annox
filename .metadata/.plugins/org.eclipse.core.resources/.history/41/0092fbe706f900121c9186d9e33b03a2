package com.adenon.sp.kernel.event.message;

public interface IMessage {

    Enum<? extends IMessageTypes> getId();

    String getSessionId();

    /**
     * Type : (BEGIN, CONTINUE, END, TERMINATE)
     */
    MessageType getType();

    /**
     * Kind : SIMPLE or RPC
     */
    MessageKind getMessageKind();

}