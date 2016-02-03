package com.lip.query;

import java.io.IOException;

import javax.jws.WebService;

import org.apache.lucene.queryparser.classic.ParseException;

/**
 * @author Li Pei
 *
 * Andrew ID : lip
 */
@WebService(endpointInterface = "com.lip.query.SearchInterface")
public class SearchInterfaceImpl implements SearchInterface {

    @Override
    public String[] basicSearch(String query, int numResultsToSkip, int numResultsToReturn) {

        PublicationSearch p = new PublicationSearch();
        SearchResult[] s = new SearchResult[0];
        try {
            s = p.basicSearch(query, numResultsToSkip, numResultsToReturn);
        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String[] ret = null;
        if (s.length == 0) {
            ret = new String[1];
            ret[0] = "No Such Entry!";
        } else {
            ret = new String[s.length];
            for (int i = 0; i < s.length; i++) {
                // System.out.print("No." + (i + 1) + " ");
                // System.out.println(s[i].toString());
                ret[i] = s[i].toString();
            }
        }

        return ret;

    }

    @Override
    public String[] spatialSearch(String query, String startYear, String endYear, int numResultsToSkip,
            int numResultsToReturn) {
        PublicationSearch p = new PublicationSearch();
        SearchRegion region = new SearchRegion();
        region.setStartYear(startYear);
        region.setEndYear(endYear);
        SearchResult[] s = new SearchResult[0];
        try {
            s = p.spatialSearch(query, region, numResultsToSkip, numResultsToReturn);
        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String[] ret = null;
        if (s.length == 0) {
            ret = new String[1];
            ret[0] = "No Such Entry!";
        }else{
            ret = new String[s.length];
            for (int i = 0; i < s.length; i++) {
//                System.out.print("No." + (i + 1) + " ");
//                System.out.println(s[i].toString());
                ret[i] = s[i].toString();
            }
        }

        return ret;


    }
}
