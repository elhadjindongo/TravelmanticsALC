package africa.ndongoel.travelmanticsalc.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import africa.ndongoel.travelmanticsalc.R;
import africa.ndongoel.travelmanticsalc.controllers.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText mEmail;
    private EditText mPassword;
    private Button mSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        mEmail = findViewById(R.id.activityLogin_username);
        mPassword = findViewById(R.id.activityLogin_password);
        mSignin = findViewById(R.id.activityLogin_loginBtn);
        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ProgressBar dialog
                //getting info
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Email or password should'nt be empty. Try again...",Toast.LENGTH_LONG).show();
                    return;
                }
               // Toast.makeText(LoginActivity.this, "Email="+email+" pass="+password,Toast.LENGTH_LONG).show();
                Log.d(TAG, "********************onClick: Email="+email+" pass="+password);

                //getting the firebase authentification
                FirebaseHelper.openRef(DealActivity.PATH);
                FirebaseHelper.signin(LoginActivity.this, email, password);

            }
        });
    }

    public void launchSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
