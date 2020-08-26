package ru.itmo.core.common.exchange.request.clientRequest.serviceRequest;


import ru.itmo.core.common.exchange.User;

public class AuthoriseUserServiceRequest extends ServiceRequest {

    public AuthoriseUserServiceRequest(User user) {
        super.setUser(user);
    }
}
