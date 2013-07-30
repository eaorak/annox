package com.adenon.sp.kernel.event;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

import com.adenon.sp.kernel.common.UnitExecution;
import com.adenon.sp.kernel.dialog.IDialog;
import com.adenon.sp.kernel.error.IError;
import com.adenon.sp.kernel.event.message.IMessage;
import com.adenon.sp.kernel.event.message.MessageType;
import com.adenon.sp.kernel.execution.IRequest;
import com.adenon.sp.kernel.execution.ISession;


public class Event implements IRequest, Serializable {

    private static final long                          serialVersionUID = 1L;
    private IMessage                                   message;
    private IDialog                                    dialog;
    private IError                                     error;
    private String                                     id;
    private int                                        index;
    private UnitExecution                              state            = UnitExecution.IDLE;
    private Direction                                  direction;
    private final AtomicReference<Direction.Processor> processor        = new AtomicReference<Direction.Processor>();

    @Deprecated
    public Event() {
        this.processor.set(Direction.Processor.NONE);
    }

    public Event(int index,
                 String id,
                 Direction direction,
                 IDialog dialog) {
        this.index = index;
        this.id = id;
        this.direction = direction;
        this.dialog = dialog;
    }

    @Override
    public IMessage getMessage() {
        return this.message;
    }

    public void setMessage(IMessage message) {
        this.message = message;
    }

    public IDialog getDialog() {
        return this.dialog;
    }

    @Override
    public ISession getSession() {
        return this.dialog;
    }

    public void setDialog(IDialog dialog) {
        this.dialog = dialog;
    }

    public IError getError() {
        return this.error;
    }

    public void setError(IError error) {
        this.error = error;
    }

    public int getIndex() {
        return this.index;
    }

    @Deprecated
    public void setIndex(int index) {
        this.index = index;
    }

    public String getId() {
        return this.id;
    }

    @Deprecated
    public void setId(String id) {
        this.id = id;
    }

    public UnitExecution getState() {
        return this.state;
    }

    public void setState(UnitExecution state) {
        this.state = state;
    }

    public boolean matches(MessageType type,
                           UnitExecution state) {
        return (this.getMessage().getType() == type) && (this.getState() == state);
    }

    public Direction getDirection() {
        return this.direction;
    }

    @Deprecated
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setProcessor(Direction.Processor processor) {
        this.processor.set(processor);
    }

    public Direction.Processor getProcessor() {
        return this.processor.get();
    }

    @Override
    public Event clone() {
        Event clone = new Event(this.getIndex(), this.getId(), this.getDirection(), this.getDialog());
        clone.setMessage(this.getMessage());
        clone.setDialog(this.getDialog());
        clone.setDirection(this.getDirection());
        clone.setError(this.getError());
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[EVENT # Id:")
           .append(this.id)
           .append(" DialogId:")
           .append(this.dialog.getId())
           .append(" Message#('")
           .append(this.message)
           .append("')")
           .append(" Dir:")
           .append(this.direction)
           .append(" State:")
           .append(this.state)
           .append(" ]");
        return str.toString();
    }

}