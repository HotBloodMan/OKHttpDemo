package startimes.com.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivPicasso;
    private ImageView ivGlide;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ivPicasso = (ImageView) findViewById(R.id.iv_picasso);
        ivGlide = (ImageView) findViewById(R.id.iv_glide);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String s="http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg";
        Picasso.with(PhotoActivity.this).load(s).into(ivPicasso);
        Glide.with(PhotoActivity.this).load(s).into(ivGlide);
    }
}
