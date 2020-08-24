package ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest;


import java.io.File;


public class ExecuteScriptCommandRequest extends UserCommandRequest {


    String filePath;



    public ExecuteScriptCommandRequest(String filePath) {
        setFilePath(filePath);
    }




    public String getFilePath() {
        return filePath;
    }


    private void setFilePath(String filePath) {

        File file = new File(filePath);

        if (
                ! file.exists()
                || file.isDirectory()
                || ! file.canRead()
        ) throw new IllegalArgumentException("Invalid file path (name). Possible reasons : file : doesn't exist; is a directory; can't be read.");

        this.filePath = filePath;

    }



}
