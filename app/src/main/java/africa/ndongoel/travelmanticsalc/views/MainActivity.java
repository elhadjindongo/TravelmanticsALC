package africa.ndongoel.travelmanticsalc.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

import africa.ndongoel.travelmanticsalc.R;
import africa.ndongoel.travelmanticsalc.controllers.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseHelper.openRef(DealActivity.PATH);
        FirebaseUser currentUser = FirebaseHelper.sFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            //launch the list
            ListDealsActivity.launchListDeal(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void launchEmailSignin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
