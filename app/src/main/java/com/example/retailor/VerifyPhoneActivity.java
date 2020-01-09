package com.example.retailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.concurrent.TimeUnit;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VerifyPhoneActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    String phoneNumber;
    String clientName;
    String verificationCode;
    Button btnSignIn;
    Button resend;
    MaterialEditText code_enter;
    RelativeLayout parent;

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
        setContentView(R.layout.activity_verify_phone);

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("ru");
        user = FirebaseAuth.getInstance().getCurrentUser();

        phoneNumber = getIntent().getStringExtra("phonenumber");
        clientName = getIntent().getStringExtra("clientName");

        code_enter = findViewById(R.id.verificationCode);
        progressBar = findViewById(R.id.progressBar);
        btnSignIn = findViewById(R.id.sign_in);
        parent = findViewById(R.id.parent);
        resend = findViewById(R.id.resend);

        sendVerification(phoneNumber);

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendVerificationCode(phoneNumber, mResendToken);
                Snackbar.make(parent, "Code resent", Snackbar.LENGTH_SHORT).show();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(code_enter.getText())){
                    Snackbar.make(parent, "Please enter code...", Snackbar.LENGTH_SHORT).show();
                }else{
                    verifyPhoneNumber();
                }
            }
        });
    }

    private void sendVerification(String phoneNumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                callbacks
        );

        Snackbar.make(parent, "Code sent", Snackbar.LENGTH_SHORT).show();
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code!=null){
                        code_enter.setText(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Snackbar.make(parent, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationCode = s;
                    mResendToken = forceResendingToken;
                    Toast.makeText(getApplicationContext(), "Code sent", Toast.LENGTH_SHORT).show();
                }
            };

    private void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            saveUserData(clientName);
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(VerifyPhoneActivity.this, HomeActivity.class));
                        }
                        else{
                            Snackbar.make(parent, task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserData(String clientName){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid())
                .child("client");
        reference.setValue(clientName);

    }

    private void verifyPhoneNumber(){
        String code = code_enter.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, code);
        signInWithCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,PhoneAuthProvider.ForceResendingToken token){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks,
                token);
    }

}
