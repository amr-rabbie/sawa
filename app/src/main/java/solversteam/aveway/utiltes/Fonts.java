package solversteam.aveway.utiltes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by mosta on 4/24/2017.
 */

public class Fonts {
    Context context;
    TextView view;
    String s;
    public Fonts(Context context ) {

       this.context=context;
    }


    public void setView(TextView view) {
        this.view=view;
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/abc.ttf");
        view.setTypeface(custom_font);
     }
    public void setString(String s) {
        this.s=s;
        Typeface fontString = Typeface.createFromAsset(context.getAssets(),
                "fonts/abc.ttf");
        Paint paint = new Paint();
        paint.setTypeface(fontString);
        Canvas mCanvas;
       // mCanvas.drawText("ID :" + m_sId, dw - 450, dh - 350, paint);
    }
}
