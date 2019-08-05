/*
 * Created by El Hadji M. NDONGO on8/3/2019
 * Purpose : DealAdapteur
 */
package africa.ndongoel.travelmanticsalc.controllers;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import africa.ndongoel.travelmanticsalc.R;
import africa.ndongoel.travelmanticsalc.models.TravelDeal;
import africa.ndongoel.travelmanticsalc.views.DealActivity;

public class DealAdapteur extends RecyclerView.Adapter<DealAdapteur.DealHolder> {
    private List<TravelDeal> mDealsList;
    private DatabaseReference mDbRef;
    private static final String TAG = "DealAdapteur";

    public DealAdapteur() {
       // mContext = context;
       /* mDealsList.add(new TravelDeal("1", "terr", "tree", "tgfds", ""));
        mDealsList.add(new TravelDeal("1", "terr", "tree", "tgfds", ""));
        mDealsList.add(new TravelDeal("1", "terr", "tree", "tgfds", ""));
        mDealsList.add(new TravelDeal("1", "terr", "tree", "tgfds", ""));
        mDealsList.add(new TravelDeal("1", "terr", "tree", "tgfds", ""));
        mDealsList.add(new TravelDeal("1", "terr", "tree", "tgfds", ""));
        mDealsList.add(new TravelDeal("1", "terr", "tree", "tgfds", ""));*/
        FirebaseHelper.openRef("traveldeals");
        mDealsList = FirebaseHelper.mDealsList;
        mDbRef = FirebaseHelper.mDbRef;
        mDbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //getting the child
                TravelDeal travelDealItem = dataSnapshot.getValue(TravelDeal.class);
                Log.d(TAG, "*****************onChildAdded:newDeal= "+ travelDealItem.toString());

                travelDealItem.setId(dataSnapshot.getKey());
                mDealsList.add(travelDealItem);
                //notifying the list
                notifyItemChanged(mDealsList.size()-1);
                Log.d(TAG, "*****************onChildAdded:mDealsList size= "+mDealsList.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @NonNull
    @Override
    public DealHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View inflatedView = LayoutInflater.from(context).inflate(R.layout.deal_item, viewGroup, false);
        return new DealHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull DealHolder dealHolder, int i) {
        TravelDeal travelDealItem = mDealsList.get(i);
        dealHolder.mTitle.setText(travelDealItem.getTitle());
        dealHolder.mDescrtiption.setText(travelDealItem.getDescription());
        dealHolder.mPrice.setText(travelDealItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return mDealsList.size();
    }

    //Nested viewHolder
   public class DealHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mDescrtiption, mPrice;

        public DealHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.deal_item_title);
            mDescrtiption = itemView.findViewById(R.id.deal_item_description);
            mPrice = itemView.findViewById(R.id.deal_item_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Log.d(TAG, "*********************onClick: itemPosition:"+position);
                    Context context = view.getContext();
                    Intent dealActivityLauncher = new Intent(context, DealActivity.class);
                    dealActivityLauncher.putExtra(DealActivity.DEAL_INDEX_POSITION, mDealsList.get(position));
                    context.startActivity(dealActivityLauncher);
                }
            });
        }
    }


    //getters setters


    public List<TravelDeal> getDealsList() {
        return mDealsList;
    }

    public void setDealsList(List<TravelDeal> dealsList) {
        mDealsList = dealsList;
    }
}
