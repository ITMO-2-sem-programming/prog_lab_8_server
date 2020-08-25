package ru.itmo.core.additional;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.connection.*;

import ru.itmo.core.common.exchange.ExchangeType;
import ru.itmo.core.common.exchange.request.Request;

import ru.itmo.core.common.exchange.response.Response;
import ru.itmo.main.CommandManager;
import ru.itmo.main.DataBaseManager;
import ru.itmo.main.UserManager;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;


/**
 * Runs the server in 1-thread mode.
 */



public class Main {

    static String filePath;
    static Connection connection;
    static String dataBaseUrl;
    static final String USER = "s284704";
    static final String PASS = "hxy284";



    public static void main(String[] args) throws IOException, SQLException {



//        String filePath = System.getenv("COLLECTION_PATH");
//        if (filePath == null) {
//            System.out.println("Error: Env var 'COLLECTION_PATH' doesn't exist.");
//            return;
//        }
//        filePath = "collection.json";

//
//
//        FileManager fileManager;
//        TreeMap<Integer, MusicBand> collectionFromFile = new TreeMap<>();




//            fileManager = new FileManager();
//            collection = fileManager.getCollectionFromFile(filePath);
//            CommandManager.setFileManager(fileManager); //Is needed only for printing collection's DateOfInitialisation when running command "info
//            CommandManager.setFilePath(filePath);



        ConcurrentSkipListMap<java.lang.Integer, MusicBand> collection;
        try {

//            for (int i = 0; i < INITIAL_CAPACITY_OF_CONNECTIONS_LIST; i ++) {
//                connectionsList.add(DataBaseManager.connectDataBase(USER, PASS));
//            }
            connection = DataBaseManager.connectDataBase(USER, PASS); /// replace every 'connection' with getConnection() method.
            collection = DataBaseManager.loadCollectionFromDataBase(connection);
            System.out.println("Collection was loaded successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }





        //--------------------------------------------


//        MusicBand musicBand = new MusicBand(1, "Beatles", new Coordinates(161, 16), 7, 3, MusicGenre.PROGRESSIVE_ROCK, new Person("John Lennon", 195L, Color.RED, Country.GERMANY, new Location(1, 2, "Scene")));
//        MusicBand musicBand2 = new MusicBand(1, "HritlesP", new Coordinates(161, 16), 7, 3, MusicGenre.PROGRESSIVE_ROCK, null);
//        User user = new User("maxi", "30981ab525a917193e15ed1e632f409ad5dd237c3cd9f4df65cd2a9e");
//
//        InsertCommand.execute(collectionFromFile, connection, user, musicBand2);
//        DataBaseManager.addMusicBandToCollectionTable(connection, musicBand2);
//            System.out.println(DataBaseManager.getMusicBandByIDFromCollectionTable(connection, 9));

//        System.out.println(DataBaseManager.getMusicBandByID(connection, 99));

        ConnectionManager connectionManager = new ConnectionManager(44231);
        while (true) {
            runCommand(collection, connectionManager, connection);
        }

    }


    public static void runCommand(ConcurrentSkipListMap<java.lang.Integer, MusicBand> collection, ConnectionManager connectionManager, Connection connection) {

        Request request;
        Response response = new Response();


        try {
            request = connectionManager.receiveRequest();
            System.out.println("I ve got request.");

            if (request.getRequestType() == ExchangeType.COMMAND_EXCHANGE) {

                response = new Response(
                                CommandManager.executeCommand(collection, connection, request.getCommandRequest())
                );


            } else if (request.getRequestType() == ExchangeType.SERVICE_EXCHANGE) {

                response = new Response(
                                UserManager.executeServiceRequest(connection, request.getServiceRequest())
                );


//                connectionManager.send(Serializer.toByteArray(response));
            }
            response.setClientInetAddress(request.getClientInetAddress());
            response.setClientPort(request.getClientPort());

            connectionManager.sendResponse(response);


        } catch (IOException | ClassNotFoundException e) { // Exception e  --make later
            e.printStackTrace();
            response.getCommandResponse().setMessage("Error: Command wasn't executed. Reason: 'ServerError'.");
            try {
                connectionManager.sendResponse(response);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }

    }

}


