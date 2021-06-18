package solversteam.aveway.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import solverteam.aveway.R;

import java.io.InputStream;
import java.net.URL;

/*
* Created by mosta on 3/12/2017.
*/
public class MyAlertDialogFragment extends DialogFragment {

    static Activity context;
     static String logos;
public static MyAlertDialogFragment newInstance(int title, Activity con,String logo  ) {
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        logos=logo;

        frag.setArguments(args);
     context=con ;
        return frag;
        }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(logos).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return context.getResources().getDrawable(R.drawable.ave);
        }
    }
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
    Log.d("nnn",logos+55);
        int title = getArguments().getInt("title");
    Resources res = getResources();
    int resourceId = res.getIdentifier(
            logos, "drawable", "solverteam.aveway" );
   // Drawable drawable = res.getDrawable( resourceId );
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder
                .setTitle(title)
                .setIcon(R.drawable.ave)
         .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {

@Override
public void onClick(DialogInterface arg0, int arg1) {

        }
        })

        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {


@Override
public void onClick(DialogInterface arg0, int arg1) {
             context.finish();
             context.overridePendingTransition(R.anim.no_thing, R.anim.left_to_right);
        }
        })
        .create();
        }
        }
