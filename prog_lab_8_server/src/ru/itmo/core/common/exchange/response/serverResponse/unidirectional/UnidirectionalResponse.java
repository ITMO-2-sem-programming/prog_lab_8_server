package ru.itmo.core.common.exchange.response.serverResponse.unidirectional;


import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.response.ServerResponse;



public abstract class UnidirectionalResponse extends ServerResponse {


    private final CRStatus status;
    private final String message;


    public UnidirectionalResponse(
            Client client,
            CRStatus status,
            String message) {

        super(client);
        this.status = status;
        this.message = message;
    }




    public boolean errorOccurred() {
        return this.status == CRStatus.ERROR;
    }




    public CRStatus getStatus() {
        return status;
    }


    public String getMessage() {
        return message;
    }



}
