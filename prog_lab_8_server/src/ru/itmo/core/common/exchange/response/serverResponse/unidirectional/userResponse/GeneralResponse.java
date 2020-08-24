package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse;


public class GeneralResponse extends UserCommandResponse {



    UserCommandStatus userCommandStatus;
    String userCommandMessage;

    ServerProcessStatus serverProcessStatus;
    String serverProcessMessage;




    public GeneralResponse(UserCommandStatus userCommandStatus, String userCommandMessage) {
        this.userCommandStatus = userCommandStatus;
        this.userCommandMessage = userCommandMessage;
    }


    public GeneralResponse(UserCommandStatus userCommandStatus, String userCommandMessage, ServerProcessStatus serverProcessStatus, String serverProcessMessage) {
        this.userCommandStatus = userCommandStatus;
        this.userCommandMessage = userCommandMessage;
        this.serverProcessStatus = serverProcessStatus;
        this.serverProcessMessage = serverProcessMessage;
    }




    public boolean anErrorOccurred() {

        return
                userCommandStatus.equals(UserCommandStatus.ERROR)
                || serverProcessStatus.equals(ServerProcessStatus.ERROR);

    }




    public UserCommandStatus getUserCommandStatus() {
        return userCommandStatus;
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
