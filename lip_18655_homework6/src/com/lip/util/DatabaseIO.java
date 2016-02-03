package com.lip.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;

import com.lip.query.SearchRegion;

/**
 * @author Li Pei
 *
 * Andrew ID : lip
 */
public class DatabaseIO {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";

    // fill Lucene index with all entry in database
    public void fillIndexWithAllPubs(IndexWriter w) throws IOException {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            if (connection != null) {
                // System.out.println("Connected to Database Successfully!");
            }

            String query = "SELECT * FROM publication.pubdetail;";

            Statement statement = connection.createStatement();

            // query all publication detail
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                // System.out.print(rs.getString("idkey"));
                // System.out.print(rs.getString("title"));
                // System.out.print(rs.getString("author"));
                // System.out.println(rs.getString("year"));
                addDoc(w, rs.getString("idkey"), rs.getString("title"), rs.getString("author"), rs.getString("year"));

            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }// end of get publication detail method

    private static void addDoc(IndexWriter w, String key, String title, String author, String year) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("idkey", key, Field.Store.YES));
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new TextField("authors", author, Field.Store.YES));
        doc.add(new StringField("year", year, Field.Store.YES));

        w.addDocument(doc);
    }

    // create database
    public void createDataBase() {
        Statement statement = null;
        try {
            // get connection
            Connection connection = null;
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            if (connection != null) {
                // System.out.println("Connected to Database Successfully!");
            }
            statement = connection.createStatement();
            String command = null;

            // firstly delete the database with name "publication"
            // to avoid "DATABASE ALREADY EXIST" exception
            try {
                command = "DROP DATABASE publication;";
                statement.executeUpdate(command);
            } catch (Exception e) {
                // if there is no such database, then do nothing and skip
            }

            // then create DATABASE for this project
            // and use this database
            try {
                command = "CREATE DATABASE publication";
                statement.executeUpdate(command);

                command = "USE publication";
                statement.executeUpdate(command);

            } catch (Exception e) {
                // if there is no such database, then do nothing and skip
            }

            // then create table for this project
            // table 1 is publication_detail

            try {
                command = "CREATE TABLE pubdetail (location GEOMETRY NOT NULL, idkey varchar(255) NOT NULL,"
                        + "mdate varchar(255) NOT NULL, author varchar(255) NOT NULL,"
                        + "title varchar(255) NOT NULL, pages varchar(255), year varchar(255),"
                        + "volume varchar(255), journal varchar(255), numbers varchar(255),"
                        + "url varchar(255), ee varchar(255), SPATIAL INDEX(location)) " + "ENGINE=MyISAM";
                statement.executeUpdate(command);


            } catch (Exception e) {
                // if there is no such database, then do nothing and skip
                e.printStackTrace();
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// end of create database method

    // add one publication to database

    /**
     * used to build spatial database.
     *
     * @param pub
     * @param index
     *            you have to input it, it is a automatical increment number
     *            outside, if you do not input it, the search will be all in
     *            database.
     *
     */
    public void addToDatabase(Publication pub, int index) {
        // prepare statement for query
        String addPubDetailPrep = "INSERT INTO publication.pubdetail VALUES (GeomFromText('Point(%d %s)'),'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e2) {

            e2.printStackTrace();
        }

        try {

            // get connection
            if (connection != null) {
                // System.out.println("Connected to Database Successfully!");
            }

            // insert pub into database
            Statement statement = connection.createStatement();



            StringBuilder authorString = new StringBuilder();
            for (int i = 0; i < pub.author.size(); i++) {
                authorString.append(pub.author.get(i) + ",");
            }


            String query = String.format(addPubDetailPrep, index, pub.year, pub.idkey, pub.mdate,
                    authorString.toString(),
                    pub.title, pub.pages, pub.year, pub.volume, pub.journal, pub.numbers, pub.url, pub.ee);
            statement.executeUpdate(query);
            statement.close();



            // System.out.println("ADD To Database " + pub.toString());
            // close connection

            connection.close();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }// end of add one method

    // get all cowork of these two author
    public void rangeFillIndex(IndexWriter w, SearchRegion region) throws IOException {
        Connection connection = null;
        String queryPrep = "SELECT * FROM publication.pubdetail WHERE MBRContains(GeomFromText('LineString(1 %s, 1000 %s)'),location);";

        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            if (connection != null) {
                // System.out.println("Connected to Database Successfully!");
            }

            String query = String.format(queryPrep, region.getStartYear(), region.getEndYear());

            // System.out.println(query);
            Statement statement = connection.createStatement();

            // query all publication detail
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                // System.out.print(rs.getString("idkey"));
                // System.out.print(rs.getString("title"));
                // System.out.print(rs.getString("author"));
                // System.out.println(rs.getString("year"));
                addDoc(w, rs.getString("idkey"), rs.getString("title"), rs.getString("author"), rs.getString("year"));

            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    } // end of method

}
