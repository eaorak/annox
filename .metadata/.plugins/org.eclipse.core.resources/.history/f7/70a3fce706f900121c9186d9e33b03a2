package com.adenon.sp.kernel.event;

public enum Direction {

    TOWARDS_IN(0, "IN", Processor.EXECUTION), //
    TOWARDS_OUT(1, "OUT", Processor.CHANNELS), //
    INTERNAL(2, "INTERNAL", Processor.NONE);

    public static enum Processor {
        NONE,
        CHANNELS,
        EXECUTION;
    }

    private int       id;
    private String    direction;
    private Processor processor;

    private Direction(int id,
                      String direction,
                      Processor processor) {
        this.id = id;
        this.direction = direction;
        this.processor = processor;
    }

    public int getId() {
        return this.id;
    }

    public static int count() {
        return values().length;
    }

    public static Direction getDirection(int id) {
        for (Direction dir : values()) {
            if (dir.getId() == id) {
                return dir;
            }
        }
        return null;
    }

    public String getDirection() {
        return this.direction;
    }

    public Processor processor() {
        return this.processor;
    }

}
