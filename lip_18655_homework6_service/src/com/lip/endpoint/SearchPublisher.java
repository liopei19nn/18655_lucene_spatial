package com.lip.endpoint;

import javax.xml.ws.Endpoint;

import com.lip.query.SearchInterfaceImpl;

/**
 * @author Li Pei
 *
 * Andrew ID : lip
 */
public class SearchPublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:9999/ws/queryPublication", new SearchInterfaceImpl());
    }
}
