package com.independeciatecnologica.logingoogle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by emperator on 02/05/2017.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient apiClient;
    String googleIdCliente = String.valueOf(R.string.google_id_client);
    private final static String TAG = LoginActivity.class.getSimpleName();
    SignInButton googleButton;
    Button customButton ;


    @Override
    protected void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        setContentView(R.layout.login);
        googleButton = (SignInButton)findViewById(R.id.google_button);
        customButton = (Button)findViewById(R.id.custom_button);
       GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestId()
              // .requestIdToken(R.string.google_id_client)
               .requestEmail()
               .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,options)
                .build();
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
                startActivityForResult(intent,2203);
            }
        });
        customButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
                startActivityForResult(intent,2203);
            }
        });
        Log.i(TAG,googleIdCliente);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"something went Wrong!!: "+connectionResult.getErrorMessage(),Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2203){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            manejoDeResultado(result);
        }
    }
    public void manejoDeResultado(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount acc = result.getSignInAccount();
            goToScreen(acc.getDisplayName(),acc.getEmail());
        }else{
            Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show();
        }
    }

    private void goToScreen(String name , String email) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("email",email);
        startActivity(intent);
    }
}
