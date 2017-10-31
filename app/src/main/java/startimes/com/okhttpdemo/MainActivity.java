package startimes.com.okhttpdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//http://www.open-open.com/lib/view/open1472216742720.html#articleHeader4
public class MainActivity extends Activity {

    private Button btnMain;
    private TextView tvMain;
    final OkHttpClient client = new OkHttpClient();
    public static String TAG= MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMain = (Button) findViewById(R.id.btn);

        tvMain = (TextView) findViewById(R.id.tv);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"onClick----------------------->>>");
                getRequest();
                //异步get
//                getRequest2();
//                new Thread(){
//                    @Override
//                    public void run() {
//                        super.run();
//                        post();
//                    }
//                }.start();

//                startActivity(new Intent(MainActivity.this));
//          Routers.open(MainActivity.this,"modularization://books_list");
                Log.i(TAG,"onClick--------222--------------->>>");

            }

        });
    }

    private void getRequest2(){
//        try {
//            String url = "https://api.github.com/";
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder().url(url).build();
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                }
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    Log.i("TAG","callback thread id is "+Thread.currentThread().getId());
//                    Log.i("TAG",response.body().string());
//                }
//            });

        new Thread() {
            public void run()  {
                 Log.d(TAG,TAG+" enter subthread----->>> ");
                //添加请求头
//                Request request = new Request.Builder()
//                        .url("https://api.github.com/repos/square/okhttp/issues")
//                        .header("User-Agent", "OkHttp Headers.java")
//                        .addHeader("Accept", "application/json; q=0.5")
//                        .addHeader("Accept", "application/vnd.github.v3+json")
//                        .build();
//
//                Response response = null;
//                try {
//                    response = client.newCall(request).execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (!response.isSuccessful()) try {
//                    throw new IOException("Unexpected code " + response);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("Server:=== " + response.header("Server"));
//                System.out.println("Date:=== " + response.header("Date"));
//                System.out.println("Vary:=== " + response.headers("Vary"));

               final MediaType MEDIA_TYPE_MARKDOWN
                        = MediaType.parse("text/x-markdown; charset=utf-8");

                 final OkHttpClient client = new OkHttpClient();

                    File file = new File("H:/out.txt");

                    Request request = new Request.Builder()
                            .url("https://api.github.com/markdown/raw")
                            .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                            .build();

                Response response = null;

                try {
                    response = client.newCall(request).execute();
                    tvMain.setText(response+" 123");
                     Log.d(TAG,TAG+" response----->>> "+response);
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code========= " + response);
                    Log.d(TAG,TAG+" response222----->>> "+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    //同步get
    private void getRequest() {
        /*
        * OK实现过程首先是需要New一个OKhttpclient对象,它的构造其中会重载一个方法里面传Builder对象，方法体中
        * 是实例化很多参数，比如说DisPatcher分发器，存储intercepter的集合等。之后是构建Request请求对象。通过
        * 一个方法链，new Request点Builder()方法点url()方法点build()的方法。build方法中是传了一个Get的请求方式，
        * 实例化请求头。之后是用client去执行这个请求，如果直接点excute()会直接跳到接口中，newCall()方法中里面又调
        * RealCall中的newRealCall()方法,它是一个RealCall的静态类方法，返回RealCall对象，RealCall是Call的实现者，所以
        * 直接在RealCall中看execute()方法，它有两个，一个是public，一个是Protecte的，主要来看public的方法，先判断request是否
        * 执行过，没有执行，捕获请求的栈，将请求设置到栈中，然后注册一个事件监听的回调，将请求添加到正在运行这些请求的队列中，这个方法是同步
        * ，接着就是重点，getResponseWithInterceptorChain()，方法直译是用拦截链来获取响应。方法的内容是 首先new一个拦截器的集合，用来存放
        * 各种拦截器，首先是添加RetryAndFollowUpInterceptor重定向拦截器，这个拦截器主要做的事是失败时重试及重定向。其次是BridgeInterceptor
        *桥接拦截器负责把用户构造的请求转换为发送到服务器的请求，把服务器返回的响应转换为用户有好的响应的。接着是CacheInterceptor缓存拦截器
        *负责读取缓存并将其返回以及缓存的更新。之后是添加ConnectInterceptor，它负责和服务器建立连接。
        *
        *
        * */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "https://api.github.com/";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    okhttp3.Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.i(TAG, response.body().string());
                    } else {
                        Log.i(TAG, "okHttp is request error");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    private void print(Object obj){
        System.out.print(obj);
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    public void post() {

             Log.d(TAG,TAG+" ----->>>post() ");

            File file = new File("H:\\out.txt");

            Request request = new Request.Builder()
                    .url("https://api.github.com/markdown/raw")
                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                    .build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            Log.d(TAG,TAG+" ----->>>post() "+response.body().string());
            System.out.println(response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
