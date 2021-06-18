package solversteam.aveway.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import solversteam.aveway.Connection.Connection;
import solversteam.aveway.Connection.ConnectionDetector;
import solversteam.aveway.Models.TestModel;
import solversteam.aveway.utiltes.Fonts;
import solverteam.aveway.R;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.include)
    Toolbar toolbar;
    private int year , month , day;
    private DatePicker datePicker;
    private Calendar calendar;
    private EditText firstname_edittext,lastname_edittext,mobile_editext,birthdate_edittext,password_editext,confpass_editext,email_edittext;
    private String firstname,lastname,mobile,birthdate,password,confirmpassword,customer_id,email,firstcolor,
            firstnameold,lastnameold,mobileold,birthdateold,oldemail;
    private Button update_button,cancel_button;
    private static final String Password_PATTERN ="^[a-zA-Z0-9 \\u0600-\\u06FF][0-9a-zA-Z0-9 \\u0600-\\u06FF/s ,_-]*$";
    private static final String Name_PATTERN ="^[a-zA-Z\\u0600-\\u06FF][a-zA-Z\\u0600-\\u06FF/s ,_-]*$";
    private static final String EMAIL_PATTERN ="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+" +
            "@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\." +
            "[0-9]{1,3}\\.[0-9]{1,3}\\])" +
            "|(([a-zA-Z\\-0-9]+\\.)" +
            "+[a-zA-Z]{2,}))$";
    private static Matcher matcher;
    private static Pattern pattern;
    private ArrayList<Integer> views;
    private SharedPreferences preferences;
    private ConnectionDetector connectionDetector;
    private int language,country;
    private SharedPreferences.Editor editor;
    private static final String MOBILE_PATTERN="[0-9]+";
    private Connection connection;
    private Intent intent;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        Fonts fonts =new Fonts(this);
        birthdate_edittext=(EditText)findViewById(R.id.EditProfile_birthdate_edittext) ;
        mobile_editext=(EditText)findViewById(R.id.EditProfile_mobile_edittext) ;
        password_editext=(EditText)findViewById(R.id.EditProfile_Password_edittext) ;
        confpass_editext=(EditText)findViewById(R.id.EditProfile_confirmpassword_edittext);
        firstname_edittext=(EditText)findViewById(R.id.EditProfile_First_Name_edittext);
        lastname_edittext=(EditText)findViewById(R.id.EditProfile_last_Name_edittext);
        email_edittext=(EditText)findViewById(R.id.EditProfile_email_edittext);
        fonts.setView(birthdate_edittext);
        fonts.setView(mobile_editext);
        fonts.setView(password_editext);
        fonts.setView(confpass_editext);
        fonts.setView(lastname_edittext);
        fonts.setView(firstname_edittext);
        fonts.setView(email_edittext);
        views=new ArrayList<>();
        views.add(R.id.EditProfile_birthdate_edittext);
        views.add(R.id.EditProfile_First_Name_edittext);
        views.add(R.id.EditProfile_last_Name_edittext);
        views.add(R.id.EditProfile_mobile_edittext);
        views.add(R.id.EditProfile_email_edittext);
        update_button=(Button)findViewById(R.id.EditProfile_Update_button);
        cancel_button=(Button)findViewById(R.id.EditProfile_Cancel_button);
        fonts.setView(update_button);
        fonts.setView(cancel_button);
        update_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        connectionDetector=new ConnectionDetector(this);
        preferences = getApplicationContext().getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        firstcolor=preferences.getString("firstmenucolour","#000000");
        toolbar.setBackgroundColor(Color.parseColor(firstcolor));
        update_button.setBackgroundColor(Color.parseColor(firstcolor));
        cancel_button.setBackgroundColor(Color.parseColor(firstcolor));
        editor=preferences.edit();

        country=preferences.getInt("Country",1);
        language=preferences.getInt("Lang",9);
        customer_id=preferences.getString("customer_id","");
        oldemail=getSharedPreferences("LanguageAndCountry",MODE_PRIVATE).getString("email","");
        connection=new Connection(this,"/GetAllCustomerData/"+customer_id+"/"+language,"Get");
        connection.reset();
        connection.Connect(new Connection.Result() {
            @Override
            public void data(String str) throws JSONException {
                try{
                    Log.d("checkgetdata",str);
                JSONObject jsonObject=new JSONObject(str);
                JSONArray jsonArray=jsonObject.getJSONArray("userdata");
                JSONObject jsonObject1=jsonArray.getJSONObject(0);
                    firstnameold=jsonObject1.getString("firstname");
                    lastnameold=jsonObject1.getString("lastname");
                    mobileold=jsonObject1.getString("note");
                    oldemail=jsonObject1.getString("email");
                    birthdateold=jsonObject1.getString("birthday");
                    String birth[]=birthdateold.split("T");
                    birthdateold=birth[0];
                    firstname_edittext.setText(firstnameold);
                    lastname_edittext.setText(lastnameold);
                    mobile_editext.setText(mobileold);
                    birthdate_edittext.setText(birthdateold);
                    email_edittext.setText(oldemail);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });







        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.abc_ic_clear_mtrl_alpha);
        getSupportActionBar().setTitle(R.string.editp);
        birthdate_edittext.setOnClickListener(this);
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
                        dateSetListener, year, month, day);
            default:
                return null;
        }
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            showDate(i,i1,i2);
        }
    };

    public void setDate(EditText date) {
        this.birthdate_edittext = date;
        showDialog(999);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent=new Intent(this,Profile.class);
        intent.putExtra("where",getIntent().getExtras().getInt("where"));
        this.finish();
        startActivity(intent);
        overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
     }
    private void showDate(int year , int month , int day){
        birthdate_edittext.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    @Override
    public void onClick(View view) {
        if(view==birthdate_edittext)
        {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            showDialog(999);
        }
        else if(view==update_button)
        {
            try{ check_views(views);

                if(validate())

                {
                    password=password_editext.getText().toString();
                    mobile=mobile_editext.getText().toString();
                    birthdate=birthdate_edittext.getText().toString();
                    firstname=firstname_edittext.getText().toString();
                    lastname=lastname_edittext.getText().toString();
                    email=email_edittext.getText().toString();
                    confirmpassword=confpass_editext.getText().toString();
                    Log.d("checkdata",firstname+"\n"+firstnameold);
                    Log.d("checkdata",lastname+"\n"+lastnameold);
                    Log.d("checkdata",mobile+"\n"+mobileold);
                    Log.d("checkdata",birthdate+"\n"+birthdateold);


                    if(connectionDetector.isConnectingToInternet())
                    {   if(firstname.equals(firstnameold)&&
                            mobile.equals(mobileold)&&
                            lastname.equals(lastnameold)&&
                            email.equals(oldemail)&&
                            birthdate.equals(birthdateold)
                            &&password.length()==0)
                    {
                        Toast.makeText(this,getResources().getString(R.string.updte),Toast.LENGTH_LONG).show();
                        intent=new Intent(EditProfile.this,Profile.class);
                        intent.putExtra("where",getIntent().getExtras().getInt("where"));
                        EditProfile.this.finish();
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                    }
                        else{
                        connection=new Connection(this,"/EditCustomerData","Post");
                        connection.reset();
                        if(password.length()>0)
                        {
                            connection.addParmmter("passwd",password);
                        }
                        connection.addParmmter("note",mobile);
                        connection.addParmmter("id_customer",customer_id);
                        connection.addParmmter("birthday",birthdate);
                        connection.addParmmter("firstname",firstname);
                        connection.addParmmter("lastname",lastname);
                        connection.addParmmter("email",email);
                        connection.Connect(new Connection.Result() {
                            @Override
                            public void data(String str) throws JSONException {

                                Log.d("checkupdate",str);
                               // Toast.makeText(EditProfile.this,str,Toast.LENGTH_LONG).show();
                                 JSONObject jsonObject=new JSONObject(str);
                                JSONArray jsonArray=jsonObject.getJSONArray("userdata");
                                customer_id=jsonArray.getJSONObject(0).getString("id_customer");
                                editor.putString("customer_id",customer_id);
                                editor.putString("name",jsonArray.getJSONObject(0).getString("firstname"));
                                editor.putString("email",jsonArray.getJSONObject(0).getString("email"));
                                editor.putString("lastname",jsonArray.getJSONObject(0).getString("lastname"));
                                editor.putString("mobile",jsonArray.getJSONObject(0).getString("note"));
                                editor.putString("email",jsonArray.getJSONObject(0).getString("email"));
                                editor.commit();
                                Toast.makeText(EditProfile.this," update Successfully",Toast.LENGTH_LONG).show();
                                intent=new Intent(EditProfile.this,Profile.class);
                                intent.putExtra("where",getIntent().getExtras().getInt("where"));
                                EditProfile.this.finish();
                                startActivity(intent);
                                overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);


                            }
                        });
                    }
                    }
                    else {
                        TestModel.connectionToast(this);
                    }


                }
            }

            catch (Exception e)
            {
               // Toast.makeText(this,"please enter valid data",Toast.LENGTH_LONG).show();

            }
        }
        else if(view==cancel_button)
        {
            intent=new Intent(this,Profile.class);
            intent.putExtra("where",getIntent().getExtras().getInt("where"));
            this.finish();
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
        }

    }
    public boolean onTouchEvent(MotionEvent event)
    {

        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            System.out.println("TOuch outside the dialog ******************** ");
            dialog.dismiss();
        }
        return false;
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
        if(password_editext.getText().toString().length()>5&&validate_information(password_editext.getText().toString(),"password"))
        {
            password_editext.setError(null);


        }
        else {
            password_editext.setError(getResources().getString(R.string.recpass));
            valid=false;
        }
        if(confpass_editext.getText().toString().length()>5&&confpass_editext.getText().toString().equals(password_editext.getText().toString()))
        {
            confpass_editext.setError(null);

        }
        else {

            confpass_editext.setError(getResources().getString(R.string.recpassconfirm));
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

        if(mobile_editext.getText().toString().length()>6&&validate_information(mobile_editext.getText().toString(),"mobile"))
        {
            mobile_editext.setError(null);

        }
        else {
            // mobile_edittext.setError("Mobile Not Valid (start with +)");
            mobile_editext.setError(getResources().getString(R.string.recmob));
            valid=false;
        }


        String [] select=birthdate_edittext.getText().toString().split("-");
        String year=select[0];
        if(birthdate_edittext.getText().toString().length()>0&&(Integer.parseInt(year)<1999))
        {

            birthdate_edittext.setError(null);

        }
        else {

            birthdate_edittext.setError(getResources().getString(R.string.recbirth));
            valid=false;
        }


        return valid;
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

    }

}
/*
java.lang.RuntimeException: Unable to start activity ComponentInfo{solverteam.aveway/com.example.ibrahim.
myapplication.Activities.EditProfile}: java.lang.ClassCastException: android.support.design.widget.TextInputLayout cannot
 be cast to android.widget.EditText
*/
