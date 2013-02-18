package com.nilhcem.fakesmtp.server;

import java.util.ArrayList;
import java.util.List;
import org.subethamail.smtp.AuthenticationHandler;
import org.subethamail.smtp.AuthenticationHandlerFactory;

public class SMTPAuthHandlerFactory implements AuthenticationHandlerFactory {

    @Override
    public List<String> getAuthenticationMechanisms() {
        List<String> result = new ArrayList<String>();
        result.add("LOGIN");
        return result;
    }

    @Override
    public AuthenticationHandler create() {
        return new SMTPAuthHandler();
    }

}
