package com.example.retailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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

        user = FirebaseAuth.getInstance().getCurrentUser();

        passcodeView = findViewById(R.id.passcode_view2);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid())
                .child("password");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                passcodeView.setLocalPasscode(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

      passcodeView
                .setPasscodeLength(4)
                .setCorrectInputTip("ПИН код введен правильно")
                .setWrongInputTip("Вы ввели не правильный ПИН код")
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
}
