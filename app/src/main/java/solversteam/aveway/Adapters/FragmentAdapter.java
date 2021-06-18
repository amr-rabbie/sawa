package solversteam.aveway.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import solversteam.aveway.Fragments.ItemShowFragment;
import solversteam.aveway.Models.ItemForView;

/**
 * Created by Ibrahim on 01/02/2017.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    ArrayList<ItemShowFragment> myFragments;
    ArrayList<ItemForView> itemForViews;
    ArrayList<Integer>IDs;
    Context context;
    int position;
    private int language;
    public FragmentAdapter(FragmentManager fm, ArrayList<ItemShowFragment> myFragments, ArrayList<ItemForView> itemForViews , ArrayList<Integer> IDs , int position , Context context,int language) {
        super(fm);
        this.myFragments = myFragments;
        this.IDs = IDs;
        this.itemForViews = itemForViews;
        this.context = context;
        this.position = position;
        this.language=language;
    }



    @Override
    public Fragment getItem(int position) {
       // Toast.makeText(context, "pppppppp "+position, Toast.LENGTH_SHORT).show();
        return myFragments.get(position).newInstance(itemForViews.get(position) , IDs , this.position , context,language);

    }

    @Override
    public int getCount() {
        return myFragments.size();
    }
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }

}
