package com.adenon.sp.kernel.dialog;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private static int           NULL_SERVER_ID      = -1;
    private transient static int serverId            = NULL_SERVER_ID;
    private static AtomicLong    protocolIdGenerator = new AtomicLong();

    public static String newId() {
        return serverId + ":" + protocolIdGenerator.incrementAndGet();
    }

    public static String newMessageId(String dialogId,
                                      int messageOrder) {
        return dialogId + "-" + messageOrder;
    }

}