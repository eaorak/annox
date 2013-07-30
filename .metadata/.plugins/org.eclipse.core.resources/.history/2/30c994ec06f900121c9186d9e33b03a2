package com.adenon.sp.kernel.dialog;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.adenon.sp.kernel.common.UnitExecution;
import com.adenon.sp.kernel.event.Direction;
import com.adenon.sp.kernel.event.Event;
import com.adenon.sp.kernel.event.message.MessageType;


public class EventHolder {

    private static int                           EXPAND_SIZE = 10;
    private final Direction                      direction;
    private Event[]                              events      = new Event[EXPAND_SIZE];
    private final AtomicInteger                  eventCount  = new AtomicInteger(0);
    private final AtomicReference<UnitExecution> execState   = new AtomicReference<UnitExecution>();
    private Event                                lastEvent;

    public EventHolder(Direction direction) {
        this.direction = direction;
        this.execState.set(UnitExecution.IDLE);
    }

    /**
     * Add event
     * 
     * @param event
     * @return Whether if event is processable or not
     */
    public synchronized void addEvent(Event event,
                                      Direction.Processor processor) {
        event.setProcessor(processor);
        boolean executable = event.getState().isExecutable();
        if (!executable) {
            throw new RuntimeException("Invalid state for event : " + event + " !");
        }
        int index = event.getIndex();
        this.checkIndex(index);
        this.events[index] = event;
    }

    private void checkIndex(int index) {
        if (index > this.events.length) {
            int newSize = ((index / EXPAND_SIZE) + 1) * EXPAND_SIZE;
            this.events = Arrays.copyOf(this.events, newSize);
        }
    }

    public synchronized Event getNextEvent(Direction.Processor processor) {
        if ((this.lastEvent != null) && (this.lastEvent.getState() != UnitExecution.PROCESSED)) {
            return null;
        }
        this.execState.set(UnitExecution.PROCESSING);
        Event event = null;
        try {
            for (Event e : this.events) {
                if ((e == null) || (e.getProcessor() != processor)) {
                    event = null;
                    break;
                }
                UnitExecution state = e.getState();
                if (state == UnitExecution.IDLE) {
                    e.setState(UnitExecution.PROCESSING);
                    event = e;
                    break;
                } else if (state == UnitExecution.PROCESSING) {
                    event = null;
                    break;
                } else if (state == UnitExecution.PROCESSED) {
                    continue;
                }
            }
        } finally {
            if (event == null) {
                this.execState.set(UnitExecution.IDLE);
            }
            this.lastEvent = event;
        }
        return event;
    }

    /**
     * Event list can be terminated if the list contains a PROCESSED - END event.
     * 
     * @return List can be terminated or not
     */
    public boolean canTerminate() {
        boolean terminate = true;
        for (Event e : this.events) {
            terminate = (e == null) || e.matches(MessageType.END, UnitExecution.PROCESSED);
            if (terminate) {
                break;
            }
        }
        if (terminate) {
            for (int i = 0; i < this.events.length; i++) {
                this.events[i] = null;
            }
        }
        return terminate;
    }

    public int getEventCount() {
        return this.eventCount.get();
    }

    public int increaseEventCount() {
        return this.eventCount.getAndIncrement();
    }

    public Direction getDirection() {
        return this.direction;
    }

}
