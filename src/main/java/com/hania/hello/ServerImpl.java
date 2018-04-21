package com.hania.hello;

import javax.jws.WebService;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
@WebService(endpointInterface = "com.hania.hello.Server")
public class ServerImpl implements Server {

    @Override
    public String sayHello(String name) {
        return "Hello " + name + "!\n Hope you are doing well!!";
    }
}
