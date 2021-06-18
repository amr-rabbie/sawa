package solversteam.aveway.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import solversteam.aveway.Activities.ItemSelectedShowActivity;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.ItemForView;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.Models.WishListModel;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ahmed ezz on 26/02/2017.
 */


public class SearchListAdapter extends ArrayAdapter<ItemForView> {
    private  SharedPreferences sharedPreferences;
    private Context context;
    private ListView listView;
    private ArrayList<ItemForView>itemForViews;
    private ArrayList<String>image_urls;
    private ArrayList<Integer>Ids;
    private Holder holder;
    private LayoutInflater inflater;
    private ArrayList<ChartModel>models;
    private ArrayList<WishListModel> list;
    private String item_id,item_name,item_image,firstcolor;
    private int item_oldprice,item_newprice,counter=0,item_offer,position;

    public SearchListAdapter(Context context, ListView listView, ArrayList<ItemForView>itemForViews,ArrayList<Integer> Ids,ArrayList<String>image_urls)
    {   super(context, 0, itemForViews);
        this.context=context;
        this.listView=listView;
        this.itemForViews=itemForViews;
        this.image_urls=image_urls;
        this.Ids=Ids;
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
    public View getView(final int position,  View convertView,  ViewGroup parent) {
        Log.d("wezzaaaaa",position+"");



        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_layout_2, parent,false);
            holder=new Holder(convertView);
            convertView.setTag(holder);}
//        }
        else
        {
            holder = (Holder) convertView.getTag();
        }
        ItemForView itemForView = itemForViews.get(position);
        Log.d("checkitem33",itemForView.getName()+"");

        if (!image_urls.get(position).isEmpty() && !image_urls.get(position).equals("null")) {

            holder.progressBar.setVisibility(View.VISIBLE);

        }
        final Holder finalHolder1 = holder;
        try{
        Picasso.with(context).load(image_urls.get(position))
                .resize(300, 300).centerInside().placeholder(R.drawable.defaultimage)
                .error(R.drawable.defaultimage).into(holder.imageView,
                new ImageLoadedCallback(finalHolder1.progressBar)
                {
                    @Override
                    public void onSuccess() {
                        if (finalHolder1.progressBar != null) {
                            finalHolder1.progressBar.setVisibility(View.GONE);
                        }
                    }
                });}catch (Exception e){

            String defult=context.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
            Picasso.with(context).load(defult).resize(300,300)
                    .placeholder(R.drawable.defaultimage).into(holder.imageView,
                    new ImageLoadedCallback(finalHolder1.progressBar)
                    {
                        @Override
                        public void onSuccess() {
                            if (finalHolder1.progressBar != null) {
                                finalHolder1.progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }


        holder.price.setText(itemForView.getPrice()+""+" "+ TestModel.getcurrencyname(context));
        holder.offer.setText(itemForView.getOffer()+"");
        holder.prevprice.setVisibility(View.INVISIBLE);
        holder.product_name.setText(itemForView.getName());
        holder.add_to_wish_list.setTag(Ids.get(position).toString());

        holder.add_to_chart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,position+":added Btn",Toast.LENGTH_SHORT).show();
                item_id=Ids.get(position).toString();
                item_name=itemForViews.get(position).getName();
                item_oldprice= itemForViews.get(position).getPrevprice();
                item_newprice=itemForViews.get(position).getPrice();
                item_offer= itemForViews.get(position).getOffer();
                item_image=image_urls.get(position).toString();

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
                Log.d("checkdatasavedcart",TestModel.getfromshared("card",context).size()+"\n"+chartModel.getItem_id()+"\n"+(position));

            }
        });
        holder.linearcontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,position+":Linear ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ItemSelectedShowActivity.class);
                intent.putExtra("ID", Ids);
                intent.putExtra("position", position);
                intent.putExtra("img_urls", image_urls);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("myImages", itemForViews);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);

            }
        });
        if(list!=null)
        {    item_id=Ids.get(position).toString();
            counter=0;

            if(TestModel.checkitem(list,item_id))
            {
                holder.add_to_wish_list.setImageResource(R.drawable.ic_favorite_black_24dp);

            }
            else {
                holder.add_to_wish_list.setImageResource(R.mipmap.ic_wishlist_outline_disable);

            }
        }

        holder.add_to_wish_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView x= (ImageView) view;
                Log.d("checkitemposiiton",position+"");
//                Toast.makeText(context,position+":added wishlist",Toast.LENGTH_SHORT).show();
                item_id=Ids.get(position).toString();
                item_name=itemForViews.get(position).getName();
                item_oldprice= itemForViews.get(position).getPrevprice();
                item_newprice=itemForViews.get(position).getPrice();
                item_offer= itemForViews.get(position).getOffer();
                item_image=image_urls.get(position).toString();
                list=TestModel.getfromshared("wish",context);


                WishListModel wishListModel=new WishListModel(item_name,item_newprice+"",item_oldprice+"",item_image,item_id);
                Log.d("checkitemid",item_id);
                Log.d("checkiteminarrayid",position+"");
                if(list!=null)
                { if(!TestModel.checkitem(list,wishListModel.getItem_id()))
                {
                    counter=0;
                }
                else {
                    counter=1;
                }}
                Log.d("checkcounter0",counter+"");


                if(!TestModel.checkitem(list,wishListModel.getItem_id()))
                {
                    Toast.makeText(context,R.string.addfav,Toast.LENGTH_SHORT).show();
                    x.setImageResource(R.drawable.ic_favorite_black_24dp);



                   // Toast.makeText(context,"1"+counter,Toast.LENGTH_SHORT).show();
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
                        x.setImageResource(R.mipmap.ic_wishlist_outline_disable);
                      //  Toast.makeText(context,"2",Toast.LENGTH_SHORT).show();

                        int  wish=TestModel.getitemposition(list,wishListModel.getItem_id());
                        list.remove(wish);
                        counter=0;
                    }
                }
                TestModel.savetoshared(list,"wish",context);
                Log.d("checkdatasaved",TestModel.getfromshared("wish",context).size()+"\n"+wishListModel.getItem_id()+"\n"+(position)
                        +"\n"+counter);
                Log.d("checkitem",TestModel.checkitem(list,wishListModel.getItem_id())+"");
                if(TestModel.checkitem(list,wishListModel.getItem_id()))
                {
                    counter++;
                }
                Log.d("checkcoun2",counter+"");


            }
        });





        return convertView;

    }



    public class Holder
    {
        ImageView imageView,add_to_chart;
        ImageView add_to_wish_list;
        Button add_to_chart_btn;
        TextView product_name, price, prevprice, offer;
        LinearLayout linearcontainer;
        ProgressBar progressBar;
        Holder(View convertView){

            imageView = (ImageView) convertView.findViewById(R.id.activity_category_item_image);
             add_to_chart = (ImageView) convertView.findViewById(R.id.activity_category_product_add_to_chart_image_button);
             add_to_wish_list = (ImageView) convertView.findViewById(R.id.activity_category_product_add_to_wishlist_image_button);
            add_to_chart_btn = (Button) convertView.findViewById(R.id.activity_category_product_add_to_chart_button);
            product_name = (TextView) convertView.findViewById(R.id.activity_category_product_name);
           price = (TextView) convertView.findViewById(R.id.activity_category_product_price);
             prevprice = (TextView) convertView.findViewById(R.id.activity_category_product_prev_price);
            offer = (TextView) convertView.findViewById(R.id.activity_category_offer);
            linearcontainer=(LinearLayout)convertView.findViewById(R.id.category2_layout_container);
             progressBar=(ProgressBar)convertView.findViewById(R.id.prog);
            progressBar.setVisibility(View.GONE);
            sharedPreferences=convertView.getContext().getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
            firstcolor=sharedPreferences.getString("firstmenucolour","#1c1c1c");
            add_to_chart_btn.setBackgroundColor(Color.parseColor(firstcolor));



        }
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

