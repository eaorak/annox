package com.adenon.sp.administration;

import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class JmxAdaptor {

    private static String      PORT_STR = "#PORT#";
    private static String      PATH     = "service:jmx:rmi:///jndi/rmi://localhost:#PORT#/server";
    private final String       path;
    private final int          port;
    private JMXConnectorServer jmxServer;

    public JmxAdaptor(final int port) {
        this.port = port;
        this.path = PATH.replace(PORT_STR, Integer.toString(port));
    }

    public MBeanServer start() throws Exception {
        final MBeanServer mbs = MBeanServerFactory.createMBeanServer();
        final JMXServiceURL url = new JMXServiceURL(this.path);
        LocateRegistry.createRegistry(this.port);
        final Map<String, String> map = new HashMap<String, String>();
        this.jmxServer = JMXConnectorServerFactory.newJMXConnectorServer(url, map, mbs);
        this.jmxServer.start();
        return mbs;
    }

}
