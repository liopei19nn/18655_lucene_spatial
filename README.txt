18655 Homework6 ReadMe

Name : Li Pei
ID : lip

There are two projects in this package. First one is basic and spatial search in local, and the _service is a RESTful service published in Endpoint and used in RPC-STYLE. 

Before run this two projects, please build both of them with external Jar for JDBC and Lucene. All Jars are in "lib" folder. You have to configure to build all of Jars in it.

For convenience, I only build one spatial database table, both for basic search
and spatial search, however, in the basic search part, I do not take advantage of the range of points. To get the database, you could restore database from database.sql in lip_18655_homework6 folder, or run the DOMParserDemo.java to build database. The database username is root, no password. The database name will be publication, and table name is pubdetail.


To run both of projects, you have to make sure database is running and relavant database table is set up correctly.

In lip_18655_homework6 projects, the PublicationSearch.java is where implements query in Lucene, and in DatabaseIO.java, all database relavant method is imlemented, including create database, fill the database with Publication objects, build Lucene index for basic search and spatial search. The demo of code is in TestSearch.java, in which you could run and see a demo search on keyword "algorithm". For basic search, you could see top 15 with no skip, top 15 with 5 skip. For spatial search, you could see top 15 with 5 skip between year 1915 to 2015, and top 15 with 5 skip between year 2000 to 2015.


In lip_18655_homework6_service, you could see RPC-Style interface SearchInterface.java and its implementation SearchInterfaceImpl.java. The Endpoint publisher is SearchPublisher.java. And the service is at "http://localhost:9999/ws/queryPublication".

To check the service, you have to run SearchPublisher.java as local Java project, and then run the client to see the results. The query and results is the same as previous one.








