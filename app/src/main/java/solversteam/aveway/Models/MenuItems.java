package solversteam.aveway.Models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ibrahim on 25/01/2017.
 */

// note parcel used when sending date from activity to fragment as array of MenuItems class we must use parcel
public class MenuItems implements Parcelable{
    private int MenuItemName;
    private int MenutItemID;

    public int getMenuItemName() {
        return MenuItemName;
    }

    public void setMenuItemName(int menuItemName) {
        MenuItemName = menuItemName;
    }

    public int getMenutItemID() {
        return MenutItemID;
    }

    public void setMenutItemID(int menutItemID) {
        MenutItemID = menutItemID;
    }

    public MenuItems(int menuItemName, int menutItemID) {
        MenuItemName = menuItemName;
        MenutItemID = menutItemID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("name" , MenuItemName+"");
        bundle.putInt("url" , MenutItemID);
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
    };
}
