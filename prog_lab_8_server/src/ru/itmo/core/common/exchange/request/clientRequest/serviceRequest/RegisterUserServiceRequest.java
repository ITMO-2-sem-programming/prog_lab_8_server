package ru.itmo.core.common.exchange.request.clientRequest.serviceRequest;


import ru.itmo.core.common.exchange.User;

public class RegisterUserServiceRequest extends ServiceRequest {
    public RegisterUserServiceRequest(User user) {
        super.setUser(user);
    }
}
