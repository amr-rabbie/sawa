package solversteam.aveway.utiltes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import solversteam.aveway.Activities.CategoriesActivity;
import solversteam.aveway.Activities.ItemSelectedShowActivity;
import solversteam.aveway.Models.CategoryModel;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.ItemForView;
import solversteam.aveway.Models.Reviews_Items_Model;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.Models.WishListModel;
import solverteam.aveway.R;

/**
 * Created by Ibrahim on 31/01/2017.
 */

public class CreateViews {
    private Activity mContext;
    private LinearLayout layoutContainer;
    private boolean flag = true;
    private ArrayList<ChartModel>models;
    private ArrayList<WishListModel>list;

    private ChartModel ChartModel;
    private String item_id,item_name,item_image;
    private int item_oldprice,item_newprice,counter=0;
    private int item_offer;
    private Toolbar toolbar;

    public CreateViews(Activity context) {
        this.mContext = context;
//        modelswithcard=new ArrayList<>();
        if(TestModel.getfromshared("wish",context)!=null)
        {
            list=TestModel.getfromshared("wish",context);
        }
        else {
            list=new ArrayList<>();
        }
        if(TestModel.getfromcardmodel("card",context)!=null)
        {
            models=TestModel.getfromcardmodel("card",context);
        }
        else {
            models=new ArrayList<>();
        }
    }
    public CreateViews(Activity context, Toolbar toolbar) {
        this.mContext = context;
        this.toolbar=toolbar;
//        modelswithcard=new ArrayList<>();
        if(TestModel.getfromshared("wish",context)!=null)
        {
            list=TestModel.getfromshared("wish",context);
        }
        else {
            list=new ArrayList<>();
        }
        if(TestModel.getfromcardmodel("card",context)!=null)
        {
            models=TestModel.getfromcardmodel("card",context);
        }
        else {
            models=new ArrayList<>();
        }
    }

    public void categoriesInit(ArrayList<String> categories , ArrayList<String> catName, HorizontalScrollView scrollView, TextView textView, int ID, int flag) {
        LinearLayout layout = (LinearLayout) scrollView.findViewById(ID);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16, 0, 0, 0);
            //textView.setVisibility(View.VISIBLE);
        for (int i = 0; i < categories.size(); i++) {
            LinearLayout relativeLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.category_layout, null);
            GetScreenSize getScreenSize = new GetScreenSize(mContext);
            getScreenSize.getImageSize();
            int width = getScreenSize.getWidth() / 3;
            int height = getScreenSize.getHeight() / 9;
            CardView cardView = (CardView) relativeLayout.findViewById(R.id.cards);
            ImageView image = (ImageView) cardView.findViewById(R.id.activity_categories_image);
            ProgressBar progressBar=(ProgressBar)cardView.findViewById(R.id.prog);
            progressBar.setVisibility(View.GONE);
            RelativeLayout relativeLayout1container=(RelativeLayout)cardView.findViewById(R.id.relativeLayout_containerbar);
            FrameLayout.LayoutParams _rootLayoutParams = new FrameLayout.LayoutParams(width,height);
            _rootLayoutParams.height=height;
            _rootLayoutParams.width=width;
            relativeLayout1container.setLayoutParams(_rootLayoutParams);
//            RelativeLayout.LayoutParams linearr_parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//            linearr_parms.height = height;
//            linearr_parms.width = width;
//            image.setLayoutParams(linearr_parms);
            String img_url=categories.get(i);
            if(img_url.isEmpty())
            {
                img_url=mContext.getSharedPreferences("LanguageAndCountry",Context.MODE_PRIVATE).getString("defaultimage","");
            }

            if ( !img_url.isEmpty()&&!img_url.equals("null")) {

                progressBar.setVisibility(View.VISIBLE);
            }
            Picasso.with(mContext).load(img_url).fit()
                    .error(R.drawable.defaultimage)
                    .placeholder(R.drawable.defaultimage).into(image,
                    new  ImageLoadedCallback(progressBar)
                    {
                        @Override
                        public void onSuccess() {
                            if (progressBar != null) {
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

            cardView.setRadius(2);
            layoutParams.width = width;
            layoutParams.height = height;
            cardView.setLayoutParams(layoutParams);
            //cardView.setLayoutParams(linearr_parms);
            LinearLayout cardView1 = (LinearLayout) cardView.findViewById(R.id.bar_card);
            if (flag == 1) {
                cardView1.setVisibility(View.GONE);
                Log.d("checkhnnananan","ana hna");
                final int finalI = i;
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(mContext, "position is::: " + finalI, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(mContext , CategoriesActivity.class);
//                        intent.putExtra("type" , "brands");
//                        intent.putExtra("position" , finalI);
//                        mContext.startActivity(intent);
//                        mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                    }
                });
            }else{
                RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                layoutParams1.width = width;
                layoutParams1.height = 70;
                layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                cardView1.setLayoutParams(layoutParams1);
                TextView textView1 = (TextView) cardView1.findViewById(R.id.activity_categories_text);
                textView1.setText(catName.get(i));
            }
            layout.addView(relativeLayout);
            final int finalI1 = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(mContext,finalI1+"",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, CategoriesActivity.class);
                    intent.putExtra("position" , finalI1);
                    intent.putExtra("type" , "brands");
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                }
            });
        }
        scrollView.setClipToPadding(false);
        scrollView.setPadding(0, 0, 16, 0);
    }
    ////////////////////////////////////////////////////////////////////////

    public void categoriesInitt(ArrayList<String> categories , ArrayList<String> catName, HorizontalScrollView scrollView, TextView textView, int ID, int flag) {
        LinearLayout layout = (LinearLayout) scrollView.findViewById(ID);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16, 0, 0, 0);
            //textView.setVisibility(View.VISIBLE);
        for (int i = 0; i < categories.size(); i++) {
            LinearLayout relativeLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.category_layout, null);
            GetScreenSize getScreenSize = new GetScreenSize(mContext);
            getScreenSize.getImageSize();
            int width = getScreenSize.getWidth() / 3;
            int height = getScreenSize.getHeight() / 6;
            CardView cardView = (CardView) relativeLayout.findViewById(R.id.cards);
            ImageView image = (ImageView) cardView.findViewById(R.id.activity_categories_image);
            ProgressBar progressBar =(ProgressBar)cardView.findViewById(R.id.prog);
            progressBar.setVisibility(View.GONE);
            RelativeLayout relativeLayout1container=(RelativeLayout)cardView.findViewById(R.id.relativeLayout_containerbar);
            FrameLayout.LayoutParams _rootLayoutParams = new FrameLayout.LayoutParams(width,height);
            _rootLayoutParams.height=height;
            _rootLayoutParams.width=width;
            relativeLayout1container.setLayoutParams(_rootLayoutParams);

            String img_url=categories.get(i);
            if(img_url.isEmpty())
            {
                img_url=mContext.getSharedPreferences("LanguageAndCountry",Context.MODE_PRIVATE).getString("defaultimage","");
            }
            if ( !img_url.isEmpty()&&!img_url.equals("null")) {

                progressBar.setVisibility(View.VISIBLE);
            }
            Picasso.with(mContext).load(img_url).fit()
                    .error(R.drawable.defaultimage)
                    .placeholder(R.drawable.defaultimage).into(image,
                    new  ImageLoadedCallback(progressBar)
                    {
                        @Override
                        public void onSuccess() {
                            if (progressBar != null) {
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });


            cardView.setRadius(2);
            layoutParams.width = width;
            layoutParams.height = height;
            cardView.setLayoutParams(layoutParams);
            //cardView.setLayoutParams(linearr_parms);
            LinearLayout cardView1 = (LinearLayout) cardView.findViewById(R.id.bar_card);

                RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                layoutParams1.width = width;
                layoutParams1.height = height/3;
                layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                cardView1.setLayoutParams(layoutParams1);
                TextView textView1 = (TextView) cardView1.findViewById(R.id.activity_categories_text);
                //textView1.setGravity(View.T);
                textView1.setText(catName.get(i));
          //  }
            layout.addView(relativeLayout);
            final int finalI1 = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(mContext,finalI1+"done",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, CategoriesActivity.class);
                    intent.putExtra("position" , finalI1);
                    intent.putExtra("type" , "cat1");
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                }
            });
        }
        scrollView.setClipToPadding(false);
        scrollView.setPadding(0, 0, 16, 0);
    }

    //////////////////////////////////////////Main Image ////////////////////////
    public void maincategorisImageInit(String cat2, HorizontalScrollView scrollView2) {
        //HorizontalScrollView scrollView2 = (HorizontalScrollView) mContext.findViewById(R.id.main_activity_categories_recycler);// horizontal scroll view for all categories
        LinearLayout layout2 = (LinearLayout) scrollView2.findViewById(R.id.main_activity_main_category_image_container);// layout holds categories
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(16, 0, 0, 0);//set margin from left for first image / items
        ImageView image = new ImageView(mContext);
        CardView cardView = new CardView(mContext);
        RelativeLayout relativ=new RelativeLayout(mContext);
        ProgressBar progressBar=new ProgressBar(mContext);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFE0E0E0,android.graphics.PorterDuff.Mode.MULTIPLY);
      RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(Gravity.CENTER);

        progressBar.setLayoutParams(layoutParams);

        cardView.setLayoutParams(layoutParams2);
        relativ.setLayoutParams(layoutParams3);
        ViewGroup.LayoutParams linearr_parms1 = layout2.getLayoutParams();
        GetScreenSize getScreenSize = new GetScreenSize(mContext);// class manually created to get the width and height of thr screen
        getScreenSize.getImageSize();//get the size of the screen
        linearr_parms1.height = getScreenSize.getHeight() / 3;// set height of image in card view
        linearr_parms1.width = (int) (getScreenSize.getWidth() / 1.5);// set width of image in card view
        image.setLayoutParams(linearr_parms1);// set the new width and height of the image
        try {


            if (!cat2.isEmpty() && !cat2.equals("null")) {

                progressBar.setVisibility(View.VISIBLE);
            }
            Picasso.with(mContext).load(cat2).
                    resize((int) (getScreenSize.getWidth() / 1.5), getScreenSize.getHeight() / 3)
                    .error(R.drawable.defaultimage)
                    .centerCrop()
                    .placeholder(R.drawable.defaultimage).into(image,
                    new ImageLoadedCallback(progressBar) {
                        @Override
                        public void onSuccess() {
                            if (progressBar != null) {
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }catch (Exception e){

        Picasso.with(mContext).load(cat2).
                resize((int) (getScreenSize.getWidth() / 1.5), getScreenSize.getHeight() / 3).
                placeholder(R.drawable.defaultimage).centerCrop().into(image);
        }

        image.setId(0);// set id for the image
        cardView.setRadius(8);// used for set the corners of the card view more curved
        relativ.addView(image);// add the new image to the card view
        relativ.addView(progressBar);
        cardView.addView(relativ);
        layout2.addView(cardView);// add the card view to it layout
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,"done",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext, CategoriesActivity.class);
                intent.putExtra("position" , 0);
                intent.putExtra("type" , "cat3");
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });
    }

    //////////////////////////////////////////categories//////////////////////////
    public void categories2Init(final ArrayList<CategoryModel> categories, int id, HorizontalScrollView scrollView) {
        LinearLayout layout;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // set margin from left side
        ViewGroup.LayoutParams linearr_parms;
        int size;
        if (id == 1) {
            layout = (LinearLayout) scrollView.findViewById(R.id.main_activity_HorizontalScrollView_Linear_Layout1);
        } else {
            layout = (LinearLayout) scrollView.findViewById(R.id.main_activity_HorizontalScrollView_Linear_Layout2);
        }
        layoutParams.setMargins(16, 0, 0, 0);
        GetScreenSize getScreenSize1 = new GetScreenSize(mContext);
        getScreenSize1.getImageSize();
        linearr_parms = layout.getLayoutParams();
        linearr_parms.height = (int) (getScreenSize1.getHeight() / 6.33);// set height of the category
        linearr_parms.width = (int) (getScreenSize1.getHeight() / 6.33);// set width of the category
        size = (int) (getScreenSize1.getHeight() / 6.33);
        for (int i = 0; i < categories.size(); i++) {
            CardView cardView = new CardView(mContext);
            cardView.setLayoutParams(layoutParams);
            ImageView image = new ImageView(mContext);
            ProgressBar progressBar=new ProgressBar(mContext);
            progressBar.getIndeterminateDrawable().setColorFilter(0xFFE0E0E0,android.graphics.PorterDuff.Mode.MULTIPLY);
            progressBar.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutPa=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
           layoutPa.gravity= Gravity.CENTER;
            progressBar.setLayoutParams(layoutPa);
            image.setLayoutParams(linearr_parms);
            String img_url=categories.get(i).getCategory_image_url();
            if(img_url.isEmpty())
            {
                img_url=mContext.getSharedPreferences("LanguageAndCountry",Context.MODE_PRIVATE).getString("defaultimage","");
            }
            try {


                if (!img_url.isEmpty() && !img_url.equals("null")) {

                    progressBar.setVisibility(View.VISIBLE);
                }
                Picasso.with(mContext).load(img_url).
                        resize(size, size)
                        .error(R.drawable.defaultimage)
                        .centerCrop()
                        .placeholder(R.drawable.defaultimage).into(image,
                        new ImageLoadedCallback(progressBar) {
                            @Override
                            public void onSuccess() {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }catch (Exception e){

                Picasso.with(mContext).load(img_url).resize(size, size).
                        placeholder(R.drawable.defaultimage).centerCrop().into(image);

            }
            cardView.setId(i + 250);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(mContext,"done",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, CategoriesActivity.class);
                    intent.putExtra("position" , TestModel.getidposition(TestModel.getCatIds2_1(),categories.get(finalI).getCategory_id()));
                    intent.putExtra("type" , "cat2");
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);

//                    Intent intent = new Intent(mContext, CategoriesTwo.class);
//                    mContext.startActivity(intent);
//                    mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                }
            });
            image.setId(350 + i);
            cardView.setRadius(8);
            cardView.addView(image);
            cardView.addView(progressBar);
            layout.addView(cardView);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////
        scrollView.setClipToPadding(false);// used to add padding to the last item in the scroll view only
        scrollView.setPadding(0, 0, 16, 0);// used to add padding to the last item in the scroll view only
    }

    //////////////////////////////////////////viewed Items///////////////////////////////
    public void CreateItemView(final ArrayList<ItemForView> itemForView, final ArrayList<Integer> Ids,
                               final LinearLayout mainLayout, int whrer_i_come,
                               final ArrayList<String> img_urls, final ArrayList<String>category_name, final ArrayList<String>catgeory_id) {

        if (itemForView.size() != 0) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View views = inflater.inflate(R.layout.item_layout_for_views, mainLayout, false);
            TextView category = (TextView) views.findViewById(R.id.item_layout_for_views_categories);
            category.setText(itemForView.get(0).getCategory());
            TextView seenORclear = (TextView) views.findViewById(R.id.item_layout_for_views_seenORclear);
           seenORclear.setText(itemForView.get(0).getSeenOrClear());
            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CategoriesActivity.class);
                    intent.putExtra("url", itemForView.get(0).getURL());
                    intent.putExtra("Key", itemForView.get(0).getKey());
                    intent.putExtra("type","allproducts");
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                }
            });
            HorizontalScrollView scrollView = (HorizontalScrollView) views.findViewById(R.id.main_activity_items_scroll);
            LinearLayout layout = (LinearLayout) scrollView.findViewById(R.id.main_activity_items_layout);
            //View lastCard = layout.getChildAt(layout.getChildCount() - 1);// i want to use for getting the last id to add after it
            //////////////////////////////////////////////////////////////////////////
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams2.setMargins(16, 0, 0, 0);
            int cardid=2000;
            for (int i = 0; i < itemForView.size(); i++) {
                final CardView cardView = (CardView)
                        LayoutInflater.from(mContext).inflate(itemForView.get(0).getLayoutID(), null);
                cardView.setLayoutParams(layoutParams2);
                layout.addView(cardView);
                ProgressBar progressBar=(ProgressBar) cardView.findViewById(R.id.prog);
                progressBar.setVisibility(View.GONE);


                if (!itemForView.get(i).getImageUrl().isEmpty() && !itemForView.get(i).getImageUrl().equals("null")) {

                    progressBar.setVisibility(View.VISIBLE);
                }
                Picasso.with(mContext).load(itemForView.get(i).getImageUrl()).
                        resize(400, 400)
                        .centerInside()
                        .error(R.drawable.defaultimage)
                        .placeholder(R.drawable.defaultimage).into((ImageView) cardView.findViewById(R.id.product),
                        new ImageLoadedCallback(progressBar) {
                            @Override
                            public void onSuccess() {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

//                Picasso.with(mContext).load(itemForView.get(i).getImageUrl()).
//                        resize(400, 400)
//                        .centerInside().placeholder(R.drawable.defaultimage).
//                        into((ImageView) cardView.findViewById(R.id.product));
                if (itemForView.get(i).getOffer() <= 0 || itemForView.get(i).getPrevprice() <= 0) {
                    cardView.findViewById(R.id.offer).setVisibility(View.INVISIBLE);
                    cardView.findViewById(R.id.offerContainer).setVisibility(View.INVISIBLE);
                    cardView.findViewById(R.id.prevPrice).setVisibility(View.INVISIBLE);
                } else {
                    TextView offer = (TextView) cardView.findViewById(R.id.offer);
                    offer.setText(itemForView.get(i).getOffer() + "");
                    TextView prevprice = (TextView) cardView.findViewById(R.id.prevPrice);
                    prevprice.setText(itemForView.get(i).getPrevprice() + "");
                }
                TextView name = (TextView) cardView.findViewById(R.id.product_name);
                name.setText(itemForView.get(i).getName());
                TextView price = (TextView) cardView.findViewById(R.id.product_price);
                price.setText(itemForView.get(i).getPrice() + ""+" "+TestModel.getcurrencyname(mContext));
                final int finalI = i;
                layoutContainer = (LinearLayout) cardView.findViewById(R.id.item_image_name_container);
                //when click the layout that contain image and price of the product
                final int finalI2 = i;
//                item_id=Ids.get(finalI2).toString();
//                item_name=itemForView.get(finalI2).getName();
//                item_oldprice=itemForView.get(finalI2).getPrevprice();
//                item_newprice=itemForView.get(finalI2).getPrice();
//                item_image=img_urls.get(finalI2).toString();

                layoutContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            Log.d("check_final_cat",catgeory_id.get(finalI2)+"\n"+category_name.get(finalI2));
                        Intent intent = new Intent(mContext, ItemSelectedShowActivity.class);
                        intent.putExtra("ID", Ids);
                        intent.putExtra("position", finalI2);
                        intent.putExtra("img_urls", img_urls);
                        intent.putExtra("cat_name", category_name);
                        intent.putExtra("cat_id", catgeory_id);

                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("myImages", itemForView);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                        // used to animate opening the new activity
                        mContext.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                        //Toast.makeText(mContext, "Clicked Position : " + finalI, Toast.LENGTH_SHORT).show();
                    }
                });
                if (whrer_i_come == 1) {
                    final int finalI1 = i;

                    cardView.findViewById(R.id.item_share).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!itemForView.get(finalI1).getProductUrl().isEmpty())
                                { view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.rotat));
                                    Picasso.with(mContext).load(itemForView.get(finalI1).getImageUrl()).into(new Target() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            Intent i = new Intent(Intent.ACTION_SEND);
                                            i.putExtra(Intent.EXTRA_TEXT, "Hey,check out this amazing item " +
                                                    itemForView.get(finalI1).getName()+" for ".concat(itemForView.get(finalI1).getPrice()+"")+ " "
                                                    +TestModel.getcurrencyname(mContext)+
                                                    " Here :"+"\n"+ itemForView.get(finalI1).getProductUrl()+ "\n"+
                                                    " Or Check out more deals on \n http://www.aveway.com or download E-Commerce App.\n" );

                                            i.setType("*/*");
                                            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                                            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                            mContext.startActivity(Intent.createChooser(i, "share"));
                                            Log.d("ccc",itemForView.get(finalI).getProductUrl());
                                        }

                                        @Override
                                        public void onBitmapFailed(Drawable errorDrawable) {
                                        }

                                        @Override
                                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                                        }
                                    });

                                }
                        }
                    });
                    cardView.findViewById(R.id.item_shopping).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            models=TestModel.getfromcardmodel("card",mContext);
//                            Toast.makeText(mContext, "would u like to buy item :" + finalI, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(mContext,finalI1+"",Toast.LENGTH_SHORT).show();

                            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.rotat));
                            item_id=Ids.get(finalI1).toString();
                            item_name=itemForView.get(finalI1).getName();
                            item_oldprice= itemForView.get(finalI1).getPrevprice();
                            item_newprice=itemForView.get(finalI1).getPrice();
                            item_offer= itemForView.get(finalI1).getOffer();
                            item_image=img_urls.get(finalI1).toString();
                            ChartModel chartModel =new ChartModel(item_image,item_name,item_newprice,item_oldprice,item_offer,item_id);
                            if(models==null)
                            {   models=new ArrayList<>();
                                models.add(chartModel);

                                Toast.makeText(mContext,R.string.youadd,Toast.LENGTH_LONG).show();

                            }
                            else {
                                if(!TestModel.checkmodel(models,chartModel.getItem_id()))
                                {
                                    Log.d("checkhna","anananna");
                                    models.add(chartModel);
                                    Toast.makeText(mContext,R.string.youadd,Toast.LENGTH_LONG).show();

                                }else
                                    Toast.makeText(mContext,R.string.youalreadyadd,Toast.LENGTH_LONG).show();

                            }
                            TestModel.savetosharedcardmodel(models,"card",mContext);
                            toolbar.findViewById(R.id.toolbar_shopping).setVisibility(View.VISIBLE);
                            TextView textView=(TextView) toolbar.findViewById(R.id.textOne);
                            if(models.size()>0)
                            {textView.setVisibility(View.VISIBLE);
                                textView.setText(TestModel.getfromcardmodel("card",mContext).size()+"");}
                            else {
                                textView.setVisibility(View.INVISIBLE);
                            }

                            Log.d("checksizearray",TestModel.getfromcardmodel("card",mContext).size()+"\n"+finalI1);


                        }
                    });

//                    TestModel.getAllids().add(Ids.get(i)+"");
//                    for(int pos=0;pos<itemForView.size();pos++)
//                    {
//                        WishListModel wishListModel=new WishListModel(itemForView.get(pos).getName(),itemForView.get(pos).getPrice()+""
//                        ,itemForView.get(pos).getPrevprice()+"",itemForView.get(pos).getImageUrl(),Ids.get(pos).toString(),itemForView.get(0).getLayoutID());
//                        modelswithcard.add(new WishListModelWithCard(wishListModel,fav_image.getId()));
//                        Log.d("checkidimage",fav_image.getId()+"");
//                    }
//                    TestModel.savetosharedcard(modelswithcard,"wish",mContext);
//                    Log.d("checkhna","1");
//                    {if(TestModel.checkitem(models,item_id))
//                    {
//                        fav_image.setImageResource(R.mipmap.ic_wishlist);
//
//                    }
//                    else {
//                        fav_image.setImageResource(R.mipmap.ic_wishlist_outline_disable);
//
//                    }}
                    final ImageView fav_image=(ImageView) cardView.findViewById(R.id.item_add_to_fav);
                    fav_image.setTag(Ids.get(i)+"");
                    if(list==null)
                    {
                        list=new ArrayList<>();
                    }
                    else {
                        list=TestModel.getfromshared("wish",mContext);

                    }
                    if(list!=null)
                    {    item_id=Ids.get(finalI).toString();
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
                            Log.d("checkidcard",fav_image.getTag()+"");

//                            Toast.makeText(mContext,finalI1+"",Toast.LENGTH_SHORT).show();
                            item_id=Ids.get(finalI1).toString();
                            item_name=itemForView.get(finalI1).getName();
                            item_oldprice= itemForView.get(finalI1).getPrevprice();
                            item_newprice=itemForView.get(finalI1).getPrice();
                            item_offer= itemForView.get(finalI1).getOffer();
                            item_image=img_urls.get(finalI1).toString();
                            list=TestModel.getfromshared("wish",mContext);

                            WishListModel wishListModel=new WishListModel(item_name,item_newprice+"",item_oldprice+"",item_image,item_id);
                            Log.d("checkitemid",item_id);
                            Log.d("checkiteminarrayid",finalI1+"");
                            if(list!=null)
                            { if(!TestModel.checkitem(list,wishListModel.getItem_id()))
                            {
                                counter=0;
                            }
                            else {
                                counter=1;
                            }}
                            Log.d("checkcounter0",counter+"");


                            if(TestModel.check(counter))
                            {
                                Toast.makeText(mContext,R.string.addfav,Toast.LENGTH_SHORT).show();
                                fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                                if(list==null)
                                {   list=new ArrayList<>();
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
                                    Toast.makeText(mContext,R.string.removefav,Toast.LENGTH_SHORT).show();
                                    fav_image.setImageResource(R.mipmap.ic_wishlist_outline_disable);
                                    int  wish=TestModel.getitemposition(list,wishListModel.getItem_id());
                                    list.remove(wish);
                                    counter=0;
                                }
                            }
                            TestModel.savetoshared(list,"wish",mContext);
                            Log.d("checkdatasaved",TestModel.getfromshared("wish",mContext).size()+"\n"+wishListModel.getItem_id()+"\n"+(finalI1)
                                    +"\n"+counter);
                            Log.d("checkitem",TestModel.checkitem(list,wishListModel.getItem_id())+"");
                            if(TestModel.checkitem(list,wishListModel.getItem_id()))
                            {
                                counter++;
                            }
                            Log.d("checkcoun2",counter+"");


                        }
                    });
                }
                scrollView.setClipToPadding(false);
                scrollView.setPadding(0, 0, 16, 32);
                cardid++;
            }
            mainLayout.addView(views);
        } else {
//            Toast.makeText(mContext, "No Data Available :):):)", Toast.LENGTH_SHORT).show();
        }
    }

    ///////////////////////////////////////change Language///////////////////////////////
    public void ChangeLanguage(String lang) {
        Log.i("lang", lang);
        String languageToLoad = lang;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        mContext.getResources().updateConfiguration(config, mContext.getResources().getDisplayMetrics());

    }

    ///////////////////////////////////////activity details some views////////////////////
    public void showSomeDetailsviews(ArrayList<String> content, String header, LinearLayout mainLayout) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View views = inflater.inflate(R.layout.details, mainLayout, false);
        HorizontalScrollView scrollView = (HorizontalScrollView) views.findViewById(R.id.activity_details_image_color_container_scroll);
        TextView headertv = (TextView) views.findViewById(R.id.activity_details_header_color);
        LinearLayout layout = (LinearLayout) scrollView.findViewById(R.id.activity_details_image_color_container);
        headertv.setText(header);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (Locale.getDefault().getLanguage().equals("en"))
            layoutParams2.setMargins(20, 0, 0, 20);
        else
            layoutParams2.setMargins(0, 0, 20, 20);
        for (int i = 0; i < content.size(); i++) {
            LinearLayout linearLayout = (LinearLayout)
                    LayoutInflater.from(mContext).inflate(R.layout.activity_details_some_details_color_item, null);
            TextView contenttv = (TextView) linearLayout.findViewById(R.id.activity_details_color);
            contenttv.setText(content.get(i));
            linearLayout.setLayoutParams(layoutParams2);
            layout.addView(linearLayout, 0);
            // color.setText(colors.get(i));
        }
        mainLayout.addView(views);
    }

    //////////////////////////////////////activity details seller description////////////////
    public void showDescriptionsDetails(LinearLayout mainLayout, final String desc, final String MoreDesc, ArrayList<String> specifications, ArrayList<String> specificationsvalus) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View views = inflater.inflate(R.layout.activity_details_description_specification, mainLayout, false);
        final WebView desctv = (WebView) views.findViewById(R.id.activity_details_description);
        // desctv.getSettings().setJavaScriptEnabled(true);
        // desctv.loadDataWithBaseURL("", desc, "text/html", "UTF-8", "");
        desctv.getSettings().setLoadsImagesAutomatically(true);
        desctv.getSettings().setJavaScriptEnabled(true);
        desctv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        final String dir=mContext.getResources().getString(R.string.rtl);
        desctv.loadDataWithBaseURL("", "<html dir="+dir+" lang=\"\"><body>" + desc + "</body></html>", "text/html", "UTF-8", "");



        final TextView readMore = (TextView) views.findViewById(R.id.more_desc);
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    desctv.loadDataWithBaseURL("", "<html dir="+dir+" lang=\"\"><body>" + MoreDesc + "</body></html>", "text/html", "UTF-8", "");

                    readMore.setText(R.string.readLess);
                    flag = false;
                } else {
                    desctv.loadDataWithBaseURL("", "<html dir="+dir+" lang=\"\"><body>" + desc + "</body></html>", "text/html", "UTF-8", "");


                    readMore.setText(R.string.readMore);
                    flag = true;
                }
            }
        });
        LinearLayout linearLayout = new LinearLayout(mContext);
        LinearLayout linearLayoutvalues = new LinearLayout(mContext);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "Wellcome", Toast.LENGTH_SHORT).show();
            }
        });
        linearLayoutvalues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "Wellcome", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout separator = new LinearLayout(mContext);
        LinearLayout descmainLayout = (LinearLayout) views.findViewById(R.id.activity_details_specifications_container);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.weight = 1;
        if (Locale.getDefault().getLanguage().equals("ar")) {
            layoutParams3.setMargins(16, 0, 0, 0);
        } else
            layoutParams3.setMargins(0, 0, 16, 0);
        separator.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(layoutParams2);
        linearLayoutvalues.setOrientation(LinearLayout.VERTICAL);
        linearLayoutvalues.setLayoutParams(layoutParams2);
        separator.setLayoutParams(layoutParams3);
//        for (int i = 0; i < specifications.size(); i++) {
//            TextView speceficatin = new TextView(mContext);
//            TextView speceficatinvalues = new TextView(mContext);
//            TextView separatortv = new TextView(mContext);
//            separatortv.setText(":");
//            if (Locale.getDefault().getLanguage().equals("ar")) {
//                speceficatin.setGravity(Gravity.END);
//                speceficatinvalues.setGravity(Gravity.END);
//            }
//            separatortv.setTypeface(null, Typeface.BOLD);
//            speceficatin.setText(specifications.get(i));
//            speceficatinvalues.setText(specificationsvalus.get(i));
//            speceficatinvalues.setTypeface(null, Typeface.BOLD);
//            separator.addView(separatortv);
//            linearLayout.addView(speceficatin);
//            linearLayoutvalues.addView(speceficatinvalues);
//        }
        descmainLayout.addView(linearLayout);
        descmainLayout.addView(separator);
        descmainLayout.addView(linearLayoutvalues);
        mainLayout.addView(views);
    }

    //////////////////////////////////////activity details reviews/////////////////////////////////////////////////////
    public void createReviews(ItemForView itemForView, ArrayList<Reviews_Items_Model> reviews_items_model, LinearLayout layout) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams3.setMargins(0, 24, 0, 0);
        View views = inflater.inflate(R.layout.item_layout_for_views, layout, false);
        TextView category = (TextView) views.findViewById(R.id.item_layout_for_views_categories);
        category.setText(itemForView.getCategory());
        TextView seenORclear = (TextView) views.findViewById(R.id.item_layout_for_views_seenORclear);
        seenORclear.setText(itemForView.getSeenOrClear());
        HorizontalScrollView scrollView = (HorizontalScrollView) views.findViewById(R.id.main_activity_items_scroll);
        LinearLayout reviewlayout = (LinearLayout) scrollView.findViewById(R.id.main_activity_items_layout);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(24, 0, 0, 0);
        GetScreenSize getScreenSize = new GetScreenSize(mContext);
        getScreenSize.getImageSize();
        LinearLayout linearLayout = new LinearLayout(mContext);
        layoutParams2.width = (int) (getScreenSize.getWidth() / 1.1);
        for (int i = 0; i < reviews_items_model.size(); i++) {
            CardView cardView = (CardView)
                    LayoutInflater.from(mContext).inflate(R.layout.activity_details_reviews, null);
            cardView.setLayoutParams(layoutParams2);
            TextView user_name = (TextView) cardView.findViewById(R.id.activity_details_reviews_user_name);
            user_name.setText(reviews_items_model.get(i).getUser_name());
            TextView date = (TextView) cardView.findViewById(R.id.activity_details_reviews_date);
            date.setText(reviews_items_model.get(i).getDate());
            TextView rated = (TextView) cardView.findViewById(R.id.activity_details_reviews_rated);
            rated.setText(reviews_items_model.get(i).getRated());
            TextView content = (TextView) cardView.findViewById(R.id.activity_details_reviews_content);
            content.setText(reviews_items_model.get(i).getContent());
            linearLayout.addView(cardView);
        }
        scrollView.setClipToPadding(false);
        scrollView.setPadding(0, 0, 16, 32);
        reviewlayout.addView(linearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "Welcome", Toast.LENGTH_SHORT).show();
            }
        });
        layout.addView(views);
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
    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
