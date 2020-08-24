package ru.itmo.core.common.exchange.response;


import ru.itmo.core.common.exchange.request.ClientCommand;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.PriorityQueue;


public class Response {


    private PriorityQueue<ClientCommand> commandQueue
            = new PriorityQueue<>();

    private InetAddress clientInetAddress;
    private int clientPort;


    public Response(PriorityQueue<ClientCommand> commandQueue) {
        this.commandQueue = commandQueue;
    }


    public Response(ClientCommand command) {
        addCommand(command);
    }


    public Response(ClientCommand... commands) {

        Arrays.stream(commands).forEach(
                this::addCommand
        );
    }


    public void addCommand(ClientCommand command) {
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

    public PriorityQueue<ClientCommand> getCommandQueue() {
        return commandQueue;
    }



}

