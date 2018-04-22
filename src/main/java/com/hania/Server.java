package com.hania;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface Server {

    @WebMethod
    void sendMessage(String message);

    @WebMethod
    String fetchMessages();

    @WebMethod
    void stopThread();
}
