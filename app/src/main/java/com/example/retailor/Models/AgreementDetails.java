package com.example.retailor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgreementDetails {
    @SerializedName("Договор")
    @Expose
    private String agreement;

    @SerializedName("Срок")
    @Expose
    private String date;

    @SerializedName("Нач")
    @Expose
    private String startingAmount;

    @SerializedName("Приход")
    @Expose
    private String toBePaidAmount;

    @SerializedName("Расход")
    @Expose
    private String paidAmount;

    @SerializedName("Кон")
    @Expose
    private String leftAmount;

    private boolean expanded;

    public AgreementDetails(String agreement, String date, String startingAmount, String toBePaidAmount, String paidAmount, String leftAmount) {
        this.agreement = agreement;
        this.date = date;
        this.startingAmount = startingAmount;
        this.toBePaidAmount = toBePaidAmount;
        this.paidAmount = paidAmount;
        this.leftAmount = leftAmount;
        this.expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getAgreement() {
        return agreement;
    }

    public String getDate() {
        return date;
    }

    public String getStartingAmount() {
        return startingAmount;
    }

    public String getToBePaidAmount() {
        return toBePaidAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public String getLeftAmount() {
        return leftAmount;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartingAmount(String startingAmount) {
        this.startingAmount = startingAmount;
    }

    public void setToBePaidAmount(String toBePaidAmount) {
        this.toBePaidAmount = toBePaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setLeftAmount(String leftAmount) {
        this.leftAmount = leftAmount;
    }
}
