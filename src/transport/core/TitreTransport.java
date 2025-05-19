package transport.core;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class TitreTransport implements Serializable {
    protected int id;
    protected LocalDate dateAchat;
    protected LocalDateTime datePrecise;
    protected double prix;
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
        // Generate a unique ID for each ticket based on datePrecise
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
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