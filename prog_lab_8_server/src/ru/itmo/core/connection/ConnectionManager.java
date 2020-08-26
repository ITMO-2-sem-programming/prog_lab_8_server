package ru.itmo.core.connection;

import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.request.ClientRequest;
import ru.itmo.core.common.exchange.request.Request;
import ru.itmo.core.common.exchange.response.Response;
import ru.itmo.core.common.exchange.response.ServerResponse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

// Server
public class ConnectionManager {

    private final DatagramSocket serverSocket;
    private DatagramPacket packet;

//    private InetAddress clientInetAddress;
//    private int clientPort;

    private int bufferSize;


    public ConnectionManager(int serverPort) {

        bufferSize = 65536;
        try {
            serverSocket = new DatagramSocket(serverPort);

        } catch (SocketException e) {
            throw new IllegalArgumentException("Error: Can't connect to specified port or get LocalHost address.");
        }

    }


//    public void send(byte[] bytes) throws IOException {
//        packet = new DatagramPacket(bytes, bytes.length, clientInetAddress, clientPort);
//        serverSocket.send(packet);
//    }


    public void sendResponse(ServerResponse response) throws IOException {

        send(
                response.getClient().getAddress(),
                response.getClient().getPort(),
                Serializer.toByteArray(response));

        System.out.println(
                "The response was sent." +
                "\n    Address : " + response.getClient().getAddress() +
                "\n    Port : .. " + response.getClient().getPort());

    }


    public void send(InetAddress clientInetAddress, int clientPort, byte[] bytes) throws IOException {

        packet = new DatagramPacket(bytes, bytes.length, clientInetAddress, clientPort);
        serverSocket.send(packet);

    }


    public void sendResponseAndCloseSocket(ServerResponse response) throws IOException {
        send(response.getClient().getAddress(), response.getClient().getPort(), Serializer.toByteArray(response));
        System.out.println(
                "The response was sent." +
                        "\n    Address : " + response.getClient().getAddress() +
                        "\n    Port : " + response.getClient().getPort());
        serverSocket.close();

    }


    public ClientRequest receiveRequest() throws IOException, ClassNotFoundException {

        byte[] buffer = new byte[bufferSize];

        packet = new DatagramPacket(buffer, buffer.length);
        serverSocket.receive(packet);


        System.out.println(
                "Received Request:" +
                "\n    Request address : " + packet.getAddress() +
                "\n    Request port : .. " + packet.getPort());

        ClientRequest request = (ClientRequest) Serializer.toObject(buffer);

        request.setClient(
                new Client(
                        packet.getAddress(),
                        packet.getPort()
                )
        );


        return request;

    }


    public int getBufferSize() {
        return bufferSize;
    }


    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}
