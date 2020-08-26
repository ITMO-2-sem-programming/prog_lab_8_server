package ru.itmo.core.common.exchange;

import java.io.Serializable;
import java.net.InetAddress;



public class Client implements Serializable {


    private InetAddress address;
    private int port;



    public Client(InetAddress address, int port) {
        setAddress(address);
        setPort(port);
    }




    public InetAddress getAddress() {
        return address;
    }


    private void setAddress(InetAddress address) {

        if (address == null)
            throw new IllegalArgumentException(
                    "Invalid client inet address : 'null'."
            );

        this.address = address;
    }


    public int getPort() {
        return port;
    }


    private void setPort(int port) {

        if (port < 0)
            throw new IllegalArgumentException(String.format(
                    "Invalid client post : '%s'.",
                    port)
            );

        this.port = port;
    }



}
