package ru.itmo.core.common.exchange.request;



import ru.itmo.core.common.exchange.User;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.PriorityQueue;


public class Request implements Serializable {


    private User user;

    private PriorityQueue<ClientCommand> commandQueue
            = new PriorityQueue<>();

    private InetAddress clientInetAddress;
    private int clientPort;


    public Request(PriorityQueue<ClientCommand> commandQueue) {
        this.commandQueue = commandQueue;
    }


    public Request(ClientCommand command) {
        addCommand(command);
    }


    public Request(ClientCommand... commands) {

        Arrays.stream(commands).forEach(
                this::addCommand
        );
    }


    public void addCommand(ClientCommand command) {
        commandQueue.add(command);
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
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
