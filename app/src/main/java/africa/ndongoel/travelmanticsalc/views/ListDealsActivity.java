/*
 * Created by El Hadji M. NDONGO on 8/2/2019
 * Purpose of the class: ListDealsActivity
 */
package africa.ndongoel.travelmanticsalc.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import africa.ndongoel.travelmanticsalc.R;
import africa.ndongoel.travelmanticsalc.controllers.DealAdapteur;
import africa.ndongoel.travelmanticsalc.controllers.FirebaseHelper;
import africa.ndongoel.travelmanticsalc.models.TravelDeal;

public class ListDealsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_deals);
        init();
    }

    private void init() {
       RecyclerView dealsRecycler = findViewById(R.id.activity_list_deals_recycler);
       dealsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        dealsRecycler.setAdapter(new DealAdapteur());


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_deal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_deal_new_deal_Item :
                Intent dealActivityLauncher = new Intent(this, DealActivity.class);
                startActivity(dealActivityLauncher);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
