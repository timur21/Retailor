package com.example.retailor.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.retailor.Adapters.AgreementDetailsAdapter;
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

import java.util.List;

public class PersonalFragment extends Fragment {

    FirebaseUser user;
    RecyclerView recyclerView;
    List<List<AgreementDetails>> agreementDetails;
    String clientName = "Дадабоев Ойбек Авазович";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal, container,false);

        recyclerView =view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid())
                .child("client");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // clientName = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        Retrofit retrofit = NetworkUtils.getRetrofit();

        ClientName clientName2 = new ClientName(clientName);

        ClientAuth clientAuth = retrofit.create(ClientAuth.class);

        Call<Table> call = clientAuth.getClientDetails(clientName2);

        call.enqueue(new Callback<Table>() {
            @Override
            public void onResponse(Call<Table> call, Response<Table> response) {
                Table table = response.body();

                agreementDetails = table.getAgreementDetails();
                if ( agreementDetails == null){
                    Toast.makeText(getContext(), "У вас нет активных договоров", Toast.LENGTH_LONG).show();
                } else {
                    AgreementDetailsAdapter agreementDetailsAdapter = new AgreementDetailsAdapter(getContext(), agreementDetails);
                    recyclerView.setAdapter(agreementDetailsAdapter);
                }
            }

            @Override
            public void onFailure(Call<Table> call, Throwable t) {
                Toast.makeText(getContext(), "Произошла ошибка подключения к серверу", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
