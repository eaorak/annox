package com.adenon.sp.configuration.test;

import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class ConfServerTest {

    private MBeanServer mbs;

    public ConfServerTest() throws Exception {
        // Get the platform MBeanServer
        // this.mbs = ManagementFactory.getPlatformMBeanServer();
        this.mbs = MBeanServerFactory.createMBeanServer();
        String path = "service:jmx:rmi:///jndi/rmi://localhost:9999/server";
        JMXServiceURL url = new JMXServiceURL(path);
        LocateRegistry.createRegistry(9999);
        Map<String, String> map = new HashMap<String, String>();
        JMXConnectorServer server = JMXConnectorServerFactory.newJMXConnectorServer(url, map, this.mbs);
        server.start();
    }

    // Utility method: so that the application continues to run
    private static void waitForEnterPressed() {
        try {
            System.out.println("Press  to continue...");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String argv[]) throws Exception {
        ConfServerTest sample = new ConfServerTest();
        System.out.println("SimpleAgent is running...");
        ConfServerTest.waitForEnterPressed();
    }

}
