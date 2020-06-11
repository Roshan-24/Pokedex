package com.example.pokedex;

import java.util.List;

public class NamedAPIResourceList {

    private int count;
    private String next;
    private String previous;
    private List<NamedAPIResource> results;

    public NamedAPIResourceList(int count, String next, String previous, List<NamedAPIResource> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<NamedAPIResource> getResults() {
        return results;
    }
}
