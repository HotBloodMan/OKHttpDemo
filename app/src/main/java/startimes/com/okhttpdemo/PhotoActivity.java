package startimes.com.okhttpdemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.IntRange;
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
import com.squareup.picasso.Request;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivPicasso;
    private ImageView ivGlide;
    private Button btn;
    String photoUrl="http://n.sinaimg.cn/translate/20160819/9BpA-fxvcsrn8627957.jpg";
    private MyProgressBar myProgressBar;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ivPicasso = (ImageView) findViewById(R.id.iv_picasso);
        myProgressBar = (MyProgressBar) findViewById(R.id.mpb);
        ivGlide = (ImageView) findViewById(R.id.iv_glide);
        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
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
        
        //10.09自定义线程池
//        int CPU_COUNT  = Runtime.getRuntime().availableProcessors();
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CPU_COUNT + 1, CPU_COUNT * 2 + 1
//                , 1, TimeUnit.MINUTES, new PriorityBlockingQueue<Runnable>());
//        Picasso picasso = new Picasso.Builder(this)
//                .executor(threadPoolExecutor)
//                //自定义下载器
//                .downloader(new OkHttp3Downloader(this.getExternalCacheDir()))
//                .build();
//        picasso.setSingletonInstance(picasso);
//        picasso.load(photoUrl).into(ivPicasso);

        //10.10
        OkHttpClient client=new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        return response.newBuilder()
                                .body(new MyProgressbarResponseBody(new ProgressListener() {
                                    @Override
                                    public void update(@IntRange(from = 0, to = 100) final int progress) {
                                        System.out.println("---------------->>> 111");
                                        //更新进度条
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                System.out.println("---------------->>>222");
                                                Log.d("TAG", "run: " + progress);
                                                try {
                                                    Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                myProgressBar.setSweepAngle(progress * 360f / 100);
                                            }
                                        });
                                    }
                                }, response.body()))
                                .build();

                    }
                })
                //设置缓存位置，Picasso下载的图片将缓存在这里
                .cache(new Cache(this.getExternalCacheDir(), 10 * 1024 * 1024))
                .build();
        System.out.println("---------------->>> 333");
        Picasso picasso = new Picasso
                .Builder(this)
                .downloader(new OkHttp3Downloader(client))
                .build();
//        picasso.setSingletonInstance(picasso);
        picasso.load(photoUrl).into(ivPicasso);

    }
    public void getPhoto(View v){
        String url="https://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=https%3A%2F%2Ftimgsa.baidu.com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1507717530859%26di%3Dbc358da1e94a3565bf7deb711cd63b68%26imgtype%3D0%26src%3Dhttp%253A%252F%252Fb.hiphotos.baidu.com%252Fimage%252Fpic%252Fitem%252F55e736d12f2eb9380291af03df628535e4dd6f47.jpg&thumburl=https%3A%2F%2Fss1.bdstatic.com%2F70cFvXSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D131043287%2C2697117014%26fm%3D200%26gp%3D0.jpg";
        int resourcesId=R.mipmap.ic_launcher;
        //加载gif
//        Glide.with(PhotoActivity.this).load(url).into(ivGlide);
        //强化版gif
//        Glide.with(PhotoActivity.this).load(url).asGif().error(R.mipmap.ic_launcher).into(ivGlide);
        //用bitMap播放Gif 更快
//        Glide.with(PhotoActivity.this).load(url).asBitmap().into(ivGlide);
        Glide.with(PhotoActivity.this).load(photoUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                //加点动画crossFade()/fadeFade(int duration)
                .crossFade(1000)
                //调整图片大小
//                .override(600,200)
                //来点缩略图
                .thumbnail(0.3f)
                .into(ivGlide);
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
