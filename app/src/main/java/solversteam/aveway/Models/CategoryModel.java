package solversteam.aveway.Models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ahmed ezz on 24/02/2017.
 */

public class CategoryModel implements Parcelable {
    private String category_id,category_image_url,category_name;

    public CategoryModel(String category_id, String category_image_url, String category_name) {
        this.category_id = category_id;
        this.category_image_url = category_image_url;
        this.category_name = category_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_image_url() {
        return category_image_url;
    }

    public void setCategory_image_url(String category_image_url) {
        this.category_image_url = category_image_url;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("name" , category_name+"");
        bundle.putString("url" , category_id);
        parcel.writeBundle(bundle);
    }
    public static final Parcelable.Creator<MenuItems> CREATOR = new Parcelable.Creator<MenuItems>(){
        @Override
        public MenuItems createFromParcel(Parcel parcel) {
            Bundle bundle = parcel.readBundle();
            int MenutItemID = bundle.getInt("url");
            String MenuItemName = bundle.getString("name");
            return new MenuItems(Integer.parseInt(MenuItemName) , MenutItemID);
        }

        @Override
        public MenuItems[] newArray(int i) {
            return new MenuItems[i];
        }
    };}