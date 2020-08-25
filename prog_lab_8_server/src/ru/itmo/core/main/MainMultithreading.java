package ru.itmo.core.main;


import com.sun.security.ntlm.Client;
import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.request.Request;
import ru.itmo.core.common.exchange.response.Response;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.MultidirectionalResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.UnidirectionalResponse;
import ru.itmo.core.connection.ConnectionManager;
import ru.itmo.core.connection.PortForwarder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.*;


/**
 * Runs the server in multithreading mode.
 */

// TODO: 24.08.2020 Пусть команды сами берут новый Connection из Main + инициализация коллекции
//  collection = main.getCollection() в конструкторе каждой команды, кой эо нужно

public class MainMultithreading {


    static int serverPort = 44231;

    static String dataBaseUrl; // Адрес БД с проброшенным портом
    static final String DB_USER = "s284704";
    static final String DB_PASS = "hxy284";

    static ConcurrentSkipListMap<java.lang.Integer, MusicBand> collection; // Коллекция
    
    static boolean isRunning;
    static final int sendResponsePoolCapacity = 8;

    static LinkedBlockingQueue<Request> requestsQueue;
    static LinkedBlockingQueue<UnidirectionalResponse> unidirectionalResponsesQueue;
    static LinkedBlockingQueue<MultidirectionalResponse> multidirectionalResponsesQueue;


    ArrayList<Client> clientsList = new ArrayList<>();

    static ConcurrentLinkedQueue<Connection> connectionsList; // Хранит подключения к БД

    static ConnectionManager connectionManagerReceiver; // Принимает Request на некоторый порт
    static ConnectionManager connectionManagerSender; // Посылает Response с другого порт



    public static void main(String[] args)  {

        if (args.length > 1) throw new IllegalArgumentException("Error: Gets only server port argument.");
        if (args.length == 1) {
            try {
                serverPort = java.lang.Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: Incorrect server port value. " +
                        "\nMust be: " +
                        "\n    Integer" +
                        "\n    50 000 <= server port <= 60 000");
            }
        }

        connectionManagerReceiver = new ConnectionManager(serverPort);
        connectionManagerSender = new ConnectionManager(serverPort + 1);

        connectionsList = new ConcurrentLinkedQueue<>();
        requestsQueue = new LinkedBlockingQueue<>();
        responsesQueue = new LinkedBlockingQueue<>();

        isRunning = true;


        try {
            
            dataBaseUrl = PortForwarder.forwardAnyPort(); // Пробрасывает порт
            collection = ru.itmo.main.DataBaseManager.loadCollectionFromDataBase(getConnection()); // Загружает коллекцию из БД
            System.out.println("Collection form db was loaded successfully!");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }


        runServer();
    }


    public static void runServer() {

        System.out.println("Server is running.");

        ForkJoinPool makeResponsePool = new ForkJoinPool();
        ExecutorService sendResponsePool = Executors.newFixedThreadPool(sendResponsePoolCapacity);


        Runnable sendResponseTask = () -> {

            Response responseToSend = responsesQueue.poll();

            if (responseToSend == null) return;

            try {
                connectionManagerSender.sendResponse(responseToSend);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        };


        Runnable makeResponseTask = () -> {

            Response responseToMake;
            Connection currentConnection = getConnection();

            Request requestToProcess = requestsQueue.poll();

            if (requestToProcess == null) {
                return;
            }

            if (requestToProcess.getRequestType() == ExchangeType.COMMAND_EXCHANGE) {
                responseToMake = new Response(
                        CommandManager.executeCommand(collection, currentConnection, requestToProcess.getCommandRequest())
                );

            } else if (requestToProcess.getRequestType() == ExchangeType.SERVICE_EXCHANGE) {
                responseToMake = new Response(
                        UserManager.executeServiceRequest(currentConnection, requestToProcess.getServiceRequest())
                );

            } else {
                throw new IllegalArgumentException("Request type is neither 'SERVICE_EXCHANGE' nor 'COMMAND_EXCHANGE'.");
            }

            responseToMake.setClientInetAddress(requestToProcess.getClientInetAddress());
            responseToMake.setClientPort(requestToProcess.getClientPort());
            System.out.println(responseToMake);
            responsesQueue.add(responseToMake);

            returnConnection(currentConnection);

            sendResponsePool.submit(sendResponseTask);

        };


        Runnable receiveRequestTask = () -> {

            Request newRequest;

            while (isRunning) {
                try {

                    newRequest = connectionManagerReceiver.receiveRequest();

//
//                        System.out.println("\n\\\\\\\\\\\\\\\\\\\\Request\\\\\\\\\\\\\\\\\\\\");
                        System.out.println(newRequest);


                    requestsQueue.add(newRequest);

                    makeResponsePool.submit(makeResponseTask);

                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        };


        new Thread(receiveRequestTask).start();

    }


    public boolean addUnidirectionalResponse(UnidirectionalResponse unidirectionalResponse) {
        return unidirectionalResponsesQueue.offer(unidirectionalResponse);
    }

    public boolean addMultidirectionalResponse(MultidirectionalResponse multidirectionalResponse) {
        return multidirectionalResponsesQueue.offer(multidirectionalResponse);
    }


    public synchronized Connection getConnection() {

        if (connectionsList.isEmpty()) {
            try {
                connectionsList.add(DataBaseManager.connectDataBase(dataBaseUrl, DB_USER, DB_PASS));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return connectionsList.poll();
    }


    public synchronized void returnConnection(Connection connection) {
        connectionsList.add(connection);
    }


    public LinkedBlockingQueue<Request> getRequestsQueue() {
        return requestsQueue;
    }

    public LinkedBlockingQueue<UnidirectionalResponse> getUnidirectionalResponsesQueue() {
        return unidirectionalResponsesQueue;
    }

    public static LinkedBlockingQueue<MultidirectionalResponse> getMultidirectionalResponsesQueue() {
        return multidirectionalResponsesQueue;
    }

    public ConcurrentSkipListMap<java.lang.Integer, MusicBand> getCollection() {
        return collection;
    }
}



