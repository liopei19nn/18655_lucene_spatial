/**
 *
 */
package com.lip.query;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import com.lip.util.DatabaseIO;

/**
 * @author Li Pei
 *
 * Andrew ID : lip
 */
public class PublicationSearch {
    private static SimpleAnalyzer analyzer_basic;
    private static Directory index_basic;
    private static IndexWriterConfig config_basic;
    private static IndexWriter w_basic;


    public SearchResult[] basicSearch(String query, int numResultsToSkip, int numResultsToReturn)
            throws IOException, ParseException {
        // 1. if analyzer == null rebuild the Lucene Index
        if (analyzer_basic == null) {
            analyzer_basic = new SimpleAnalyzer();
            // 1.create the index
            index_basic = new RAMDirectory();
            config_basic = new IndexWriterConfig(analyzer_basic);
            w_basic = new IndexWriter(index_basic, config_basic);
            DatabaseIO db = new DatabaseIO();
            db.fillIndexWithAllPubs(w_basic);
            w_basic.close();

        }

        // return result;
        SearchResult[] ret = new SearchResult[0];

        // 2. query

        if (query == null) {
            return ret;
        }

        Query q = new MultiFieldQueryParser(new String[] { "title", "authors" }, analyzer_basic).parse(query);

        // 3.search
        if (numResultsToSkip < 0) {
            numResultsToReturn = 0;
        }
        if (numResultsToReturn < 1) {
            return ret;
        }

        int hitsPerPage = numResultsToSkip + numResultsToReturn;
        IndexReader reader = DirectoryReader.open(index_basic);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        if (numResultsToSkip > hits.length) {
            // System.out.println("Query Nothing!");
            return ret;
        }

        // 4. return
        ArrayList<SearchResult> retList = new ArrayList<>();
        // System.out.println("Found " + hits.length + "hits");

        for (int i = numResultsToSkip; i < numResultsToReturn + numResultsToSkip && i < hits.length; ++i) {
            int docID = hits[i].doc;
            Document d = searcher.doc(docID);
            SearchResult currResult = new SearchResult();
            currResult.setIdkey(d.get("idkey"));
            currResult.setTitle(d.get("title"));
            currResult.setAuthors(d.get("authors"));
            currResult.setYear(d.get("year"));
            retList.add(currResult);
        }
        reader.close();

        ret = new SearchResult[retList.size()];
        for (int i = 0; i < retList.size(); i++) {
            ret[i] = retList.get(i);
        }

        return ret;
    }



    public SearchResult[] spatialSearch(String query, SearchRegion region, int numResultsToSkip,
 int numResultsToReturn)
            throws IOException, ParseException {
        // 0.Specify the analyzer for tokenizing text.
        // The same analyzer should be used for indexing and searching
        SimpleAnalyzer analyzer = new SimpleAnalyzer();
        // 1.create the index
        Directory index = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter w = new IndexWriter(index, config);
        DatabaseIO db = new DatabaseIO();
        db.rangeFillIndex(w, region);
        w.close();

        // return result;
        SearchResult[] ret = new SearchResult[0];

        // 2. query

        if (query == null) {
            return ret;
        }

        Query q = new MultiFieldQueryParser(new String[] { "title", "authors" }, analyzer).parse(query);

        // 3.search
        if (numResultsToSkip < 0) {
            numResultsToReturn = 0;
        }
        if (numResultsToReturn < 1) {
            return ret;
        }

        int hitsPerPage = numResultsToSkip + numResultsToReturn;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        if (numResultsToSkip > hits.length) {
            // System.out.println("Query Nothing!");
            return ret;
        }

        // 4. return
        ArrayList<SearchResult> retList = new ArrayList<>();
        // System.out.println("Found " + hits.length + "hits");
        for (int i = numResultsToSkip; i < numResultsToReturn + numResultsToSkip && i < hits.length; ++i) {
            int docID = hits[i].doc;
            Document d = searcher.doc(docID);
            SearchResult currResult = new SearchResult();
            currResult.setIdkey(d.get("idkey"));
            currResult.setTitle(d.get("title"));
            currResult.setAuthors(d.get("authors"));
            currResult.setYear(d.get("year"));
            retList.add(currResult);
        }
        reader.close();

        ret = new SearchResult[retList.size()];
        for (int i = 0; i < retList.size(); i++) {
            ret[i] = retList.get(i);
        }

        return ret;


    }
}
