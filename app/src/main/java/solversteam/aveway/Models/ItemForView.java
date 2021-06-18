package solversteam.aveway.Models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Ibrahim on 29/01/2017.
 */

public class ItemForView implements Parcelable{
    String Category, SeenOrClear;
    String ImageUrl , name  , URL,catgeory_id ;
    int layoutID , price , prevprice , offer ;
    ArrayList<Integer> ID;
    private String ProductUrl , Key;
    private static ArrayList<String> Images;
    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }
    public  ArrayList<String> getImages() {
        return Images;
    }

    public String getProductUrl() {
        return ProductUrl;
    }

    public  void setImages(ArrayList<String> images) {
        Images = images;
    }

    public String getURL() {
        return URL;
    }

    public ItemForView(String ProductUrl, String category, String seenOrClear, String Imageurl, String name,
                       int price, int prevprice , int offer, int layoutID , String Url , String Key) {
        Category = category;
        SeenOrClear = seenOrClear;
        this.ImageUrl = Imageurl;
        this.name = name;
        this.price = price;
        this.URL = Url;
        this.ProductUrl = ProductUrl;
        this.prevprice = prevprice;
        this.layoutID = layoutID;
        this.offer = offer;
        this.Key = Key;

    }
    public ItemForView(String ProductUrl, String category, String seenOrClear, String Imageurl, String name, int price, int prevprice , int offer, int layoutID , String Url , String Key,String categoryid) {
        Category = category;
        SeenOrClear = seenOrClear;
        this.ImageUrl = Imageurl;
        this.name = name;
        this.price = price;
        this.URL = Url;
        this.ProductUrl = ProductUrl;
        this.prevprice = prevprice;
        this.layoutID = layoutID;
        this.offer = offer;
        this.Key = Key;
        this.catgeory_id=categoryid;

    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getKey() {
        return Key;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrevprice() {
        return prevprice;
    }
    public void setPrevprice(int prevprice) {
        this.prevprice = prevprice;
    }
    public int getLayoutID() {
        return layoutID;
    }
    public ItemForView(String category, String seenOrClear) {
        Category = category;
        SeenOrClear = seenOrClear;
    }
    public String getCategory() {
        return Category;
    }
    public String getSeenOrClear() {
        return SeenOrClear;
    }
    public String getImageUrl() {
        return ImageUrl;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public String getCatgeory_id() {
        return catgeory_id;
    }

    public void setCatgeory_id(String catgeory_id) {
        this.catgeory_id = catgeory_id;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("Category" , Category);
        bundle.putString("SeenOrClear" , SeenOrClear);
        bundle.putString("ImageUrl" , ImageUrl);

        bundle.putInt("layoutID" , layoutID);
        bundle.putInt("price" , price);
        bundle.putInt("prevprice" , prevprice);
        bundle.putString("name" , name);
        bundle.putInt("offer" , offer);
        bundle.putString("AllURL" , URL);
        bundle.putString("ProductUrl" , ProductUrl);
        bundle.putString("Key" , Key);
        bundle.putStringArrayList("Images" , Images);
        bundle.putIntegerArrayList("ID" , ID);
        bundle.putStringArrayList("Images" , Images);
        bundle.putString("cat_id",catgeory_id);
        parcel.writeBundle(bundle);
    }
    public static final Creator<ItemForView> CREATOR = new Creator<ItemForView>(){
        @Override
        public ItemForView createFromParcel(Parcel parcel) {
            Bundle bundle = parcel.readBundle();
            String Category = bundle.getString("Category");
            String SeenOrClear = bundle.getString("SeenOrClear");
            String Url = bundle.getString("Url");
            int layoutID = bundle.getInt("layoutID");
            int price = bundle.getInt("price");
            int prevprice = bundle.getInt("prevprice");
            String name = bundle.getString("name");
            int offer = bundle.getInt("offer");
            String ProductUrl = bundle.getString("ProductUrl");
            String AllURL =  bundle.getString("AllURL");
            String Key =  bundle.getString("Key");
            String categoryid=bundle.getString("cat_id");
            ArrayList<String> images= bundle.getStringArrayList("Images");
            return new ItemForView(ProductUrl , Category , SeenOrClear , Url , name , price  , prevprice , offer , layoutID,AllURL , Key,categoryid);
        }

        @Override
        public ItemForView[] newArray(int i) {
            return new ItemForView[i];
        }
    };
}
