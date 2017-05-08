package com.independeciatecnologica.logingoogle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
   TextView nameView ;
    TextView emailView;
    Button logOut;
    GoogleApiClient client;
    private final static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent getData = getIntent();
        nameView = (TextView)findViewById(R.id.name_view);
        nameView.setText( getData.getStringExtra("name"));
        emailView = (TextView)findViewById(R.id.email_view);
        emailView.setText(getData.getStringExtra("email"));
        logOut = (Button)findViewById(R.id.button_logout);
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        client = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Auth.GoogleSignInApi.signOut(client).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                Log.i("result",status.getStatus().toString());
                                if(status.isSuccess()){
                                    Toast.makeText(MainActivity.this,"You log out correctly , code: "+status.getStatusCode(),Toast.LENGTH_LONG).show();
                                    Intent goToLogin = new Intent(MainActivity.this,LoginActivity.class);
                                    startActivity(goToLogin);
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Estatus: "+status.getStatusMessage(),Toast.LENGTH_LONG);
                                }
                            }
                        }
                );
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(client);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleResult(result);
        }
        else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    handleResult(result);
                }
            });
        }
    }

    private void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            GoogleSignInAccount acc = result.getSignInAccount();
            Log.i(TAG,"Token Id:"+acc.getIdToken());
        }
        else{
          Toast.makeText(this,"you coud not initialize a session",Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
