package com.independeciatecnologica.logingoogle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity {
   TextView nameView ;
    TextView emailView;
    Button logOut;
    GoogleApiClient client;
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
                            }
                        }
                );
            }
        });

    }
}
