package solversteam.aveway.Models;

import android.view.View;

/**
 * Created by ahmed ezz on 27/02/2017.
 */

public class FragmentViews {
    private String item_id;
    private View view;

    public FragmentViews(String item_id, View view) {
        this.item_id = item_id;
        this.view = view;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
