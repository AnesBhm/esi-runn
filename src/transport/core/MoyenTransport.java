package transport.core;

import java.io.Serializable;

public class MoyenTransport implements Suspendable, Serializable {
    private static final long serialVersionUID = 1L;
    private String identifiant;
    private boolean suspendu;

    public MoyenTransport(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    @Override
    public String toString() {
        return identifiant;
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