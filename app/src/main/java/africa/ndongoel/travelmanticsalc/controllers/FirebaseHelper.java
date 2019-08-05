/*
 * Created by El Hadji M. NDONGO on 03 8/3/2019
 * Purpose : FirebaseHelper
 */
package africa.ndongoel.travelmanticsalc.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import africa.ndongoel.travelmanticsalc.models.TravelDeal;
import africa.ndongoel.travelmanticsalc.views.ListDealsActivity;
import africa.ndongoel.travelmanticsalc.views.LoginActivity;



public final class FirebaseHelper {
    private static final String TAG = "FirebaseHelper";
    private static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDbRef;
    private static FirebaseHelper mFirebaseHelperInstance;
    private static FirebaseAuth mFirebaseAuth;
    private static FirebaseStorage mFbSotarage;
    public static StorageReference sStorageReference;
    static ArrayList<TravelDeal> mDealsList;
    //public static Boolean sLoginSuccess, sRegisterSuccess;

    private FirebaseHelper() {}

    /**
     * initialize firebase
     * @param path of the firebase database
     */
    public static void openRef(String path) {
        if (mFirebaseHelperInstance == null) {
            mFirebaseHelperInstance = new FirebaseHelper();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            mDealsList = new ArrayList<>();
            mDbRef = mFirebaseDatabase.getReference().child(path);
        }
        connectStorage();
    }

    /**
     * Checking user's authentification on Firebase
     * @param activity the calling activity
     * @param email of the user
     * @param password of the user
     */
    public static void signin(final Activity activity , String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "************************signInWithEmail:success");
                            Intent intent = new Intent(activity, ListDealsActivity.class);
                            activity.startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "***************signInWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Could not sign in. Try again...",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
    /**
     * REgistering user's authentification on Firebase
     * @param activity the calling activity
     * @param email of the user
     * @param password of the user
     */
    public static void register( final Activity activity , String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success
                    Log.d(TAG, "************************signUpWithEmail:success");
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "***************signUpWithEmail:failure", task.getException());
                    Toast.makeText(activity, "Could not sign up. Try again...",Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    public static void connectStorage() {

        mFbSotarage = FirebaseStorage.getInstance();
        sStorageReference = mFbSotarage.getReference().child("deals_pictures");

    }
}
