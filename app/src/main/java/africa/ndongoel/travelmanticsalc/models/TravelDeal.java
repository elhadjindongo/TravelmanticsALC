/*
 * Created by El Hadji M. NDONGO on 8/2/2019
 * Purpose of the class: TravelDeal
 */
package africa.ndongoel.travelmanticsalc.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TravelDeal implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        @Override
        public TravelDeal createFromParcel(Parcel parcel) {
            return new TravelDeal(parcel);
        }

        @Override
        public TravelDeal[] newArray(int i) {
            return new TravelDeal[i];
        }
    };
    private String mId;
    private String mTitle;
    private String mPrice;
    private String mDescription;
    private String mImgUrl;

    public TravelDeal(Parcel parcel) {
        mId = parcel.readString();
        mTitle = parcel.readString();
        mDescription = parcel.readString();
        mPrice = parcel.readString();
        mImgUrl = parcel.readString();
    }
    //Empty ctor
    public TravelDeal() { }

    public TravelDeal(String title, String description, String price) {
        this.mTitle = title;
        this.mDescription = description;
        this.mPrice = price;
    }

    public TravelDeal(String id, String title, String description, String price, String imgUrl) {
        this.mId = id;
        this.mTitle = title;
        this.mPrice = price;
        this.mDescription = description;
        this.mImgUrl = imgUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        this.mPrice = price;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.mImgUrl = imgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeString(mPrice);
        parcel.writeString(mImgUrl);
    }
}
