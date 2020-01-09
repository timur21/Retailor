package com.example.retailor.Models;

import com.google.gson.annotations.SerializedName;

public class ClientName {
    @SerializedName("Клиент")
    private String clientName;

    public ClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
