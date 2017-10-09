package startimes.com.okhttpdemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivPicasso;
    private ImageView ivGlide;
    private Button btn;
    String photoUrl="http://n.sinaimg.cn/translate/20160819/9BpA-fxvcsrn8627957.jpg";
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
//        String s="http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg";
//        Picasso.with(PhotoActivity.this).load(s).into(ivPicasso);
//        Glide.with(PhotoActivity.this).load(s).into(ivGlide);
//        Picasso.with(this).load(photoUrl)
                //剪裁
//                .transform(transformation)
//                .into(ivPicasso);
//        Picasso p = Picasso.with(this);
//        //开启指示器  红、蓝、绿三种颜色分别代表网络、SD卡和内存。
//        p.setIndicatorsEnabled(true);
//        p.load(photoUrl).
//         ////第一个参数是指图片加载时放弃在内存缓存中查找
//        //第二个参数是指图片加载完不缓存在内存中
//        memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//        .into(ivPicasso, new Callback() {
//            @Override
//            public void onSuccess() {
//                Log.d("TAG","onSuccess: 图片加载成功");
//            }
//
//            @Override
//            public void onError() {
//                Log.d("TAG","onError: 图片加载失败！");
//            }
//        });
        
        //自定义线程池
        int CPU_COUNT  = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CPU_COUNT + 1, CPU_COUNT * 2 + 1
                , 1, TimeUnit.MINUTES, new PriorityBlockingQueue<Runnable>());
        Picasso picasso = new Picasso.Builder(this)
                .executor(threadPoolExecutor)
                //自定义下载器
                .downloader(new OkHttp3Downloader(this.getExternalCacheDir()))
                .build();
        picasso.setSingletonInstance(picasso);
        picasso.load(photoUrl).into(ivPicasso);


    }


    //Transformation是一个接口 如果反编译 会发现编译器自动生成一个类PhotoActivity$1 implements Transformation :
    Transformation transformation = new Transformation() {
        @Override
        public Bitmap transform(Bitmap source) {
            int width = source.getWidth();
            int height = source.getHeight();
            int size = Math.min(width, height);
            Bitmap blankBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(blankBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawCircle(size / 2, size / 2, size / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, 0, 0, paint);
            if (source != null && !source.isRecycled()) {
                source.recycle();
            }
            return blankBitmap;
        }

        @Override
        public String key() {
            return "squareup";
        }
    };
}
