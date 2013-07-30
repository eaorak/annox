package com.adenon.sp.kernel.execution.internal;

import com.adenon.sp.kernel.event.Event;
import com.adenon.sp.kernel.event.message.IMessage;
import com.adenon.sp.kernel.execution.IRequest;
import com.adenon.sp.kernel.execution.ISession;

public class Request implements IRequest {

    private ISession session;
    private IMessage message;

    // TODO : Session ?
    public Request(Event event,
                   ISession session) {
        this.message = event.getMessage();
        this.session = session;
    }

    @Override
    public IMessage getMessage() {
        return this.message;
    }

    @Override
    public ISession getSession() {
        return this.session;
    }

}