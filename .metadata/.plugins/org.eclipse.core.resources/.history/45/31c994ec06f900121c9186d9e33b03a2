package com.adenon.sp.kernel.dialog;

import java.util.concurrent.TimeUnit;

import com.adenon.sp.kernel.event.Direction;
import com.adenon.sp.kernel.event.Event;
import com.adenon.sp.kernel.event.SubsystemType;
import com.adenon.sp.kernel.event.message.IMessage;
import com.adenon.sp.kernel.execution.IContainer;
import com.adenon.sp.kernel.execution.ISession;
import com.adenon.sp.kernel.profile.ISecurityContext;


public interface IDialog extends ISession {

    public static long DEFAULT_LIFE_TIME = TimeUnit.MINUTES.toMillis(2);
    public static long DIALOG_EXPIRY     = TimeUnit.MINUTES.toMillis(2);
    public static int  NOT_AN_ENABLER    = -1;

    DialogState getState();

    DialogType getType();

    IContainer getContainer();

    IDialogHandler getHandler();

    boolean isExpiredByNow();

    void setEndpoint(String serviceUniqueId);

    void setSecurityContext(ISecurityContext securityContext);

    void setHandler(IDialogHandler handler);

    void setContainer(IContainer container);

    //

    Event begin(IMessage eventMessage,
                SubsystemType origin,
                SubsystemType destination,
                Direction direction,
                int enablerUniqueId,
                DialogType status,
                long lifeTime);

    Event continuee(IMessage eventMessage,
                    Direction direction);

    Event end(IMessage eventMessage,
              Direction direction);

}
