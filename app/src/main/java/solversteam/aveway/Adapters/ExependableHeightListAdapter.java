package solversteam.aveway.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import solverteam.aveway.R;

import java.util.ArrayList;

/**
 * Created by Ibrahim on 09/02/2017.
 */

public class ExependableHeightListAdapter extends BaseAdapter{
    private Context context ;
    private ArrayList<String> Categories;
    public ExependableHeightListAdapter(Context context , ArrayList<String> Categories) {
        this.context = context;
        this.Categories = Categories;
    }

    @Override
    public int getCount() {
        return Categories.size();
    }

    @Override
    public Object getItem(int i) {
        return Categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.expendable_list_item, null);
        TextView name = (TextView) view.findViewById(R.id.activity_categories_2_name);
        name.setText(Categories.get(i));
        return view;
    }
}
