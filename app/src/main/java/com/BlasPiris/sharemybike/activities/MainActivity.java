package com.BlasPiris.sharemybike.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sharemybike.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    SignInButton login;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.signInButton);

        mAuth = FirebaseAuth.getInstance();
        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        gsc=GoogleSignIn.getClient(this,gso);


        //EVENTO DEL BOTON LOGIN
        login.setOnClickListener(view -> signIn()
        );
    }

    private void signIn() {
        Intent signInIntent=gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1000){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                goBikeActivity();
            } catch (ApiException e) {
               Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }


        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
       mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            //Log.d(TAG, "signInWithCredential:success");
//                    FirebaseUser user = mAuth.getCurrentUser();
                          //updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            //updateUI(null);
//                        }
                });
    }

    //METODO QUE REDIRIGE AL BIKE ACTIVITY
    public void goBikeActivity(){

        GoogleSignInAccount acct=GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            Toast.makeText(this,acct.getDisplayName()+" "+acct.getEmail(),Toast.LENGTH_LONG).show();
        }

        finish();
        Intent intent = new Intent(this, BikeActivity.class);
        startActivity(intent);
    }


}