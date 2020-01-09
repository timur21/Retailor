package com.example.retailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hanks.passcodeview.PasscodeView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BasicLogin extends AppCompatActivity {

    PasscodeView passcodeView;
    DatabaseReference reference;
    FirebaseUser user;

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
        setContentView(R.layout.activity_basic_login);

        String code = getCode();

        passcodeView = findViewById(R.id.passcode_view);
        passcodeView
                .setPasscodeLength(4)
                .setLocalPasscode(code)
                .setCorrectInputTip("Успешно!")
                .setWrongInputTip("Неправильный пароль")
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        passcodeView.clearAnimation();
                    }

                    @Override
                    public void onSuccess(String number) {
                        startActivity(new Intent(BasicLogin.this, HomeActivity.class));
                    }
                });
    }

    private String getCode(){
        final String[] temp = {""};
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .child("passcode");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                temp[0] = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return temp[0];
    }
}