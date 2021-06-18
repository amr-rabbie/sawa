package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Connection.ConnectionDetector;
import solversteam.aveway.Models.LanguageClass;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.utiltes.GetScreenSize;
import solverteam.aveway.R;

public class SplashActivity extends AppCompatActivity {
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private int country =1, language,default_lang,conversion_rate,languageid,test,current_version_number;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor,editor2;
    private String stringLanguage,default_image,logo,splash,currency_name,languagename,lang_name,stringlangugaename,iso_code,current_version_code,playstore_version_code,show,playstore_url;
    private ArrayList<String>homecatids,brands_name_array,time,shippingat;
    private ArrayList<LanguageClass>langaugearray;
    private String projectname;
    private Dialog dialog;
    private LinearLayout relativeLayout;
    private String img;
    private String first_color,second_color,active;
    private boolean start=true;
    private String[] recource_version;
    private String resource_content;
    private Intent intent;
    private SharedPreferences sharedPreferences2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ConnectionDetector connectionDetector = new ConnectionDetector(this);

        sharedPreferences = getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        get_cureent_version();

        language = sharedPreferences.getInt("Lang", 1);
        currency_name = sharedPreferences.getString("currencyname", "EGP");
        iso_code = sharedPreferences.getString("iso_code", "en");
        conversion_rate = sharedPreferences.getInt("currencyconversion", 1);
        country = sharedPreferences.getInt("Country", 1);
        test = sharedPreferences.getInt("Test", -1);//if first time  open app
        stringlangugaename = sharedPreferences.getString("langugaename", "sara");
        Log.d("checklanguagestring3",stringlangugaename);


        homecatids=new ArrayList<>();
        brands_name_array=new ArrayList<>();
        time=new ArrayList<>();
        shippingat=new ArrayList<>();
        langaugearray=new ArrayList<>();
        Log.d("checklang1",Locale.getDefault().getDisplayLanguage().toString());
        stringLanguage=Locale.getDefault().getDisplayLanguage().toString();
        Log.d("iso", iso_code + "");
        Locale locale = new Locale(iso_code);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        Connection connection5 = new Connection(this, "/GetAllLanguages", "Get" );
        connection5.reset();
        connection5.Connect(new Connection.Result() {
            @Override
            public void data(String str) throws JSONException {
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    jsonArray = jsonObject.getJSONArray("1-languages");
                    Log.d("checkjson12222", jsonArray.length() + "");
                    for (int position = 0; position < jsonArray.length(); position++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(position);
                        languageid = jsonObject1.getInt("id_lang");
                        languagename = jsonObject1.getString("name");
                        lang_name = jsonObject1.getString("language_name");
                       iso_code = jsonObject1.getString("iso_code");
                        img = jsonObject1.getString("language_logo");

//                          languagename="English";

                        Log.d("checkjson12", jsonObject1.getString("name") + "");
                        LanguageClass languageClass = new LanguageClass(languagename, languageid, lang_name,iso_code,img);
                        langaugearray.add(languageClass);
                        Log.d("checklanguagesdata", languageid + "\n+" + languagename);
                    }
                    Log.d("checklanguagesizearray", langaugearray.size() + "");
                    TestModel.setLanguages(langaugearray);
                    sharedPreferences = getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);

                    if(!stringlangugaename.equals("sara"))
                    {
                        stringLanguage=stringlangugaename;
                        Log.d("checklanguagestring1",stringLanguage);
                    }
                    Log.d("checklanguagestring2",stringLanguage);

                    for(int i=0; i<langaugearray.size();i++){

                        if(stringLanguage.equals(langaugearray.get(i).getLang_name()))
                        {
                            iso_code=langaugearray.get(i).getIso_code();
                            language=langaugearray.get(i).getLanguage();
                            Log.d("checkisoandlang1",iso_code+"\n"+language);


                        }
                    }
                    Log.d("checksizeinlanguage", TestModel.getLanguages().size() + "");
                    if (TestModel.getlanguagename(language, TestModel.getLanguages()).isEmpty()) {
                        language = getlangid(stringLanguage, TestModel.getLanguages());
                        iso_code=getiso(stringLanguage, TestModel.getLanguages());
                        Log.d("iso2542", iso_code + "");

                    } else {
                        language = getlangid(TestModel.getlanguagename(language, TestModel.getLanguages()), TestModel.getLanguages());
                        iso_code = getiso(TestModel.getlanguagename(language, TestModel.getLanguages()), TestModel.getLanguages());
                        Log.d("iso2542333", iso_code + "");
                    }

                    Log.d("checkisoandlang2",iso_code+"\n"+language);

                    // Log.d("iso2542", iso_code + "");
                    Locale locale = new Locale(iso_code);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


                    country = sharedPreferences.getInt("Country", 1);
                    Log.d("checkshared", language + "\n" + "country:" + country);
                    editor = sharedPreferences.edit();
                    editor.putInt("Country", country);
                    editor.putInt("Lang", language);
                    editor.putString("currencyname", currency_name);
                    editor.putString("iso_code", iso_code);
                    editor.putInt("currencyconversion", conversion_rate);
                    editor.commit();
                    if(test==-1)
                    {
                        country=-1;
                        language=-1;
                    }


                    final String lo=country+"/"+"'logo'"+","+"'splashscreen'"+","+"'defaultimage'"+","+"'firstmenucolour'"+","+"'secondmenucolour',"+
                            "'versioncode',"+ "'storeurl'"+","+"'deliverytime'"+","+"'startshippingat'"+"/"+language;


                    Connection connection = new Connection(SplashActivity.this, "/GetAppResource/"+lo, "Get");
                    connection.reset();
                    connection.Connect(new Connection.Result() {
                        @Override
                        public void data(String str) throws JSONException {
                            JSONObject jsonObject = new JSONObject(str);
                            jsonArray = jsonObject.getJSONArray("1-appresources");
                            if(jsonArray.length()>0)
                            {GetScreenSize getScreenSize = new GetScreenSize(SplashActivity.this);
                                try
                                {
                                    for(int pos=0;pos<jsonArray.length();pos++)
                                    {
                                        String resourcetype=jsonArray.getJSONObject(pos).getString("resourcetype");
                                        if(resourcetype.equals("logo"))
                                        {
                                            logo=jsonArray.getJSONObject(pos).getString("resourceurl");
                                            projectname=jsonArray.getJSONObject(pos).getString("resourcename");
                                            editor.putString("projectname",projectname);
                                            editor.putString("logo",logo);
                                        }
                                        else if(resourcetype.equals("defaultimage"))
                                        {
                                            default_image=jsonArray.getJSONObject(pos).getString("resourceurl");
                                            editor.putString("defaultimage",default_image);

                                        }
                                        else if(resourcetype.equals("firstmenucolour"))
                                        {
                                            first_color=jsonArray.getJSONObject(pos).getString("resource_content");
                                            editor.putString("firstmenucolour",first_color);

                                            Log.d("colors",first_color);
                                        }
                                        else if(resourcetype.equals("secondmenucolour"))
                                        {
                                            second_color=jsonArray.getJSONObject(pos).getString("resource_content");
                                            editor.putString("secondmenucolour",second_color);
                                            Log.d("colors",second_color);
                                        }
                                        else if(resourcetype.equals("deliverytime"))
                                        {

                                            time.add(jsonArray.getJSONObject(pos).getString("resourceurl"));
                                            TestModel.setTime(time);


                                        }
                                        else if(resourcetype.equals("startshippingat"))
                                        {

                                            shippingat.add(jsonArray.getJSONObject(pos).getString("resource_content"));
                                            TestModel.setStartshippingat(shippingat);

                                        }

                                        else if(resourcetype.equals("splashscreen"))
                                        {    splash=jsonArray.getJSONObject(pos).getString("resourceurl");

                                            // String defult=sharedPreferences.getString("defaultimage",R.drawable.defaultimage+"");
                                            Picasso.with(SplashActivity.this).load(splash)
                                                    .placeholder(R.drawable.splach)
                                                    .error(R.drawable.sawasplashscreen)
                                                    .fit().into((ImageView) findViewById(R.id.splash));
                                        }
                                        else if(resourcetype.equals("versioncode"))
                                        {
                                            recource_version = jsonArray.getJSONObject(pos).getString("resourceurl").split("-");
                                            playstore_version_code = recource_version[0];
                                            active = recource_version[1];
                                            resource_content=jsonArray.getJSONObject(pos).getString("resource_content");
                                            Log.d("checkactive_version",active);


                                        }
                                        else if(resourcetype.equals("storeurl"))
                                        {
                                            playstore_url = jsonArray.getJSONObject(pos).getString("resource_content");
                                        }
                                    }
                                    editor.commit();



                                }


                                catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                            if(test!=-1)
                            {
                                checkversion();
                            }
                            else {
                                open_application();
                            }

                        }

                    });



                    ////////////////////////////////////////////////////////////////

                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });






    }

    private void checkversion() {
        if (current_version_code.equals(playstore_version_code)) {
            start = true;
            Log.d("checkanafen","hna1");


        } else {
            if (active.equals("active")) {
                Log.d("checkanafen","hna2");
                if(show.equals("yes"))
                {   start=false;
                    new AlertDialog.Builder(SplashActivity.this)
                            .setIcon(R.drawable.appicon)
                            .setTitle(resource_content)
                            .setCancelable(false)
                            .setPositiveButton("تحديث الان", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //Stop the activity
                                    try{
                                        Uri uri = Uri.parse(playstore_url);
                                        intent = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(intent);  }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                    SplashActivity.this.finish();
                                }

                            })
                            .setNegativeButton("عدم الاظهر مرة اخرى", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            //Stop the activity
                                            editor.putString("show","no");
                                            editor.commit();
                                            open_application();



                                        }

                                    }
                            )
                            .show();
                }
                else {
                    start=true;
                }
            }
            else {
                Log.d("checkanafen","hna3");

                start = false;
                new AlertDialog.Builder(SplashActivity.this)
                        .setIcon(R.drawable.appicon)
                        .setTitle(resource_content)
                        .setCancelable(false)
                        .setPositiveButton("تحديث الان", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Stop the activity
                                try{
                                    Uri uri = Uri.parse(playstore_url);
                                    intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);  }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                SplashActivity.this.finish();
                            }

                        })
                        .setNegativeButton("خروج", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //Stop the activity

                                        SplashActivity.this.finish();
                                    }

                                }
                        )
                        .show();
            }
        }
        if (start)
        {
            open_application();
        }
    }

    private void get_cureent_version() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        current_version_number = pInfo.versionCode;
        current_version_code=current_version_number+"";
        show=sharedPreferences.getString("show","yes");
    }

    private void open_application()
    {
        Log.d("check where","hna");
        if(test!=-1){
            final ArrayList<String> Images = new ArrayList<>();
            final ArrayList<String> cat_id = new ArrayList<>();
            final ArrayList<String> ad_url = new ArrayList<>();
            final ArrayList<String> type = new ArrayList<>();
            Connection connection1 = new Connection(SplashActivity.this, "/GetAllapads/"+country+"/"+language, "Get");
            connection1.reset();
            connection1.Connect(new Connection.Result() {
                @Override
                public void data(String str) throws JSONException {
                    JSONObject jsonObject = new JSONObject(str);
                    TestModel.setImages(Images);
                    jsonArray = jsonObject.getJSONArray("1-apads");
                    if(jsonArray.length()>0)
                    {Log.d("checkapimages",jsonArray.length()+"");
                        for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                            Images.add(jsonObject1.getString("adimage"));
                            cat_id.add(jsonObject1.getString("id_category"));
                            ad_url.add(jsonObject1.getString("adurl"));
                            type.add(jsonObject1.getString("adtype"));
                            Log.d("Cat_oo",cat_id.get(Counter)+"");
                        }
                        Log.d("Cat_oo","xyz");
                        TestModel.setImages(Images);
                        TestModel.setApads(cat_id);
                        TestModel.setType(type);
                        TestModel.setAd_url(ad_url);

                    }
                }
            });
            /////////////////////////////////////////////////////////////////
            final ArrayList<String> cat1 = new ArrayList<>();
            final ArrayList<Integer> bandsIds = new ArrayList<>();
            Connection connection = new Connection(SplashActivity.this, "/GetAlCountryProductManufacturer/"+country+"/"+language+"/0/10", "Get");
            connection.reset();
            connection.Connect(new Connection.Result() {
                @Override
                public void data(String str) throws JSONException {
                    JSONObject jsonObject = new JSONObject(str);
                    jsonArray = jsonObject.getJSONArray("1-CountryProductManufacturer");
                    if(jsonArray.length()>0)
                    {for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                        cat1.add(jsonObject1.getString("imageurl"));
                        bandsIds.add(jsonObject1.getInt("id_manufacturer"));
                        brands_name_array.add(jsonObject1.getString("manufacturername"));
                    }
                        TestModel.setCat1(cat1);
                        TestModel.setBrandsIds(bandsIds);
                        TestModel.setBrands_name(brands_name_array);
                    }
                }
            });
            /////////////////////////////////////////////////////////////////
            final ArrayList<String> cat = new ArrayList<>();
            final ArrayList<String> catName = new ArrayList<>();
            final ArrayList<Integer> catIds = new ArrayList<>();
            Connection connection3 = new Connection(SplashActivity.this, "/GetAllHomeParentCategory/"+country+"/"+language, "Get");
            connection3.reset();
            connection3.Connect(new Connection.Result() {
                @Override
                public void data(String str) throws JSONException {
                    try {
                        JSONObject  jsonObject = new JSONObject(str);
                        jsonArray = jsonObject.getJSONArray("1-HomeCategory");
                        if (jsonArray.length() > 0) {
                            Log.d("checkjsonarray1", jsonArray.length() + "");
                            for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                                cat.add(jsonObject1.getString("category_image_url"));
                                catName.add(jsonObject1.getString("category_name"));
                                catIds.add(jsonObject1.getInt("id_category"));
                            }
                            TestModel.setCat(cat);
                            TestModel.setCatName(catName);
                            TestModel.setCatIds(catIds);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            Connection connection4 = new Connection(SplashActivity.this, "/GetAllHomeCategory/"+country+"/"+language+"/0/20", "Get");
            connection4.reset();
            connection4.Connect(new Connection.Result() {
                @Override
                public void data(String str) throws JSONException {
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        jsonArray = jsonObject.getJSONArray("1-HomeCategory");
                        if (jsonArray.length() > 0) {
                            Log.d("checkjsonarray2", jsonArray.length() + "");
                            for (int Counter = 0; Counter < jsonArray.length(); Counter++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(Counter);
                                homecatids.add(jsonObject1.getString("id_category"));
                            }
                            TestModel.setHomecatids(homecatids);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });}
        /////////////////////////////////////////////////////////////////
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (test==-1)//th
                {

                    Intent i=new Intent(SplashActivity.this,LanguageAndCountry.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(i);
                    return;

                }
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
                finish();
            }
        }, 5000);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                // View v=R.layout.layout_loading_item;
                dialog = new Dialog(this, R.style.CircularProgress);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_loading_item);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

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

    private String getiso(String name,ArrayList<LanguageClass> langaugearray)
    {
        for (int pos=0;pos<langaugearray.size();pos++)
        {
            if(name.equals(langaugearray.get(pos).getLang_name()))
            {
                return langaugearray.get(pos).getIso_code();
            }
        }
        return langaugearray.get(0).getLangname().equals("English")?langaugearray.get(0).getIso_code():langaugearray.get(1).getIso_code();
    }

    private int getlangid(String name,ArrayList<LanguageClass> langaugearray)
    {
        for (int pos=0;pos<langaugearray.size();pos++)
        {
            if(name.equals(langaugearray.get(pos).getLang_name()))
            {
                return langaugearray.get(pos).getLanguage();
            }
        }
        return langaugearray.get(0).getLangname().equals("English")?langaugearray.get(0).getLanguage():langaugearray.get(1).getLanguage();
    }
}
