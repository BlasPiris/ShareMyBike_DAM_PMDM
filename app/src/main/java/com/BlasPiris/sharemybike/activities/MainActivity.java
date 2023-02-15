package com.BlasPiris.sharemybike.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.BlasPiris.sharemybike.pojos.User;
import com.example.sharemybike.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SignInButton login;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.signInButton);

        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        gsc=GoogleSignIn.getClient(this,gso);


        //EVENTO DEL BOTON LOGIN
        login.setOnClickListener(view -> signIn());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                Log.i("Permisos", "Se tienen los permisos!");
            } else {
                ActivityCompat.requestPermissions(
                        MainActivity.this, new String[] { android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION }, 1222);
            }
        }
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

            } catch (ApiException e) {
               Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }


        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
       mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            goMainPanelActivity();
                            registerUser(mAuth);
//                    FirebaseUser user = mAuth.getCurrentUser();
//                          updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                        }
                });
    }

    private void registerUser(FirebaseAuth mAuth) {
        String idUser=mAuth.getCurrentUser().getUid();
        Map<String,Object> map=new HashMap<>();
        map.put("uid",idUser);
        map.put("name",mAuth.getCurrentUser().getDisplayName());
        map.put("email",mAuth.getCurrentUser().getEmail());

        User user=User.getInstance();
        user.setUid(idUser);
        user.setName((String) map.get("name"));
        user.setEmail((String) map.get("email"));

        mDatabase.child("user").child(idUser).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }
            }
        });
    }

    //METODO QUE REDIRIGE AL MAIN PANEL ACTIVITY
    public void goMainPanelActivity(){

        GoogleSignInAccount acct=GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            finish();
            Intent intent = new Intent(this, MainPanelActivity.class);
            startActivity(intent);
        }


    }


}