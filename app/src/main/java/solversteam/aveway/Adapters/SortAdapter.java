package solversteam.aveway.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import solverteam.aveway.R;

import java.util.ArrayList;

/**
 * Created by Ibrahim on 15/02/2017.
 */

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.Sort>{
    ArrayList<String> items;
    public SortAdapter(ArrayList<String> items){
        this.items = items;
    }
    @Override
    public Sort onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sort_category, parent, false);
        return new Sort(v);
    }

    @Override
    public void onBindViewHolder(Sort holder, int position) {
        holder.textView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Sort extends RecyclerView.ViewHolder {
        TextView textView;
        public Sort(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.sort_kind);
        }
    }
}
