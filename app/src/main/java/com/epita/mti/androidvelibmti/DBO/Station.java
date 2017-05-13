package com.epita.mti.androidvelibmti.DBO;

/**
 * Created by William on 12/05/2017.
 */

public class Station {
    private final String uri;
    private final String title;
    private final String auteur;
    private final String type;

    public Station(String uri, String auteur, String title, String type) {
        this.uri = uri;
        this.auteur = auteur;
        this.title = title;
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public String getTitle() {
        return title;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getType() {
        return type;
    }
}
