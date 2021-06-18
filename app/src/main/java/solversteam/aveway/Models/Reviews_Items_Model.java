package solversteam.aveway.Models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ibrahim on 31/01/2017.
 */

public class Reviews_Items_Model implements Parcelable{
    String user_name , Date , content , Rated;

    public String getUser_name() {
        return user_name;
    }

    public String getDate() {
        return Date;
    }

    public String getContent() {
        return content;
    }

    public String getRated() {
        return Rated;
    }

    public Reviews_Items_Model(String user_name, String date, String content, String rated) {
        this.user_name = user_name;
        Date = date;
        this.content = content;
        Rated = rated;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("user_name" , user_name);
        bundle.putString("date" , Date);
        bundle.putString("content" , content);
        bundle.putString("Rated" , Rated);
        parcel.writeBundle(bundle);
    }
    public static final Parcelable.Creator<Reviews_Items_Model> CREATOR = new Parcelable.Creator<Reviews_Items_Model>(){
        @Override
        public Reviews_Items_Model createFromParcel(Parcel parcel) {
            Bundle bundle = parcel.readBundle();
            String user_name = bundle.getString("user_name");
            String Date = bundle.getString("date");
            String content = bundle.getString("content");
            String Rated = bundle.getString("Rated");
            return new Reviews_Items_Model(user_name , Date , content , Rated);
        }

        @Override
        public Reviews_Items_Model[] newArray(int i) {
            return new Reviews_Items_Model[i];
        }
    };
}
