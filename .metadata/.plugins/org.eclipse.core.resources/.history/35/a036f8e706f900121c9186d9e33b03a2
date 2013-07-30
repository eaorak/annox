package com.adenon.sp.kernel.event.message;

public abstract class AbstractMessage implements IMessage {

    protected MessageType type;
    protected MessageKind messageKind = MessageKind.SIMPLE;
    protected String      id;
    protected String      sessionId;

    public AbstractMessage(MessageType type) {
        this.type = type;
    }

    @Override
    public final MessageType getType() {
        return this.type;
    }

    @Override
    public final MessageKind getMessageKind() {
        return this.messageKind;
    }

    public final void setType(MessageType type) {
        this.type = type;
    }

    public final void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public final String getSessionId() {
        return this.sessionId;
    }

}