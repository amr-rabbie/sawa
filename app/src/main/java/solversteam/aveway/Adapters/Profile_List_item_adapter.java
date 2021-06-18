package solversteam.aveway.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import solversteam.aveway.utiltes.CircleTransform;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

public class Profile_List_item_adapter extends BaseAdapter {
    private  ArrayList<Integer> countryId;
    ArrayList<String> Names;
    ArrayList<Object> Images;

    Context context;
    int where_i_come, pos;
    View view;
    private int countryname;

    public Profile_List_item_adapter(Context context, ArrayList<String> names, ArrayList<Object> images, int pos,int countryname,ArrayList<Integer>countryid) {


        Names=new ArrayList<>();
        Images=new ArrayList<>();
        this.Names = names;
        this.Images = images;
        this.context = context;
        this.pos = pos;
        this.countryname=countryname;
        Log.d("checknameincountry",countryname+"");
        this.countryId=countryid;



    }
    public Profile_List_item_adapter(Context context, ArrayList<String> names, ArrayList<Object> images, int pos,int countryname) {
        Names=new ArrayList<>();
        Images=new ArrayList<>();
        this.Names = names;
        this.Images = images;
        this.context = context;
        this.pos = pos;
        this.countryname=countryname;
        Log.d("checknameincountry",countryname+"");



    }

    public Profile_List_item_adapter(ArrayList<String> names, Context context, ArrayList<Object> images, int pos) {


        Names=new ArrayList<>();
        Images=new ArrayList<>();
        this.Names = names;
        this.Images = images;
        this.context = context;
        this.pos = pos;
        this.where_i_come = where_i_come;
    }

    @Override
    public int getCount() {
        return Images.size();
    }

    @Override
    public Object getItem(int i) {
        return Images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            LayoutInflater vi;

            if (Images.get(i) instanceof String) {
                vi = LayoutInflater.from(context);
                v = vi.inflate(R.layout.profile_list_items2, null);
                TextView textView = (TextView) v.findViewById(R.id.profile_list_item_name);

                ImageView imageView = (ImageView) v.findViewById(R.id.profile_list_item_image);
                try {
                    Log.d("cont2",countryname+"\n"+Names.get(i));
                    textView.setText(Names.get(i));
                    Picasso.with(context).load((String) Images.get(i)).resize(70, 70)
                            .transform(new CircleTransform()).placeholder(R.drawable.defaultimage).into(imageView);
                }catch (Exception e){
                    String defult=context.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
                    Picasso.with(context).load(defult).resize(70, 70).transform(new CircleTransform())
                            .placeholder(R.drawable.defaultimage).into(imageView);
                }
            }
            if (Images.get(i) instanceof Integer) {//profile
                vi = LayoutInflater.from(context);
                v = vi.inflate(R.layout.profile_list_items, null);
                TextView textView = (TextView) v.findViewById(R.id.profile_list_item_name);
                ImageView imageView = (ImageView) v.findViewById(R.id.profile_list_item_image);
                try {
                    Log.d("cont1",i+"\n"+Names.get(i));
                    imageView.setImageResource(Integer.parseInt(Images.get(i).toString()));

                   textView.setText( Names.get(i).toString());
//                Picasso.with(context).load((Integer) Images.get(i)).transform(new CircleTransform())
//                        .placeholder(R.drawable.defaultimage).into(imageView);
                }catch (Exception e){
                    String defult=context.getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage","");
//                    Picasso.with(context).load(defult).transform(new CircleTransform())
//                            .placeholder(R.drawable.defaultimage).into(imageView);
                }

            }
        }
        Log.d("checknames",countryname+"\n"+Names.get(i));

        if(countryId!=null)

        { if(countryId.get(i)==(countryname))
        {
            v.findViewById(R.id.selected).setVisibility(View.VISIBLE);
        }
        else {
            v.findViewById(R.id.selected).setVisibility(View.GONE);

        }}
        return v;
    }

    public View getmyView() {
        return view;
    }
    public int getcountryposition(String name, ArrayList<Object> countrynames)
    {
        for(int position=0;position<countrynames.size();position++)
        {
            if(name.equals(countrynames.get(position)))
            {
                return position;
            }
        }
        return 0;
    }
}