package solversteam.aveway.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import solversteam.aveway.Models.CatModel;
import solverteam.aveway.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ibrahim on 05/02/2017.
 */

public class MiniAdapter extends RecyclerView.Adapter<MiniAdapter.miniHolder> {
    ArrayList<CatModel> items;
    Context context ;
    public MiniAdapter(ArrayList<CatModel> items , Context context) {
        this.items = items;
        this.context = context;
    }



    @Override
    public miniHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mini_categories, parent, false);

        return new miniHolder(v1);
    }

    @Override
    public void onBindViewHolder(final miniHolder holder, int position) {
        CatModel catModel = (CatModel) items.get(position);
        if ( !(items.get(position).getUrl()+"").isEmpty()&&!(items.get(position).getUrl()+"").equals("null")) {

            holder.progressBar.setVisibility(View.VISIBLE);
        }
        try{
        Picasso.with(context).load(items.get(position).getUrl()).fit()
                .placeholder(R.drawable.defaultimage).into(holder.imageView,
                new  ImageLoadedCallback(holder.progressBar)
                {
                    @Override
                    public void onSuccess() {
                        if (holder.progressBar != null) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    }
                });


        holder.textView.setText(items.get(position).getName());
    }catch (Exception e){
            String defult=context.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
            Picasso.with(context).load(defult).fit()
                    .placeholder(R.drawable.defaultimage).into(holder.imageView,
                    new  ImageLoadedCallback(holder.progressBar)
                    {
                        @Override
                        public void onSuccess() {
                            if (holder.progressBar != null) {
                                holder.progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class miniHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;

        public miniHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.activity_categories_image);
            textView = (TextView) itemView.findViewById(R.id.activity_categories_text);
            progressBar=(ProgressBar)itemView.findViewById(R.id.prog);
            progressBar.setVisibility(View.GONE);
        }
    }
    class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public ImageLoadedCallback(ProgressBar progBar) {
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }

}