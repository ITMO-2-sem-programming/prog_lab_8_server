package ru.itmo.core.common.exchange.response.serverCommand.unidirectional.userCommand;


public class GeneralCommand implements UserCommand {



    UserCommandStatus userCommandStatus;
    String userCommandMessage;

    ServerProcessStatus serverProcessStatus;
    String serverProcessMessage;




    public GeneralCommand(UserCommandStatus userCommandStatus, String userCommandMessage) {
        this.userCommandStatus = userCommandStatus;
        this.userCommandMessage = userCommandMessage;
    }


    public GeneralCommand(UserCommandStatus userCommandStatus, String userCommandMessage, ServerProcessStatus serverProcessStatus, String serverProcessMessage) {
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
