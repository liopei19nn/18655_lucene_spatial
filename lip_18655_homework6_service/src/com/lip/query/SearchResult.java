/**
 *
 */
package com.lip.query;

/**
 * @author Li Pei
 *
 * Andrew ID : lip
 */
public class SearchResult {
    private String title;
    private String year;
    private String[] authors;
    private String idkey;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public String[] getAuthors() {
        return this.authors;
    }

    public void setAuthors(String s) {
        authors = s.split(",");
    }



    public String getIdkey() {
        return idkey;
    }


    public void setIdkey(String idkey) {
        this.idkey = idkey;
    }


    public String getYear() {
        return year;
    }


    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("idKey : ");
        s.append(this.idkey);
        s.append("; ");
        s.append("Title : ");
        s.append(this.title);
        s.append("; ");
        s.append("Year : ");
        s.append(this.year);
        s.append("; ");
        for (int i = 1; i <= authors.length; i++) {
            s.append("author " + i + " : " + authors[i - 1] + "; ");
        }
        return s.toString();
    }
}
