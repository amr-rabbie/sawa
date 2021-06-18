package solversteam.aveway.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import solversteam.aveway.Activities.Final_check;
import solversteam.aveway.Models.Address_model;
import solversteam.aveway.utiltes.ExpandableHeightGridView;
import solverteam.aveway.R;

/**
 * Created by mosta on 2/8/2017.
 */

public class Address_adapter extends BaseAdapter {



    Context cntx;
    ArrayList<Address_model> adress;
    String cruncy;
    ExpandableHeightGridView mygrid2;
    String  frag ="x";
    public Address_adapter(Context c,ArrayList<Address_model> adress,String frag) {
        cntx = c;
        this.adress=adress;
        try {
            this.frag = frag;
        }catch (Exception e){
            this.frag ="x";
        }


    }

    @Override
    public int getCount() {
        return adress.size();
    }

    @Override
    public Object getItem(int position) {
        return adress.get(position);
    }

    class ViewHold {
        TextView tx,mob,address,city;
        RelativeLayout re;
        ProgressBar progressBar;

        ViewHold(View v) {
             tx = (TextView) v.findViewById(R.id.name);
            city = (TextView) v.findViewById(R.id.city_country);
            mob = (TextView) v.findViewById(R.id.mobile);
            address = (TextView) v.findViewById(R.id.street);





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
            row = layoutInflater.inflate(R.layout.addres_item, parent, false);
            holder = new ViewHold(row);
            row.setTag(holder);
        } else {
            holder = (ViewHold) row.getTag();
        }

        holder.tx.setText(adress.get(position).getName()+"");
        holder.mob.setText(adress.get(position).getMobile());
        holder.city.setText(adress.get(position).getCity());
        holder.address.setText(adress.get(position).getAddress());

row.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (frag.equals("x")){
        Intent intent = new Intent(cntx, Final_check.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("address",adress.get(position).getAddress());
        intent.putExtra("name",adress.get(position).getName());
        intent.putExtra("mob",adress.get(position).getMobile());
        intent.putExtra("id_add",adress.get(position).getId());
        intent.putExtra("id_zone",adress.get(position).getIdzone());

            //((Activity)cntx).finish();
        cntx.startActivity(intent);
        ((Activity)cntx).overridePendingTransition(R.anim.no_thing, R.anim.slide_out_right);}
    }
});

        return row;
    }




}