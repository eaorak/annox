package com.adenon.sp.kernel.dialog;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import com.adenon.sp.kernel.event.Direction;
import com.adenon.sp.kernel.event.Event;
import com.adenon.sp.kernel.event.SubsystemType;
import com.adenon.sp.kernel.event.message.AbstractMessage;
import com.adenon.sp.kernel.event.message.IMessage;
import com.adenon.sp.kernel.execution.IContainer;
import com.adenon.sp.kernel.profile.ISecurityContext;


public class Dialog implements IDialog, Serializable {

    private static final long             serialVersionUID = 1L;
    //
    private static int                    NULL_SERVER_ID   = -1;
    private transient static IDialogCache dialogCache;
    private transient static int          serverId         = NULL_SERVER_ID;
    //
    private final String                  id;
    private String                        endpoint;
    private ISecurityContext              securityContext;
    private int                           enablerId        = IDialog.NOT_AN_ENABLER;
    private long                          lifeTime         = IDialog.DEFAULT_LIFE_TIME;
    private long                          modifiedTime;
    private DialogType                    type             = DialogType.STATELESS;
    private DialogState                   state            = DialogState.OPEN;
    //
    private IDialogHandler                handler          = null;
    private Direction                     direction        = null;
    private transient EventHolder[]       eventHolders     = new EventHolder[Direction.count()];
    private IContainer                    container;
    private final AtomicInteger           messageOrder     = new AtomicInteger();

    public static void initialize(int serverId,
                                  IDialogCache dialogCache) {
        if (Dialog.serverId != NULL_SERVER_ID) {
            throw new RuntimeException("Server id is already set as [" + serverId + "] !");
        }
        if (serverId < 0) {
            throw new RuntimeException("Invalid server id [" + serverId + "] !");
        }
        Dialog.serverId = serverId;
        Dialog.dialogCache = dialogCache;
    }

    public Dialog() {
        if (serverId == -1) {
            throw new IllegalStateException("Not set Own server id in " + Dialog.class.getSimpleName() + " !");
        }
        this.id = IdGenerator.newId();
        // Initialize event processors
        for (int i = 0; i < this.eventHolders.length; i++) {
            this.eventHolders[i] = new EventHolder(Direction.getDirection(i));
        }
    }

    // --+-+-+-+-+-+ Event Creation +-+-+-+-+-+--

    public static Event beginEvent(IMessage eventMessage,
                                   SubsystemType origin,
                                   SubsystemType destination,
                                   Direction direction,
                                   int enablerId,
                                   DialogType status,
                                   long lifeTime) {
        Dialog dialog = new Dialog();
        return dialog.begin(eventMessage, origin, destination, direction, enablerId, status, lifeTime);
    }

    @Override
    public Event begin(IMessage eventMessage,
                       SubsystemType origin,
                       SubsystemType destination,
                       Direction direction,
                       int enablerId,
                       DialogType type,
                       long lifeTime) {
        this.direction = direction;
        this.enablerId = enablerId;
        this.lifeTime = lifeTime;
        this.modifiedTime = System.currentTimeMillis();
        this.type = type;
        Event event = this.createEvent(direction, eventMessage);
        dialogCache.putDialog(this);
        return event;
    }

    @Override
    public Event continuee(IMessage eventMessage,
                           Direction direction) {
        if (!this.state.isOpen()) {
            throw new RuntimeException("Dialog [" + this + "] was already expired by platform !");
        }
        this.modifiedTime = System.currentTimeMillis();
        return this.createEvent(direction, eventMessage);
    }

    @Override
    public Event end(IMessage eventMessage,
                     Direction direction) {
        if (!this.state.isOpen()) {
            throw new RuntimeException("Dialog [" + this + "] was already expired by platform !");
        }
        Event event = this.createEvent(direction, eventMessage);
        this.modifiedTime = System.currentTimeMillis();
        return event;
    }

    private Event createEvent(Direction direction,
                              IMessage message) {
        String messageId = IdGenerator.newMessageId(this.id, this.messageOrder.incrementAndGet());
        ((AbstractMessage) message).setSessionId(messageId);
        int index = this.eventHolders[direction.getId()].increaseEventCount();
        Event event = new Event(index, this.id + ":" + index, direction, this);
        event.setMessage(message);
        //
        this.addEventForProcess(event, direction.processor());
        //
        return event;
    }

    // -+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    /**
     * Add event to the relevant event list and return if event should be added to queue or not.
     * 
     * @param event Event to be added to list
     * @return if event should be added to queue or not
     */
    public void addEventForProcess(Event event,
                                   Direction.Processor processor) {
        if (!this.state.isOpen()) {
            throw new RuntimeException("Dialog is closed ! " + this.toString());
        }
        // OUT events will be proceseed directly in stream.
        if (event.getDirection() == Direction.TOWARDS_OUT) {
            return;
        }
        this.eventHolders[event.getDirection().getId()].addEvent(event, processor);
    }

    /**
     * Get next event of the desired direction.
     * 
     * @param direction Direction to be searched.
     * @return the event
     */
    public Event getNextEvent(Direction direction,
                              Direction.Processor processor) {
        return this.eventHolders[direction.getId()].getNextEvent(processor);
    }

    public boolean checkTerminate() {
        boolean close = true;
        switch (this.type) {
            case STATEFUL:
                for (EventHolder holder : this.eventHolders) {
                    if (holder.canTerminate()) {
                        dialogCache.removeDialog(this);
                    } else {
                        close = false;
                        break;
                    }
                }
                break;
            case STATELESS:
                break;
            default:
                throw new RuntimeException("Invalid dialog type [" + this.type + "] !");
        }
        if (close) {
            this.closeDialog();
        }
        return close;
    }

    private void closeDialog() {
        this.state = DialogState.CLOSED;
        this.securityContext = null;
        this.type = null;
        this.handler = null;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public long getLifeTime() {
        return this.lifeTime;
    }

    public void setLifeTime(long lifeTime) {
        this.lifeTime = lifeTime;
    }

    @Override
    public DialogState getState() {
        return this.state;
    }

    @Override
    public boolean isExpiredByNow() {
        return (System.currentTimeMillis() - this.modifiedTime) >= this.lifeTime;
    }

    public void setModifiedTime(long creationTime) {
        this.modifiedTime = creationTime;
    }

    @Override
    public ISecurityContext getSecurityContext() {
        return this.securityContext;
    }

    @Override
    public void setSecurityContext(ISecurityContext securityContext) {
        assert this.securityContext == null;
        this.securityContext = securityContext;
    }

    @Override
    public int getEnablerId() {
        return this.enablerId;
    }

    public void setEnablerId(int enablerId) {
        this.enablerId = enablerId;
    }

    @Override
    public String getEndpoint() {
        return this.endpoint;
    }

    @Override
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public DialogType getType() {
        return this.type;
    }

    public void setType(DialogType type) {
        this.type = type;
    }

    @Override
    public void setHandler(IDialogHandler handler) {
        assert this.handler == null;
        this.handler = handler;
    }

    @Override
    public IDialogHandler getHandler() {
        return this.handler;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public IContainer getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(IContainer container) {
        this.container = container;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[DIALOG #dId:");
        sb.append(this.id).append(") Type:").append(this.type).append(" State:").append(this.state).append(" endpoint:'").append(this.endpoint).append("' ]");
        return sb.toString();
    }

}