package solversteam.aveway.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import solversteam.aveway.Activities.Order_desc;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Models.Order_model;
import solversteam.aveway.utiltes.ExpandableHeightGridView;
import solversteam.aveway.utiltes.Fonts;
import solverteam.aveway.R;

/**
 * Created by mosta on 2/8/2017.
 */

public class OrderAdapter extends BaseAdapter {


    private  SharedPreferences sharedPreferences;
    Context cntx;

    String cruncy,customername,id_customer,firstcolor,secondcolor,date_order;
    ExpandableHeightGridView mygrid2;
    int language,  country;
    ArrayList<Order_model> list;
    private Connection connection;
    public OrderAdapter(Context c, String currencyname, ArrayList<Order_model> list, String customername,
                        String id_customer, int language, int country) {

        cntx = c;
        this.cruncy=currencyname;
        this.list=list;
        this.customername=customername;
        this.id_customer=id_customer;
        this.country=country;
        this.language=language;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    class ViewHold {
        ImageView imagess;
        TextView txn,orderid,order_date,pric,estmatedate,desc,status,canceled;
        RelativeLayout re;
        ProgressBar progressBar;
        Button cancel,details;

        ViewHold(View v) {
          //  imagess = (ImageView) v.findViewById(R.id.profile_image_image);
            Fonts fonts=new Fonts(cntx);
            txn = (TextView) v.findViewById(R.id.name);
            pric = (TextView) v.findViewById(R.id.price);
            status = (TextView) v.findViewById(R.id.status_order);
            order_date = (TextView) v.findViewById(R.id.order_date);
            estmatedate = (TextView) v.findViewById(R.id.estmatedate);
            desc = (TextView) v.findViewById(R.id.numerofproducts);
            cancel = (Button) v.findViewById(R.id.cancelorder);
            details = (Button) v.findViewById(R.id.details);
            orderid=(TextView) v.findViewById(R.id.order_id);
            canceled=(TextView) v.findViewById(R.id.canceled);
            fonts.setView(details);
            fonts.setView(cancel);

            sharedPreferences=v.getContext().getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
            firstcolor=sharedPreferences.getString("firstmenucolour","#1c1c1c");
            secondcolor=sharedPreferences.getString("secondmenucolour","#7d2265");
            details.setBackgroundColor(Color.parseColor(firstcolor));
            cancel.setBackgroundColor(Color.parseColor(secondcolor));

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHold holder = null;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) cntx.getSystemService(cntx.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.orderitem, parent, false);
            holder = new ViewHold(row);
            row.setTag(holder);
        } else {
            holder = (ViewHold) row.getTag();
        }

//list.get(position).getOrder_id()+
        String date = list.get(position).getDelveery_date();

        String[] date_prder = date.split("T");
        holder.txn.setText(list.get(position).getName());
        holder.pric.setText(list.get(position).getPrice()+cruncy);
        holder.status.setText(list.get(position).gettype());
        holder.order_date.setText(date_prder[0]);
        holder.estmatedate.setText("\t"+date_prder[0]);

        holder.orderid.setText(list.get(position).getOrder_id());
        if(list.get(position).getOrederstatenumber().equals("3"))
        {   holder.cancel.setVisibility(View.VISIBLE);
            holder.canceled.setVisibility(View.GONE);

        }

        else if(list.get(position).getOrederstatenumber().equals("6"))
        { holder.cancel.setVisibility(View.GONE);
        holder.canceled.setVisibility(View.VISIBLE);}
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(cntx, Order_desc.class);
                i.putExtra("status",list.get(position).getOrder_status());
                i.putExtra("name",list.get(position).getName());
                i.putExtra("orderid",list.get(position).getOrder_id());
                i.putExtra("price",list.get(position).getPrice());
                i.putExtra("type",list.get(position).gettype());
                i.putExtra("date",list.get(position).getDelveery_date()+"");
                cntx.startActivity(i);

            }
        });
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(cntx, Order_desc.class);
                i.putExtra("status",list.get(position).getOrder_status());
                i.putExtra("name",list.get(position).getName());
                i.putExtra("price",list.get(position).getPrice());
                i.putExtra("orderid",list.get(position).getOrder_id());
                i.putExtra("type",list.get(position).gettype());
                i.putExtra("date",list.get(position).getDelveery_date()+"");
                cntx.startActivity(i);

            }
        });
//
        final ViewHold finalHolder = holder;
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    connection = new Connection(((Activity)cntx), "/ChangeOrderHistory/" + country + "/" + list.get(position).getOrder_id()
                            + "/" + 6+ "/" + language, "Get");
                    Log.d("custid", id_customer + "");
                    //     Toast.makeText(Order_show.this,id_customer,Toast.LENGTH_LONG).show();
                    connection.reset();
                    connection.Connect(new Connection.Result() {
                        @Override
                        public void data(String str) throws JSONException {
                          String msg="\"Order status changed successfully ..\"";
                            if(str.equals(msg))
                           // finalHolder.ship.setText(cntx.getResources().getString(R.string.status)+": \t"+cntx.getResources().getString(R.string.canceled));
                            finalHolder.cancel.setVisibility(View.GONE);
                        }
                    });
                }catch (Exception e){}

            }
        });
//



        return row;
    }




}