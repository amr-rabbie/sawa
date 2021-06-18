package solversteam.aveway.Adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import solversteam.aveway.Models.CatModel;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.ItemForView;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.Models.WishListModel;
import solversteam.aveway.utiltes.OnLoadMoreListener;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ibrahim on 03/02/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AppCompatActivity context;
    private ArrayList<Object> items;
    private ArrayList<CatModel> miniCat;
    private final MiniAdapter miniAdapter;
    private RecyclerView recyclerView;
    private View v4;
    private FrameLayout layout;
    private final int VIEW_TYPE_LOADING = 6;
    private OnLoadMoreListener mOnLoadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    private LoadingViewHolder loadingViewHolder;
    private GridLayoutManager gridLayoutManager;
    private String item_id,item_name,item_image;
    private int item_oldprice,item_newprice,counter=0,item_offer,position;
    private ArrayList<Integer>Ids;
    private ArrayList<String>image_urls;
    private ArrayList<ChartModel>models;
    private ArrayList<WishListModel> list;
    private ArrayList<ItemForView>itemforviews;
    private TextView notifytext;

    String gsize;
    public CategoriesAdapter(final ArrayList<Object> items, AppCompatActivity context, ArrayList<CatModel> miniCat, RecyclerView recyclerView , FrameLayout layout) {
        this.items = items;
        this.context = context;
        this.miniCat = miniCat;
        miniAdapter = new MiniAdapter(miniCat, context);
        this.recyclerView = recyclerView;
        this.layout = layout;
        onScroll();
    }
    public CategoriesAdapter(final ArrayList<Object> items, AppCompatActivity context, ArrayList<CatModel> miniCat, RecyclerView recyclerView, FrameLayout layout, GridLayoutManager linearLayoutManager
            , ArrayList<Integer> Ids, ArrayList<String> image_urls, ArrayList<ItemForView> itemforviews, TextView notifytext, String gsize) {
        this.items = items;
        this.context = context;
        this.miniCat = miniCat;
        miniAdapter = new MiniAdapter(miniCat, context);
        this.recyclerView = recyclerView;
        this.layout = layout;
        this.gridLayoutManager=linearLayoutManager;
        this.Ids=Ids;
        this.image_urls=image_urls;
        this.itemforviews=itemforviews;
        this.notifytext=notifytext;
        this.gsize=gsize;
        this.gsize=TestModel.getSize_array();
        Log.d("checkgsize",gsize);
        onScroll();
        if(TestModel.getfromshared("wish",context)!=null)
        {
            list=TestModel.getfromshared("wish",context);
            position=list.size();
        }
        else {
            list=new ArrayList<>();
        }
        if(TestModel.getfromcardmodel("card",context)==null)
        {
            notifytext.setVisibility(View.INVISIBLE);
        }
        if(TestModel.getfromcardmodel("card",context)!=null)
        {
            models=TestModel.getfromcardmodel("card",context);
            notifytext.setVisibility(View.VISIBLE);
            if(models.size()>0)
            {notifytext.setText(TestModel.getfromshared("card",context).size()+"");}
            else {
                notifytext.setVisibility(View.INVISIBLE);

            }

        }
        else {
            models=new ArrayList<>();
            notifytext.setVisibility(View.INVISIBLE);

        }
    }
    public void onScroll(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading)
                    return;
                if(gridLayoutManager.findLastCompletelyVisibleItemPosition()==items.size()-1)
                {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
//                if (!isLoading && dy > 10) {
//                    if (mOnLoadMoreListener != null) {
//                        mOnLoadMoreListener.onLoadMore();
//                    }
//                    isLoading = true;
//                }
            }
        });
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case 1:
                View v1 = inflater.inflate(R.layout.category_layout, viewGroup, false);
                viewHolder = new CatViewHolder(v1);
                break;
            case 2:
                View v2 = inflater.inflate(R.layout.header_catergory_item, viewGroup, false);
                viewHolder = new headerCatViewHolder(v2);
                break;
            case 3:
                View v3 = inflater.inflate(R.layout.category_separator, viewGroup, false);
                viewHolder = new SeparatorViewHolder(v3);
                break;
            case 4:
                //if (flag) {
                v4 = inflater.inflate(R.layout.category_layout_2, viewGroup, false);
                viewHolder = new Cat2ViewHolder(v4);
//                } else {
//                    v4 = inflater.inflate(R.layout.category_layout_3, viewGroup, false);
//                    viewHolder = new Cat2ViewHolder(v4);
//                }
                break;
            case 5:
                View v5 = inflater.inflate(R.layout.header_catergory_item, viewGroup, false);
                viewHolder = new headerCatViewHolder(v5);
                break;
            case VIEW_TYPE_LOADING:
                View v6 = inflater.inflate(R.layout.layout_loading_item, viewGroup, false);
                viewHolder = new LoadingViewHolder(v6);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                CatViewHolder vh1 = (CatViewHolder) holder;
                CatViewHolder(vh1, position);
                break;
            case 2:
                headerCatViewHolder vh2 = (headerCatViewHolder) holder;
                headerCatViewHolder(vh2, position);
                break;
            case 3:
                SeparatorViewHolder vh3 = (SeparatorViewHolder) holder;
                SeparatorViewHolder(vh3, position);
                break;
            case 4:
                Cat2ViewHolder vh4 = (Cat2ViewHolder) holder;
                Cat2ViewHolder(vh4, position);
                break;
            case 5:
                headerCatViewHolder vh6 = (headerCatViewHolder) holder;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                vh6.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                vh6.recyclerView.setLayoutParams(layoutParams);
                vh6.recyclerView.setAdapter(miniAdapter);
                vh6.cardView.setVisibility(View.GONE);
                break;
            case VIEW_TYPE_LOADING:
                loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
                break;
            default:
                break;
        }
    }
    public void setLoaded() {
        isLoading = false;
    }
    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof CatModel)
            return 1;
        else if (items.get(position) instanceof Integer) {
            return 2;
        } else if (items.get(position) instanceof String) {
            return 3;
        } else if (items.get(position) instanceof ItemForView) {
            return 4;
        } else if (items.get(position) instanceof Float) {
            return 5;
        } else {
            return VIEW_TYPE_LOADING;
        }
    }
    public class headerCatViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RecyclerView recyclerView;
        CardView cardView;
        ProgressBar progressBar;
        public headerCatViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.activity_categories_container_card_view);
            imageView = (ImageView) itemView.findViewById(R.id.activity_categories_header_item);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.first_items);
            progressBar=(ProgressBar)itemView.findViewById(R.id.prog);
            progressBar.setVisibility(View.GONE);
        }
    }
    public class CatViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;

        public CatViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.activity_categories_image);
            textView = (TextView) itemView.findViewById(R.id.activity_categories_text);
            progressBar=(ProgressBar)itemView.findViewById(R.id.prog);
            progressBar.setVisibility(View.GONE);
        }
    }
    public class SeparatorViewHolder extends RecyclerView.ViewHolder {
        ImageButton sort, grid, list;
        TextView result;

        public SeparatorViewHolder(View itemView) {
            super(itemView);
            sort = (ImageButton) itemView.findViewById(R.id.activity_categories_sort_imageButton);
            sort.setVisibility(View.GONE);
            //list = (ImageButton) itemView.findViewById(R.id.activity_categories_list_imageButton);
            // grid = (ImageButton) itemView.findViewById(R.id.activity_categories_grid_imageButton);
            result = (TextView) itemView.findViewById(R.id.activity_categories_results_text);
        }
    }
    public class Cat2ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, add_to_wish_list;
        ImageView add_to_chart;
        Button add_to_chart_btn;
        TextView product_name, price, prevprice, offer;
        ProgressBar progressBar;
        public Cat2ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.activity_category_item_image);
            add_to_chart = (ImageView) itemView.findViewById(R.id.activity_category_product_add_to_chart_image_button);
            add_to_wish_list = (ImageView) itemView.findViewById(R.id.activity_category_product_add_to_wishlist_image_button);
            add_to_chart_btn = (Button) itemView.findViewById(R.id.activity_category_product_add_to_chart_button);
            product_name = (TextView) itemView.findViewById(R.id.activity_category_product_name);
            price = (TextView) itemView.findViewById(R.id.activity_category_product_price);
            prevprice = (TextView) itemView.findViewById(R.id.activity_category_product_prev_price);
            offer = (TextView) itemView.findViewById(R.id.activity_category_offer);
            progressBar=(ProgressBar)itemView.findViewById(R.id.prog);
            progressBar.setVisibility(View.GONE);
        }
    }
    private void headerCatViewHolder(final headerCatViewHolder vh2, int position) {

        if ( !(items.get(position)+"").isEmpty()&&!items.get(position).equals("null")) {

            vh2.progressBar.setVisibility(View.VISIBLE);

        }
        try{
        Picasso.with(context).load((int) items.get(position)).fit()
                .placeholder(R.drawable.defaultimage).into(vh2.imageView,
                new ImageLoadedCallback(vh2.progressBar)
                {
                    @Override
                    public void onSuccess() {
                        if (vh2.progressBar != null) {
                            vh2.progressBar.setVisibility(View.GONE);
                        }
                    }
                });}catch (Exception e){
            String defult=context.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
            Picasso.with(context).load(defult).fit()
                    .placeholder(R.drawable.defaultimage).into(vh2.imageView,
                    new ImageLoadedCallback(vh2.progressBar)
                    {
                        @Override
                        public void onSuccess() {
                            if (vh2.progressBar != null) {
                                vh2.progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }


    }
    private void CatViewHolder(final CatViewHolder vh, int position) {
        CatModel catModel = (CatModel) items.get(position);
        if ( !(catModel.getUrl()+"").isEmpty()&&!(catModel.getUrl()+"").equals("null")) {

            vh.progressBar.setVisibility(View.VISIBLE);

        }
        try{
        Picasso.with(context).load(catModel.getUrl()).fit()
                .placeholder(R.drawable.defaultimage).into(vh.imageView,
                new ImageLoadedCallback(vh.progressBar)
                {
                    @Override
                    public void onSuccess() {
                        if (vh.progressBar != null) {
                            vh.progressBar.setVisibility(View.GONE);
                        }
                    }
                });


        vh.textView.setText(catModel.getName());
    }catch (Exception e){
            String defult=context.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
            Picasso.with(context).load(defult).fit()
                    .placeholder(R.drawable.defaultimage).into(vh.imageView,
                    new ImageLoadedCallback(vh.progressBar)
                    {
                        @Override
                        public void onSuccess() {
                            if (vh.progressBar != null) {
                                vh.progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }}
    private void SeparatorViewHolder(SeparatorViewHolder vh, final int position) {
        vh.result.setText( items.size()-1+"-"+TestModel.getSize_array()+context.getResources().getString(R.string.results));
        vh.sort.setVisibility(View.GONE);
//        vh.sort.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                DailogFragment fragmentDrawer = DailogFragment.newInstance() ;
////                FragmentManager fm = context.getSupportFragmentManager();
////                fragmentDrawer.show(fm , "dialog");
//                layout.setVisibility(View.VISIBLE);
//                layout.bringToFront();
//                TestModel.setFlag(true);
//                RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.activity_categories_sort_recycler_view);
//                ArrayList<String> items = new ArrayList<>();
//                items.add("Best Match");
//                items.add("New Arrival");
//                items.add("Popularity");
//                items.add("Top Rated");
//                items.add("Price:Low to High");
//                items.add("Price:High to low");
//                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//                recyclerView.setAdapter(new SortAdapter(items));
//                recyclerView.setLayoutManager(layoutManager);
//                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
////                        Toast.makeText(context, "position is  " + position, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                //context.getSupportFragmentManager().beginTransaction().replace(R.id.dailog_fragment, fragmentDrawer).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
////        FragmentDrawer drawerFragment = (FragmentDrawer)
////                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
//            }
//        });
//        vh.grid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                flag = false;
//                setAdapter(position, flag);
//            }
//        });
//        vh.list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                flag = true;
//                setAdapter(position, flag);
//            }
//        });
    }
    private void Cat2ViewHolder(final Cat2ViewHolder vh, final int position) {
        final ItemForView itemForView = (ItemForView) items.get(position);

        if (!(itemForView.getImageUrl()+"").isEmpty()&& !(itemForView.getImageUrl()+"").equals("null")) {

            vh.progressBar.setVisibility(View.VISIBLE);

        }
        try{
        Picasso.with(context).load(itemForView.getImageUrl()).resize(300, 300).centerInside()
                .placeholder(R.drawable.defaultimage).into(vh.imageView,
                new ImageLoadedCallback(vh.progressBar)
                {
                    @Override
                    public void onSuccess() {
                        if (vh.progressBar != null) {
                            vh.progressBar.setVisibility(View.GONE);
                        }
                    }
                });}catch (Exception e){
            String defult=context.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
            Picasso.with(context).load(defult).fit()
                    .placeholder(R.drawable.defaultimage).into(vh.imageView,
                    new ImageLoadedCallback(vh.progressBar)
                    {
                        @Override
                        public void onSuccess() {
                            if (vh.progressBar != null) {
                                vh.progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }


        vh.price.setText(itemForView.getPrice()+""+" "+TestModel.getcurrencyname(context));
        vh.offer.setText(itemForView.getOffer()+"");
        vh.prevprice.setVisibility(View.INVISIBLE);
        vh.product_name.setText(itemForView.getName());
        final int  pos=position-1;
        vh.add_to_wish_list.setTag(Ids.get(pos).toString());
        vh.add_to_chart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,pos+"",Toast.LENGTH_SHORT).show();

                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotat));
                item_id=Ids.get(pos).toString();
                item_name=itemforviews.get(pos).getName();
                item_oldprice= itemforviews.get(pos).getPrevprice();
                item_newprice=itemforviews.get(pos).getPrice();
                item_offer= itemforviews.get(pos).getOffer();
                item_image=image_urls.get(pos).toString();
                ChartModel chartModel =new ChartModel(item_image,item_name,item_newprice,item_oldprice,item_offer,item_id);
                models=TestModel.getfromcardmodel("card",context);

                if(models==null)
                {
                    models=new ArrayList<>();
                    models.add(chartModel);
                    Toast.makeText(context,R.string.youadd,Toast.LENGTH_LONG).show();

                }
                else {
                    if(!TestModel.checkmodel(models,chartModel.getItem_id()))
                    {
                        Log.d("checkhna","anananna");
                        models.add(chartModel);
                        Toast.makeText(context,R.string.youadd,Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(context,R.string.youalreadyadd,Toast.LENGTH_LONG).show();

                }
                TestModel.savetosharedcardmodel(models,"card",context);
                if(models.size()>0)
                {notifytext.setVisibility(View.VISIBLE);
                    notifytext.setText(TestModel.getfromcardmodel("card",context).size()+"");}
                else {
                    notifytext.setVisibility(View.INVISIBLE);
                }


            }
        });
        if(list!=null)
        {    item_id=Ids.get(pos).toString();
            counter=0;


            if(TestModel.checkitem(list,item_id))
            {
                vh.add_to_wish_list.setImageResource(R.drawable.ic_favorite_black_24dp);

            }
            else {
                vh.add_to_wish_list.setImageResource(R.mipmap.ic_wishlist_outline_disable);

            }}
        vh.add_to_wish_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,pos+"",Toast.LENGTH_SHORT).show();
                item_id=Ids.get(pos).toString();
                item_name=itemforviews.get(pos).getName();
                item_oldprice= itemforviews.get(pos).getPrevprice();
                item_newprice=itemforviews.get(pos).getPrice();
                item_offer= itemforviews.get(pos).getOffer();
                item_image=image_urls.get(pos).toString();
                list=TestModel.getfromshared("wish",context);

                WishListModel wishListModel=new WishListModel(item_name,item_newprice+"",item_oldprice+"",item_image,item_id);
                Log.d("checkitemid",item_id);
                Log.d("checkiteminarrayid",pos+"");
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
                    Toast.makeText(context,R.string.addfav,Toast.LENGTH_SHORT).show();
                    vh.add_to_wish_list.setImageResource(R.drawable.ic_favorite_black_24dp);
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
                        Toast.makeText(context,R.string.removefav,Toast.LENGTH_SHORT).show();
                        vh.add_to_wish_list.setImageResource(R.mipmap.ic_wishlist_outline_disable);
                        int  wish=TestModel.getitemposition(list,wishListModel.getItem_id());
                        list.remove(wish);
                        counter=0;
                    }
                }
                TestModel.savetoshared(list,"wish",context);
                Log.d("checkdatasaved",TestModel.getfromshared("wish",context).size()+"\n"+wishListModel.getItem_id()+"\n"+(pos)
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
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }
    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }
    public void notifyDataChanged(){
        notifyItemRangeChanged(0, items.size());
        isLoading = false;
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
