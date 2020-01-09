package com.example.retailor.Network;

import com.example.retailor.Models.ClientName;
import com.example.retailor.Models.Table;
import com.example.retailor.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClientAuth {

    @GET("/testikbol/hs/client")
    Call<List<User>> basicLogin();

    @POST("/testikbol/hs/client")
    Call<Table> getClientDetails(@Body ClientName clientName);
}
