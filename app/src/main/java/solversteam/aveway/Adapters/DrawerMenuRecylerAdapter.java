package solversteam.aveway.Adapters;

import android.app.Activity;
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
import android.widget.Toast;

import java.util.List;

import solversteam.aveway.Activities.ChartActivity;
import solversteam.aveway.Activities.Contact_us;
import solversteam.aveway.Activities.LanguageAndCountry;
import solversteam.aveway.Activities.Login;
import solversteam.aveway.Activities.MainActivity;
import solversteam.aveway.Activities.Profile;
import solversteam.aveway.Activities.WishListActivity;
import solversteam.aveway.Models.MenuItems;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ibrahim on 25/01/2017.
 */

public class DrawerMenuRecylerAdapter extends RecyclerView.Adapter<DrawerMenuRecylerAdapter.MyViewHolder> {
    private SharedPreferences.Editor editor;
    private List<MenuItems> ItemsList;
    private Activity mContext;
    private int where_i_come;
    private String firstcolor;
    StateListDrawable states;
    private SharedPreferences sharedPreferences;
 private RecyclerView mMenuRecylerAdapter;
    public DrawerMenuRecylerAdapter(List<MenuItems> moviesList, Activity mContext, int where_i_come,RecyclerView mMenuRecylerAdapter) {
        this.ItemsList = moviesList;
        this.mContext = mContext;
        this.where_i_come = where_i_come;
        this.mMenuRecylerAdapter=mMenuRecylerAdapter;
        sharedPreferences=mContext.getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_recycler_view_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        firstcolor=mContext.getSharedPreferences("LanguageAndCountry", MODE_PRIVATE).getString("secondmenucolour","#7d2265");

        GradientDrawable hover = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.parseColor(firstcolor),Color.parseColor(firstcolor)});
        hover.setCornerRadius(0f);

        states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed},hover);
        states.addState(new int[] {android.R.attr.state_focused},hover);

        holder.mItemClick.setBackgroundDrawable(states);


        MenuItems menuItem = ItemsList.get(position);
        holder.MnueItemtext.setText(menuItem.getMenuItemName());
        holder.MenuItemImage.setBackgroundResource(menuItem.getMenutItemID());
        holder.mItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        if (where_i_come == 1) {
                            Intent intents = new Intent(mContext, LanguageAndCountry.class);
                            mContext.startActivity(intents);
                            mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                        } else {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mContext.startActivity(intent);
                            mContext.finish();
                            mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                        }


                        break;
                    case 1:
                        if (where_i_come == 2) {
                            Intent intent = new Intent(mContext, LanguageAndCountry.class);
                            mContext.startActivity(intent);
                            mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                        } else {
                            if(sharedPreferences.getString("name","").isEmpty())
                            {Intent intent = new Intent(mContext, Login.class);
                                intent.putExtra("where",where_i_come);
                                intent.putExtra("from","account");
                                mContext.startActivity(intent);
                                mContext.overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);}
                            else {
                                Intent intent = new Intent(mContext, Profile.class);
                                intent.putExtra("where",where_i_come);
                                mContext.startActivity(intent);
                                mContext.overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                            }
                        }
                        break;
                    case 2:
                        if (where_i_come == 1) {
                            Intent intent = new Intent(mContext, WishListActivity.class);
                            mContext.startActivity(intent);
                            mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                        } else {
                            if(sharedPreferences.getString("name","").isEmpty())
                            {Intent intent = new Intent(mContext, Login.class);
                                intent.putExtra("from","account");
                                mContext.startActivity(intent);


                                mContext.overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);}
                            else {
                                Intent intent = new Intent(mContext, Profile.class);
                                intent.putExtra("where",where_i_come);

                                mContext.startActivity(intent);
                                mContext.overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                            }
                        }
                        break;
                    case 3:
                        if (where_i_come == 2) {
                            Intent intent = new Intent(mContext, WishListActivity.class);
                            mContext.startActivity(intent);
                            mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                        } else {
                            Intent intent = new Intent(mContext, ChartActivity.class);
                            mContext.startActivity(intent);
                            mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                        }
                        break;
                    case 4:

                        if (where_i_come == 2) {
                            Intent intent = new Intent(mContext, ChartActivity.class);
                            mContext.startActivity(intent);
                            mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                        }
                        else {

                            Intent intent = new Intent(mContext, Contact_us.class);
                            //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("cont","contactus");
                            mContext.startActivity(intent);
                            mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);

                        }
                        break;
                    case 7:
                    {

                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        editor.putString("customer_id","");
                        editor.putString("name","");
                        editor.putString("email","");
                        editor.commit();
                        Toast.makeText(mContext,"Successfully loggedout",Toast.LENGTH_SHORT).show();
                        mContext.startActivity(intent);

                        mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                        mContext.finish();
                        ItemsList.remove(ItemsList.size()-1);
                       notifyDataSetChanged();
                    }
                    break;

                    case 5:
                    {   if(where_i_come==2)
                    {
                        Intent intent = new Intent(mContext, Contact_us.class);
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("cont","contactus");
                        mContext.startActivity(intent);
                        mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                    }
                    else {
                        Intent intent = new Intent(mContext, Contact_us.class);
                        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("cont","aboutus");
                        mContext.startActivity(intent);

                        mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);

                    }

                        // mContext.finish();
                        // Items.remove(Items.size()-1);

                       notifyDataSetChanged();
                    }
                    break;
                    case 6:
                    {   if(where_i_come==2)
                    {Intent intent = new Intent(mContext, Contact_us.class);
                        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("cont","aboutus");
                        mContext.startActivity(intent);

                        mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);}
                    else{
                        if(!sharedPreferences.getString("name","").isEmpty())
                        {Intent intent = new Intent(mContext, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            editor.putString("customer_id","");
                            editor.putString("name","");
                            editor.putString("email","");
                            editor.commit();
                            Toast.makeText(mContext,"Successfully loggedout",Toast.LENGTH_SHORT).show();
                            mContext.startActivity(intent);
                            mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                            ItemsList.remove(ItemsList.size()-1);
                            notifyDataSetChanged();
                            //DrawerMenuRecylerAdapter.notifyDataSetChanged();
                            //login_button.setVisibility(View.VISIBLE);
                            //name_text.setVisibility(View.GONE);
                        }
                    }
                        // mContext.finish();
                        // Items.remove(Items.size()-1);
                        //mMenuRecylerAdapter.notifyDataSetChanged();
                    }
                    break;

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemsList.size();
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
