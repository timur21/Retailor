package com.example.retailor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.retailor.Models.DatabaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanks.passcodeview.PasscodeView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PassActivity extends AppCompatActivity {

    PasscodeView passcodeView;
    FirebaseUser user;
    FirebaseAuth auth;

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
        setContentView(R.layout.activity_pass);

        user = FirebaseAuth.getInstance().getCurrentUser();

        auth = FirebaseAuth.getInstance();

        passcodeView = findViewById(R.id.passcode_view);
        passcodeView
                .setPasscodeLength(4)
                .setLocalPasscode("1111")
                .setSecondInputTip("Подтвердите пароль")
                .setCorrectInputTip("Успешно!")
                .setWrongInputTip("Пароли не совпадают")
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        passcodeView.clearAnimation();
                    }

                    @Override
                    public void onSuccess(String number) {
                        Intent intent = new Intent(PassActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
    }
}
