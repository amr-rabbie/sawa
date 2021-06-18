package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.SlidingImage_Adapter;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Fragments.FragmentDrawer;
import solversteam.aveway.Models.CategoryModel;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.ItemForView;
import solversteam.aveway.Models.MenuItems;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.Models.WishListModel;
import solversteam.aveway.utiltes.CreateViews;
import solversteam.aveway.utiltes.GetScreenSize;
import solversteam.aveway.utiltes.LocaleHelper;
import solversteam.aveway.utiltes.MyObservableScrollView;
import solversteam.aveway.utiltes.MyScrollViewListener;
import solverteam.aveway.R;


public class MainActivity extends AppCompatActivity implements MyScrollViewListener {
    @BindView(R.id.home_page_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_main_viewpager)
    ViewPager mPager;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.searchtext)
    EditText editText;
    private Handler mHandler;
    private Dialog dialog;
    private Runnable mUptateImage;
    private FragmentDrawer fragmentDrawer;
    private ArrayList<MenuItems> items;
    private CreateViews createViews;
    private int i = 0, j = 0, a = 0;
    private String firstcolor;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    MyObservableScrollView scrollView;
    ProgressBar progressBar;
    private boolean myflag = false;
    private ArrayList<WishListModel> models;
    private int id = 0,conversion_rate;
    private int country, language;
    private ArrayList<CategoryModel> catgeoriesImages1,category_menu_array;
    private ArrayList<CategoryModel> catgeoriesImages2;

    private ArrayList<String> cat2_1,cat2_2,cat2_1_name,category_name,category_id;
    private ArrayList<ChartModel> chartModels;
    ArrayList<String> Idsfull = new ArrayList<>();
    private SharedPreferences preferences;
    private MenuItems item;
private SharedPreferences.Editor editor;

    private TextView notifytext;
    private String iso_code;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        iso_code = preferences.getString("iso_code", "en");
        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale(iso_code));
        Context context = LocaleHelper.setLocale(this, iso_code);
        Resources resources = context.getResources();
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        catgeoriesImages1=new ArrayList<>();
         items = new ArrayList<>();
        catgeoriesImages2=new ArrayList<>();
        category_id=new ArrayList<>();
        category_name=new ArrayList<>();
        LinearLayout linearLayoutsearch = (LinearLayout) findViewById(R.id.linearLayout);
        editor=preferences.edit();
        firstcolor=preferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        linearLayoutsearch.setBackgroundColor(Color.parseColor(firstcolor));
        language = preferences.getInt("Lang", 1) ;

        Log.d("checklang",iso_code+"");
        country = preferences.getInt("Country", 1) ;
        conversion_rate=preferences.getInt("currencyconversion",1);
        cat2_1=new ArrayList<>();
         fragmentDrawer = new FragmentDrawer();

        cat2_2=new ArrayList<>();
        cat2_1_name=new ArrayList<>();



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        TextView tx= (TextView)toolbar.findViewById(R.id.title);
        tx.setText(preferences.getString("projectname","E_commerce"));
        toolbar.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_LONG).show();
            }
        });
        views();

    }

    public void views() {
        toolbar.findViewById(R.id.toolbar_shopping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });
        toolbar.findViewById(R.id.toolbar_shopping).setVisibility(View.VISIBLE);
        createViews = new CreateViews(this,toolbar);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_from_bottom, R.anim.no_thing);
//                mHandler.removeCallbacks(mUptateImage);
            }
        });
         notifytext=(TextView)toolbar.findViewById(R.id.textOne);
         notifytext.setVisibility(View.INVISIBLE);
        if(TestModel.getfromcardmodel("card",this)==null)
        {
            notifytext.setVisibility(View.INVISIBLE);
        }
        if(TestModel.getfromcardmodel("card",this)!=null)
        {
            chartModels=TestModel.getfromcardmodel("card",this);
            notifytext.setVisibility(View.VISIBLE);
            if(chartModels.size()>0)
            {notifytext.setText(TestModel.getfromshared("card",MainActivity.this).size()+"");}
            else {
                notifytext.setVisibility(View.INVISIBLE);

            }

        }
        else {
            chartModels=new ArrayList<>();
            notifytext.setVisibility(View.INVISIBLE);

        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //////////////////////////////////////////////////////////////////
        GetScreenSize getScreenSize = new GetScreenSize(MainActivity.this);
        getScreenSize.getImageSize();
        ViewGroup.LayoutParams parms = mPager.getLayoutParams();
        parms.height = getScreenSize.getHeight() / 5;
        parms.width = getScreenSize.getWidth();
        ////////////////////////////////view Pager/////////////////////////////////////////
        mPager.setLayoutParams(parms);
        if(TestModel.getImages()!=null)
        {
            if (TestModel.getImages().size() > 0) {
        try {



                    mPager.setAdapter(new SlidingImage_Adapter(MainActivity.this, 1, TestModel.getImages()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mHandler = new Handler();
        mUptateImage = new Runnable() {
            @Override
            public void run() {
                AnimateandSlidShow();
                Log.i("pop", 123 + "");
            }
        };
        int delay = 3000;//delay for 3 sec
        int period = 5000;//repeat every 5 sec
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(mUptateImage);
            }
        }, delay, period);
        }
            else {
                mPager.setVisibility(View.GONE);}}
        else {
            mPager.setVisibility(View.GONE);
        }
        /////////////////////////////slid show with handler////////////////////////////////////////
        appendData("/GetAllNewProduct/" + country + "/" + language, "1-NEW_PRODUCT");
        if (TestModel.getHomecatids()!=null) {
            appendData("/GetAllGategoryProduct/" + country + "/" + language + "/" + TestModel.getHomecatids().get(id), "1-GategoryProduct");
        }
        ////////////////////////////categories/////////////////////////////////////////////////////
        try {
            createViews.categoriesInitt(TestModel.getCat(), TestModel.getCatName(), (HorizontalScrollView) findViewById(R.id.activity_main_category_recycler), (TextView) findViewById(R.id.show_brands_text), R.id.main_activity_HorizontalScrollView_Linear_Layout, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ////////////////////////////////categories2 Main Image///////////////////////////////////////////////////////////
         createMenu();
        //////////////////////////////////////categories2////////////////////////////////////////////////////////////////
        Connection connection3 = new Connection(MainActivity.this, "/GetAllHomeTopCategory/"+country+"/"+language+"/0"+"/11", "Get");
        connection3.reset();
        connection3.Connect(new Connection.Result() {
            @Override
            public void data(String str) throws JSONException {
                try {

                    jsonObject = new JSONObject(str);
                    jsonArray = jsonObject.getJSONArray("1-HomeCategory");
                    Log.d("checkjsonarray", jsonArray.length() + "");
                    for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);

                        String catgory_image_url = jsonObject1.getString("category_image_url");
                        String category_name = jsonObject1.getString("category_name");
                        String catgory_id = jsonObject1.getString("id_category");
                        CategoryModel categoryModel=new CategoryModel(catgory_id,catgory_image_url,category_name);
                        cat2_1.add(jsonObject1.getString("id_category"));
                        cat2_1_name.add(jsonObject1.getString("category_name"));

                        Log.d("checkcatsize",cat2_1.size()+"");
                        if(Counter==0||Counter<6)
                        {
                            catgeoriesImages1.add(categoryModel);

                        }
                        else if(Counter!=0&&Counter>5)
                        {
                            catgeoriesImages2.add(categoryModel);

                        }
                        Log.d("checkarraysize",catgeoriesImages1.size()+"\n"+catgeoriesImages2.size());
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                if(catgeoriesImages1.size()>0)
                {  try {

                    createViews.maincategorisImageInit(catgeoriesImages1.get(0).getCategory_image_url(), (HorizontalScrollView) findViewById(R.id.main_activity_categories_recycler));
                    TestModel.setCat3(catgeoriesImages1.get(0).getCategory_id());
                    TestModel.setCat3_name(catgeoriesImages1.get(0).getCategory_name());
                }

                catch (Exception e) {
                    e.printStackTrace();
                }
                    catgeoriesImages1.remove(0);
                    cat2_1.remove(0);
                    cat2_1_name.remove(0);
                    TestModel.setCatIds2_1(cat2_1);
                    TestModel.setCatIds2_1_name(cat2_1_name);
                    Log.d("checkcatsize1",cat2_1.size()+"");
                    createViews.categories2Init(catgeoriesImages1, 1, (HorizontalScrollView) findViewById(R.id.main_activity_sub_categories_recycler1));
                    createViews.categories2Init(catgeoriesImages2, 2, (HorizontalScrollView) findViewById(R.id.main_activity_sub_categories_recycler2));

                }


            }});

        //////////////////////////////////////recently seen items//////////////////////////////////////////////////////////
        ///////////////////////////////////////////////Brands////////////////////////////////////////////////////////////

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.container_Layout);
        try {
//            Toast.makeText(this, "size " + TestModel.getCat1().size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            createViews.categoriesInit(TestModel.getCat1(), null, (HorizontalScrollView) linearLayout.findViewById(R.id.brands), (TextView) findViewById(R.id.show_brands_text), R.id.brands_layout, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scrollView = (MyObservableScrollView) findViewById(R.id.activity_main_scroll_view);
        scrollView.setScrollViewListener(this);
        scrollView.setClipToPadding(true);
        findViewById(R.id.container_Layout).setPadding(0, 0, 0, 200);
    }

    private void createMenu() {


        Connection connection3 = new Connection(MainActivity.this, "/GetAllHomeParentCategory/"+country+"/"+language, "Get");
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


                    item =new MenuItems(R.string.Countryandlanguage,R.drawable.eart);
                    items.add(item);
                    item = new MenuItems(R.string.myAccount, R.mipmap.ic_menu_account_circle);
                    items.add(item);
                    item = new MenuItems(R.string.myWishList, R.drawable.heart);
                    items.add(item);
                    item = new MenuItems(R.string.myCart, R.drawable.cart);
                    items.add(item);
                    item = new MenuItems(R.string.contact,R.mipmap.contact);
                    items.add(item);
                    item = new MenuItems(R.string.call, R.mipmap.agenda);
                    items.add(item);

                    Log.d("mname",preferences.getString("name",""));
                    if(!preferences.getString("name","").isEmpty())
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

                    /////////////////////////////used to replace the framelayout in the xml with the new fragment////////////////////////
                    ////////////////////////////do it as u see because i want to send array list of the menu from here to make it dynamic/////////////////
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_navigation_drawer, fragmentDrawer).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();

                    //////////////////////////////send menu date in a bundle using parcel/////////////////////
                    Bundle NavItems = new Bundle();
                    NavItems.putParcelableArrayList("items", items);
                    NavItems.putParcelableArrayList("categories", category_menu_array);
                    NavItems.putInt("where_icome" , 1);
                    ///////////////////////////used to setup the tool bar and the toggle button in the fragmenet//////////////////
                    fragmentDrawer.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
                    fragmentDrawer.setArguments(NavItems);






                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }




            }});






























    }

    //////////////////////used to slide images in the view pager/////////////
    private void AnimateandSlidShow() {
        /// set item and i use i%image.size to make it as a circle/////////////
        mPager.setCurrentItem(i, true);
        i++;
        try {
            if (i == TestModel.getImages().size())
                i = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public boolean onTouchEvent(MotionEvent event)
    {

        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            System.out.println("TOuch outside the dialog ******************** ");
            dialog.dismiss();
        }
        return false;
    }


    //////////////////////////////close the navigation drawer if needed/////////////
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            String logo=preferences.getString("logo","");
//            DialogFragment newFragment = MyAlertDialogFragment.newInstance(
//                    R.string.close_app,MainActivity.this,logo);
//            newFragment.show(getFragmentManager(), "dialog");


            new AlertDialog.Builder(this)
                    .setTitle(preferences.getString("projectname","E_commerce"))
                    .setMessage(getResources().getString(R.string.close_app)+"\t"+preferences.getString("projectname","E_commerce"))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                          finish();
                            overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(R.drawable.appicon)
                    .show();
        }
    }

    void appendData(final String url, final String key) {
        myflag = true;
        final ArrayList<ItemForView> itemForViews = new ArrayList<>();
        final ArrayList<String> image_urls = new ArrayList<>();
        Connection connection = new Connection(MainActivity.this, url, "Get");
        connection.reset();
        connection.Connect(new Connection.Result() {
            ArrayList<Integer> Ids = new ArrayList<>();



            @Override
            public void data(String str) throws JSONException {
                if (str != null && !str.isEmpty()) {
                    jsonObject = new JSONObject(str);
                    jsonArray = jsonObject.getJSONArray(key);
                    for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                        itemForViews.add(new ItemForView(jsonObject1.getString("productlink")
                                ,jsonObject1.getString("category_name")
                                , "",
                                jsonObject1.getString("url_image"),
                                jsonObject1.getString("Name"),
                                jsonObject1.getInt("Price tax excl.")*conversion_rate,
                                0, 0, R.layout.item_layout, url, key,jsonObject1.getString("categoryid")));
                        Log.d("checkimage", jsonObject1.getString("url_image"));
                        Ids.add(jsonObject1.getInt("ID"));
                        image_urls.add(jsonObject1.getString("url_image"));
                        Idsfull.add(jsonObject1.getString("ID"));
                        category_name.add(jsonObject1.getString("category_name"));
                        category_id.add(jsonObject1.getString("categoryid"));
                    }
                    try {
                        createViews.CreateItemView(itemForViews,
                                Ids, (LinearLayout) findViewById(R.id.activity_main_views_container),
                                1, image_urls,category_name,category_id);
                        TestModel.setAllids(Idsfull);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // scrollView.smoothScrollTo((int)scrollView.getX(),(int)scrollView.getY()+100);
                } else {
//                    Toast.makeText(MainActivity.this, "123 No Data Available", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onScrollEnded(MyObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
//        Toast.makeText(this, "id = " + id, Toast.LENGTH_SHORT).show();
        View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        if (diff <= 10)  try {
            if(id<TestModel.getHomecatids().size()) {
//                Toast.makeText(this, " id " + TestModel.getHomecatids().get(id) + "    Size " + TestModel.getHomecatids().size(), Toast.LENGTH_SHORT).show();
                id++;
                appendData("/GetAllGategoryProduct/" + country + "/" + language + "/" + TestModel.getHomecatids().get(id), "1-GategoryProduct");

            }
// if (id == a)
//                a++;id++;
        } catch (Exception e) {

        }
    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
//        super.onSaveInstanceState(outState);
//    }




    @Override
    protected void onRestart() {
        super.onRestart();


       /* fragmentDrawer=new FragmentDrawer();
        if(!preferences.getString("name","").isEmpty())
        { if(items.get(items.size()-1).getMenuItemName()!=(R.string.LogOut))
            {
                item = new MenuItems(R.string.LogOut, R.mipmap.logoutmenu);
                items.add(item);
            }
        }
        else{
            for(int pos=0;pos<items.size();pos++)
            {   String name=String.valueOf(items.get(pos).getMenuItemName());
                if(name.equals(R.string.LogOut))
                {
                    items.remove(pos);
                }
            }
        }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_navigation_drawer, fragmentDrawer).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();

//        FragmentDrawer drawerFragment = (FragmentDrawer)
//                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        //////////////////////////////send menu date in a bundle using parcel/////////////////////
        Bundle NavItems = new Bundle();
        NavItems.putParcelableArrayList("items", items);
        NavItems.putInt("where_icome" , 1);
        NavItems.putParcelableArrayList("categories", category_menu_array);
        ///////////////////////////used to setup the tool bar and the toggle button in the fragmenet//////////////////
        fragmentDrawer.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        fragmentDrawer.setArguments(NavItems);

//        Toast.makeText(this,"testtest",Toast.LENGTH_SHORT).show();


*/
       createMenu();
        if(TestModel.getfromcardmodel("card",this)!=null)
        {
            chartModels=TestModel.getfromcardmodel("card",this);
            if(chartModels.size()>0)
            {notifytext.setVisibility(View.VISIBLE);
            notifytext.setText(TestModel.getfromshared("card",MainActivity.this).size()+"");}
            else {
            notifytext.setVisibility(View.INVISIBLE);
        }

        }
        else {
            notifytext.setVisibility(View.INVISIBLE);

        }
        models=new ArrayList<>();
        models=TestModel.getfromshared("wish",this);
        MyObservableScrollView linearLayout=(MyObservableScrollView)findViewById(R.id.activity_main_scroll_view);
            if(models!=null) {

                for (int position = 0; position < models.size(); position++) {
                    if (TestModel.checkidinmain(TestModel.getAllids(), models.get(position).getItem_id())) {
                        Log.d("checkidb2a", models.get(position).getItem_id() + "");
                        try {
                            ImageView view = (ImageView) linearLayout.findViewWithTag(models.get(position).getItem_id() + "");
                            view.setImageResource(R.drawable.ic_favorite_black_24dp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
                try{
                models=TestModel.getfromshared("wish",this);
                for (int position=0;position<TestModel.getAllids().size();position++)
                {   //Log.d("checkmogod",TestModel.checkitem(models,TestModel.getAllids().get(position).toString())+"");
                    if (!TestModel.checkitem(models,TestModel.getAllids().get(position).toString())) {
                        try {
                            ImageView view = (ImageView) linearLayout.findViewWithTag(TestModel.getAllids().get(position) + "");
                            view.setImageResource(R.mipmap.ic_wishlist_outline_disable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }}catch (Exception e){}
            }
//        }



    }



    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }



}
