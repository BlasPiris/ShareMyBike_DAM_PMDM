package com.example.sharemybike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Object signInRequest;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        
        SignInButton login=findViewById(R.id.signInButton);

        //EVENTO DEL BOTON LOGIN
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInRequest = BeginSignInRequest.builder()
                        .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                       .setSupported(true)
                       // Your server's client ID, not your Android client ID.
                               .setServerClientId(getString(R.string.default_web_client_id))
                              // Only show accounts previously used to sign in.
                                .setFilterByAuthorizedAccounts(true)
                               .build());
            }
        }
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    //METODO QUE REDIRIGE AL BIKE ACTIVITY
    public void goBikeActivity(){
        Intent intent = new Intent(this, BikeActivity.class);
        startActivity(intent);
    }


}