package solversteam.aveway.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;

import solverteam.aveway.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WriteReviewActivity extends AppCompatActivity {
    @BindView(R.id.include)
    Toolbar toolbar;
    @BindView(R.id.ratingBar1)
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.abc_ic_clear_mtrl_alpha);
        getSupportActionBar().setTitle("");
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                rb.setRating(rating);
//                Toast.makeText(getApplicationContext(), Float.toString(rating), Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_thing, R.anim.slide_out_bottom);
    }
}
