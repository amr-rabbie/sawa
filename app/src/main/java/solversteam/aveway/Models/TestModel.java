package solversteam.aveway.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import solverteam.aveway.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ibrahim on 08/02/2017.
 */

public class TestModel {
    public static boolean flag;
    private static String size_array;
    public static int Lang,position;
    private static ArrayList<Integer> catIds  , brandsIds;
    private static ArrayList<String> catIds2_1,catIds2_2,brands_name,catIds2_1_name,apads,ad_url,type;
    private static String cat3,cat3_name;
    private static ArrayList<String>homecatids;

    public static ArrayList<String> getAd_url() {
        return ad_url;
    }

    public static void setAd_url(ArrayList<String> ad_url) {
        TestModel.ad_url = ad_url;
    }

    public static ArrayList<String> getType() {
        return type;
    }

    public static void setType(ArrayList<String> type) {
        TestModel.type = type;
    }

    public static ArrayList<String> getCatIds2_1_name() {
        return catIds2_1_name;
    }

    public static void setCatIds2_1_name(ArrayList<String> catIds2_1_name) {
        TestModel.catIds2_1_name = catIds2_1_name;
    }

    private static ArrayList<String>allids;
    private static ArrayList<String>time;
    private static ArrayList<String>startshippingat;
    private static Boolean start;

    public static ArrayList<String> getTime() {
        return time;
    }

    public static void setTime(ArrayList<String> time) {
        TestModel.time = time;
    }

    public static ArrayList<String> getStartshippingat() {
        return startshippingat;
    }

    public static void setStartshippingat(ArrayList<String> startshippingat) {
        TestModel.startshippingat = startshippingat;
    }

    public static String getCat3_name() {
        return cat3_name;
    }

    public static void setCat3_name(String cat3_name) {
        TestModel.cat3_name = cat3_name;
    }

    private static FirebaseAnalytics mFirebaseAnalytics;

    public static ArrayList<String> getBrands_name() {
        return brands_name;
    }

    public static void setBrands_name(ArrayList<String> brands_name) {
        TestModel.brands_name = brands_name;
    }

    public static ArrayList<String> getApads() {
        return apads;
    }

    public static void setApads(ArrayList<String> apads) {
        TestModel.apads = apads;
    }

    private static View view;
    private static  ArrayList<CartCLass>cartList;
    private static ArrayList<ItemForView>itemForViews;
    private static ArrayList<Integer>Ids;
    private static int positionitem;
    private static ArrayList<String>itemimagearray;
    private static ArrayList<String>address;
    private static ArrayList<String> emails;


    public static String getSize_array() {
        return size_array;
    }

    public static void  setSize_array(String size_array) {
        TestModel.size_array = size_array;
    }
    public static ArrayList<String> getEmails(Context context) {
        if (emails!=null)
            Log.d("emails_",emails.size()+"");

        SharedPreferences sharedPreferences = context.getSharedPreferences("emails_", MODE_PRIVATE);

        ArrayList<String> arrayList=null;
        Gson gson = new Gson();

        try {
            String json = sharedPreferences.getString("email_", "");
            String[] arr= gson.fromJson(json,String[].class );
            List favorites = Arrays.asList(arr);
            arrayList = new ArrayList(favorites);

            //Log.d("checkarraysize2",arrayList.size()+"");

        }
        catch (Exception e) {
            Log.i("wezzashared", "no data");
        }



        return arrayList;

    }

    public static void setEmails(String email,Context context) {
        if( emails ==null )
        {
            emails=new ArrayList<>();
        }
        emails.add(email);


        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(emails);
        emails.clear();
        emails.addAll(hashSet);
        Log.d("emails_",emails.size()+"");
        SharedPreferences sharedPreferences = context.getSharedPreferences("emails_", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        try {

            Gson gson = new Gson();
            String json = gson.toJson(emails);
            editor.putString("email_", json);

            editor.commit();
        } catch (Exception e) {

        }
//        Log.d("checkarraysize",array.size()+"");


    }
    public static ArrayList<String> getAddress() {
        return address;
    }

    public static void setAddress(ArrayList<String> address) {
        TestModel.address = address;
    }

    public static ArrayList<ItemForView> getItemForViews() {
        return itemForViews;
    }

    public static void setItemForViews(ArrayList<ItemForView> itemForViews) {
        TestModel.itemForViews = itemForViews;
    }

    public static ArrayList<Integer> getIds() {
        return Ids;
    }

    public static void setIds(ArrayList<Integer> ids) {
        Ids = ids;
    }

    public static int getPositionitem() {
        return positionitem;
    }

    public static void setPositionitem(int positionitem) {
        TestModel.positionitem = positionitem;
    }

    public static ArrayList<String> getItemimagearray() {
        return itemimagearray;
    }

    public static void setItemimagearray(ArrayList<String> itemimagearray) {
        TestModel.itemimagearray = itemimagearray;
    }

    public static ArrayList<CartCLass> getCartList() {
        return cartList;
    }

    public static void setCartList(ArrayList<CartCLass> cartList) {
        TestModel.cartList = cartList;
    }

    public static ArrayList<Integer> getNums(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("return_qnt", MODE_PRIVATE);
        Type type = new TypeToken<List<Integer>>(){}.getType();
        ArrayList<Integer> arrayList=null;
        Gson gson = new Gson();

        try {
            String json = sharedPreferences.getString("qnt_list", "");
            Integer[] arr= gson.fromJson(json,Integer[].class );
            List favorites = Arrays.asList(arr);
            arrayList = new ArrayList(favorites);

         //   Log.d("checkarraysize2",arrayList.size()+"\n"+arrayList.get(0));

        }
        catch (Exception e) {
            Log.i("wezzashared",e+ "no data");
        }

        return arrayList;
      //  return Nums;
    }

    public static void setNums(ArrayList<Integer> nums,Context context) {
        Nums = nums;
        SharedPreferences sharedPreferences = context.getSharedPreferences("return_qnt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
      //  Log.d("checkarraysize1",nums.size()+"");


        try {
            if (nums != null || nums.size() > 0) {
                Gson gson = new Gson();
                String json = gson.toJson(nums);
                editor.putString("qnt_list", json);

            }
        } catch (Exception e) {
            Log.i("wezzashared", "cantsavedata");
        }
//        Log.d("checkarraysize",nums.size()+"\n"+nums.get(0));
        editor.commit();


    }

    private static ArrayList<Integer>Nums;
    private static ArrayList<FragmentViews>fragmentViewses;
    private static ArrayList<LanguageClass>languages;
    private static LanguageHelper languageHelpers;

    public static LanguageHelper getLanguageHelpers() {
        return languageHelpers;
    }

    public static void setLanguageHelpers(LanguageHelper languageHelpers) {
        TestModel.languageHelpers = languageHelpers;
    }

    public static ArrayList<FragmentViews> getFragmentViewses() {
        return fragmentViewses;
    }

    public static void setFragmentViewses(ArrayList<FragmentViews> fragmentViewses) {
        TestModel.fragmentViewses = fragmentViewses;
    }

    public static ArrayList<LanguageClass> getLanguages() {
        return languages;
    }

    public static void setLanguages(ArrayList<LanguageClass> languages) {
        TestModel.languages = languages;
    }

    public static View getView() {
        return view;
    }

    public static void setView(View view) {
        TestModel.view = view;
    }

    public static ArrayList<String> getHomecatids() {
        return homecatids;
    }

    public static ArrayList<String> getAllids() {
        return allids;
    }

    public static void setAllids(ArrayList<String> allids) {
        TestModel.allids = allids;
    }

    public static void setHomecatids(ArrayList<String> homecatids) {
        TestModel.homecatids = homecatids;
    }

    public static String getCat3() {
        return cat3;
    }

    public static void setCat3(String cat3) {
        TestModel.cat3 = cat3;
    }

    public static ArrayList<String> getCatIds2_2() {
        return catIds2_2;
    }

    public static ArrayList<String> getCatIds2_1() {
        return catIds2_1;
    }

    public static void setCatIds2_1(ArrayList<String> catIds2_1) {
        TestModel.catIds2_1 = catIds2_1;
    }



    public static void setCatIds2_2(ArrayList<String> catIds2_2) {
        TestModel.catIds2_2 = catIds2_2;
    }



    public static ArrayList<Integer> getBrandsIds() {
        return brandsIds;
    }
    public static void setBrandsIds(ArrayList<Integer> brandsIds) {
        TestModel.brandsIds = brandsIds;
    }
    public static ArrayList<Integer> getCatIds() {
        return catIds;
    }
    public static void setCatIds(ArrayList<Integer> catIds) {
        TestModel.catIds = catIds;
    }
    public static ArrayList<String> cat, catName, cat1, Images, Images2;

    public static ArrayList<String> getImages2() {
        return Images2;
    }

    public static void setImages2(ArrayList<String> images2) {
        Images2 = images2;
    }

    public static ArrayList<String> getCat() {
        return cat;
    }

    public static void setCat(ArrayList<String> cat) {
        TestModel.cat = cat;
    }

    public static ArrayList<String> getCatName() {
        return catName;
    }

    public static void setCatName(ArrayList<String> catName) {
        TestModel.catName = catName;
    }

    public static ArrayList<String> getCat1() {
        return cat1;
    }

    public static void setCat1(ArrayList<String> cat1) {
        TestModel.cat1 = cat1;
    }

    public static ArrayList<String> getImages() {
        return Images;
    }

    public static void setImages(ArrayList<String> images) {
        ArrayList<String> al = new ArrayList<>();
        TestModel.Images=al;
        Images = images;
    }

    public static int getLang() {
        return Lang;
    }

    public static void setLang(int lang) {
        Lang = lang;
    }

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        TestModel.flag = flag;
    }

    public static void connectionToast(Context _context) {

        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.my_customtoast, null);
        LinearLayout linear = (LinearLayout) layout.findViewById(R.id.Forget_customlayout_LinearLayout);
        Toast toast = new Toast(_context);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void savetoshared(ArrayList<WishListModel> array, String sharedName, Context context)
    {
        start=false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedName, MODE_PRIVATE);
        if(getfromshared("wish",context)!=null)
        {
            int  oldsize=getfromshared("wish",context).size();
            try{
                if(array.size()>oldsize )
                {
                    start=true;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else {
            start=true;
        }        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("checkarraysize1",array.size()+"");


        try {
            if (array != null || array.size() > 0) {
                Gson gson = new Gson();
                String json = gson.toJson(array);
                editor.putString("list", json);

            }
        } catch (Exception e) {
            Log.i("wezzashared", "cantsavedata");
        }
            Log.d("checkarraysize",array.size()+"");
        editor.commit();
// start analytics
        if(start) {
            position = 0;
            try {
                if (array.size() > 0) {
                    position = array.size() - 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            try {
                Bundle bundle = new Bundle();
                Log.d("checkdatasave", array.get(position).getName() + "\n" + array.get(position).getItem_id());
                bundle.putString("product_name", array.get(position).getName());
                bundle.putString("product_id", array.get(position).getItem_id());

                mFirebaseAnalytics.logEvent("most_favourite", bundle);
                mFirebaseAnalytics.setUserProperty("product_name", array.get(position).getName());
                mFirebaseAnalytics.setUserProperty("product_id", array.get(position).getItem_id());


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<WishListModel> getfromshared(String sharedName, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedName, MODE_PRIVATE);
        Type type = new TypeToken<List<WishListModel>>(){}.getType();
        ArrayList<WishListModel> arrayList=null;
        Gson gson = new Gson();

        try {
             String json = sharedPreferences.getString("list", "");
             WishListModel[] arr= gson.fromJson(json,WishListModel[].class );
              List favorites = Arrays.asList(arr);
            arrayList = new ArrayList(favorites);

            Log.d("checkarraysize2",arrayList.size()+"");

        }
         catch (Exception e) {
            Log.i("wezzashared", "no data");
        }

        return arrayList;

    }
    public static void clear( String sharedName, Context context)
    {   SharedPreferences sharedPreferences = context.getSharedPreferences(sharedName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
    }
    public static void save_pos(Context context,ArrayList<String> posarray)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("poscard",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        try{
            if (posarray != null || posarray.size() > 0) {
            Set<String> setMenu = new HashSet<>();
            setMenu.addAll(posarray);
            editor.putStringSet("posarray", setMenu);
        }
        }
     catch (Exception e) {
        Log.i("wezzashared", "cantsavedata");
    }

        editor.commit();
    }
    public static ArrayList<String> get_pos(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("poscard",MODE_PRIVATE);
        ArrayList<String>posarray=null;
        try {
            Set<String> setMenu = sharedPreferences.getStringSet("posarray", null);
            if (setMenu.size() > 0 && setMenu != null) {
               posarray=new ArrayList<>(setMenu);
            }
        } catch (Exception e) {
            Log.i("wezzashared", "no data");
        }
        return  posarray ;

    }
    public static Boolean checkitem(ArrayList<WishListModel>wishListModel,String item_id)
    {
        if(wishListModel!=null) {
            for (int position = 0; position < wishListModel.size(); position++) {
                if (item_id.equals(wishListModel.get(position).getItem_id())) {
                    return true;
                }
            }
        }
        return false;
    }
    public static Boolean checkmodel(ArrayList<ChartModel>wishListModel,String item_id)
    {
        if(wishListModel!=null) {
        for(int position=0;position<wishListModel.size();position++)
        {
            if(item_id.equals(wishListModel.get(position).getItem_id()))
            {
                return true;
            }
        }}
        return false;
    }
    public static int getitemposition(ArrayList<WishListModel>wishListModel,String item_id)
    {
        if(wishListModel!=null) {
        for(int position=0;position<wishListModel.size();position++)
        {
            if(item_id.equals(wishListModel.get(position).getItem_id()))
            {
                return position;
            }
        }}
        return 0;
    }

    public static Boolean check(int counter)
    {
        if(counter%2==0)
        {
            return true;
        }
        else return false;
    }
    public static void savetosharedcard(ArrayList<WishListModelWithCard> array, String sharedName, Context context)
    {   SharedPreferences sharedPreferences = context.getSharedPreferences(sharedName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("checkarraysize1",array.size()+"");


        try {
            if (array != null || array.size() > 0) {
                Gson gson = new Gson();
                String json = gson.toJson(array);
                editor.putString("listwithcard", json);

            }
        } catch (Exception e) {
            Log.i("wezzashared", "cantsavedata");
        }
        Log.d("checkarraysize",array.size()+"");
        editor.commit();
    }

    public static ArrayList<WishListModelWithCard> getfromsharedcard(String sharedName, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedName, MODE_PRIVATE);
        Type type = new TypeToken<List<WishListModelWithCard>>(){}.getType();
        ArrayList<WishListModelWithCard> arrayList=null;
        Gson gson = new Gson();

        try {
            String json = sharedPreferences.getString("listwithcard", "");
            WishListModelWithCard[] arr= gson.fromJson(json,WishListModelWithCard[].class );
            List favorites = Arrays.asList(arr);
            arrayList = new ArrayList(favorites);

            Log.d("checkarraysize2",arrayList.size()+"");

        }
        catch (Exception e) {
            Log.i("wezzashared", "no data");
        }

        return arrayList;

    }
    public static void savetosharedcardmodel(ArrayList<ChartModel> array, String sharedName, Context context)
    {
        start=false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedName, MODE_PRIVATE);
        if(getfromshared("card",context)!=null)
        {
            int  oldsize=getfromshared("card",context).size();
            try{
                if(array.size()>oldsize )
                {
                    start=true;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else {
            start=true;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("checkarraysize1",array.size()+"");


        try {
            if (array != null || array.size() > 0) {
                Gson gson = new Gson();
                String json = gson.toJson(array);
                editor.putString("list", json);

            }
        } catch (Exception e) {
            Log.i("wezzashared", "cantsavedata");
        }
        Log.d("checkarraysize",array.size()+"");
        editor.commit();
        // start analytics
        if(start) {
            position = 0;
            try {
                if (array.size() > 0) {
                    position = array.size() - 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            try {
                Bundle bundle = new Bundle();
                Log.d("checkdatasave", array.get(position).getName() + "\n" + array.get(position).getItem_id());
                bundle.putString("product_name", array.get(position).getName());
                bundle.putString("product_id", array.get(position).getItem_id());

                mFirebaseAnalytics.logEvent("most_items_in_cart", bundle);
                mFirebaseAnalytics.setUserProperty("product_name", array.get(position).getName());
                mFirebaseAnalytics.setUserProperty("product_id", array.get(position).getItem_id());


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<ChartModel> getfromcardmodel (String sharedName, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedName, MODE_PRIVATE);
        Type type = new TypeToken<List<ChartModel>>(){}.getType();
        ArrayList<ChartModel> arrayList=null;
        Gson gson = new Gson();

        try {
            String json = sharedPreferences.getString("list", "");
            ChartModel[] arr= gson.fromJson(json,ChartModel[].class );
            List favorites = Arrays.asList(arr);
            arrayList = new ArrayList(favorites);

            Log.d("checkarraysize2",arrayList.size()+"");

        }
        catch (Exception e) {
            Log.i("wezzashared", "no data");
        }

        return arrayList;

    }
    public static String getcurrencyname(Context context)
    {
       SharedPreferences preferences = context.getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        return preferences.getString("currencyname","EGB");
    }
    public static int getidposition(ArrayList<String>catids,String id)
    {
        for (int position=0;position<catids.size();position++)
        {
            if(catids.get(position).equals(id))
            {
                return position;
            }
        }
        return 0;
    }
    public static Boolean checkidinmain(ArrayList<String>allids,String id)
    {
        if(allids!=null) {
            for (int position = 0; position < allids.size(); position++) {
                Log.d("cehckidcheck", "idcoming:" + id + "\n" + "idinlist:" + allids.get(position));
                if (id.equals(allids.get(position).toString())) {
                    return true;
                }
            }
        }
        return false;
    }
    public static View getviewfragment(ArrayList<FragmentViews> fragmentViewses,String item_id)
    {
        if(fragmentViewses!=null) {
        for(int position=0;position<fragmentViewses.size();position++)
        {
            if(item_id.equals(fragmentViewses.get(position).getItem_id()))
            {
                return fragmentViewses.get(position).getView();
            }
        }}
        return null;
    }
//    public static boolean checkLogOut(ArrayList<MenuItem>menuItems,String)

    public static String getlanguagename(int id,ArrayList<LanguageClass> languagearray)
    {
        for(int position=0;position<languagearray.size();position++)
        {
            if(id==languagearray.get(position).getLanguage())
            {
                return languagearray.get(position).getLang_name();
            }
        }
        return "";
    }

}