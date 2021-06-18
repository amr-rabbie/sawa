package solversteam.aveway.utiltes;

import android.app.Activity;

import solversteam.aveway.Models.ItemForView;

import java.util.ArrayList;

/**
 * Created by Ibrahim on 09/02/2017.
 */

public interface FragmentInterface {
    public void fragmentBecameVisible(Activity activity, int position, ArrayList<ItemForView> itemForViews, int language);
}
