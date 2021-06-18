package solversteam.aveway.Models;

import java.util.ArrayList;

/**
 * Created by Ibrahim on 02/02/2017.
 */

public class FragmentModel {
    ArrayList<ItemForView> Item;

    public FragmentModel(ArrayList<ItemForView> Item) {
        this.Item = Item;
    }

    public ArrayList<ItemForView> getSlidingImages() {
        return Item;
    }

    public void setSlidingImages(ArrayList<ItemForView> slidingImages) {
        this.Item = slidingImages;
    }

}
