package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.Profile_List_item_adapter;
import solversteam.aveway.Adapters.Profile_List_item_adapterlanguage;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Models.ChartModel;
import solversteam.aveway.Models.CurrencyModel;
import solversteam.aveway.Models.LanguageClass;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.Models.WishListModel;
import solversteam.aveway.utiltes.LocaleHelper;
import solverteam.aveway.R;

public class LanguageAndCountry extends AppCompatActivity {
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private Connection connection;
    private int language,conversion_rate,lang;
    private ArrayList<CurrencyModel>currencyModels;
    private FirebaseAnalytics mFirebaseAnalytics;
    @BindView(R.id.listView1)
    ExpandableHeightListView listv;
    @BindView(R.id.listView2)
    ExpandableHeightListView listv2;
    @BindView(R.id.listView3)
    ExpandableHeightListView listv3;
    @BindView(R.id.include)
    Toolbar toolbar;
    private SharedPreferences preferences;
    private String countryname,shippingcountryname,stringLanguage,
            currency_name,currency_id,old_currency,old_currencyname,old_countryname,firstcolor;

    ArrayList<Object>items;
    SharedPreferences.Editor editor;
    private int old_conversion,old_country,x;
    private Dialog dialog;
    private int newcountry;
    private int test;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_and_country);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        currencyModels=new ArrayList<>();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = getApplicationContext().getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        firstcolor=preferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        getSupportActionBar().setTitle("");
        toolbar.findViewById(R.id.toolbar_apply).setVisibility(View.VISIBLE);
        final ArrayList<Object> images = new ArrayList<>();
        final ArrayList<String> Names = new ArrayList<>();
        final ArrayList<String> currency = new ArrayList<>();
        final ArrayList<Integer> countryID = new ArrayList<>();
        items=new ArrayList<>();
        test = preferences.getInt("Test", -1);
        if(test==-1){

            ArrayList<WishListModel> wishListModels = TestModel.getfromshared("wish", LanguageAndCountry.this);
            if (wishListModels != null) {
                wishListModels.clear();
                TestModel.savetoshared(wishListModels, "wish", LanguageAndCountry.this);
            }


            ArrayList<ChartModel> chartModels = TestModel.getfromcardmodel("card", LanguageAndCountry.this);
            if (chartModels != null) {
                chartModels.clear();
                TestModel.savetosharedcardmodel(chartModels, "card", LanguageAndCountry.this);
            }

        }
        if(TestModel.getLanguages()!=null)
        for(int pos=0;pos< TestModel.getLanguages().size();pos++)
        {
            LanguageClass languageClass=new LanguageClass(TestModel.getLanguages().get(pos).getLangname(),
                    TestModel.getLanguages().get(pos).getLanguage(),"",TestModel.getLanguages().get(pos).getIso_code()
                    ,TestModel.getLanguages().get(pos).getImg());
            items.add(languageClass);
        }

        final String[] items1 = getResources().getStringArray(R.array.Languages);
//        final ArrayList<Object> items = new ArrayList<Object>(Arrays.asList(items1));
        final ArrayList<Object> image_item = new ArrayList<>();
        image_item.add(R.mipmap.english_icon);
        image_item.add(R.mipmap.arabic_icon);

        editor = preferences.edit();
        stringLanguage= Locale.getDefault().getDisplayLanguage().toString();


        x=preferences.getInt("Lang",1);
        if(stringLanguage.equals("English"))
        {
            language = preferences.getInt("Lang",1);
            countryname=preferences.getString("countryname","egypt");
            shippingcountryname=preferences.getString("Shippingcountryname","egypt");
            lang=0;
            old_country=preferences.getInt("Country",1);
            old_currency= preferences.getString("currency","1" );
            old_countryname= preferences.getString("countryname","egypt");
            old_currencyname= preferences.getString("currencyname","EGP");
            old_conversion=preferences.getInt("currencyconversion",1);

        }
        else {
            language = preferences.getInt("Lang",2);
            countryname=preferences.getString("countryname","ظ…طµط±");
            shippingcountryname=preferences.getString("Shippingcountryname","ظ…طµط±");
            old_country=preferences.getInt("Country",1);
            old_currency= preferences.getString("currency","1" );
            old_countryname= preferences.getString("countryname","ظ…طµط±");
            old_currencyname= preferences.getString("currencyname","EGP");
            old_conversion=preferences.getInt("currencyconversion",1);

            lang=1;

        }
        newcountry=old_country;
        Log.d("checkdatalang1",countryname+"\n"+shippingcountryname+"\n"+language);
        connection = new Connection(LanguageAndCountry.this, "/GetAllCurrencies/"+language, "Get");
        connection.reset();
        connection.Connect(new Connection.Result() {
            @Override
            public void data(String str) throws JSONException {
                jsonObject = new JSONObject(str);
                jsonArray = jsonObject.getJSONArray("1-currencies");
                for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                    currency_id=jsonObject1.getString("id_currency");
                    currency_name=jsonObject1.getString("sign");
                    conversion_rate=jsonObject1.getInt("conversion_rate");
                    CurrencyModel currencyModel=new CurrencyModel(currency_name,currency_id,conversion_rate);
                    currencyModels.add(currencyModel);
                }



                connection = new Connection(LanguageAndCountry.this, "/GetAllCounteries/"+language, "Get");
                connection.reset();
                connection.Connect(new Connection.Result() {
                    @Override
                    public void data(String str) throws JSONException {
                        jsonObject = new JSONObject(str);
                        jsonArray = jsonObject.getJSONArray("1-countries");
                        for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                            images.add(jsonObject1.getString("countryflagurl"));
                            Names.add(jsonObject1.getString("countrylatname"));
                            Log.d("checkname",jsonObject1.getString("countrylatname")+"\n"+countryname);
                            countryID.add(jsonObject1.getInt("countrysourcefilterid"));
                            currency.add(jsonObject1.getString("id_currency"));

                        }

                        Log.d("checkcurrencysize",currencyModels.size()+"");

                        Profile_List_item_adapterlanguage adapter = new Profile_List_item_adapterlanguage(items, LanguageAndCountry.this, image_item, language);
                        listv.setAdapter(adapter);
                        listv2.setAdapter(new Profile_List_item_adapter(LanguageAndCountry.this, Names, images, lang,old_country,countryID));
                        listv3.setAdapter(new Profile_List_item_adapter(LanguageAndCountry.this, Names, images, lang,old_country));
                        listv.setExpanded(true);
                        listv2.setExpanded(true);
                        listv3.setExpanded(true);
                        Log.d("checkdatalang",countryname+"\n"+shippingcountryname);
                        Log.d("checkchildcount",listv2.getChildCount()+"");
//                listv2.getChildAt(getcountryposition(countryname,images)).findViewById(R.id.selected).setVisibility(View.VISIBLE);
//                listv3.getChildAt(getcountryposition(shippingcountryname,images)).findViewById(R.id.selected).setVisibility(View.VISIBLE);
//
//                Toast.makeText(LanguageAndCountry.this, ""+preferences.getInt("Lang", 1), Toast.LENGTH_SHORT).show();
                        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                listv.getChildAt(i).findViewById(R.id.selected).setVisibility(View.VISIBLE);
                                for (int j = 0; j < items.size(); j++)
                                { if(j!=i)
                                {listv.getChildAt(j).findViewById(R.id.selected).setVisibility(View.GONE);}


                                    ChangeLanguage(TestModel.getLanguages().get(i).getIso_code());}
                                x=TestModel.getLanguages().get(i).getLanguage();

//                                switch (i) {
//                                    case 0:
//                                        ChangeLanguage("en");
//                                        editor.putInt("Lang", 1);
//
//
//                                        break;
//                                    case 1:
//                                        ChangeLanguage("ar");
//                                        editor.putInt("Lang", 2);
//
//                                        break;
//                                }
                                editor.apply();
                                TextView textView = (TextView) toolbar.findViewById(R.id.apply);
                                textView.setText(R.string.apply);
                                TextView textView1 = (TextView) findViewById(R.id.select_lang);
                                textView1.setText(R.string.lang);
                                TextView textView2 = (TextView) findViewById(R.id.select_country);
                                textView2.setText(R.string.country);
                                TextView textView3 = (TextView) findViewById(R.id.select_shipping_country);
                                textView3.setText(R.string.shippingcountry);
                            }
                        });
                        listv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                for (int j = 0; j < images.size(); j++) {
                                    listv2.getChildAt(j).findViewById(R.id.selected).setVisibility(View.GONE);
                                    //listv3.getChildAt(j).findViewById(R.id.selected).setVisibility(View.GONE);
                                }
                                listv2.getChildAt(i).findViewById(R.id.selected).setVisibility(View.VISIBLE);
                                Log.d("checkcountryid",countryID.get(i)+"\n"+currency.get(i));
//                        TestModel.setLanguageHelpers(new LanguageHelper(currencyModels.get(getcurrencyposition(currencyModels,currency.get(i))).getConversion_rate(),
//                                countryID.get(i),Names.get(i)+"",currencyModels.get(getcurrencyposition(currencyModels,currency.get(i))).getCurrency_name(),
//                                currency.get(i)));
                                editor.putInt("Country",countryID.get(i));
                                editor.putString("currency", currency.get(i));
                                Log.d("checknameincountry",Names.get(i)+"");
                                editor.putString("countryname",Names.get(i)+"");
                                //editor.putString("iso_code",iso_code+"");
                                editor.putString("currencyname",currencyModels.get(getcurrencyposition(currencyModels,currency.get(i))).getCurrency_name());
                                editor.putInt("currencyconversion",currencyModels.get(getcurrencyposition(currencyModels,currency.get(i))).getConversion_rate());

                                newcountry=countryID.get(i);
                                countryname=Names.get(i)+"";
                                editor.apply();
                            }
                        });
                        listv3.setVisibility(View.GONE);
//                listv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        for (int j = 0; j < images.size(); j++) {
//                            // listv2.getChildAt(j).findViewById(R.id.selected).setVisibility(View.GONE);
//                            listv3.getChildAt(j).findViewById(R.id.selected).setVisibility(View.GONE);
//                        }
//                        listv3.getChildAt(i).findViewById(R.id.selected).setVisibility(View.VISIBLE);
//                        editor.putInt("ShippingCountry", countryID.get(i));
//                        editor.putString("Shippingcountryname",Names.get(i)+"");
//                        editor.putString("currency", currency.get(i));
//                        editor.apply();
//                    }
//                });
                        //adapter.getView().findViewById(R.id.selected).setVisibility(View.VISIBLE);
                        //  getViewByPosition(0, listv)
                        //listv.getItemAtPosition(1).findViewById(R.id.selected).setVisibility(View.VISIBLE);
//                listv2.getChildAt(0).findViewById(R.id.selected).setVisibility(View.VISIBLE);
//                listv3.getChildAt(0).findViewById(R.id.selected).setVisibility(View.VISIBLE);
                        toolbar.findViewById(R.id.toolbar_apply).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(old_country!=newcountry) {
                                    editor.putString("customer_id", "");
                                    editor.putString("name", "");
                                    editor.putString("email", "");
                                    editor.commit();


                                    editor.commit();
                                    ArrayList<WishListModel> wishListModels = TestModel.getfromshared("wish", LanguageAndCountry.this);
                                    if (wishListModels != null) {
                                        wishListModels.clear();
                                        TestModel.savetoshared(wishListModels, "wish", LanguageAndCountry.this);
                                    }


                                    ArrayList<ChartModel> chartModels = TestModel.getfromcardmodel("card", LanguageAndCountry.this);
                                    if (chartModels != null) {
                                        chartModels.clear();
                                        TestModel.savetosharedcardmodel(chartModels, "card", LanguageAndCountry.this);
                                    }
                                }
                                try{
                                    Bundle bundle = new Bundle();
                                    String lang_=TestModel.getlanguagename(x, TestModel.getLanguages());

                                    bundle.putString("language_name", lang_);
                                    bundle.putString("country_name",countryname+"");
                                    Log.d("check_lag_cont",countryname+"\n"+lang_);
                                    mFirebaseAnalytics.logEvent("most_country", bundle);
                                    mFirebaseAnalytics.setUserProperty("language_name", lang_+ "");
                                    mFirebaseAnalytics.setUserProperty("country_name", countryname + "");


                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                editor.putInt("Lang", x);
                                editor.putInt("Test", 1);
                                editor.putString("langugaename", TestModel.getlanguagename(x, TestModel.getLanguages()));
                                 editor.commit();
                                Intent i = new Intent(LanguageAndCountry.this, SplashActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                                finish();
                            }
                        });
                    }
                });
            }});
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("checkdatalang2",countryname+"\n"+shippingcountryname+"n"+language);

        editor.putInt("Lang",language);
        if(language==1)
        {
            ChangeLanguage("en");
            editor.putInt("Country",old_country);
            editor.putString("currency", old_currency);
            editor.putString("countryname",old_countryname);
            editor.putString("currencyname",old_currencyname);
            editor.putInt("currencyconversion",old_conversion);
            editor.apply();
        }
        else {
            ChangeLanguage("ar");
            editor.putInt("Country",old_country);
            editor.putString("currency", old_currency);
            editor.putString("countryname",old_countryname);
            editor.putString("currencyname",old_currencyname);
            editor.putInt("currencyconversion",old_conversion);
        }
        editor.commit();
        int test = preferences.getInt("Test", -1);
        if(test==-1){
            finish();
        }

        overridePendingTransition(R.anim.no_thing, R.anim.slide_out_bottom);
    }

    ///////////////////////////////////////change Language///////////////////////////////
    public void ChangeLanguage(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);
        Resources resources = context.getResources();
    }

    public View getViewByPosition(int pos, ExpandableHeightListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = listView.getChildCount() + firstListItemPosition - 1;
        if (pos < firstListItemPosition || pos > lastListItemPosition)
            return listView.getAdapter().getView(pos, null, listView);
        else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
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
    public int getconversion_rate(ArrayList<CurrencyModel>currencyModels1,String currency_id)
    {
        for(int position=0;position<currencyModels1.size();position++)
        {
            if(currency_id.equals(currencyModels1.get(position).getCurrency_id()))
            {
                return conversion_rate;
            }
        }
        return 0;
    }
    public int getcurrencyposition(ArrayList<CurrencyModel>currencyModels1,String currency_id)
    {
        for(int position=0;position<currencyModels1.size();position++)
        {
            if(currency_id.equals(currencyModels1.get(position).getCurrency_id()))
            {
                return position;
            }
        }
        return 0;
    }
    public String getcurrency_name(ArrayList<CurrencyModel>currencyModels1,String currency_name)
    {
        for(int position=0;position<currencyModels1.size();position++)
        {
            if(currency_name.equals(currencyModels1.get(position).getCurrency_name()))
            {
                return currency_name;
            }
        }
        return "";
    }
    public boolean onTouchEvent(MotionEvent event)
    {

        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            System.out.println("TOuch outside the dialog ******************** ");
            dialog.dismiss();
        }
        return false;
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                dialog = new Dialog(this, R.style.CircularProgress);
                dialog = new Dialog(this, R.style.CircularProgress);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_loading_item);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                LinearLayout relativeLayout;
                relativeLayout=(LinearLayout) dialog.findViewById(R.id.rel_loder);
                relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent event) {
                        switch(event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                // The user just touched the screen
                                //   Toast.makeText(SplashActivity.this,"ended", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                                break;
                            case MotionEvent.ACTION_UP:
                                // The touch just ended
                                // Toast.makeText(SplashActivity.this,"ended", Toast.LENGTH_SHORT).show();

                                break;
                        }

                        return false;
                    }
                });




                return dialog;
            default:
                return null;
        }
    }

}



