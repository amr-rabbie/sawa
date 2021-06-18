package solversteam.aveway.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText user_name_edittext,password_edittext;
    private Button login_button;
    private String username,password,customer_id,firstname,email,from,firstcolor;
    private Connection connection;
    private Intent intent;
    private AccessToken accessToken;
    private ConnectionDetector connectionDetector;
    private CallbackManager mCallbackManager;
    private ConstraintLayout facebook_login_constraint;
    private static final String Password_PATTERN ="^[a-zA-Z0-9 \\u0600-\\u06FF][0-9a-zA-Z0-9 \\u0600-\\u06FF/s ,_-]*$";
    private static final String EMAIL_PATTERN ="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+" +
            "@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\." +
            "[0-9]{1,3}\\.[0-9]{1,3}\\])" +
            "|(([a-zA-Z\\-0-9]+\\.)" +
            "+[a-zA-Z]{2,}))$";
    private static Matcher matcher;
    private static Pattern pattern;
    private ArrayList<Integer> views;
    private CheckBox showPassword;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button register;
    private Dialog dialog;

    Fonts fonts;
    int country,language;
    private String defult,birthD_date,gender,first_name,last_name,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
fonts=new Fonts(this);
        try{
            from=getIntent().getExtras().getString("from");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            from="any where";
        }
        preferences = getApplicationContext().getSharedPreferences("LanguageAndCountry", MODE_PRIVATE);
        firstcolor=preferences.getString("firstmenucolour","#000000");
        user_name_edittext=(EditText)findViewById(R.id.Login_username_edittext) ;
        password_edittext=(EditText)findViewById(R.id.Login_password_edittext) ;
        login_button=(Button)findViewById(R.id.Login_login_button);
        register=(Button) findViewById(R.id.Login_register_textview);
        login_button.setBackgroundColor(Color.parseColor(firstcolor));
        register.setBackgroundColor(Color.parseColor(firstcolor));
        fonts.setView(login_button);
        fonts.setView(register);
        register.setOnClickListener(this);
        facebook_login_constraint=(ConstraintLayout)findViewById(R.id.Login_signin_fb_constraintLayout) ;
        facebook_login_constraint.setOnClickListener(this);
        facebook_login_constraint.setOnClickListener(this);
        login_button.setOnClickListener(this);
        country=preferences.getInt("Country",1);
        language=preferences.getInt("Lang",1);
        String logo =preferences.getString("logo","");
        defult=preferences.getString("defaultimage","");
        editor=preferences.edit();
        showPassword=(CheckBox)findViewById(R.id.Login_showpass_checkBox);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    password_edittext.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    password_edittext.setInputType(129);
                }
            }
        });
        ImageView imageView=(ImageView)findViewById(R.id.imageView2);

        connectionDetector=new ConnectionDetector(this);
        views=new ArrayList<>();
        views.add(R.id.Login_username_edittext);
        views.add(R.id.Login_password_edittext);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "solversteam.aveway",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

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

    @Override
    public void onClick(View view) {
        if(view==login_button)
        {
            try{check_views(views);
                if(user_name_edittext.getError()==null
                        &&password_edittext.getError()==null)
                {username=user_name_edittext.getText().toString();
                    password=password_edittext.getText().toString();
                    if(connectionDetector.isConnectingToInternet())
                    {
                        connection=new Connection(this,"/CustomerLogin","Post");
                        connection.reset();
                        connection.addParmmter("username",username);
                        connection.addParmmter("password",password);
                        connection.addParmmter("id_shop",country+"");
                        connection.addParmmter("lang",language+"");
                        connection.Connect(new Connection.Result() {
                            @Override
                            public void data(String str) throws JSONException {
                                Log.d("checklogin",str);
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
                                    Log.d("checkcustomerid",customer_id+"555");
                                    Toast.makeText(Login.this,"Login Successfully",Toast.LENGTH_LONG).show();
                                    //lsa hn3ml checkout w nshof
                                    if(from.equals("account"))
                                    {
                                        intent=new Intent(Login.this,solversteam.aveway.Activities.Profile.class);
                                        intent.putExtra("where",getIntent().getExtras().getInt("where"));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);



                                    }
                                    else if(from.equals("checkout"))
                                    {
                                        intent=new Intent(Login.this,ChartActivity.class);
                                        intent.putExtra("where",getIntent().getExtras().getInt("where"));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );

                                    }
                                    else if(from.equals("order"))
                                    {
                                        intent=new Intent(Login.this,Order_show.class);
                                        intent.putExtra("where",getIntent().getExtras().getInt("where"));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );

                                    }
                                    else if(from.equals("adress"))
                                    {
                                        intent=new Intent(Login.this,Add_Address.class);
                                        intent.putExtra("where",getIntent().getExtras().getInt("where"));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );

                                    }
                                    else {
                                        intent=new Intent(Login.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                    }
                                    finish();
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                    Log.d("checkcustomerid",e+"555");
                                }


                                if(customer_id==null)
                                    Toast.makeText(Login.this,R.string.checkpass,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this,"please enter valid data",Toast.LENGTH_LONG).show();
            }
        }
        else if(view==register)
        {
            intent=new Intent(this,SignUp.class);
            intent.putExtra("from",from);
            intent.putExtra("where",getIntent().getExtras().getInt("where"));
            this.finish();
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);

        }
        else if(view==facebook_login_constraint)
        {   if(connectionDetector.isConnectingToInternet())
        {getFaceBookProfileData();}
        else {
            TestModel.connectionToast(this);
        }


           // Toast.makeText(this,"facebook",Toast.LENGTH_SHORT).show();

        }
    }
    private boolean validate_information(String name,String kind) {

        if(kind.equals("password"))
        {pattern = Pattern.compile(Password_PATTERN);
            matcher = pattern.matcher(name);
        }

        else if(kind.equals("email"))
        {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(name);
        }


        return matcher.matches();
    }
    private void check_views(ArrayList<Integer> views)
    {
        for (int viewcount=0;viewcount<views.size();viewcount++)
        {
            EditText editText=(EditText)findViewById(views.get(viewcount));
            fonts.setView(editText);

            String text=editText.getText().toString();
            if(text.length()==0)
            {
                editText.setError("required!");
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);


    }
    private void getFaceBookProfileData() {

        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        final Profile profile=Profile.getCurrentProfile();

                        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {

//                                    Log.d("checkname",object.getString("name"));
//                                    Log.d("checkmail",object.getString("email"));
//                                    Log.d("checkgender",object.getString("gender"));
//                                    Log.d("checkfirst",profile.getFirstName());
//                                    Log.d("checklast",profile.getLastName());
//                                    Log.d("checkbirth",object.getString("birthday"));

                                    try{
                                        Log.d("checkrepsonse","555");
                                        first_name=profile.getFirstName();
                                    }catch (Exception E){first_name="";}
                                    try{
                                    last_name=profile.getLastName();}catch (Exception E){last_name="";}
                                    try{
                                    email=object.getString("email");
                                    }catch (Exception E){email=object.getString("id")+"@facebook.com";}
                                    try{
                                    gender=object.getString("gender");}catch (Exception E){gender="";}
                                    try{
                                    birthD_date=object.getString("birthday");}catch (Exception E){birthD_date="";}

                                    sign_up(birthD_date,gender,first_name,last_name,email);



                                    mCallbackManager = null;
                                } catch (Exception e) {

                                    Log.e("Exception", e.getMessage() + "");
                                }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,birthday,gender,picture");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
//                        Toast.makeText(RegistrationActivity.this, getResources().
//                                getString(R.string.reg_failed_to_connect_facebook), Toast.LENGTH_LONG).show();
                        Log.e("FacebookException", exception.getMessage() + "");
                    }
                });

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("" +
                "public_profile", "email", "user_friends","user_birthday"));
    }

    private void sign_up(String birthDate,String gender,String firstName,String lastName,String email) {
        Log.d("here","iamlogin");
        connection=new Connection(this,"/SignUp","Post");
        connection.reset();
        connection.addParmmter("passwd","");
        connection.addParmmter("email",email);
        connection.addParmmter("note","");
        connection.addParmmter("birthday",birthDate);
        connection.addParmmter("id_gender",gender);
        connection.addParmmter("firstname",firstName);
        connection.addParmmter("lastname",lastName);
        connection.addParmmter("id_shop",country+"");
        connection.addParmmter("signup_source","1");
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
                        intent=new Intent(Login.this, solversteam.aveway.Activities.Profile.class);
                        intent.putExtra("where",getIntent().getExtras().getInt("where"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);



                    }
                    else if(from.equals("checkout"))
                    {
                        intent=new Intent(Login.this,ChartActivity.class);
                        intent.putExtra("where",getIntent().getExtras().getInt("where"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );

                    }
                    else {
                        intent=new Intent(Login.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    }
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_to_left, R.anim.no_thing);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(Login.this,str,Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}