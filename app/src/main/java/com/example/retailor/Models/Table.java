package com.example.retailor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Table {
    @SerializedName("Наименование")
    @Expose
    private String name;

    @SerializedName("ИНН")
    @Expose
    private String INN;

    @SerializedName("ПаспортСерияНомер")
    @Expose
    private String passportSeriesNumber;

    @SerializedName("КемВыдан")
    @Expose
    private String issuedBy;

    @SerializedName("ДатаВыдачи")
    @Expose
    private String issueDate;

    @SerializedName("АдресПроживания")
    @Expose
    private String homeAddress;

    @SerializedName("Комментария")
    @Expose
    private String comments;

    @SerializedName("ОсновнойТелефон")
    @Expose
    private String mainPhoneNumber;

    @SerializedName("СписокКонтакты")
    @Expose
    private List<String> ContanctList;

    @SerializedName("ОстаткиПоДоговорам")
    @Expose
    private List<List<AgreementDetails>> agreementDetails;

    public String getName() {
        return name;
    }

    public String getINN() {
        return INN;
    }

    public String getPassportSeriesNumber() {
        return passportSeriesNumber;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getComments() {
        return comments;
    }

    public String getMainPhoneNumber() {
        return mainPhoneNumber;
    }

    public List<String> getContanctList() {
        return ContanctList;
    }

    public List<List<AgreementDetails>> getAgreementDetails() {
        return agreementDetails;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public void setPassportSeriesNumber(String passportSeriesNumber) {
        this.passportSeriesNumber = passportSeriesNumber;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setMainPhoneNumber(String mainPhoneNumber) {
        this.mainPhoneNumber = mainPhoneNumber;
    }

    public void setContanctList(List<String> contanctList) {
        ContanctList = contanctList;
    }

    public void setAgreementDetails(List<List<AgreementDetails>> agreementDetails) {
        this.agreementDetails = agreementDetails;
    }
}
