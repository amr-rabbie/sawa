package solversteam.aveway.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import solversteam.aveway.Activities.Add_Address;
import solversteam.aveway.Activities.Login;
import solversteam.aveway.Activities.Order_show;
import solversteam.aveway.Activities.Profile;
import solversteam.aveway.Adapters.DrawerMenuRecylerAdapter;
import solversteam.aveway.Adapters.categories_menu_adapter;
import solversteam.aveway.Models.CategoryModel;
import solversteam.aveway.Models.MenuItems;
import solversteam.aveway.utiltes.Fonts;
import solversteam.aveway.utiltes.GetScreenSize;
import solversteam.aveway.utiltes.ItemClickSupport;
import solversteam.aveway.utiltes.LocaleHelper;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDrawer extends Fragment {

    private DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mDrawerRrecyclerView,categories_RecycleView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ImageView user_image;
    private TextView login_button,name_text;
    private LinearLayout addresslinear,orderlinear;
    ArrayList<CategoryModel>cateories;
    private int where_i_come;
    private TextView welcome;
    String firstcolor,secondcolor;

    public FragmentDrawer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        ItemClickSupport item=new ItemClickSupport (getContext());
        sharedPreferences=getContext().getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);

        firstcolor=sharedPreferences.getString("firstmenucolour","#7d2265");
        secondcolor=sharedPreferences.getString("secondmenucolour","#000000");
       String iso_code=sharedPreferences.getString("iso_code","en");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_drawer, container, false);

        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale(iso_code));
        Context context = LocaleHelper.setLocale(getContext(), iso_code);
        Resources resources = context.getResources();
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        Fonts fonts=new Fonts(view.getContext());
        user_image=(ImageView)view.findViewById(R.id.user_image);
        View  constraintLayout=view.findViewById(R.id.constraintLayout);
        constraintLayout.setBackgroundColor(Color.parseColor(firstcolor));
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        View layout = view.findViewById(R.id.relativeLayout2);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.parseColor(firstcolor),Color.parseColor(secondcolor)});
        gd.setCornerRadius(0f);

        layout.setBackgroundDrawable(gd);

        final GetScreenSize getScreenSize=new GetScreenSize(getActivity());
        getScreenSize.getImageSize();
        int width=getScreenSize.getWidth();
        int height=getScreenSize.getHeight();
        where_i_come=getArguments().getInt("where_icome");

        RelativeLayout menu_scroll=(RelativeLayout)view.findViewById(R.id.menu_scroll) ;
        addresslinear=(LinearLayout)menu_scroll.findViewById(R.id.address2);
        orderlinear=(LinearLayout)menu_scroll.findViewById(R.id.order);
         String logo =sharedPreferences.getString("logo","");
        ImageView imageView=(ImageView)view.findViewById(R.id.user_image);
        if(logo.isEmpty())
        {
            String defult=getActivity().getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
            Picasso.with(getActivity())
                    .load(R.drawable.defaultimage)
                    .placeholder(R.drawable.ave)
                    .resize(width/4,height/12)
                    .error(R.drawable.ave)
                    .into(imageView);
        }
        else {
            Picasso.with(getActivity())
                    .load(logo)
                    .placeholder(R.drawable.defaultimage)
                    .resize(width/4,height/12)
                    .error(R.drawable.ave)
                    .into(imageView);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // nothing
            }
        });
        Picasso.with(getActivity())
                .load(R.mipmap.order_history)
                .resize(width/8,height/20)
                .into((ImageView) orderlinear.findViewById(R.id.orderimage));
        Picasso.with(getActivity())
                .load(R.mipmap.gps_address)
                .resize(width/8,height/20)
                .centerInside()
                .into((ImageView) addresslinear.findViewById(R.id.addressimage));
       orderlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sharedPreferences.getString("name","").isEmpty() )
                {

                    Intent i =new Intent(view.getContext(), Order_show.class);
                    i.putExtra("frag","frag");
                    startActivity(i);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.msglogin, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getActivity(), Login.class);
                    intent.putExtra("where",where_i_come);
                    intent.putExtra("from","order");
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                }       }
        });
        addresslinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String customer_id=sharedPreferences.getString("customer_id","-1");
                if(!sharedPreferences.getString("name","").isEmpty() )
                {
                    Log.d("od",customer_id);
                    Intent i =new Intent(view.getContext(), Add_Address.class);
                i.putExtra("frag","frag");
                startActivity(i);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.msglogin, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getActivity(), Login.class);
                    intent.putExtra("where",where_i_come);
                    intent.putExtra("from","adress");
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                } }
        });
//
//        Picasso.with(getActivity())
//                .load(R.drawable.ave)
//                .resize(width/4,height/12)
//                .centerInside()
//                .into(user_image);
        //view.findViewById(R.id.menu_scroll).setNestedScrollingEnabled(false);
        Picasso.with(getActivity())
                .load(R.mipmap.setting)
                .resize(width/10,height/20)
                .centerInside()
                .into((ImageView) view.findViewById(R.id.settings));
        view.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getActivity() , Profile.class);
                intent.putExtra("where",where_i_come);
             //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });
        //login_button=(TextView)view.findViewById(R.id.loginandsignup);
        name_text=(TextView)view.findViewById(R.id.fragment_drawer_name_textview);
        welcome=(TextView)view.findViewById(R.id.welcome);
        fonts.setView(name_text);
        fonts.setView(welcome);

        //login_button.setVisibility(View.GONE);
        name_text.setVisibility(View.GONE);
        welcome.setVisibility(View.GONE);
        if(sharedPreferences.getString("name","").isEmpty())
        {
//            login_button=(TextView)view.findViewById(R.id.loginandsignup);
//            login_button.setVisibility(View.VISIBLE);
            name_text.setVisibility(View.GONE);
            welcome.setVisibility(View.GONE);
//        view.findViewById(R.id.loginandsignup).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDrawerLayout.closeDrawer(GravityCompat.START);
//                Intent intent = new Intent(getActivity() , Login.class);
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
//            }
//        });
        }
        else {
           // view.findViewById(R.id.loginandsignup).setVisibility(View.GONE);
            name_text.setVisibility(View.VISIBLE);
            welcome.setVisibility(View.VISIBLE);
            name_text.setText(sharedPreferences.getString("name",""));

        }
        editor=sharedPreferences.edit();
        mDrawerRrecyclerView = (RecyclerView) view.findViewById(R.id.drawer_layout_RecyclerView);
        categories_RecycleView=(RecyclerView)view.findViewById(R.id.categories_list);
        final ArrayList<MenuItems> Items;

        if (getArguments() != null)
        { Items = getArguments().getParcelableArrayList("items");
            cateories=  getArguments().getParcelableArrayList("categories");}
        else {
            Items = new ArrayList<>();
            Items.add(new MenuItems(R.string.NoDataArrived, R.drawable.truck));
        }
          DrawerMenuRecylerAdapter mMenuRecylerAdapter = null;
        mMenuRecylerAdapter = new DrawerMenuRecylerAdapter
              (Items, getActivity() , getArguments().getInt("where_icome"),mDrawerRrecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mDrawerRrecyclerView.setLayoutManager(mLayoutManager);
        mDrawerRrecyclerView.setItemAnimator(new DefaultItemAnimator());
        mDrawerRrecyclerView.setAdapter(mMenuRecylerAdapter);
       categories_menu_adapter cat_adapter = null;
       cat_adapter = new categories_menu_adapter
                ( getActivity() , cateories);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext());
       categories_RecycleView.setLayoutManager(LayoutManager);
       categories_RecycleView.setItemAnimator(new DefaultItemAnimator());
       categories_RecycleView.setAdapter(cat_adapter);

        return view;
    }

    public void setUp(DrawerLayout drawerLayout, final Toolbar toolbar) {

        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.DrawerIsOPend, R.string.DrawerIsClosed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

//            if (!mUserLearnedDrawer) {
//                mUserLearnedDrawer = true;
//                saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
//            }
//            getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };
//    if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
//        mDrawerLayout.openDrawer(containerView);
//    }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }
}
