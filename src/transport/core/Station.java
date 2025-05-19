package transport.core;

import java.io.Serializable;

public class Station implements Suspendable, Serializable {
    private static final long serialVersionUID = 1L;

    private String nom;
    private boolean suspendu;

    public Station(String nom) {
        this.nom = nom;
        suspendu = false;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Station de " + nom;
    }

    @Override
    public void suspendre() {
        suspendu = true;
    }

    @Override
    public void reactiver() {
        suspendu = false;
    }

    @Override
    public boolean estSuspendu() {
        return suspendu;
    }

    @Override
    public String getEtat() {
        return suspendu ? "suspendu" : "actif";
    }
}
