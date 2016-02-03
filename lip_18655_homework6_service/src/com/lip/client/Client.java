package com.lip.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.lip.query.SearchInterface;

/**
 * @author Li Pei
 *
 * Andrew ID : lip
 */
public class Client {
    public static void main(String[] args) throws MalformedURLException {
        System.out.println("Search Service On");
        System.out.println("http://localhost:9999/ws/queryPublication");
        URL url = new URL("http://localhost:9999/ws/queryPublication");

        // 1st argument service URI, refer to wsdl document above
        // 2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://query.lip.com/", "SearchInterfaceImplService");

        Service service = Service.create(url, qname);

        SearchInterface hello = service.getPort(SearchInterface.class);

        System.out.println("Basic Search 1 Search In large Range");
        System.out.println("Search \"algorithm\" TOP 15 Records");
        String[] s1 = hello.basicSearch("algorithm", 0, 15);
        for (int i = 0; i < s1.length; i++) {
            System.out.print("No." + (i + 1) + " ");
            System.out.println(s1[i].toString());
        }
        System.out.println("------------------------------------------------------");

        System.out.println("Basic Search 2 Search In small Range");
        System.out.println("Skip top 5 and Print 15 results");
        String[] s2 = hello.basicSearch("algorithm", 5, 15);
        for (int i = 0; i < s2.length; i++) {
            System.out.print("No." + (i + 1) + " ");
            System.out.println(s2[i].toString());
        }
        System.out.println("------------------------------------------------------");

        System.out.println("Spatial Search 1 Search In Large Year Range");
        System.out.println("From Year 1915 to Year 2015");
        String[] s3 = hello.spatialSearch("algorithm", "1915", "2015", 5, 15);
        for (int i = 0; i < s3.length; i++) {
            System.out.print("No." + (i + 1) + " ");
            System.out.println(s3[i].toString());

        }
        System.out.println("------------------------------------------------------");

        System.out.println("Spatial Search 2 Search In Small Year Range");
        System.out.println("From Year 2000 to Year 2015");

        String[] s4 = hello.spatialSearch("algorithm", "2000", "2015", 5, 15);
        for (int i = 0; i < s4.length; i++) {
            System.out.print("No." + (i + 1) + " ");
            System.out.println(s4[i].toString());
        }
        System.out.println("------------------------------------------------------");

    }
}
