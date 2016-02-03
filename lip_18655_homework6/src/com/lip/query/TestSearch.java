package com.lip.query;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

/**
 * @author Li Pei
 *
 * Andrew ID : lip
 */
public class TestSearch {
    public static void main(String[] args) throws IOException, ParseException {
        PublicationSearch p = new PublicationSearch();

        System.out.println("Basic Search 1 Search In large Range");
        System.out.println("Search \"algorithm\" TOP 15 Records");
        SearchResult[] s1 = p.basicSearch("algorithm", 0, 15);
        for (int i = 0; i < s1.length; i++) {
            System.out.print("No." + (i + 1) + " ");
            System.out.println(s1[i].toString());
        }
        System.out.println("------------------------------------------------------");

        System.out.println("Basic Search 2 Search In small Range");
        System.out.println("Skip top 5 and Print 15 results");
        SearchResult[] s2 = p.basicSearch("algorithm", 5, 15);
        for (int i = 0; i < s2.length; i++) {
            System.out.print("No." + (i + 1) + " ");
            System.out.println(s2[i].toString());
        }
        System.out.println("------------------------------------------------------");

        System.out.println("Spatial Search 1 Search In Large Year Range");
        System.out.println("From Year 1915 to Year 2015");
        SearchRegion region1 = new SearchRegion();
        region1.setStartYear("1915");
        region1.setEndYear("2015");
        SearchResult[] s3 = p.spatialSearch("algorithm", region1, 5, 15);
        for (int i = 0; i < s3.length; i++) {
            System.out.print("No." + (i + 1) + " ");
            System.out.println(s3[i].toString());

        }
        System.out.println("------------------------------------------------------");

        System.out.println("Spatial Search 2 Search In Small Year Range");
        System.out.println("From Year 2000 to Year 2015");
        SearchRegion region2 = new SearchRegion();
        region2.setStartYear("2000");
        region2.setEndYear("2015");
        SearchResult[] s4 = p.spatialSearch("algorithm", region2, 5, 15);
        for (int i = 0; i < s4.length; i++) {
            System.out.print("No." + (i + 1) + " ");
            System.out.println(s4[i].toString());
        }
        System.out.println("------------------------------------------------------");
    }
}
