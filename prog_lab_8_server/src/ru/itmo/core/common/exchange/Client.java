package ru.itmo.core.common.exchange;

import java.net.InetAddress;



public class Client {


    private InetAddress clientAddress;
    private int clientPort;



    public Client(InetAddress clientAddress, int clientPort) {
        setClientAddress(clientAddress);
        setClientPort(clientPort);
    }




    public InetAddress getClientAddress() {
        return clientAddress;
    }


    private void setClientAddress(InetAddress clientAddress) {

        if (clientAddress == null)
            throw new IllegalArgumentException(
                    "Invalid client inet address : 'null'."
            );

        this.clientAddress = clientAddress;
    }


    public int getClientPort() {
        return clientPort;
    }


    private void setClientPort(int clientPort) {

        if (clientPort < 0)
            throw new IllegalArgumentException(String.format(
                    "Invalid client post : '%s'.",
                    clientPort)
            );

        this.clientPort = clientPort;
    }



}
