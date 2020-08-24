//package ru.itmo.deprecated;
//
//
//import ru.itmo.core.connection.ConnectionManager;
//import ru.itmo.core.connection.Serializer;
//import ru.itmo.core.common.exchange.request.Request;
//import ru.itmo.main.MainMultithreading;
//
//import java.io.IOException;
//import java.util.concurrent.Callable;
//
//
//public class GetRequestTask implements Runnable {
//
//    private ConnectionManager connectionManager;
//
//
//
//    public GetRequestTask(ConnectionManager connectionManager) {
//        this.connectionManager = connectionManager;
//    }
//
//
//
//    @Override
//    public void run() {
//        Object requestObject;
//        try {
//            requestObject = Serializer.toObject(connectionManager.receive());
//        } catch (IOException | ClassNotFoundException e) {
//            throw new IllegalArgumentException(e.getMessage());
//        }
//        MainMultithreading.requestsList.add((Request) requestObject);
//    }
//
//
//
//
//    public ConnectionManager getConnectionManager() {
//        return connectionManager;
//    }
//
//    public void setConnectionManager(ConnectionManager connectionManager) {
//        this.connectionManager = connectionManager;
//    }
//}
