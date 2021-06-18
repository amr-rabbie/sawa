package solversteam.aveway.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import solversteam.aveway.Activities.CategoriesActivity;
import solversteam.aveway.Models.CategoryModel;
import solversteam.aveway.Models.MenuItems;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

public class categories_menu_adapter extends RecyclerView.Adapter<categories_menu_adapter.MyViewHolder>  {
    private Context context;
    private ArrayList<CategoryModel>categories;

    private SharedPreferences.Editor editor;


    private int where_i_come;
    private String firstcolor;
    StateListDrawable states;
    private SharedPreferences sharedPreferences;


public categories_menu_adapter(Context context, ArrayList<CategoryModel>categories){

    this.context=context;
    this.categories=categories;




}
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_recycler_view_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        firstcolor=context.getSharedPreferences("LanguageAndCountry", MODE_PRIVATE).getString("secondmenucolour","#7d2265");

        GradientDrawable hover = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.parseColor(firstcolor),Color.parseColor(firstcolor)});
        hover.setCornerRadius(0f);

        states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed},hover);
        states.addState(new int[] {android.R.attr.state_focused},hover);

        holder.mItemClick.setBackgroundDrawable(states);


       CategoryModel menuItem = categories.get(position);
        holder.MnueItemtext.setText(menuItem.getCategory_name());
        holder.MenuItemImage.setBackgroundResource(R.drawable.ic_fiber_manual_record_black_24dp);
        holder.mItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoriesActivity.class);
                intent.putExtra("name",categories.get(position).getCategory_name());
                intent.putExtra("id",categories.get(position).getCategory_id());
                intent.putExtra("type" , "cat1_menu");
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView MnueItemtext;
        public ImageButton MenuItemImage;
        public LinearLayout mItemClick;
        public LinearLayout item;

        public MyViewHolder(View view) {
            super(view);
            MenuItemImage = (ImageButton) view.findViewById(R.id.menuItemimage);
            MnueItemtext = (TextView) view.findViewById(R.id.menuItemitext);
            mItemClick = (LinearLayout) view.findViewById(R.id.NavegationDrawerItemLinearLayout);


        }
    }


















}
