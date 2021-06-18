package solversteam.aveway.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import solversteam.aveway.Models.CustomCountries;
import solversteam.aveway.Activities.PaymentOption;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mosta on 2/8/2017.
 */

public class Shippingadapter extends BaseAdapter {



    Context cntx;
    ArrayList<CustomCountries> countriesList;  String price;
    String cruncy;



    public Shippingadapter(Context c, ArrayList<CustomCountries> countriesList, String price, String cruncy) {
        this.cntx=c;
        this.countriesList=countriesList;
        this.price=price;
        this.cruncy=cruncy;
    }

    @Override
    public int getCount() {
        return countriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return countriesList.get(position);
    }

    class ViewHold {
        ImageView imagess;
        TextView tx,seller,qnt,pric;
        RelativeLayout re;
        ProgressBar progressBar;
      RelativeLayout rv;
        ViewHold(View v) {
            imagess = (ImageView) v.findViewById(R.id.profile_image_image);
            tx = (TextView) v.findViewById(R.id.name);
            qnt = (TextView) v.findViewById(R.id.qnt);
            seller = (TextView) v.findViewById(R.id.seller);
            pric = (TextView) v.findViewById(R.id.total_price);
            rv = (RelativeLayout) v.findViewById(R.id.free_shipping_text);






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
            row = layoutInflater.inflate(R.layout.final_check_item, parent, false);
            holder = new ViewHold(row);
            row.setTag(holder);
        } else {
            holder = (ViewHold) row.getTag();
        }

        String logo=((Activity)cntx).getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("logo","");


        holder.rv.setBackgroundResource(R.drawable.item_clicked);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(cntx, PaymentOption.class);
                intent.putExtra("id_carrier",countriesList.get(position).getId_country());
                intent.putExtra("shippingprice",countriesList.get(position).getPrice());
                intent.putExtra("address",((Activity)cntx).getIntent().getExtras().getString("address"));
                intent.putExtra("id_add",((Activity)cntx).getIntent().getExtras().getString("id_add"));
                intent.putExtra("name",((Activity)cntx).getIntent().getExtras().getString("name"));
                intent.putExtra("mob",((Activity)cntx).getIntent().getExtras().getString("mob"));
                intent.putExtra("delay",countriesList.get(position).getDelay()+"");
                cntx.startActivity(intent);
                ((Activity)cntx).overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);

            }
        });

        holder.tx.setText(countriesList.get(position).getCountry_name()+"");
      //  holder.qnt.setText("qnt : \t"+TestModel.getCartList().get(position).getQuantity());
        holder.qnt.setVisibility(View.GONE);
        holder.seller.setText(countriesList.get(position).getDelay());


        holder.pric.setText(countriesList.get(position).getPrice()+"\t"+cruncy);
        try {
            Picasso.with(cntx).load(countriesList.get(position).getImg()).

                    resize(300, 300)
                    .centerInside().placeholder(R.drawable.defaultimage).error(R.drawable.defaultimage).into(holder.imagess);

        }catch (Exception e){
            Picasso.with(cntx).load(R.drawable.defaultimage).
                    resize(300, 300)
                    .centerInside().placeholder(R.drawable.defaultimage).into(holder.imagess);

        }



        return row;
    }




}