package solversteam.aveway.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import solversteam.aveway.Activities.CategoriesActivity;
import solversteam.aveway.Activities.ItemSelectedShowActivity;
import solversteam.aveway.Activities.SearchActivity;
import solversteam.aveway.Activities.ShowItem_Images_With_Zoom;
import solversteam.aveway.Models.ItemForView;
import solversteam.aveway.Models.Reviews_Items_Model;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.utiltes.GetScreenSize;
import solversteam.aveway.utiltes.TouchImageView;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ibrahim on 25/01/2017.
 */

public class SlidingImage_Adapter extends PagerAdapter {
    private ArrayList<String> IMAGES;
    private ArrayList<String> IMAGES1;
    private ArrayList<Reviews_Items_Model> Reviews;
    private LayoutInflater inflater;
    private Context context;
    private int whrer_i_comming, i;
    private ItemForView itemForView;
    private ViewPager layout;

    public SlidingImage_Adapter(Context context, ArrayList<String> IMAGES, int whrer_i_comming, int i) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);
        this.whrer_i_comming = whrer_i_comming;
        this.i = i;
    }

    public SlidingImage_Adapter(Activity context, int whrer_i_comming, ArrayList<String> IMAGES1) {
        this.context = context;
        this.IMAGES1 = IMAGES1;
        inflater = LayoutInflater.from(context);
        this.whrer_i_comming = whrer_i_comming;
    }

    public SlidingImage_Adapter(Context context, int whrer_i_comming, ArrayList<Reviews_Items_Model> Reviews, ItemForView itemForView, ViewPager layout) {
        this.context = context;
        this.Reviews = Reviews;
        inflater = LayoutInflater.from(context);
        this.whrer_i_comming = whrer_i_comming;
        this.itemForView = itemForView;
        this.layout = layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (whrer_i_comming == 1) {
            try {
                return IMAGES1.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (whrer_i_comming != 3) {
            try {
                return IMAGES.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            return Reviews.size();
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        final View imageLayout;
        if (whrer_i_comming == 1) {
            imageLayout = inflater.inflate(R.layout.image_sliding_item, view, false);
            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.activity_main_sliding_image);
            final ProgressBar progressBar=(ProgressBar)imageLayout.findViewById(R.id.prog) ;
            progressBar.setVisibility(View.GONE);
            CardView linearLayout = (CardView) imageLayout.findViewById(R.id.activity_main_sliding_image_container);
            GetScreenSize getScreenSize = new GetScreenSize(context);
            getScreenSize.getImageSize();
            ViewGroup.LayoutParams parms = imageView.getLayoutParams();
            Log.i("ehab", parms.height + "***" + parms.width + "");
            parms.height = getScreenSize.getHeight() / 5;
            parms.width = getScreenSize.getWidth();
            linearLayout.setLayoutParams(parms);

             imageView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if (TestModel.getType().get(position).equals("1")){
                     Intent intent = new Intent(context, CategoriesActivity.class);
                     intent.putExtra("position" , position);
                     intent.putExtra("type" , "apads");
                     context.startActivity(intent);
                     ((Activity) context).overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                     }
                     else if(TestModel.getType().get(position).equals("2")){
                         String url = TestModel.getAd_url().get(position).toString();
                         Intent i = new Intent(Intent.ACTION_VIEW);
                         i.setData(Uri.parse(url));
                         context.startActivity(i);
                         ((Activity) context).overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);

                     }
                     else if(TestModel.getType().get(position).equals("3")){
                         ItemForView xv= new ItemForView("",
                                 "", "",
                                 "",
                                 "",
                                 0,
                                 0, 0, R.layout.item_layout, "", "","");

                         ArrayList<ItemForView> arr =new ArrayList();
                         arr.add(xv);
                         ArrayList<String> imgs =new ArrayList();
                         imgs.add("");
                         ArrayList<Integer> Ids = new ArrayList<>();
                         Ids.add(Integer.parseInt(TestModel.getApads().get(position)));
                         Intent intent = new Intent(context, ItemSelectedShowActivity.class);
                         intent.putExtra("ID",Ids );
                         intent.putExtra("position", 0);
                         Bundle bundle = new Bundle();
                         bundle.putParcelableArrayList("myImages",arr);
                         intent.putExtra("img_urls", imgs);
                         intent.putExtras(bundle);
                         context.startActivity(intent);
                         ((Activity) context).overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);

                     }
                     else if(TestModel.getType().get(position).equals("4")){
                        Intent i =new Intent(context, SearchActivity.class);
                         i.putExtra("Search_name",TestModel.getApads().get(position));
                        context.startActivity(i);
                         ((Activity) context).overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);

                     }
                 }
             });
            if ( !(IMAGES1.get(position)+"").isEmpty()&&!(IMAGES1.get(position)+"").equals("null")) {

                 progressBar.setVisibility(View.VISIBLE);
            }
            try{
            Picasso.with(context).load(IMAGES1.get(position)).
                    resize(getScreenSize.getWidth(), getScreenSize.getHeight() / 5)
                    .centerCrop()
                    .placeholder(R.drawable.defaultimage).into( imageView,
                    new   ImageLoadedCallback( progressBar)
                    {
                        @Override
                        public void onSuccess() {
                            if (progressBar != null) {
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });}catch (Exception e){
                String defult=context.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
                Picasso.with(context).load(defult).
                        resize(getScreenSize.getWidth(), getScreenSize.getHeight() / 5)
                        .centerCrop()
                        .placeholder(R.drawable.defaultimage).into( imageView,
                        new   ImageLoadedCallback( progressBar)
                        {
                            @Override
                            public void onSuccess() {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }



            view.addView(imageLayout, 0);
        } else if (whrer_i_comming == 3) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams3.setMargins(0, 24, 0, 0);
            View views = inflater.inflate(R.layout.item_layout_for_views, layout, false);
            TextView category = (TextView) views.findViewById(R.id.item_layout_for_views_categories);
            category.setText(itemForView.getCategory());
            TextView seenORclear = (TextView) views.findViewById(R.id.item_layout_for_views_seenORclear);
            seenORclear.setText(itemForView.getSeenOrClear());
            imageLayout = inflater.inflate(R.layout.activity_details_reviews, view, false);
            TextView user_name = (TextView) imageLayout.findViewById(R.id.activity_details_reviews_user_name);
            user_name.setText(Reviews.get(position).getUser_name());
            TextView date = (TextView) imageLayout.findViewById(R.id.activity_details_reviews_date);
            date.setText(Reviews.get(position).getDate());
            TextView rated = (TextView) imageLayout.findViewById(R.id.activity_details_reviews_rated);
            rated.setText(Reviews.get(position).getRated());
//            TextView content = (TextView) imageLayout.findViewById(R.id.activity_details_reviews_content);
//            content.setText(Reviews.get(position).getContent());
            layout.addView(views);
            view.addView(imageLayout, 0);
        } else {
            imageLayout = inflater.inflate(R.layout.details_image_sliding_items, view, false);
            assert imageLayout != null;
            final TouchImageView imageView = (TouchImageView) imageLayout
                    .findViewById(R.id.details_sliding_image);
            ProgressBar progressBar=(ProgressBar) imageLayout.findViewById(R.id.prog);
            progressBar.setVisibility(View.GONE);
            LinearLayout linearLayout = (LinearLayout) imageLayout.findViewById(R.id.details_sliding_image_container);
            GetScreenSize getScreenSize = new GetScreenSize(context);
            getScreenSize.getImageSize();
            ViewGroup.LayoutParams parms = imageView.getLayoutParams();
            linearLayout.setLayoutParams(parms);
            if (i != 1) {
                parms.height = (int) (getScreenSize.getHeight() / 2.9);
                parms.width = getScreenSize.getWidth();
                if ( !(IMAGES.get(position)+"").isEmpty()&&!(IMAGES.get(position)+"").equals("null")) {

                    progressBar.setVisibility(View.VISIBLE);
                }
                Picasso.with(context).load(IMAGES.get(position)).
                        resize(getScreenSize.getWidth(), (int) (getScreenSize.getHeight() / 2.9))
                        .centerInside()
                        .placeholder(R.drawable.defaultimage).into( imageView,
                        new   ImageLoadedCallback( progressBar)
                        {
                            @Override
                            public void onSuccess() {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });


            } else {
                parms.height = (int) (getScreenSize.getHeight() / 1.5);
                parms.width = getScreenSize.getWidth();
                if ( !(IMAGES.get(position)+"").isEmpty()&&!(IMAGES.get(position)+"").equals("null")) {

                    progressBar.setVisibility(View.VISIBLE);
                }
                Picasso.with(context).load(IMAGES.get(position)).
                        resize(getScreenSize.getWidth(), (int) (getScreenSize.getHeight() / 1.5))
                        .centerInside()
                        .placeholder(R.drawable.defaultimage).into( imageView,
                        new   ImageLoadedCallback( progressBar)
                        {
                            @Override
                            public void onSuccess() {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });


             }
            view.addView(imageLayout, 0);
            if (i != 1) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ShowItem_Images_With_Zoom.class);
                        intent.putStringArrayListExtra("Images", IMAGES);
                        intent.putExtra("position", position);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                    }
                });
            }
        }
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
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
