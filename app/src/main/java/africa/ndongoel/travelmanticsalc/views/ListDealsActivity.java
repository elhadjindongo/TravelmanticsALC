/*
 * Created by El Hadji M. NDONGO on 8/2/2019
 * Purpose of the class: ListDealsActivity
 */
package africa.ndongoel.travelmanticsalc.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import africa.ndongoel.travelmanticsalc.R;
import africa.ndongoel.travelmanticsalc.controllers.DealAdapteur;
import africa.ndongoel.travelmanticsalc.controllers.FirebaseHelper;

public class ListDealsActivity extends AppCompatActivity {
    private static final String TAG = "ListDealsActivity";
    public static void launchListDeal(Context context) {
        Intent intent = new Intent(context, ListDealsActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_deals);
        init();
    }

    private void init() {
       RecyclerView dealsRecycler = findViewById(R.id.activity_list_deals_recycler);
       dealsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        dealsRecycler.setAdapter(new DealAdapteur(this));


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseHelper.openRef(DealActivity.PATH);
        FirebaseHelper.connectListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (FirebaseHelper.sStateListener != null) {
            FirebaseHelper.disconnectListener();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_deal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_listDeal_new_deal:
                Intent dealActivityLauncher = new Intent(this, DealActivity.class);
                startActivity(dealActivityLauncher);
                return true;
            case R.id.menu_listdeal_Logout_item :
                Log.d(TAG, "**************************onOptionsItemSelected: signout pressed");
                FirebaseHelper.sFirebaseAuth.signOut();
                signout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void signout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
