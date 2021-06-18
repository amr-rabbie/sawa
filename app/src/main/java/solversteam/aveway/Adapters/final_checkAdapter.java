package solversteam.aveway.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
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

import solversteam.aveway.Models.CartCLass;
import solversteam.aveway.utiltes.ExpandableHeightGridView;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mosta on 2/8/2017.
 */

public class final_checkAdapter extends BaseAdapter {



    Context cntx;

String cruncy;
    ExpandableHeightGridView mygrid2;
    ArrayList<CartCLass> cartList;
    public final_checkAdapter(Context c, String cruncy, ArrayList<CartCLass> cartList) {
        cntx = c;
        this.cruncy=cruncy;
        this.cartList=cartList;



    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    class ViewHold {
        ImageView imagess;
        TextView tx,seller,qnt,pric;
        RelativeLayout re;
        ProgressBar progressBar;

        ViewHold(View v) {
            imagess = (ImageView) v.findViewById(R.id.profile_image_image);
            tx = (TextView) v.findViewById(R.id.name);
            qnt = (TextView) v.findViewById(R.id.qnt);
            seller = (TextView) v.findViewById(R.id.seller);
            pric = (TextView) v.findViewById(R.id.total_price);






        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        SharedPreferences preferences = cntx.getApplicationContext().getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        holder.tx.setText(cartList.get(position).getName()+"");
        holder.qnt.setText(cntx.getResources().getString(R.string.qnt)+" : \t"+cartList.get(position).getQuantity());
        holder.seller.setText(preferences.getString("projectname","E_commerce"));
        holder.pric.setText(cartList.get(position).getPrice()+"\t"+cruncy);
        try{
        Picasso.with(cntx).load(cartList.get(position).getImg()).
                resize(200, 200)
                .centerInside().placeholder(R.drawable.placeholdernew).into(holder.imagess);
        }catch (Exception e){
            String defult=cntx.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
            Picasso.with(cntx).load(defult).resize(200,200)
                    .placeholder(R.drawable.defaultimage).into(holder.imagess);
        }



        return row;
    }




}