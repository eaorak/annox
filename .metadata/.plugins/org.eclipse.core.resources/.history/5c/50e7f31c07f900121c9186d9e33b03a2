package com.adenon.sp.kernel.common;

public enum UnitExecution {

    IDLE(0),
    PROCESSING(1),
    PROCESSED(2);

    private int id;

    private UnitExecution(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public boolean isExecutable() {
        return this == IDLE;
    }

    public boolean isProcessed() {
        return this == PROCESSED;
    }

}
