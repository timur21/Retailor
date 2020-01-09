package com.example.retailor;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;



import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retailor.Network.NetworkUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import androidx.annotation.NonNull;

import com.example.retailor.Models.User;
import com.example.retailor.Network.ClientAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText phone_num;
    Button register_btn;
    String phone_number;
    ProgressBar progressBar;
    RelativeLayout parent;
    Retrofit retrofit;

    List<User> userList= new ArrayList();

    private static final String TAG = "PhoneAuthActivity";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        setContentView(R.layout.activity_main);

//        activityConsider();

        FirebaseApp.initializeApp(this);

        parent = (RelativeLayout) findViewById(R.id.parent);

        phone_num = findViewById(R.id.phoneNum);
        register_btn = findViewById(R.id.register_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone_number = phone_num.getText().toString().trim();

                checkData();
            }
        });
    }

    public void checkData() {

        progressBar.setVisibility(View.VISIBLE);

        retrofit = NetworkUtils.getRetrofit();

        ClientAuth clientAuth = retrofit.create(ClientAuth.class);

        Call<List<User>> call = clientAuth.basicLogin();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){

                    userList =response.body();

                    boolean serverResponse = false;

                    for (int position = 0; position < userList.size(); position++) {

                        if (phone_number.equals(userList.get(position).getContacts())) {

                            String substr=phone_number.substring(1);
                            String clientName = userList.get(position).getName();
                            String fullPhoneNumber = "+996" + substr;

                            Intent intent = new Intent(MainActivity.this, VerifyPhoneActivity.class);
                            intent.putExtra("phonenumber", fullPhoneNumber);
                            intent.putExtra("clientName", clientName);
                            startActivity(intent);

                            serverResponse = true;

                        }
                    }

                    if (serverResponse == false) {

                        //Toast.makeText(MainActivity.this, "Ваши данные не найдены в базе данных. Введите номер телефона правильно!", Toast.LENGTH_SHORT).show();
                        Snackbar.make(parent, "Ваши данные не найдены в базе данных. Введите номер телефона правильно!", Snackbar.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Snackbar.make(parent,"Произошла ошибка при получении данных!", Snackbar.LENGTH_LONG).show();
                   // Toast.makeText(MainActivity.this, "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void activityConsider(){
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(user.getUid())
                    .child("activated");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue().equals(true)){
                        Intent intent = new Intent(MainActivity.this, BasicLogin.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(MainActivity.this, PassActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}