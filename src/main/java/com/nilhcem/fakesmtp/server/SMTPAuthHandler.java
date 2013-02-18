package com.nilhcem.fakesmtp.server;

import org.subethamail.smtp.AuthenticationHandler;
import org.subethamail.smtp.RejectException;

public class SMTPAuthHandler implements AuthenticationHandler {

    private int pass = 0;

    @Override
    public String auth(String string) throws RejectException {
        /**
         * Initially called using an input string in the RFC2554 form: "AUTH <mechanism> [initial-response]". <br>
         * This method must return text which will be delivered to the client, or null if the exchange
         * has been completed successfully.  If a response is provided to the client, this continues
         * the exchange - there will be another auth() call with the client's response.
         * <p>
         * Depending on the authentication mechanism, the handshaking process may require
         * many request-response passes. This method will return <code>null</code> only when the authentication process is finished.
         * <p>
         * AuthenticationHandlers are associated with a single authentication exchange.  If the exchange is stopped
         * (ie fails) and is restarted, a new AuthenticationHandler is created.  Upon successful authentication, your
         * implementation of this object becomes part of the MessageContext.  Your MessageHandler may upcast your
         * AuthenticationHandler to obtain further information, such as identity.
         * <p>
         * AuthenticationHandlers do not need to handle the "*" cancel response; this is handled by the framework.
         *
         * @return <code>null</code> if the authentication process is finished, otherwise a string to hand back to the client.
         * @param clientInput The client's input, eg "AUTH PLAIN dGVzdAB0ZXN0ADEyMzQ="
         * @throws org.subethamail.smtp.RejectException if authentication fails.
         */

        ++pass;
        if (pass == 1)
            return "334 VXNlcm5hbWU6";
        if (pass == 2)
            return "334 UGFzc3dvcmQ6";

        return null;
    }

    @Override
    public Object getIdentity() {
        /**
         * If the authentication process was successful, this returns the identity
         * of the user.  The type defining the identity can vary depending on the
         * authentication mechanism used, but typically this returns a String username.
         * If authentication was not successful, the return value is undefined.
         */
        return "User";
    }

}
