package solversteam.aveway.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Connection.ConnectionDetector;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.utiltes.Fonts;
import solverteam.aveway.R;

/**
 * Created by mosta on 2/9/2017.
 */
public class SignUp extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ConstraintLayout sign;
    private Intent intent;
    private DatePicker datePicker;
    private Dialog dialog;
    private Calendar calendar;
    private int year, month, day,language,country;
    private EditText birth_date_edittext,email_edittext,mobile_edittext,password_edittext,firstname_edittext
            ,lastname_edittext,confirm_editext;
    private CheckBox male_checkbox ,female_checkbox;
    private Button sign_up_button;
    private String confpassword,lastName,firstName,email,gender,birthDate,mobile,password,customer_id,firstcolor;
    private Connection connection;
    private ConnectionDetector connectionDetector;
    private static final String Password_PATTERN ="^[a-zA-Z0-9 \\u0600-\\u06FF][0-9a-zA-Z0-9 \\u0600-\\u06FF/s ,_-]*$";
    private static final String Name_PATTERN ="^[a-zA-Z\\u0600-\\u06FF][a-zA-Z\\u0600-\\u06FF/s ,_-]*$";
    private static final String EMAIL_PATTERN ="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+" +
            "@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\." +
            "[0-9]{1,3}\\.[0-9]{1,3}\\])" +
            "|(([a-zA-Z\\-0-9]+\\.)" +
            "+[a-zA-Z]{2,}))$";
    private static final String MOBILE_PATTERN="[0-9]+";
    private static final String DATE_PATTERN="^\\d{4}-\\d{2}-\\d{2}$\n";
    private Boolean valid=false;
    private TextView gendererror;
String from;
    private static Matcher matcher;
    private static Pattern pattern;
    private ArrayList<Integer> views;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        preferences = getApplicationContext().getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);

        try{
            from=getIntent().getExtras().getString("from");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            from="any where";
        }
        views=new ArrayList<>();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Fonts fonts=new Fonts(this);

        birth_date_edittext=(EditText)findViewById(R.id.SignUp_birthdate_edittext) ;
        email_edittext=(EditText)findViewById(R.id.SignUp_email_edittext) ;
        mobile_edittext=(EditText)findViewById(R.id.SignUp_mobile_edittext) ;
        password_edittext=(EditText)findViewById(R.id.SignUp_password_edittext) ;
        confirm_editext=(EditText)findViewById(R.id.SignUp_confirm_edittext);
        firstname_edittext=(EditText)findViewById(R.id.SignUp_firstname_editText);
        lastname_edittext=(EditText)findViewById(R.id.SignUp_lastname_editText2);
        male_checkbox=(CheckBox)findViewById(R.id.SignUp_male_checkbox) ;
        female_checkbox=(CheckBox)findViewById(R.id.SignUp_female_checkbox) ;


        sign_up_button=(Button)findViewById(R.id.SignUp_register_button);
        firstcolor=preferences.getString("firstmenucolour","#000000");
        sign_up_button.setBackgroundColor(Color.parseColor(firstcolor));

        fonts.setView(birth_date_edittext);
        fonts.setView(email_edittext);
        fonts.setView(mobile_edittext);
        fonts.setView(password_edittext);
        fonts.setView(confirm_editext);
        fonts.setView(firstname_edittext);
        fonts.setView(lastname_edittext);
        fonts.setView(sign_up_button);
        fonts.setView(female_checkbox);
        fonts.setView(male_checkbox);



        views.add(R.id.SignUp_birthdate_edittext);
        views.add(R.id.SignUp_email_edittext);
        views.add(R.id.SignUp_mobile_edittext);
        views.add(R.id.SignUp_password_edittext);
        views.add(R.id.SignUp_confirm_edittext);
        views.add(R.id.SignUp_firstname_editText);
        views.add(R.id.SignUp_lastname_editText2);




        gendererror=(TextView)findViewById(R.id.SignUp_wronggender_textview);


        male_checkbox.setOnCheckedChangeListener(this);
        female_checkbox.setOnCheckedChangeListener(this);


        birth_date_edittext.setOnClickListener(this);

       // showDate(year, month+1, day);
        connectionDetector=new ConnectionDetector(this);
        //check_data();
        


        setLocale(Locale.getDefault().toString());


        sign_up_button.setOnClickListener(this);
        editor=preferences.edit();
        country=preferences.getInt("Country",1);
        language=preferences.getInt("Lang",9);
        String logo =preferences.getString("logo","");
        String defult =preferences.getString("defaultimage","");
        ImageView imageView=(ImageView)findViewById(R.id.imageView2);
        if (!logo.isEmpty())
            Picasso.with(this)
                    .load(logo)
                    .placeholder(R.drawable.defaultimage)
                     .error(R.drawable.ave)
                    .into(imageView);
        else
            Picasso.with(this)
                    .load(defult)
                    .placeholder(R.drawable.defaultimage)
                     .error(R.drawable.ave)
                    .into(imageView);



    }

    public void setDate() {
        showDialog(999);

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
        // TODO Auto-generated method stub
//        if (id == 999) {
//            return new DatePickerDialog(this,
//                    myDateListener, year, month, day);
//        }
        switch (id) {
            case 1:
                dialog = new Dialog(this, R.style.CircularProgress);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_loading_item);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                LinearLayout relativeLayout=(LinearLayout) dialog.findViewById(R.id.rel_loder);
                relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent event) {
                        switch(event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                // The user just touched the screen

                                dialog.dismiss();
                                break;
                            case MotionEvent.ACTION_UP:
                                // The touch just ended

                                break;
                        }

                        return false;
                    }
                });

                return dialog;
            case 999:
                return new DatePickerDialog(this,
                        myDateListener, year, month, day);
            default:
                return null;
        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        birth_date_edittext.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }






        @Override
    public void onClick(View view) {
        if(view==birth_date_edittext)
        {
            setDate();
        }
        else if(view==sign_up_button)
        {
            try{ check_views(views);
                if(validate()
                        && !get_gender(male_checkbox.isChecked(),female_checkbox.isChecked()).equals("0")
                        )
            {
//                gendererror.setVisibility(View.GONE);
                password=password_edittext.getText().toString();
                email=email_edittext.getText().toString();
                mobile=mobile_edittext.getText().toString();
                birthDate=birth_date_edittext.getText().toString();
                gender=get_gender(male_checkbox.isChecked(),female_checkbox.isChecked());
                firstName=firstname_edittext.getText().toString();
                lastName=lastname_edittext.getText().toString();
                confpassword=confirm_editext.getText().toString();

                    if(connectionDetector.isConnectingToInternet())
                    {
                        connection=new Connection(this,"/SignUp","Post");
                        connection.reset();
                        connection.addParmmter("passwd",password);
                        connection.addParmmter("email",email);
                        connection.addParmmter("note",mobile);
                        connection.addParmmter("birthday",birthDate);
                        connection.addParmmter("id_gender",gender);
                        connection.addParmmter("firstname",firstName);
                        connection.addParmmter("lastname",lastName);
                        connection.addParmmter("id_shop",country+"");
                        connection.addParmmter("id_lang",language+"");
                        connection.Connect(new Connection.Result() {
                            @Override
                            public void data(String str) throws JSONException {

                                Log.d("checkregister",str+"\n"+language+"\n"+country);
                                try {


                                    JSONObject jsonObject=new JSONObject(str);
                                    JSONArray jsonArray=jsonObject.getJSONArray("userdata");
                                    customer_id=jsonArray.getJSONObject(0).getString("id_customer");
                                    editor.putString("customer_id",customer_id);
                                    editor.putString("name",jsonArray.getJSONObject(0).getString("firstname"));
                                    editor.putString("email",jsonArray.getJSONObject(0).getString("email"));
                                    editor.putString("lastname",jsonArray.getJSONObject(0).getString("lastname"));
                                    editor.putString("mobile",jsonArray.getJSONObject(0).getString("note"));
                                    editor.commit();
                                    Log.d("checkcustomerid",customer_id);
//                                    Toast.makeText(SignUp.this,"Register Successfully",Toast.LENGTH_LONG).show();
                                    //lsa hn3ml checkout w nshof
                                    if(from.equals("account"))
                                    {
                                        intent=new Intent(SignUp.this,Profile.class);
                                        intent.putExtra("where",getIntent().getExtras().getInt("where"));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);



                                    }
                                    else if(from.equals("checkout"))
                                    {
                                        intent=new Intent(SignUp.this,ChartActivity.class);
                                        intent.putExtra("where",getIntent().getExtras().getInt("where"));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );

                                    }
                                    else {
                                        intent=new Intent(SignUp.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                    }
                                    finish();
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                    Toast.makeText(SignUp.this,str,Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                    else {
                        TestModel.connectionToast(this);
                    }


                }
            }

            catch (Exception e)
            {
                Toast.makeText(this,R.string.valid,Toast.LENGTH_LONG).show();

            }
        }

    }
    private String get_gender(Boolean male,Boolean female)
    {

        if(male)
        {
            return "1";
        }
        else if(female)
        {
            return "2";
        }
        else {
            return "0";
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton==male_checkbox)
        {
            if(b)
            {
                female_checkbox.setChecked(false);
            }

        }
        else if(compoundButton==female_checkbox){
            if(b)
            {
                male_checkbox.setChecked(false);
            }

        }
    }
    public void setLocale(String lang) {

     Locale   myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        onConfigurationChanged(conf);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        male_checkbox.setText(R.string.male);
        female_checkbox.setText(R.string.female);
        firstname_edittext.setHint(R.string.firstName);
        lastname_edittext.setHint(R.string.lastName);
        confirm_editext.setHint(R.string.confoirmpassword);

        email_edittext.setHint(R.string.email);
        password_edittext.setHint(R.string.password);
        mobile_edittext.setHint(R.string.mobile);
        birth_date_edittext.setHint(R.string.birthdate);
        sign_up_button.setHint(R.string.register);
        super.onConfigurationChanged(newConfig);
    }


    private boolean validate_information(String name,String kind) {
        if(kind.equals("name"))
        {pattern = Pattern.compile(Name_PATTERN);
            matcher = pattern.matcher(name);}
        else if(kind.equals("password"))
        {pattern = Pattern.compile(Password_PATTERN);
            matcher = pattern.matcher(name);
        }

        else if(kind.equals("email"))
        {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(name);
        }
        else if(kind.equals("mobile"))
        {
            pattern = Pattern.compile(MOBILE_PATTERN);
            matcher = pattern.matcher(name);
        }

        return matcher.matches();
    }

    private void check_views(ArrayList<Integer> views)
    {
        for (int viewcount=0;viewcount<views.size();viewcount++)
        {
            EditText editText=(EditText)findViewById(views.get(viewcount));

            String text=editText.getText().toString();
            if(text.length()==0)
            {
                editText.setError(getResources().getString(R.string.required));
            }
        }
        if(get_gender(male_checkbox.isChecked(),female_checkbox.isChecked()).equals("0"))
        {
            gendererror.setVisibility(View.VISIBLE);
        }
        else {
            gendererror.setVisibility(View.GONE);

        }



    }


    public boolean validate(){
        boolean valid = true;


        if(firstname_edittext.getText().toString().length()>=2&&validate_information(firstname_edittext.getText().toString(),"name"))
        {
            firstname_edittext.setError(null);
        }
        else {
            firstname_edittext.setError(getResources().getString(R.string.recfirstname));
            valid=false;
        }

        if(lastname_edittext.getText().toString().length()>=2&&validate_information(lastname_edittext.getText().toString(),"name"))
        {
            lastname_edittext.setError(null);


        }
        else {
            lastname_edittext.setError(getResources().getString(R.string.reclastname));
            valid=false;
        }
        if(password_edittext.getText().toString().length()>5&&validate_information(password_edittext.getText().toString(),"password"))
        {
            password_edittext.setError(null);


        }
        else {
            password_edittext.setError(getResources().getString(R.string.recpass));
            valid=false;
        }
        if(confirm_editext.getText().toString().length()>5&&confirm_editext.getText().toString().equals(password_edittext.getText().toString()))
        {
            confirm_editext.setError(null);

        }
        else {

            confirm_editext.setError(getResources().getString(R.string.recpassconfirm));
            valid=false;
        }

        if(email_edittext.getText().toString().length()>6&&validate_information(email_edittext.getText().toString(),"email"))
        {
            email_edittext.setError(null);

        }
        else {

            email_edittext.setError(getResources().getString(R.string.recemail));
            valid=false;
        }

        if(mobile_edittext.getText().toString().length()>6&&validate_information(mobile_edittext.getText().toString(),"mobile"))
        {
            mobile_edittext.setError(null);

        }
        else {
            // mobile_edittext.setError("Mobile Not Valid (start with +)");
            mobile_edittext.setError(getResources().getString(R.string.recmob));
            valid=false;
        }


        String [] select=birth_date_edittext.getText().toString().split("-");
        String year=select[0];
        if(birth_date_edittext.getText().toString().length()>0&&(Integer.parseInt(year)<1999))
        {

            birth_date_edittext.setError(null);

        }
        else {

            birth_date_edittext.setError(getResources().getString(R.string.recbirth));
            valid=false;
        }


        return valid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
    }


}
