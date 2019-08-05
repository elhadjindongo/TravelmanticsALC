package africa.ndongoel.travelmanticsalc.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import africa.ndongoel.travelmanticsalc.R;
import africa.ndongoel.travelmanticsalc.controllers.FirebaseHelper;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private EditText mEmail, mPassword, mFullName;
    private Button mSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {

        mEmail = findViewById(R.id.activitySignUp_email);
        mPassword = findViewById(R.id.activitySignUp_password);
        mFullName = findViewById(R.id.activitySignUp_name);
        mSignup = findViewById(R.id.activitySignUp_signupBtn);
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting info
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String name = mFullName.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.empty_input), Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d(TAG, "********************onClick: Email=" + email + " pass=" + password);

                //saving on the firebase authentification
                FirebaseHelper.openRef(DealActivity.PATH);
                FirebaseHelper.register(SignUpActivity.this, email, password);
            }
        });
    }
}
