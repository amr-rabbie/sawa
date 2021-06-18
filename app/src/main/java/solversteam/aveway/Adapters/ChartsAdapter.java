package solversteam.aveway.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import solversteam.aveway.Activities.CheckOutActivity;
import solversteam.aveway.Activities.Final_check;
import solversteam.aveway.Activities.Login;
import solversteam.aveway.Activities.MainActivity;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Connection.ConnectionDetector;
import solversteam.aveway.Models.CartCLass;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.TestModel;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ibrahim on 03/02/2017.
 */

public class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.MyViewHolder> {
    float sum=0,price=0;
    int counter=1,language,currency,country,customer_id;
    ArrayList<ChartModel> models;
    Context context;
    private View itemView;

    private Intent intent;
    View v;
    TextView tx;
    private String cart_id,order_i,id_customer;
    int [] count;
    SharedPreferences.Editor editor ;
    RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<Integer>num;
    private TextView totalmoney;
    private Button checkout;
    private ArrayList<CartCLass>cartCLasses;
    private SharedPreferences sharedPreferences;
    private Connection connection;
    private ConnectionDetector connectionDetector;
    private String currencyname;
    ArrayList<String> adress,mob,name;
    SharedPreferences prefs;
    private ArrayList<String> idzone;

    public ChartsAdapter(ArrayList<ChartModel> models, Context context, TextView totalmoney,
                         LinearLayoutManager linearLayoutManager,
                         RecyclerView recyclerView,ArrayList<Integer> num,Button checkout) {
        this.models = models;
        this.context = context;
        this.recyclerView=recyclerView;
        this.totalmoney=totalmoney;
        cartCLasses=new ArrayList<>();
        adress=new ArrayList<>();
        mob=new ArrayList<>();
        name=new ArrayList<>();
        idzone=new ArrayList<>();
        this.num= TestModel.getNums(context);
        editor = context.getSharedPreferences("price", 0).edit();
        prefs = context.getSharedPreferences("price", 0);
        this.linearLayoutManager=linearLayoutManager;
        sum = 0;
        this.checkout=checkout;
        connectionDetector=new ConnectionDetector(context);
        count =new int[models.size()];
        for (int i=0;i<models.size();i++)
        {count[i]=1;
        }
        sharedPreferences=context.getSharedPreferences("LanguageAndCountry",Context.MODE_PRIVATE);
        country=sharedPreferences.getInt("Country",1);
        currency=Integer.parseInt(sharedPreferences.getString("currency","1"));
        language=sharedPreferences.getInt("Lang",1);
        currencyname=sharedPreferences.getString("currencyname","EGB");
        id_customer=sharedPreferences.getString("customer_id","53");
        Log.d("checkcustomer",sharedPreferences.getString("customer_id",""));

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chart_item, parent, false);
        mRecyclerView = (RecyclerView) parent;

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(models.get(position).getName());
//        ArrayList<View>allviews=new ArrayList<>();
//        TestModel.setAllviews(allviews);
//        TextView text=
//        allviews.add(Textview);
//        holder.seller.setText(models.get(position).getSeller());
        // holder.offer.setText(models.get(position).getOffer()+"");
        //holder.prevprice.setText(models.get(position).getPrevPrice()+"");
        holder.num_of_item.setTag(Integer.parseInt(models.get(position).getItem_id()));
        //holder.num_of_item.setText(1+"");
        num=TestModel.getNums(context);
        for(int i=0;i<num.size();i++)
        {
            Log.d("checkdatainnum",num.get(i).toString());
        }
        holder.num_of_item.setText(num.get(position).toString());
//        Toast.makeText(context,"done",Toast.LENGTH_SHORT).show();

        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);  // the values are saved in the screenSize object
        final int width = screenSize.x;
        final int height = screenSize.y;
        ViewGroup.LayoutParams params1 = holder.itemImage.getLayoutParams();
        params1.height= (int) (height/8);
        params1.width= (int) (width/4);
        holder.itemImage.setLayoutParams(params1);


        holder.price.setText(models.get(position).getPrice()+" "+currencyname);
        try{
        Picasso.with(context).load(models.get(position).getImageUrl()).
               placeholder(R.drawable.defaultimage).into(holder.itemImage);}
        catch (Exception e){
            String defult=context.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
            Picasso.with(context).load(defult)
                    .placeholder(R.drawable.defaultimage).into(holder.itemImage);
        }


        getprice(num);
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[position]++;
                num.set(position,num.get(position)+1);
                TestModel.setNums(num,context);
                holder.num_of_item.setText(num.get(position)+"");
                price =models.get(position).getPrice()*counter;
                editor.putInt(models.get(position).getItem_id(),count[position]);
                editor.commit();
                getprice(num);
            }
        });









        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num.get(position)!=1)
                {
                    count[position]--;
                    num.set(position,num.get(position)-1);

                    TestModel.setNums(num,context);
                    holder.num_of_item.setText(num.get(position)+"");
                    price =models.get(position).getPrice()*counter;
                    getprice(num);
                    Log.d("BHB",holder.num_of_item.getTag()+"");

                }

            }
        });
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum=sum-(models.get(position).getPrice())*num.get(position);
                totalmoney.setText(sum+". LE");
                Log.d("checktaaaaag",holder.num_of_item.getTag()+"\n"+models.get(position).getItem_id());

                models.remove(position);
                count[position]=0;
                num.remove(position);
                for(int i=0;i<num.size();i++)
                {
                    Log.d("checkdatainnum1",num.get(i).toString());
                }
                notifyDataSetChanged();



//                Log.d("checkitemcount",+recyclerView.get()+"");
                TestModel.savetosharedcardmodel(models,"card",context);
                TestModel.setNums(num,context);
                for(int i=0;i<num.size();i++)
                {
                    Log.d("checkdatainnum2",num.get(i).toString());
                }
                recyclerView.setAdapter(recyclerView.getAdapter());


                getprice(num);
                if ( TestModel.getfromcardmodel("card",context).size()==0)
                    initiatePopupWindow();
//
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartCLasses=new ArrayList<CartCLass>();
                if ( TestModel.getfromcardmodel("card",context).size()!=0){

                for(int position=0;position<models.size();position++)
                {

                    CartCLass cartCLass=new CartCLass(country,language,currency,id_customer,
                            Integer.parseInt(models.get(position).getItem_id()),
                            Integer.parseInt(String.valueOf(num.get(position))),sum+""
                            ,models.get(position).getPrice()+"",models.get(position).getName(),models.get(position).getImageUrl());
                    Log.d("cehckdata",country+"\n"+language+"\n"+currency+"\n"+id_customer+"\n"+Integer.parseInt(models.get(position).getItem_id())
                    +"\n"+Integer.parseInt(String.valueOf(num.get(position))));
                    cartCLasses.add(cartCLass);}
                    TestModel.setCartList(cartCLasses);
                Log.d("confirm_order",TestModel.getCartList().get(0).getTotalmoney()+"");
//
                boolean x=false;
                if(!sharedPreferences.getString("name","").isEmpty())

                {
                    try {
                        final String[] id = {""};
                        connection = new Connection((Activity) context, "/GetAllCustomerAddresses/" + id_customer + "/" + language, "Get");

                        connection.reset();
                        connection.Connect(new Connection.Result() {
                                               @Override
                                               public void data(String str) throws JSONException {

                                                   JSONObject jsonObject = new JSONObject(str);
                                                   JSONArray jsonArray = jsonObject.getJSONArray("1-addresses");
                                                   Log.d("custid", jsonArray.length()+ "555");
                                                   if (jsonArray.length()<=0){

                                                       intent = new Intent(context, CheckOutActivity.class);
                                                       intent.putExtra("frag", "x");
                                                       context.startActivity(intent);
                                                       ((Activity) context).overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);

                                                   }
                                                   for (int Counter = 0; Counter < 1; Counter++) {
                                                       JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                                                       adress.add(jsonObject1.getString("address1") + "\t , \t" + jsonObject1.getString("city"));
                                                       name.add(jsonObject1.getString("firstname") + "\t " + jsonObject1.getString("lastname"));
                                                       mob.add(jsonObject1.getString("phone_mobile"));
                                                       idzone.add(jsonObject1.getString("id_zone"));
                                                       id[0] =jsonObject1.getString("id_address");
                                                   }



                                                   if (adress.size() > 0) {
                                                       TestModel.setAddress(adress);
                                                       intent = new Intent(context, Final_check.class);
                                                       intent.putExtra("address", adress.get(0));
                                                       intent.putExtra("name", name.get(0));
                                                       intent.putExtra("mob", mob.get(0));
                                                       intent.putExtra("id_add",id[0]);
                                                       intent.putExtra("id_zone",idzone.get(0));
                                                       intent.putExtra("frag", "x");
                                                       context.startActivity(intent);
                                                       ((Activity) context).overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                                                   } else {
                                                       intent = new Intent(context, CheckOutActivity.class);
                                                       intent.putExtra("frag", "x");
                                                       context.startActivity(intent);

                                                       ((Activity) context).overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                                                   }


//
//                intent = new Intent(context, CheckOutActivity.class);
//                intent.putExtra("frag","x");
//                context.startActivity(intent);
//                ((Activity)context).overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);

                                               }


                                           }

                        );
                    } catch (Exception e) {
                        intent = new Intent(context, CheckOutActivity.class);
                        intent.putExtra("frag", "x");
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                    }

                }
                else {
                     intent=new Intent(context, Login.class);
                    intent.putExtra("from","checkout");
                    context.startActivity(intent);
                    ((Activity)context).overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                }
//                ((Activity)context).finish();



            }}
        });


    }

    public void  getprice(int[] count) {

        sum=0;
        for(int i=0;i<models.size();i++)
            sum=sum+(models.get(i).getPrice())*count[i];
        totalmoney.setText(sum+"\t"+currencyname);


    }
    public void  getprice(ArrayList<Integer> num) {

        sum=0;
        for(int i=0;i<models.size();i++)
            sum=sum+(models.get(i).getPrice())*num.get(i);
        totalmoney.setText(sum+" "+currencyname);


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, seller, price, prevprice, offer,num_of_item;
        ImageView itemImage,increase,decrease, delet;
        CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);
            TestModel.setView(itemView);
            name = (TextView) itemView.findViewById(R.id.activity_cart_item_name);
            //  seller = (TextView) itemView.findViewById(R.id.activity_cart_seller_name);
            price = (TextView) itemView.findViewById(R.id.activity_cart_item_price);
            prevprice = (TextView) itemView.findViewById(R.id.activity_cart_prevPrice);
            offer = (TextView) itemView.findViewById(R.id.activity_cart_offer);
            num_of_item = (TextView) itemView.findViewById(R.id.activity_cart_item_qountity);
            itemImage = (ImageView) itemView.findViewById(R.id.activity_cart_item_image);
            increase = (ImageView) itemView.findViewById(R.id.activity_cart_item_qountity_plus);
            decrease = (ImageView) itemView.findViewById(R.id.activity_cart_item_qountity_mins);
            delet=(ImageView) itemView.findViewById(R.id.delete);
            card=(CardView)itemView.findViewById(R.id.cv);
        }
    }

    private void initiatePopupWindow() {


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
        final View child = ((Activity)context).getLayoutInflater().inflate(R.layout.popup, null);
        TextView tx =(TextView) child.findViewById(R.id.pop_text);
        ImageView img =(ImageView) child.findViewById(R.id.imageView9);
        if (!logo.isEmpty())
            Picasso.with(context)
                    .load(logo)
                    .placeholder(R.drawable.defaultimage)
                    .resize(250,180)
                    .error(R.drawable.ave)
                    .into(img);
        else
            Picasso.with(context)
                    .load(defult)
                    .placeholder(R.drawable.defaultimage)
                    .resize(250,180)
                    .error(R.drawable.ave)
                    .into(img);

         TextView tx2 =(TextView) child.findViewById(R.id.pop_text2);
        tx.setText(R.string.msg_cart);
        tx2.setText(R.string.msg_cart_wish_2);        child.findViewById(R.id.pop).setPadding(10,10,10,10);
        child.findViewById(R.id.pop).setBackgroundDrawable(gd);
        child.findViewById(R.id.pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ((Activity)context).finish();
                context.startActivity(i);
                ((Activity)context).overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
            }
        });
        item.addView(child);

    }
}