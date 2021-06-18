package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.FragmentAdapter;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Fragments.FragmentDrawer;
import solversteam.aveway.Fragments.ItemShowFragment;
import solversteam.aveway.Models.CategoryModel;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.FragmentViews;
import solversteam.aveway.Models.ItemForView;
import solversteam.aveway.Models.MenuItems;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.Models.WishListModel;
import solversteam.aveway.utiltes.CreateViews;
import solversteam.aveway.utiltes.Fonts;
import solversteam.aveway.utiltes.FragmentInterface;
import solversteam.aveway.utiltes.GetScreenSize;
import solverteam.aveway.R;

public class ItemSelectedShowActivity extends AppCompatActivity {
    @BindView(R.id.include)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager mPager;
    @BindView(R.id.details_drawer_layout)
    DrawerLayout drawerLayout;
    private CreateViews mCreateViews;
    private static int pos;
    private  MenuItems item;
    private ArrayList<ItemForView> itemForViews;
    private ArrayList<WishListModel> list;
    private ArrayList<Integer> itemsID;
    private ArrayList<String> image_urls;
    private ArrayList<ChartModel>chartModels;
    private String item_id,item_name,item_image,category_name,category_id;
    private int item_oldprice,item_newprice,counter=0,item_offer,position,language;
    private Handler handler;
    private TextView notify;
    private TextView notifytext;
    private ArrayList<FragmentViews>fragmentViewses;
    private SharedPreferences sharedPreferences;
    private FragmentDrawer fragmentDrawer;
    private ArrayList<MenuItems> items;
    private Dialog dialog;
    private String logo,defult,firstcolor;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ArrayList<CategoryModel>category_menu_array;

    private int country;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected_show);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        ButterKnife.bind(this);
        fragmentViewses=new ArrayList<>();
        sharedPreferences=this.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE);
        country = sharedPreferences.getInt("Country", 1) ;
        firstcolor=sharedPreferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        language=sharedPreferences.getInt("lang",1);
        logo=sharedPreferences.getString("logo",R.drawable.ave+"");
        defult=sharedPreferences.getString("defaultimage",R.drawable.ave+"");
        Log.d("lang", language+"");
        if(TestModel.getfromshared("wish",this)!=null)
        {
            list=TestModel.getfromshared("wish",this);
            position=list.size();
        }
        else {
            list=new ArrayList<>();
        }
        if(TestModel.getfromcardmodel("card",this)!=null)
        {
            chartModels=TestModel.getfromcardmodel("card",this);
            position=chartModels.size();
        }
        else {
            chartModels=new ArrayList<>();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        TextView tx= (TextView)toolbar.findViewById(R.id.title);
        tx.setText(sharedPreferences.getString("projectname","E_commerce"));
        toolbar.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ItemSelectedShowActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);


            }
        });

        toolbar.findViewById(R.id.toolbar_share).setVisibility(View.VISIBLE);
        toolbar.findViewById(R.id.toolbar_shopping).setVisibility(View.VISIBLE);
         notifytext=(TextView) toolbar.findViewById(R.id.textOne);

        notifytext.setVisibility(View.INVISIBLE);
        if(TestModel.getfromcardmodel("card",this)==null)
        {
            notifytext.setVisibility(View.INVISIBLE);
        }
        if(TestModel.getfromcardmodel("card",this)!=null)
        {
            chartModels=TestModel.getfromcardmodel("card",this);
            if(chartModels.size()>0)
            {            notifytext.setVisibility(View.VISIBLE);

                notifytext.setText(TestModel.getfromshared("card",ItemSelectedShowActivity.this).size()+"");}
            else {
                notifytext.setVisibility(View.INVISIBLE);

            }

        }
        else {
            chartModels=new ArrayList<>();
            notifytext.setVisibility(View.INVISIBLE);

        }
        mCreateViews = new CreateViews(this);
        createMenu();
        ArrayList<ItemShowFragment> fragments = new ArrayList<>();
        try{
            itemForViews = getIntent().getExtras().getParcelableArrayList("myImages");
            itemsID=getIntent().getExtras().getIntegerArrayList("ID");
            pos = getIntent().getExtras().getInt("position");
            image_urls=getIntent().getExtras().getStringArrayList("img_urls");


            TestModel.setItemForViews(itemForViews);
            TestModel.setPositionitem(pos);
            TestModel.setIds(itemsID);
            TestModel.setItemimagearray(image_urls);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            itemForViews=TestModel.getItemForViews();
            pos=TestModel.getPositionitem();
            image_urls=TestModel.getItemimagearray();
            itemsID=TestModel.getIds();
        }




//        Log.d("checkitemscount",itemForViews.size()+"");
        for (int i = 0; i < itemsID.size(); i++) {
            fragments.add(new ItemShowFragment());
        }
        final FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, itemForViews, itemsID, pos, this,language);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(pos);

try {
    item_id = itemsID.get(pos).toString();
    item_name = itemForViews.get(pos).getName();
    item_oldprice = itemForViews.get(pos).getPrevprice();
    item_newprice = itemForViews.get(pos).getPrice();
    item_offer = itemForViews.get(pos).getOffer();
    item_image = image_urls.get(pos).toString();
    Log.d("checkimg", item_id + "");

}catch (Exception e){}
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FragmentInterface fragment = (FragmentInterface) adapter.instantiateItem(mPager, position);
                if (fragment != null) {
                    fragment.fragmentBecameVisible(ItemSelectedShowActivity.this, position , itemForViews,language);
                }
                pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        FragmentInterface fragment = (FragmentInterface) adapter.instantiateItem(mPager, pos);
        if (fragment != null) {
            fragment.fragmentBecameVisible(this, pos , itemForViews, language);
        }
        toolbar.findViewById(R.id.toolbar_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("ccc",itemForViews.get(pos).getImageUrl());
//                Toast.makeText(ItemSelectedShowActivity.this,itemForViews.get(pos).getProductUrl(),Toast.LENGTH_SHORT).show();
                //    view.startAnimation(AnimationUtils.loadAnimation(ItemSelectedShowActivity.this, R.anim.rotat));
                try {
                    Picasso.with(getApplicationContext()).load(image_urls.get(pos).toString()).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.putExtra(Intent.EXTRA_TEXT, "Hey,check out this amazing item " +
                                    itemForViews.get(pos).getName() + " for ".concat(itemForViews.get(pos).getPrice() + "") + " "
                                    + TestModel.getcurrencyname(getApplicationContext()) +
                                    " Here :" + "\n" + itemForViews.get(pos).getProductUrl() + "\n" +
                                    " Or Check out more deals on \n http://www.aveway.com or download E-Commerce App.\n");

                            i.setType("*/*");
                            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(Intent.createChooser(i, "share"));

                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });


//
                } catch (Exception e) {
                }
            } });
        toolbar.findViewById(R.id.toolbar_shopping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ItemSelectedShowActivity.this, ChartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
            overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
        }
    }
    public void onFragmentReady(final View v) {
        TestModel.setView(v);
        item_id=itemsID.get(pos).toString();
        item_name=itemForViews.get(pos).getName();
        category_name=itemForViews.get(pos).getCategory();
        category_id=itemForViews.get(pos).getCatgeory_id();

        analtyic();
        GetScreenSize getScreenSize = new GetScreenSize(this);
        getScreenSize.getImageSize();
        int width = getScreenSize.getWidth() / 5;
        int height = getScreenSize.getHeight() / 15;
        ImageView imageView=(ImageView)v.findViewById(R.id.fragment_item_show_image);
        if (!logo.isEmpty())
            Picasso.with(this)
                    .load(logo)
                    .placeholder(R.drawable.defaultimage)
                    .resize(width,height)
                    .error(R.drawable.ave)
                    .into(imageView);
        else
            Picasso.with(this)
                    .load(defult)
                    .placeholder(R.drawable.defaultimage)
                    .resize(width,height)
                    .error(R.drawable.ave)
                    .into(imageView);
        fragmentViewses.add(new FragmentViews(itemsID.get(pos).toString(),v));
        Log.d("checkitemcategory",itemForViews.get(pos).getCategory()+"\n"
                +itemForViews.get(pos).getCatgeory_id()+"\n"+item_name);
        TestModel.setFragmentViewses(fragmentViewses);
        Button button = (Button) v.findViewById(R.id.activity_details_add_to_chart_button);
        button.setBackgroundColor(Color.parseColor(firstcolor));
        new Fonts(this).setView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position=chartModels.size();
                chartModels=TestModel.getfromcardmodel("card",ItemSelectedShowActivity.this);
                item_id=itemsID.get(pos).toString();
                item_name=itemForViews.get(pos).getName();
                item_oldprice=itemForViews.get(pos).getPrevprice();
                item_newprice=itemForViews.get(pos).getPrice();
                item_offer=itemForViews.get(pos).getOffer();
                item_image=image_urls.get(pos).toString();
                ChartModel chartModel =new ChartModel(item_image,item_name,item_newprice,item_oldprice,item_offer,item_id);
                if(chartModels==null)
                {   chartModels=new ArrayList<ChartModel>();
                    chartModels.add(chartModel);
                    Toast.makeText(ItemSelectedShowActivity.this,R.string.youadd,Toast.LENGTH_LONG).show();

                }
                else {
                    if(!TestModel.checkmodel(chartModels,chartModel.getItem_id()))
                    {
                        Log.d("checkhna","anananna");
                        chartModels.add(chartModel);
                        Toast.makeText(ItemSelectedShowActivity.this,R.string.youadd,Toast.LENGTH_LONG).show();

                    }
                else
                        Toast.makeText(ItemSelectedShowActivity.this,R.string.youalreadyadd,Toast.LENGTH_LONG).show();
                }
                TestModel.savetosharedcardmodel(chartModels,"card",ItemSelectedShowActivity.this);
                if(chartModels.size()>0)
                {notifytext.setVisibility(View.VISIBLE);
                notifytext.setText(TestModel.getfromcardmodel("card",ItemSelectedShowActivity.this).size()+"");}
                else {
                    notifytext.setVisibility(View.INVISIBLE);
                }
                Log.d("checksizearray",TestModel.getfromcardmodel("card",ItemSelectedShowActivity.this).size()+"\n"+pos);
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("fragmentItem", itemForViews);
//                bundle.putInt("KEY_POSITION", pos);
//                Intent intent = new Intent(ItemSelectedShowActivity.this, ChartActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
//                posarray.add(pos+"");
//                TestModel.save_pos(ItemSelectedShowActivity.this,posarray);
//                Log.d("checkarraypositions",TestModel.get_pos(ItemSelectedShowActivity.this).size()+"");

            }
        });
        final ImageView fav_image = (ImageView) v.findViewById(R.id.activity_details_fav_Image_view);
        fav_image.setTag(itemsID.get(pos).toString());
        if(list!=null)
        {    item_id=itemsID.get(pos).toString();
            counter=0;

            if(TestModel.checkitem(list,item_id))
        {
            fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);

        }
        else {
            fav_image.setImageResource(R.mipmap.ic_wishlist_outline_disable);

        }}

        fav_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("checkfavtag",fav_image.getTag()+"\n"+pos);
                item_id=itemsID.get(pos).toString();
                item_name=itemForViews.get(pos).getName();
                item_oldprice=itemForViews.get(pos).getPrevprice();
                item_newprice=itemForViews.get(pos).getPrice();
                item_offer=itemForViews.get(pos).getOffer();
                item_image=image_urls.get(pos).toString();
                WishListModel wishListModel=new WishListModel(item_name,item_newprice+"",item_oldprice+"",item_image,item_id,item_offer);
                    Log.d("checkitemimage",item_id+"\n"+pos);
                    Log.d("checkiteminarrayid",counter+"");
                if(!TestModel.checkitem(list,wishListModel.getItem_id()))
                {
                    counter=0;
                }
                else {
                    counter=1;
                }
                Log.d("checkcounter0",counter+"");


                if(TestModel.check(counter))
                {
                    Toast.makeText(ItemSelectedShowActivity.this,R.string.addfav,Toast.LENGTH_SHORT).show();
                    fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                    if(list==null)
                    {   list=new ArrayList<WishListModel>();
                        list.add(wishListModel);

                    }
                    else {
                        if(!TestModel.checkitem(list,wishListModel.getItem_id()))
                        {
                            list.add(wishListModel);
                            Log.d("checkitemisinlist",TestModel.checkitem(list,wishListModel.getItem_id())+"hna");
                        }

                    }

                }
                else {
                    if(TestModel.checkitem(list,wishListModel.getItem_id()))
                    {
                        Toast.makeText(ItemSelectedShowActivity.this,R.string.removefav,Toast.LENGTH_SHORT).show();
                        fav_image.setImageResource(R.mipmap.ic_wishlist_outline_disable);
                        int  wish=TestModel.getitemposition(list,wishListModel.getItem_id());
                        list.remove(wish);
                        counter=0;
                    }
                }
                TestModel.savetoshared(list,"wish",ItemSelectedShowActivity.this);
                Log.d("checkdatasaved",TestModel.getfromshared("wish",ItemSelectedShowActivity.this).size()+"\n"+wishListModel.getItem_id()+"\n"+(pos)
                        +"\n"+counter);
                Log.d("checkitem",TestModel.checkitem(list,wishListModel.getItem_id())+"");
                if(TestModel.checkitem(list,wishListModel.getItem_id()))
                {
                    counter++;
                }
                Log.d("checkcoun2",counter+"");

            }
        });
        ImageView shop_image = (ImageView) v.findViewById(R.id.activity_details_shop_image_view);
        shop_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(AnimationUtils.loadAnimation(ItemSelectedShowActivity.this, R.anim.rotat));
                chartModels=TestModel.getfromcardmodel("card",ItemSelectedShowActivity.this);
                item_id=itemsID.get(pos).toString();
                item_name=itemForViews.get(pos).getName();
                item_oldprice=itemForViews.get(pos).getPrevprice();
                item_newprice=itemForViews.get(pos).getPrice();
                item_offer=itemForViews.get(pos).getOffer();
                item_image=image_urls.get(pos).toString();
                ChartModel chartModel =new ChartModel(item_image,item_name,item_newprice,item_oldprice,item_offer,item_id);
                if(chartModels==null)
                {   chartModels=new ArrayList<ChartModel>();
                    chartModels.add(chartModel);
                    Toast.makeText(ItemSelectedShowActivity.this,R.string.youadd,Toast.LENGTH_LONG).show();

                }
                else {
                    if(!TestModel.checkmodel(chartModels,chartModel.getItem_id()))
                    {
                        Log.d("checkhna","anananna");
                        chartModels.add(chartModel);
                        Toast.makeText(ItemSelectedShowActivity.this,R.string.youadd,Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(ItemSelectedShowActivity.this,R.string.youalreadyadd,Toast.LENGTH_LONG).show();
                }
                TestModel.savetosharedcardmodel(chartModels,"card",ItemSelectedShowActivity.this);
                if(chartModels.size()>0)
                {notifytext.setVisibility(View.VISIBLE);
                    notifytext.setText(TestModel.getfromcardmodel("card",ItemSelectedShowActivity.this).size()+"");}
                else {
                    notifytext.setVisibility(View.INVISIBLE);
                }



            }
        });
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.write_review);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemSelectedShowActivity.this, WriteReviewActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(item));
    }
    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
    public boolean onTouchEvent(MotionEvent event)
    {

        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            System.out.println("TOuch outside the dialog ******************** ");
            dialog.dismiss();
        }
        return false;
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                dialog = new Dialog(this, R.style.CircularProgress);
                dialog = new Dialog(this, R.style.CircularProgress);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_loading_item);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                LinearLayout relativeLayout;
                relativeLayout=(LinearLayout) dialog.findViewById(R.id.rel_loder);
                relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent event) {
                        switch(event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                // The user just touched the screen
                                //   Toast.makeText(SplashActivity.this,"ended", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                                break;
                            case MotionEvent.ACTION_UP:
                                // The touch just ended
                                // Toast.makeText(SplashActivity.this,"ended", Toast.LENGTH_SHORT).show();

                                break;
                        }

                        return false;
                    }
                });




                return dialog;
            default:
                return null;
        }
    }
    public void createMenu(){



        Connection connection3 = new Connection(ItemSelectedShowActivity.this, "/GetAllHomeParentCategory/"+country+"/"+language, "Get");
        connection3.reset();
        connection3.Connect(new Connection.Result() {
            @Override
            public void data(String str) throws JSONException {
                try {
                    category_menu_array=new ArrayList<>();
                    jsonObject = new JSONObject(str);
                    jsonArray = jsonObject.getJSONArray("1-HomeCategory");
                    Log.d("checkjsonarray", jsonArray.length() + "");
                    for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);

                        String catgory_image_url = jsonObject1.getString("category_image_url");
                        String category_name = jsonObject1.getString("category_name");
                        String catgory_id = jsonObject1.getString("id_category");
                        CategoryModel categoryModel=new CategoryModel(catgory_id,catgory_image_url,category_name);

                        category_menu_array.add(categoryModel);

                    }















         items = new ArrayList<>();
         item = new MenuItems(R.string.Home, R.mipmap.ic_home);
        items.add(item);
        item =new MenuItems(R.string.Countryandlanguage,R.drawable.eart);
        items.add(item);
        item = new MenuItems(R.string.myAccount, R.mipmap.ic_menu_account_circle);
        items.add(item);
        item = new MenuItems(R.string.myWishList, R.mipmap.ic_wishlist_outline_disable);
        items.add(item);
        item = new MenuItems(R.string.myCart, R.mipmap.ic_cart_outline);
        items.add(item);
        item = new MenuItems(R.string.contact,R.mipmap.contact);
        items.add(item);
        item = new MenuItems(R.string.call, R.mipmap.agenda);
        items.add(item);
        if(!sharedPreferences.getString("name","").isEmpty())
        { item = new MenuItems(R.string.LogOut, R.mipmap.logoutmenu);
            items.add(item);}
        else{
            for(int pos=0;pos<items.size();pos++)
            {   String name=String.valueOf(items.get(pos).getMenuItemName());
                if(name.equals(R.string.LogOut))
                {
                    items.remove(pos);
                }
            }
        }


        /////////////the same as main activity using the navegation drawer/////////////////
         fragmentDrawer = new FragmentDrawer();
        getSupportFragmentManager().beginTransaction().replace(R.id.details_fragment_navigation_drawer, fragmentDrawer).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
//        FragmentDrawer drawerFragment = (FragmentDrawer)
//                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        Bundle NavItems = new Bundle();
        NavItems.putInt("where_icome", 2);
        NavItems.putParcelableArrayList("items", items);
        NavItems.putParcelableArrayList("categories", category_menu_array);
        fragmentDrawer.setUp((DrawerLayout) findViewById(R.id.details_drawer_layout), toolbar);
        fragmentDrawer.setArguments(NavItems);
    } catch (Exception e)
                {
                    e.printStackTrace();
                }




            }});}

    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

       /// createMenu();

        if(TestModel.getfromcardmodel("card",this)!=null)
        {
            chartModels=TestModel.getfromcardmodel("card",this);
            if(chartModels.size()>0)
            {notifytext.setVisibility(View.VISIBLE);
            notifytext.setText(TestModel.getfromshared("card",ItemSelectedShowActivity.this).size()+"");}
            else {
                notifytext.setVisibility(View.INVISIBLE);
            }

        }
        else {
            notifytext.setVisibility(View.INVISIBLE);

        }
        if(TestModel.getfromcardmodel("wish",this)!=null)
        {
            list=TestModel.getfromshared("wish",this);
            item_id=itemsID.get(pos).toString();
            if(TestModel.getFragmentViewses()!=null)

            {
//                Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
                item_id=itemsID.get(pos).toString();
                ImageView fav_image;
                try{
                  fav_image = (ImageView) TestModel.getviewfragment(TestModel.getFragmentViewses(),item_id).findViewWithTag(item_id);
                if(list!=null)
                {    item_id=itemsID.get(pos).toString();
                    Log.d("checkitemslected","kda list msh b null"+"id:"+"\n"+item_id+"\n"+pos);
                    if(TestModel.checkitem(list,item_id))
                    {
                        fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                        Log.d("checkitemslected","kda item mogod f list  wh3mlo 2lb mnwr");

                    }
                    else {
                        Log.d("checkitemslected","kda item msh  mogod f list  wh3mlo 2lb mtfy");
                        fav_image.setImageResource(R.mipmap.ic_wishlist_outline_disable);
                    }


                }
                else {
                    Log.d("checkitemslected","kda list b null"+"id:"+"\n"+item_id);
                    fav_image.setImageResource(R.mipmap.ic_wishlist_outline_disable);

                }

            }
            catch (Exception e){}
        }}
        else {
            if(TestModel.getFragmentViewses()!=null)
            {
                try {
                    Log.d("checkitemslected", "kda shared fadya w view s7" + "id:" + "\n" + item_id);
                    final ImageView fav_image = (ImageView) TestModel.getviewfragment(TestModel.getFragmentViewses(), item_id).findViewWithTag(item_id);
                    fav_image.setImageResource(R.mipmap.ic_wishlist_outline_disable);
                }catch (Exception e){}
            }
            list=TestModel.getfromshared("wish",this);

            for(int posiiton=0;posiiton<TestModel.getFragmentViewses().size();posiiton++)
            {
                if(!TestModel.checkitem(list,TestModel.getFragmentViewses().get(posiiton).getItem_id()))
                {
                    final ImageView fav_image = (ImageView) TestModel.getviewfragment(TestModel.getFragmentViewses(),item_id).findViewWithTag(item_id);
                    fav_image.setImageResource(R.mipmap.ic_wishlist_outline_disable);
                }
            }
        }




    }
    private void analtyic() {


        try {
            Bundle bundle = new Bundle();
            bundle.putString("product_id", item_id+"");
            bundle.putString("product_name", item_name);
            bundle.putString("category_id", category_id+"");
            bundle.putString("category_name", category_name);


            mFirebaseAnalytics.logEvent("most_product", bundle);
            mFirebaseAnalytics.setUserProperty("product_id", item_id+"");
            mFirebaseAnalytics.setUserProperty("product_name",item_name+"");
            mFirebaseAnalytics.setUserProperty("category_id", category_id);
            mFirebaseAnalytics.setUserProperty("category_name", category_name);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
