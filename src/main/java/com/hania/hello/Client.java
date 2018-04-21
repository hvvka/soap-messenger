package com.hania.hello;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Client {

    public static void main(String[] args) {

        try {
            URL url = new URL("http://localhost:9999/ws/hello?wsdl");
            QName qname = new QName("http://hello.hania.com/", "ServerImplService");

            Service service = Service.create(url, qname);

            Server server = service.getPort(Server.class);
            String name = "Hania";
            System.out.println(server.sayHello(name));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
