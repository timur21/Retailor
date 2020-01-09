package com.example.retailor.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retailor.Models.AgreementDetails;
import com.example.retailor.Models.ClientName;
import com.example.retailor.Models.Table;
import com.example.retailor.Network.ClientAuth;
import com.example.retailor.Network.NetworkUtils;
import com.example.retailor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainFragment extends Fragment {
    Retrofit retrofit;
    FirebaseUser user;
    public static String clientName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        retrofit = NetworkUtils.getRetrofit();

        getUserName();

        return view;
    }

    private void getUserName(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid())
                .child("client");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getUserAgreement(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserAgreement(String userName){
        ClientName clientName = new ClientName(userName);

        ClientAuth auth = retrofit.create(ClientAuth.class);

        Call<Table> call = auth.getClientDetails(clientName);

        call.enqueue(new Callback<Table>() {
            @Override
            public void onResponse(Call<Table> call, Response<Table> response) {
                if (response.isSuccessful()){
                    Table table = response.body();
                    List<List<AgreementDetails>> userAgreementDetails = table.getAgreementDetails();
                    Log.d("MYTAG", table.getMainPhoneNumber());
                }
            }

            @Override
            public void onFailure(Call<Table> call, Throwable t) {

            }
        });
    }
}


