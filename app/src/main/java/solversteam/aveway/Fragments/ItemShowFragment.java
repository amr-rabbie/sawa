package solversteam.aveway.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Activities.ItemSelectedShowActivity;
import solversteam.aveway.Adapters.SlidingImage_Adapter;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Models.ItemForView;
import solversteam.aveway.Models.Reviews_Items_Model;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.utiltes.CreateViews;
import solversteam.aveway.utiltes.FragmentInterface;
import solversteam.aveway.utiltes.GetScreenSize;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemShowFragment extends Fragment implements ViewPager.OnPageChangeListener, FragmentInterface {
    private static Bundle bundle;
    @BindView(R.id.activity_details_item_viewpager)
    ViewPager mPager;
    @BindView(R.id.activity_details_review_viewpager)
    ViewPager mReviewPager;
    private int dotsCount;
    private ImageView[] dots;
    private LinearLayout pager_indicator;
    private SlidingImage_Adapter adapter;
    private CreateViews mCreateViews;
    ArrayList<Integer> relatedItems;
    ArrayList<String> slidingImages;
    String ProductName, CountOFReviews, City, Date, OffersCount, Description;
    ArrayList<String> Colors, Capacity, specification1, specification2;
    static ArrayList<Reviews_Items_Model> reviews;
    static ArrayList<ItemShowFragment> fragments;
    static ArrayList<Bundle> bundles;
    private int Offer, Price, pevPrice;
    static int myposition;
    ProgressBar progressBar;
    static ArrayList<Integer> myIDs;
    View v;
    private SharedPreferences preferences;
    private int country, language;
    private Context context;
    private TextView price,postion,seller_name;

    // Button addToChart ;
    public static final ItemShowFragment newInstance(ItemForView itemForViews, ArrayList<Integer> IDs, int position, Context context,int language) {
        ItemShowFragment fragmentFirst = new ItemShowFragment();
        // reviews = reviews_items_models;
        myIDs = IDs;
        Log.d("checkitemlanguage",language+"");

        bundle = new Bundle();
        fragments = new ArrayList<>();
        bundles = new ArrayList<>();

        bundle.putStringArrayList("slidingImages", itemForViews.getImages());
//
        bundle.putInt("Offer", itemForViews.getOffer());
        bundle.putString("ProductName", itemForViews.getName());
        bundle.putInt("Price", itemForViews.getPrice());
        bundle.putInt("pevPrice", itemForViews.getPrevprice());

        fragmentFirst.setArguments(bundle);
        fragments.add(fragmentFirst);
        bundles.add(bundle);
        return fragmentFirst;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Offer = getArguments().getInt("Offer");
        ProductName = getArguments().getString("ProductName");
        Price = getArguments().getInt("Price");
        pevPrice = getArguments().getInt("pevPrice");
        SharedPreferences preferences;

        Description = getArguments().getString("Description");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_item_show, container, false);
        ButterKnife.bind(this, v);
        preferences = v.getContext().getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        language = preferences.getInt("Lang", 1) ;

        price = (TextView) v.findViewById(R.id.acrivity_details_price);
         postion = (TextView) v.findViewById(R.id.item_position);
         seller_name=(TextView) v.findViewById(R.id.seller_name);
        Log.d("hlang_f",language+"");

        /////////////////////////////
//        TextView price = (TextView) v.findViewById(R.id.acrivity_details_price);
//        price.setText(Price + " "+ TestModel.getcurrencyname(getContext()));
        ///////////////////////////////
        mCreateViews = new CreateViews(((Activity)v.getContext()));
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewPager);

        v.findViewById(R.id.acrivity_details_offer).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.offerContainer).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.acrivity_details_prev_price).setVisibility(View.INVISIBLE);
        GetScreenSize getScreenSize = new GetScreenSize(v.getContext());
        getScreenSize.getImageSize();
        ViewGroup.LayoutParams parms = mPager.getLayoutParams();
        parms.height = (int) (getScreenSize.getHeight() / 2.7);
        parms.width = getScreenSize.getWidth();
        ////////////////////////////////view Pager/////////////////////////////////////////
        pager_indicator = (LinearLayout) v.findViewById(R.id.viewpagerCountDots);
        mPager.setLayoutParams(parms);


        //used to lesen when swiping the view pager images
        mPager.setOnPageChangeListener(this);
//        final TextView postion = (TextView) v.findViewById(R.id.item_position);
//        postion.setText(ProductName);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ItemSelectedShowActivity) this.v.getContext()).onFragmentReady(v);
    }

    /////////set the indicator to act with arabic language /////////////////
    private void setUiPageViewControlAR() {
        if (pager_indicator.getChildCount() > 0)
            pager_indicator.removeAllViews();
        dotsCount = adapter.getCount();
//        Toast.makeText(v.getContext(), "**********" + adapter.getCount(), Toast.LENGTH_SHORT).show();
        mPager.setCurrentItem(0);
        dots = new ImageView[dotsCount];
        for (int i = dotsCount -1 ; i >= 0; i--) {
            try{
            dots[i] = new ImageView(v.getContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.tab_selector));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            pager_indicator.addView(dots[i], params);}catch (Exception e){}
        }
        if (dotsCount != 0)

        try{
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.tab_indicator_selected));}catch (Exception e){}
    }

    /////////set the indicator to act with english language /////////////////
    private void setUiPageViewControlEN() {
        if (pager_indicator.getChildCount() > 0)
            pager_indicator.removeAllViews();
        dotsCount = adapter.getCount();
//        Toast.makeText(v.getContext(), "**********" + adapter.getCount(), Toast.LENGTH_SHORT).show();
        mPager.setCurrentItem(0);
        dots = new ImageView[dotsCount];
        try{
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(v.getContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.tab_selector));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            pager_indicator.addView(dots[i], params);
        }
        if (dotsCount != 0)
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.tab_indicator_selected));}catch (Exception e){}
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    //used to change indicator image when swiping///////////////////
    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.tab_selector));
        }
        if (dotsCount != 0)
            dots[position].setImageDrawable(getResources().getDrawable(R.drawable.tab_indicator_selected));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void fragmentBecameVisible(Activity activity, final int myposition, final ArrayList<ItemForView> itemForViews, int lang) {
//        Toast.makeText(activity, "pop " + myposition, Toast.LENGTH_SHORT).show();
//        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        SharedPreferences preferences=activity.getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        language = preferences.getInt("Lang",1);
        Log.i("kkkkk", "http://166.63.124.72:81/ecommerce" + "/GetAllProductData/" + myIDs.get(myposition) + "/"+language);
        Connection connection = new Connection(activity, "/GetAllProductData/" + myIDs.get(myposition) + "/" +
                language, "Get");
        connection.reset();
        connection.Connect(new Connection.Result() {
            JSONObject jsonObject;
            JSONArray jsonArray;

            @Override
            public void data(String str) throws JSONException {
                jsonObject = new JSONObject(str);
                jsonArray = jsonObject.getJSONArray("1-ProductData");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                mCreateViews.showDescriptionsDetails((LinearLayout) v.findViewById(R.id.activity_details_description_container), jsonObject1.getString("Short description"), jsonObject1.getString("Description"), null, null);
                try {price.setText(jsonObject1.getString("Price tax excl.") + " "+ TestModel.getcurrencyname(v.getContext()));
                    postion.setText(jsonObject1.getString("Name"));
                    seller_name.setText(jsonObject1.getString("SellerName"));
                    Log.d("yamosahel",jsonObject1.getString("Name")+"\n"+jsonObject1.getString("Price tax excl.") );
                }catch (Exception e){}

            }
        });

//        if (Offer <= 0 || pevPrice <= 0) {
//            v.findViewById(R.id.acrivity_details_offer).setVisibility(View.INVISIBLE);
//            v.findViewById(R.id.offerContainer).setVisibility(View.INVISIBLE);
//            v.findViewById(R.id.acrivity_details_prev_price).setVisibility(View.INVISIBLE);
//        } else {
//            TextView offer = (TextView) v.findViewById(R.id.offer);
//            offer.setText(Offer + "");
//            TextView prevprice = (TextView) v.findViewById(R.id.prevPrice);
//            prevprice.setText(prevprice + "");
//        }
        slidingImages = new ArrayList<>();
        Log.d("checklangb2a",language+"");
        Connection connection1 = new Connection(activity, "/GetAllProductImages/" + myIDs.get(myposition) + "/"+language, "Get");
        connection1.reset();
        connection1.Connect(new Connection.Result() {
            @Override
            public void data(String str) throws JSONException {
                JSONObject jsonObject;
                JSONArray jsonArray;
                jsonObject = new JSONObject(str);
                jsonArray = jsonObject.getJSONArray("1-ProductImages");
                for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                    slidingImages.add(Counter, jsonObject1.getString("url_image"));
                }
                adapter = new SlidingImage_Adapter(v.getContext(), slidingImages, 2, 0);
                mPager.setAdapter(adapter);
                itemForViews.get(myposition).setImages(slidingImages);
                if (Locale.getDefault().getLanguage().equals("en") && slidingImages.size() > 0)
                    setUiPageViewControlEN();
                else if (slidingImages.size() > 0)
                    setUiPageViewControlAR();
                //slidingImages.clear();

            }
        });

    }
}
