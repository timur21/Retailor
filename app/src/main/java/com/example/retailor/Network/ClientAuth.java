package com.example.retailor.Network;

import com.example.retailor.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClientAuth {

    @GET("/testikbol/hs/client")
    Call<List<User>> basicLogin();
}
