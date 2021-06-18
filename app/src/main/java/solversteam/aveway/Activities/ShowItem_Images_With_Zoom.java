package solversteam.aveway.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.SlidingImage_Adapter;
import solversteam.aveway.utiltes.GetScreenSize;
import solversteam.aveway.utiltes.IndicatedHorizontalScrollView;
import solversteam.aveway.utiltes.ViewPagerFixed;
import solverteam.aveway.R;

public class ShowItem_Images_With_Zoom extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.activity_details_item_viewpager)
    ViewPagerFixed mPager;
    @BindView(R.id.viewpagerCountDots)
    LinearLayout pager_indicator;
    @BindView(R.id.horzintal_Scroll)
    IndicatedHorizontalScrollView scrollView;
    private int dotsCount;
    private ImageView[] dots;
    private LinearLayout[] linearLayout;
    private SlidingImage_Adapter adapter;
    ArrayList<String> slidingImages;
    private int pos;
    private String firstcolor;
    @BindView(R.id.include)
    Toolbar toolbar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item__images__with__zoom);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        firstcolor=sharedPreferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.abc_ic_clear_mtrl_alpha);
        getSupportActionBar().setTitle("");
        GetScreenSize getScreenSize = new GetScreenSize(this);
        getScreenSize.getImageSize();
        ViewGroup.LayoutParams parms = mPager.getLayoutParams();
        parms.height = (int) (getScreenSize.getHeight() / 1.5);
        parms.width = getScreenSize.getWidth();
        slidingImages = getIntent().getStringArrayListExtra("Images");
//        Toast.makeText(this, "pos "+getIntent().getExtras().getInt("position"), Toast.LENGTH_SHORT).show();
        pos = getIntent().getExtras().getInt("position");
        mPager.setLayoutParams(parms);
        adapter = new SlidingImage_Adapter(this, slidingImages, 2 , 1);
        if (Locale.getDefault().getLanguage().equals("en"))
            setUiPageViewControlEN();
        else
            setUiPageViewControlAR();
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(pos);
        //used to lesen when swiping the view pager images
        mPager.setOnPageChangeListener(this);
    }
    /////////set the indicator to act with english language /////////////////
    private void setUiPageViewControlEN() {
        GetScreenSize getScreenSize = new GetScreenSize(this);
        dotsCount = adapter.getCount();
       // mPager.setCurrentItem(pos);
        dots = new ImageView[dotsCount];
        pager_indicator.setWeightSum(dotsCount);
        linearLayout = new LinearLayout[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            linearLayout[i] = new LinearLayout(this);
            linearLayout[i].setBackgroundResource(R.drawable.default_indicator);
            Picasso.with(this).load(slidingImages.get(i)).resize(100, 100).placeholder(R.drawable.defaultimage).centerInside().into(dots[i]);
            //dots[i].setImageDrawable(getResources().getDrawable(R.drawable.tab_selector));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(70, 70);
            params2.setMargins(16, 16, 16, 16);;
            linearLayout[i].addView(dots[i] , params2);
            final int finalI = i;
            linearLayout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPager.setCurrentItem(finalI);
                    linearLayout[finalI].setBackgroundResource(R.drawable.color_square_background);
                }
            });
            pager_indicator.addView(linearLayout[i], params);
        }
        linearLayout[pos].setBackgroundResource(R.drawable.color_square_background);
        scrollView.setClipToPadding(false);
        scrollView.setPadding(16, 16, 16, 16);
        scrollView.canScrollHorizontally(pos);
       // scrollView.addView(pager_indicator);
    }

    /////////set the indicator to act with arabic language /////////////////
    private void setUiPageViewControlAR() {
        GetScreenSize getScreenSize = new GetScreenSize(this);
        dotsCount = adapter.getCount();
        // mPager.setCurrentItem(pos);
        dots = new ImageView[dotsCount];
        pager_indicator.setWeightSum(dotsCount);
        linearLayout = new LinearLayout[dotsCount];
        for (int i = dotsCount - 1; i >= 0; i--) {
            dots[i] = new ImageView(this);
            linearLayout[i] = new LinearLayout(this);
            linearLayout[i].setBackgroundResource(R.drawable.default_indicator);
            Picasso.with(this).load(slidingImages.get(i)).resize(100, 100).placeholder(R.drawable.defaultimage).centerInside().into(dots[i]);
            //dots[i].setImageDrawable(getResources().getDrawable(R.drawable.tab_selector));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(70, 70);
            params2.setMargins(16, 16, 16, 16);;
            linearLayout[i].addView(dots[i] , params2);
            final int finalI = i;
            linearLayout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPager.setCurrentItem(finalI);
                    linearLayout[finalI].setBackgroundResource(R.drawable.color_square_background);
                }
            });
            pager_indicator.addView(linearLayout[i], params);
        }
        linearLayout[pos].setBackgroundResource(R.drawable.color_square_background);
        scrollView.setClipToPadding(false);
        scrollView.setPadding(16, 16, 16, 16);
        scrollView.canScrollHorizontally(pos);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            linearLayout[i].setBackgroundResource(R.drawable.default_indicator);
        }
        linearLayout[position].setBackgroundResource(R.drawable.color_square_background);
        scrollView.canScrollHorizontally(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_thing, R.anim.slide_out_bottom);
    }
}
