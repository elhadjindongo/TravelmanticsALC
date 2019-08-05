/*
 * Created by El Hadji M. NDONGO on 8/2/2019
 * Purpose of the class: DealActivity
 */
package africa.ndongoel.travelmanticsalc.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import africa.ndongoel.travelmanticsalc.R;
import africa.ndongoel.travelmanticsalc.controllers.FirebaseHelper;
import africa.ndongoel.travelmanticsalc.models.TravelDeal;

public class DealActivity extends AppCompatActivity {
    public static final String DEAL_INDEX_POSITION = "DEAL_INDEX_POSITION";
    public static final String PATH = "traveldeals";

    private static final String TAG = "DealActivity";
    private EditText mTitle, mDescription, mPrice;
    private TravelDeal mTravelDeal;
    private DatabaseReference mDbRef;
    private static final int m_REQUEST_CODE = 42;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);
        init();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        FirebaseHelper.openRef(PATH);
        mDbRef = FirebaseHelper.mDbRef;
    }

    private void init() {
        mTitle = findViewById(R.id.activity_insert_title_input);
        mDescription = findViewById(R.id.activity_insert_description_input);
        mPrice = findViewById(R.id.activity_insert_price_input);
        //checking if a TravelDeal is passed
        Intent intent = getIntent();
        mTravelDeal = intent.getParcelableExtra(DealActivity.DEAL_INDEX_POSITION);
        Log.d(TAG, "received TravelDeal= "+mTravelDeal);
        if (mTravelDeal != null) {
            mTitle.setText(mTravelDeal.getTitle());
            mDescription.setText(mTravelDeal.getDescription());
            mPrice.setText(mTravelDeal.getPrice());
        }else {
            mTravelDeal = new TravelDeal();
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_save_item:
                Log.d(TAG, "**************************onOptionsItemSelected: save pressed");
                saveDeal();
                cleanInputs ();
                backToList();
                return true;
            case R.id.menu_save_del_item:
                Log.d(TAG, "**************************onOptionsItemSelected: delete pressed");
                removeDeal();
                cleanInputs ();
                backToList();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void backToList() {
        Intent listDealsLauncher = new Intent(this, ListDealsActivity.class);
        listDealsLauncher.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(listDealsLauncher);
    }

    private void cleanInputs() {
        mTitle.setText("");
        mDescription.setText("");
        mPrice.setText("");
    }

    /**
     * Add a new travelDeal on firebase
     */
    private void saveDeal() {
        //getting data
        mTravelDeal.setTitle(mTitle.getText().toString());
        mTravelDeal.setDescription(mDescription.getText().toString());
        mTravelDeal.setPrice(mPrice.getText().toString());
        //saving on firebase

        if (mTravelDeal.getId() != null) {
            Log.d(TAG, "****************saveDeal: modification");
            mDbRef.child(mTravelDeal.getId()).setValue(mTravelDeal);
        }else {
            Log.d(TAG, "****************saveDeal: new");
            mDbRef.push().setValue(mTravelDeal);
        }
        Toast.makeText(DealActivity.this, getString(R.string.saved_success), Toast.LENGTH_LONG).show();
    }

    /**
     * Remove an existing travelDeal on firebase
     */
    private void removeDeal() {
        if (mTravelDeal.getId() != null) {
            Log.d(TAG, "****************removeDeal: delete");
            mDbRef.child(mTravelDeal.getId()).removeValue();
            Toast.makeText(DealActivity.this, getString(R.string.del_success), Toast.LENGTH_LONG).show();
        }else {
            Log.d(TAG, "****************removeDeal: seve before deleting");
            Toast.makeText(DealActivity.this, getString(R.string.please_saved), Toast.LENGTH_LONG).show();
        }
    }

    public void loadImg(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
        startActivityForResult(Intent.createChooser(intent,"Insert Picture (only jpeg type)"), m_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == m_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri pictureUrl = data.getData();
            StorageReference child = FirebaseHelper.sStorageReference.child(pictureUrl.getLastPathSegment());
            child.putFile(pictureUrl).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //getting the file's ulr
                    String pictureUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    Log.d(TAG, "***************onSuccess: picture's url="+pictureUrl);
                    mTravelDeal.setImgUrl(pictureUrl);
                }
            });
        }
    }
}