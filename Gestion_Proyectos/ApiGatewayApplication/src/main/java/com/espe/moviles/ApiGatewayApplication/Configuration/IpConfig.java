package com.espe.moviles.ApiGatewayApplication;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class IpConfig {

    @PostConstruct
    public void init() {
        String ip = getLocalIp();
        System.setProperty("MY_IP", ip); // Establecer la variable en tiempo de ejecución
        System.out.println("MY_IP set to: " + ip);
    }

    public static String getLocalIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "127.0.0.1"; // Fallback a localhost si no encuentra otra IP
    }
}
