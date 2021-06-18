package solversteam.aveway.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import solversteam.aveway.Models.LanguageClass;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.utiltes.CircleTransform;
import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

public class Profile_List_item_adapterlanguage extends BaseAdapter {
    ArrayList<Object> Names, Images;
    Context context;
    int where_i_come, pos;
    View view;
    private String countryname;

    public Profile_List_item_adapterlanguage(Context context, ArrayList<Object> names, ArrayList<Object> images, int pos, String countryname) {
        this.Names = names;
        this.Images = images;
        this.context = context;
        this.pos = pos;
        this.countryname=countryname;

    }

    public Profile_List_item_adapterlanguage(ArrayList<Object> names, Context context, ArrayList<Object> images, int pos) {
        this.Names = names;
        this.Images = images;
        this.context = context;
        this.pos = pos;
        this.where_i_come = where_i_come;
    }

    @Override
    public int getCount() {
        return Names.size();
    }

    @Override
    public Object getItem(int i) {
        return Names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
//        Log.d("checknames122",((LanguageClass)Names.get(i)).getLangname()+""+"\n"+i);

        if (v == null) {
            LayoutInflater vi;
            String defult= context.getSharedPreferences("LanguageAndCountry", MODE_PRIVATE).getString("defaultimage","");
            if (Images.get(0) instanceof String) {
                vi = LayoutInflater.from(context);
                v = vi.inflate(R.layout.profile_list_items2, null);
                TextView textView = (TextView) v.findViewById(R.id.profile_list_item_name);
                String text=((LanguageClass)Names.get(i)).getLangname();

                ImageView imageView = (ImageView) v.findViewById(R.id.profile_list_item_image);
                try{
                    textView.setText(text);
                }catch (Exception e){
                    textView.setText("");
                }
                try{
                    Picasso.with(context).load((String) Images.get(i)).resize(70, 70).transform(new CircleTransform())
                            .placeholder(R.drawable.defaultimage).into(imageView);
                }catch (Exception e){

                    Picasso.with(context).load(R.drawable.defaultimage).resize(70, 70).transform(new CircleTransform())
                            .placeholder(R.drawable.defaultimage).into(imageView);
                }
            }
            if (Images.get(0) instanceof Integer) {
                vi = LayoutInflater.from(context);
                v = vi.inflate(R.layout.profile_list_items2, null);
                TextView textView = (TextView) v.findViewById(R.id.profile_list_item_name);

                ImageView imageView = (ImageView) v.findViewById(R.id.profile_list_item_image);
                try {
                    textView.setText(((LanguageClass) Names.get(i)).getLangname() + "");
                }catch (Exception e){
                    textView.setText("");
                }
                try {

                    Picasso.with(context).load(TestModel.getLanguages().get(i).getImg()).resize(70, 70).transform(new CircleTransform()).centerCrop()
                            .placeholder(R.drawable.defaultimage).into(imageView);
                }catch (Exception e){
                    Picasso.with(context).load(R.drawable.defaultimage).resize(70, 70).transform(new CircleTransform()).centerCrop()
                            .placeholder(R.drawable.defaultimage).into(imageView);
                }
            }
        }

        if (pos == TestModel.getLanguages().get(i).getLanguage())
        {
            v.findViewById(R.id.selected).setVisibility(View.VISIBLE);
        }
        else
        {
            v.findViewById(R.id.selected).setVisibility(View.GONE);
        }

//        if(Names.get(i).equals(countryname))
//        {
//            v.findViewById(R.id.selected).setVisibility(View.VISIBLE);
//        }
//        else {
//            v.findViewById(R.id.selected).setVisibility(View.GONE);
//
//        }
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