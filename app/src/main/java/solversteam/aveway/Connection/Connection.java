package solversteam.aveway.Connection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import solversteam.aveway.Models.CartCLass;
import solverteam.aveway.R;


public class Connection {


    private  SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private  LayoutInflater inflater;
    private View layout;
    private Handler mainHandler;
    private OkHttpClient client;
    private Request requesturl;
    private Activity _context;
    private String json, method, url;
    private FormEncodingBuilder formBody;
    private Result DelegateMethod;
    private ConnectionDetector testConnection;
    private static final String baseurl = "http://www.estaterus.net:83/ecommerce";
    private Boolean check=true;
    private boolean found=true;


    public Connection(Activity context, String URL, String method) {
        sharedPreferences=context.getSharedPreferences("LanguageAndCountry", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        url = baseurl + URL;
        Log.i("wezza1", url);
        this._context = context;
        mainHandler = new Handler(_context.getMainLooper());
        client = new OkHttpClient();
//        this.loading = (ProgressBar) _context.findViewById(progressBar);
        this.method = method;
        testConnection = new ConnectionDetector(_context);
        inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.test, null);

    }

    // ===================== Deleage Method ==============================================
    public interface Result {
        public void data(String str) throws JSONException;
    }

    public void Connect(Result dlg) {
        DelegateMethod = dlg;

        if (testConnection.isConnectingToInternet()) {
            if (method.equals("Post")) {
                ReadFromServer();
            } else if (method.equals("Get")) {
                ReadFromServer1();
            } else if (method.equals("Put"))
                ReadFromServer2();
        } else {if(sharedPreferences.getBoolean("found",true) ){
            found=false;
            editor.putBoolean("found",false);
            editor.commit();
            connectionToast();
            Log.i("wezza12", "ana hna11");}
            repeat();
            Log.i("wezza", "ana hna");
        }


    }

    public void executeNow(String result) {
        try {
            DelegateMethod.data(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // ===================== End Deleage Method ==============================================
    public void reset() {
        formBody = new FormEncodingBuilder();
    }

    public void addParmmter(String key, String value) {
        formBody.add(key, value);
    }
    public void addList(String key, ArrayList<CartCLass> value) {
        Gson gson = new Gson();
        String json = gson.toJson(value);
        Log.d("checkjson",json);
        formBody.add(key, json);

    }


    private void ReadFromServer() {


        try {
            _context.showDialog(1);
        } catch (Exception ex) {
        }
        RequestBody body = formBody.build();
        try {
            requesturl = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();


            client.newCall(requesturl).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    try {
                        _context.dismissDialog(1);
                    } catch (Exception ex) {
                    }
                    json = request.toString();
                    Log.i("Failuremsg", json);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (testConnection.isConnectingToInternet()) {
                                check=true;
                               repeat();
                            } else {
                                check=true;
                                repeat();
                             //   connectionToast();
                            }
                        }
                    });


                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        _context.dismissDialog(1);
                    } catch (Exception ex) {
                    }
                    int code = response.code();
                    json = response.body().string();
                    Log.i("ResponseMsg", json);
                    Log.i("ResponseCode", code + "");
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            executeNow(json);
                        }
                    });

                }

            });
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void ReadFromServer1() {
        //Log.i("12555" , progressBar+"");
        //loading = (ProgressBar) _context.findViewById(progressBar);
        //loading.setVisibility(View.VISIBLE);
        try {
           _context.showDialog(1);
        } catch (Exception e) {
        }

        try {
            requesturl = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            client.newCall(requesturl).enqueue(new Callback() {


                @Override
                public void onFailure(Request request, IOException e) {
                    try { _context.dismissDialog(1);
                    } catch (Exception ex) {
                    }
                    json = request.toString();
                    Log.i("Failuremsg", json);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (testConnection.isConnectingToInternet()) {
                                check=true;
                                 repeat();
                            } else {
                                check=true;
                                repeat();
                              //  connectionToast();
                            }
                        }
                    });


                }

                @Override
                public void onResponse(Response response) throws IOException {
//                    loading.setVisibility(View.INVISIBLE);
                    try {
                           _context.dismissDialog(1);
                    } catch (Exception e) {
                    }
                    int code = response.code();
                    json = response.body().string();
                    Log.i("ResponseMsg", json);
                    Log.i("ResponseCode", code + "");
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            executeNow(json);
                        }
                    });
                }

            });
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void connectionToast() {


        Button linear = (Button) layout.findViewById(R.id.button2);
        LinearLayout.LayoutParams layoutParams= new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        _context.addContentView(layout,layoutParams);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ViewGroup)layout.getParent()).removeView(layout);
               // layout.setVisibility(View.GONE);

                found=true;
                editor.putBoolean("found",true);
                editor.commit();
            }
        });



    }

    private void ReadFromServer2() {

        _context.showDialog(1);
        RequestBody body = formBody.build();
        try {
            requesturl = new Request.Builder()
                    .url(url)
                    .put(body)
                    .build();

            client.newCall(requesturl).enqueue(new Callback() {


                @Override
                public void onFailure(Request request, IOException e) {
                    _context.dismissDialog(1);
                    json = request.toString();
                    Log.i("Failuremsg", json);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (testConnection.isConnectingToInternet()) {
                                check=true;
                                 repeat();
                            } else {
                                check=true;
                                repeat();
                               // connectionToast();
                            }
                        }
                    });


                }

                @Override
                public void onResponse(Response response) throws IOException {
                    _context.dismissDialog(1);
                    int code = response.code();
                    json = response.body().string();
                    Log.i("ResponseMsg", json);
                    Log.i("ResponseCode", code + "");
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            executeNow(json);
                        }
                    });
                }

            });
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    public void repeat()
    {
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(check) {
                    Log.d("checkconn","first");
                    repeat();
                    if (testConnection.isConnectingToInternet()) {
                        editor.putBoolean("found",true);
                        editor.commit();
                        try{
                        ((ViewGroup)layout.getParent()).removeView(layout);}catch (Exception e){}
                        check=false;
                        Log.d("checkconn","second");

                        if (method.equals("Post")) {
                            ReadFromServer();
                        } else if (method.equals("Get")) {
                            ReadFromServer1();
                        } else if (method.equals("Put"))

                        {
                            ReadFromServer2();
                        }
                    }

                    else
                    if(sharedPreferences.getBoolean("found",true) ){
                        found=false;
                        editor.putBoolean("found",false);
                        editor.commit();
                        connectionToast();}
            }
            }
        },3000);

    }



}
