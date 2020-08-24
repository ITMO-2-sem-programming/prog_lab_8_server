package ru.itmo.core.common.exchange.response;


import ru.itmo.core.common.exchange.request.ClientRequest;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.PriorityQueue;


public class Response {


    private PriorityQueue<ClientRequest> commandQueue
            = new PriorityQueue<>();

    private InetAddress clientInetAddress;
    private int clientPort;


    public Response(PriorityQueue<ClientRequest> commandQueue) {
        this.commandQueue = commandQueue;
    }


    public Response(ClientRequest command) {
        addCommand(command);
    }


    public Response(ClientRequest... commands) {

        Arrays.stream(commands).forEach(
                this::addCommand
        );
    }


    public void addCommand(ClientRequest command) {
        commandQueue.add(command);
    }



    public InetAddress getClientInetAddress() {
        return clientInetAddress;
    }


    public void setClientInetAddress(InetAddress clientInetAddress) {
        this.clientInetAddress = clientInetAddress;
    }


    public int getClientPort() {
        return clientPort;
    }


    public void setClientPort(int clientPort) {

        if (clientPort < 0)
            throw new IllegalArgumentException(String.format(
                    "Invalid port : '%s'.",
                    clientPort)
            );

        this.clientPort = clientPort;

    }

    public PriorityQueue<ClientRequest> getCommandQueue() {
        return commandQueue;
    }



}

