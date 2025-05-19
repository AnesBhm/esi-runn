package transport.core;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class TitreTransport implements Serializable {
    protected int id;
    protected LocalDate dateAchat;
    protected double prix;
    public static int counter = 0;
    private String paymentMethod;

    public abstract boolean estValide(LocalDate dateValidation) throws TitreNonValideException;

    // Getters
    public int getId() {
        return id;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public double getPrix() {
        return prix;
    }

    protected int generateId() {
        return ++counter;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

}