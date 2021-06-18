package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Adapters.Profile_List_item_adapter;
import solversteam.aveway.utiltes.CircleTransform;
import solverteam.aveway.R;


public class Profile extends AppCompatActivity {
    @BindView(R.id.include)
    Toolbar toolbar;
    @BindView(R.id.imageView4)
    ImageView edit;
    @BindView(R.id.imageView5)
    ImageView logoimag;
    @BindView(R.id.profile_listview)
    ExpandableHeightListView listv;

    public static final String MyPREFERENCES = "MyPrefs";
    private TextView name_textview,email_textview,terms_condtion;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int where_i_come;
    private Intent intent;
    private Dialog dialog;
    private TextView return_,privacy;
    private String firstcolor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences("LanguageAndCountry",MODE_PRIVATE);
        firstcolor=sharedPreferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.account);
        try{
            where_i_come=getIntent().getExtras().getInt("where");
            Log.d("checkwhere",where_i_come+"");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

       // sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        name_textview=(TextView)findViewById(R.id.profile_name_textview);
        email_textview=(TextView)findViewById(R.id.profile_email_textview);
        terms_condtion=(TextView)findViewById(R.id.profile_Terms_text);
        return_=(TextView)findViewById(R.id.profile_returnandreplac_text);
        privacy=(TextView)findViewById(R.id.profile_Privacy_text);


        terms_condtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(Profile.this, Contact_us.class);
                intent.putExtra("cont","termsandconditions");
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);
            }
        });
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(Profile.this, Contact_us.class);
                intent.putExtra("cont","returnandreplacementpolicy");
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(Profile.this, Contact_us.class);
                intent.putExtra("cont","privacypolicy");
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);
            }
        });


        editor=sharedPreferences.edit();
        String logo=getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("logo","");

        String defult=getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("defaultimage",R.drawable.ave+"");
        if (!logo.isEmpty())
            Picasso.with(this)
                    .load(logo)
                    .placeholder(R.drawable.defaultimage)
                     .error(R.drawable.ave)
                    .into(logoimag);
        else
            Picasso.with(this)
                    .load(defult)
                    .placeholder(R.drawable.defaultimage)
                     .error(R.drawable.ave)
                    .into(logoimag);
        try{if(!getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("name","").isEmpty())
        { String name=getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("name","");
            String lastname=getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("lastname","");
            String emil=getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("email","");

            name_textview.setText(name+"\t"+lastname);
            email_textview.setText(emil);
        }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        if(!getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("name","").isEmpty())
        {
            edit.setVisibility(View.VISIBLE);
            toolbar.findViewById(R.id.toolbar_logout).setVisibility(View.VISIBLE);
            toolbar.findViewById(R.id.toolbar_logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Profile.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    editor.putString("customer_id","");
                    editor.putString("name","");
                    editor.putString("email","");
                    editor.commit();
                    Toast.makeText(Profile.this,"Successfully loggedout",Toast.LENGTH_SHORT).show();
                    Profile.this.startActivity(intent);
                    Profile.this.overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);

                }
            });

        }
        else{

            edit.setVisibility(View.GONE);
        }


        String[] items1 = getResources().getStringArray(R.array.account);
         ArrayList<String> items = new ArrayList();
        items.add(getResources().getString(R.string.myorder));
        items.add(getResources().getString(R.string.myWishList));
        items.add(getResources().getString(R.string.myaddress));
        items.add(getResources().getString(R.string.country));
        items.add(getResources().getString(R.string.contact));
        items.add(getResources().getString(R.string.call));
        items.add(getResources().getString(R.string.share));
        items.add(getResources().getString(R.string.feed));
        items.add(getResources().getString(R.string.rate));


        ArrayList<Object> image_item = new ArrayList<>();
        image_item.add(R.mipmap.orders);
        image_item.add(R.drawable.ic_favorite_black_24dp);
        image_item.add(R.drawable.address);
        image_item.add(R.drawable.eart);
        image_item.add(R.mipmap.contact);
        image_item.add(R.mipmap.agenda);
        image_item.add(R.mipmap.share);
        image_item.add(R.mipmap.feedback);
        image_item.add(R.mipmap.rate);
        listv.setAdapter(new Profile_List_item_adapter(items, this, image_item , -5));
        listv.setExpanded(true);

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        if(!getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("name","").isEmpty())
                        {
                         intent = new Intent(Profile.this, Order_show.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);}
                        else {
                            Toast.makeText(getApplicationContext(), R.string.msglogin, Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(Profile.this, Login.class);
                            intent.putExtra("where",where_i_come);
                            intent.putExtra("from","order");
                            startActivity(intent);
                            overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                        }
                        break;

                    case 1:
                         intent = new Intent(Profile.this, WishListActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);
                        break;
                    case 2:
                        if(!getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("name","").isEmpty())
                        {
                         intent = new Intent(Profile.this, Add_Address.class);
                        intent.putExtra("frag","frag");
                        startActivity(intent);

                        overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);}
                        else {
                            Toast.makeText(getApplicationContext(), R.string.msglogin, Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(Profile.this, Login.class);
                            intent.putExtra("where",where_i_come);
                            intent.putExtra("from","address");
                           startActivity(intent);
                            overridePendingTransition(R.anim.no_thing, R.anim.slide_out_left);
                        }
                        break;
                    case 3:
                         intent= new Intent(Profile.this, LanguageAndCountry.class);
                        startActivity(intent);

                        overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);
                        break;
                    case 4:
                        intent= new Intent(Profile.this, Contact_us.class);
                        intent.putExtra("cont","contactus");
                        startActivity(intent);

                        overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);
                        break;
                    case 5:
                        intent= new Intent(Profile.this, Contact_us.class);
                        intent.putExtra("cont","aboutus");
                        startActivity(intent);

                        overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);
                        break;
                    case 6:
                        try {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/plain");
                            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                            String sAux = "\nLet me recommend you this application\n\n";
                            sAux = sAux + "http://play.google.com/store/apps/details?id=" + Profile.this.getPackageName() ;
                            i.putExtra(Intent.EXTRA_TEXT, sAux);
                            startActivity(Intent.createChooser(i, "choose one"));
                        } catch(Exception e) {
                            //e.toString();
                        }

                        overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);
                        break;
                    case 8:
                        Uri uri = Uri.parse("market://details?id=" + Profile.this.getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + Profile.this.getPackageName())));
                        }

                        overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);
                        break;
                }
            }
        });
        updateView(3);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this , EditProfile.class);
                intent.putExtra("where",where_i_come);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_top, R.anim.no_thing);
            }
        });
    }

    private void updateView(int index) {
        View v = listv.getChildAt(index -
                listv.getFirstVisiblePosition());

        if (v == null)
            return;

        ImageView imageView = (ImageView) v.findViewById(R.id.profile_list_item_image);
        String url = sharedPreferences.getString("url", null);
        try {
            Picasso.with(this).load(url).transform(new CircleTransform()).placeholder(R.drawable.defaultimage).into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

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
         
//            if(where_i_come==1)
//            {
//                intent=new Intent(this,MainActivity.class);}
//            else if(where_i_come==2) {
//                intent=new Intent(this,ItemSelectedShowActivity.class);
//            }
//            else {
//                intent=new Intent(this,MainActivity.class);
//            }
        finish();
       // startActivity(intent);
        overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                
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