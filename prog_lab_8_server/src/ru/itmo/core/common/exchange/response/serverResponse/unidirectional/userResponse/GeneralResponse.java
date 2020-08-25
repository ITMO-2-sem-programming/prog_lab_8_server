package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse;


import ru.itmo.core.common.exchange.Client;

public class GeneralResponse extends UserCommandResponse {



    UCStatus userCommandResponseStatus;
    String userCommandMessage;

    ServerProcessStatus serverProcessStatus;
    String serverProcessMessage;




    public GeneralResponse(Client client, UCStatus userCommandStatus, String userCommandMessage) {
        super(client);
        this.userCommandResponseStatus = userCommandStatus;
        this.userCommandMessage = userCommandMessage;
    }


    public GeneralResponse(Client client, UCStatus userCommandResponseStatus, String userCommandMessage, ServerProcessStatus serverProcessStatus, String serverProcessMessage) {
        super(client);
        this.userCommandResponseStatus = userCommandResponseStatus;
        this.userCommandMessage = userCommandMessage;
        this.serverProcessStatus = serverProcessStatus;
        this.serverProcessMessage = serverProcessMessage;
    }




    public boolean isCancelled() {

        return
                userCommandResponseStatus.equals(UCStatus.ERROR)
                || serverProcessStatus.equals(ServerProcessStatus.ERROR);

    }




    public UCStatus getUserCommandResponseStatus() {
        return userCommandResponseStatus;
    }


    public String getUserCommandMessage() {
        return userCommandMessage;
    }


    public void setServerProcessStatus(ServerProcessStatus serverProcessStatus) {
        this.serverProcessStatus = serverProcessStatus;
    }


    public ServerProcessStatus getServerProcessStatus() {
        return serverProcessStatus;
    }


    public void setServerProcessMessage(String serverProcessMessage) {
        this.serverProcessMessage = serverProcessMessage;
    }


    public String getServerProcessMessage() {
        return serverProcessMessage;
    }



}
