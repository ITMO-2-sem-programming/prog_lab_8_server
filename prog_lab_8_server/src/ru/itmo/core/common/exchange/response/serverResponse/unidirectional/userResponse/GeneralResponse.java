package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse;


public class GeneralResponse extends UserCommandResponse {



    UserCommandResponseStatus userCommandResponseStatus;
    String userCommandMessage;

    ServerProcessStatus serverProcessStatus;
    String serverProcessMessage;




    public GeneralResponse(UserCommandResponseStatus userCommandStatus, String userCommandMessage) {
        this.userCommandResponseStatus = userCommandStatus;
        this.userCommandMessage = userCommandMessage;
    }


    public GeneralResponse(UserCommandResponseStatus userCommandResponseStatus, String userCommandMessage, ServerProcessStatus serverProcessStatus, String serverProcessMessage) {
        this.userCommandResponseStatus = userCommandResponseStatus;
        this.userCommandMessage = userCommandMessage;
        this.serverProcessStatus = serverProcessStatus;
        this.serverProcessMessage = serverProcessMessage;
    }




    public boolean anErrorOccurred() {

        return
                userCommandResponseStatus.equals(UserCommandResponseStatus.ERROR)
                || serverProcessStatus.equals(ServerProcessStatus.ERROR);

    }




    public UserCommandResponseStatus getUserCommandResponseStatus() {
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
