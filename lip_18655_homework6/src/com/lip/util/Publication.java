/**
 *
 */
package com.lip.util;

import java.util.ArrayList;

/**
 * @author Li Pei
 *
 * Andrew ID : lip
 */
public class Publication {

    // filed of Publication
    public String idkey = "";
    public String mdate = "";
    public ArrayList<String> author;
    public String title = "";
    public String pages = "";
    public String year = "";
    public String volume = "";
    public String journal = "";
    public String numbers = "";
    public String url = "";
    public String ee = "";

    // Constructor to build up author array
    public Publication() {
        // TODO Auto-generated constructor stub
        author = new ArrayList<String>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("key: " + idkey + "\n");
        sb.append("mdate: " + mdate + "\n");
        for (String s : author) {
            sb.append("author: " + s + "\n");
        }
        sb.append("title: " + title + "\n");
        sb.append("pages: " + pages + "\n");
        sb.append("year: " + year + "\n");
        sb.append("volume: " + volume + "\n");
        sb.append("journal: " + journal + "\n");
        sb.append("numbers: " + numbers + "\n");
        sb.append("url: " + url + "\n");
        sb.append("ee: " + ee + "\n");

        return sb.toString();

    }



}
