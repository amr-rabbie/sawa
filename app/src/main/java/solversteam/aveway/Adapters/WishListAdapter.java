package solversteam.aveway.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import solversteam.aveway.Activities.MainActivity;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.Models.WishListModel;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ibrahim on 19/02/2017.
 */

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

    private  ArrayList<ChartModel> cartmodels;
    private  TextView notifytext;
    ArrayList<WishListModel> models;
    Context context;
    private String item_id,item_name,item_image;
    private int item_oldprice,item_newprice,counter=0,item_offer,position;

    public WishListAdapter(ArrayList<WishListModel> models, Context context,TextView notifytext,ArrayList<ChartModel>cartmodels) {
        this.models = models;
        this.context = context;
        this.notifytext=notifytext;
        this.cartmodels=cartmodels;
        if(TestModel.getfromcardmodel("card",context)==null)
        {
            notifytext.setVisibility(View.INVISIBLE);
        }
        if(TestModel.getfromcardmodel("card",context)!=null)
        {
            cartmodels=TestModel.getfromcardmodel("card",context);
            notifytext.setVisibility(View.VISIBLE);
            if(cartmodels.size()>0)
            {
                notifytext.setText(TestModel.getfromshared("card",context).size()+"");
            }
            else {
                notifytext.setVisibility(View.INVISIBLE);

            }

        }
    }

    @Override
    public WishListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_item, parent, false);
        return new WishListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WishListAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(models.get(position).getName());
        holder.prevprice.setVisibility(View.INVISIBLE);
        holder.price.setText(models.get(position).getPrice()+""+" "+TestModel.getcurrencyname(context));
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);  // the values are saved in the screenSize object
        final int width = screenSize.x;
        final int height = screenSize.y;
        ViewGroup.LayoutParams params1 = holder.itemImage.getLayoutParams();
        params1.height= (int) (height/8);
        params1.width= (int) (width/4);
        holder.itemImage.setLayoutParams(params1);


        try{
        Picasso.with(context).load(models.get(position).getImage()).
               placeholder(R.drawable.defaultimage).into(holder.itemImage);
        }catch (Exception e){
            String defult=context.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
            Picasso.with(context).load(defult)
                    .placeholder(R.drawable.defaultimage).into(holder.itemImage);
        }
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,position+"",Toast.LENGTH_LONG).show();
                models.remove(position);
                notifyDataSetChanged();
                TestModel.savetoshared(models,"wish",context);
                if(models.size()<=0){initiatePopupWindow();}
            }
        });
        holder.Add_To_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"this",Toast.LENGTH_SHORT).show();

                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotat));
                cartmodels=TestModel.getfromcardmodel("card",context);
                item_id=models.get(position).getItem_id();
                item_name=models.get(position).getName();
                  item_oldprice=Integer.parseInt( models.get(position).getPrevPrice());
                  item_newprice=Integer.parseInt(models.get(position).getPrice());
                item_image=models.get(position).toString();
                 item_offer=models.get(position).getIdcard();
                ChartModel chartModel =new ChartModel(item_image,item_name,item_newprice,item_oldprice,item_offer,item_id);
                if(cartmodels==null)
                {   cartmodels=new ArrayList<>();
                    cartmodels.add(chartModel);

                    Toast.makeText(context,R.string.youadd,Toast.LENGTH_LONG).show();
                }
                else {
                    if(!TestModel.checkmodel(cartmodels,chartModel.getItem_id()))
                    {
                        Log.d("checkhna","anananna");
                        cartmodels.add(chartModel);
                        Toast.makeText(context,R.string.youadd,Toast.LENGTH_LONG).show();

                    }else
                        Toast.makeText(context,R.string.youalreadyadd,Toast.LENGTH_LONG).show();
                }
                TestModel.savetosharedcardmodel(cartmodels,"card",context);
                if(cartmodels.size()>0)
                {notifytext.setVisibility(View.VISIBLE);
                    notifytext.setText(TestModel.getfromcardmodel("card",context).size()+"");}
                else {
                    notifytext.setVisibility(View.INVISIBLE);
                }
                Log.d("checksizearray",TestModel.getfromcardmodel("card",context).size()+"\n"+position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, prevprice;
        ImageView itemImage;
        ImageButton Add_To_Cart  , Delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.product_name);
            Add_To_Cart = (ImageButton) itemView.findViewById(R.id.add_to_cart);
            price = (TextView) itemView.findViewById(R.id.product_price);
            prevprice = (TextView) itemView.findViewById(R.id.prev_price);
            Delete = (ImageButton) itemView.findViewById(R.id.delete);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
    private void initiatePopupWindow() {


        SharedPreferences sharedPreferences=context.getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        String logo=sharedPreferences.getString("logo","");
        String defult=sharedPreferences.getString("defaultimage","");
        String   firstcolor=sharedPreferences.getString("firstmenucolour","#7d2265");
        String secondcolor=sharedPreferences.getString("secondmenucolour","#000000");


        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {Color.parseColor(firstcolor),Color.parseColor(firstcolor)});
        gd.setCornerRadius(5);

        RelativeLayout item = (RelativeLayout)((Activity)context).findViewById(R.id.popp);
        ((Activity)context).findViewById(R.id.contain).setVisibility(View.GONE);
        View child = ((Activity)context).getLayoutInflater().inflate(R.layout.popup, null);ImageView img =(ImageView) child.findViewById(R.id.imageView9);
        if (!logo.isEmpty())
            Picasso.with(( context))
                    .load(logo)
                    .resize(250,180)
                    .placeholder(R.drawable.defaultimage)
                    .error(R.drawable.ave)
                    .into(img);
        else
            Picasso.with(context)
                    .load(defult)
                    .resize(250,180)
                    .placeholder(R.drawable.defaultimage)
                    .error(R.drawable.ave)
                    .into(img);

        TextView tx =(TextView) child.findViewById(R.id.pop_text);
        TextView tx2 =(TextView) child.findViewById(R.id.pop_text2);
        tx.setText(R.string.msg_wish);
        tx2.setText(R.string.msg_cart_wish_2);
        child.findViewById(R.id.pop).setPadding(10,10,10,10);
        child.findViewById(R.id.pop).setBackgroundDrawable(gd);
        child.findViewById(R.id.pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ((Activity)context).finish();
                ( context). startActivity(i);
                ((Activity)context). overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });
        item.addView(child);

    }
}
