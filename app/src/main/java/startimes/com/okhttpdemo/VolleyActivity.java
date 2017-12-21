package startimes.com.okhttpdemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyActivity extends AppCompatActivity implements View.OnClickListener {
    public static String TAG= VolleyActivity.class.getSimpleName();
    private Button btnV;
    private RequestQueue mQueue;
    String imgUrl="http://images.cnblogs.com/cnblogs_com/yeshuwei/837768/o_u=733417015,367501698_fm=11_gp=0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        btnV = (Button) findViewById(R.id.btn_volley);
        btnV.setOnClickListener(this);
        mQueue = Volley.newRequestQueue(VolleyActivity.this);
    }

    @Override
    public void onClick(View v) {
        //1 StringRequest  Get
        StringRequest stringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            Log.d(TAG,TAG+" ----->>>response= "+response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             Log.d(TAG,TAG+" ----->>>error= "+error.toString());
            }
        });

        // 2  Post
        StringRequest string = new StringRequest(Request.Method.POST, "http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, TAG + " ----->>>response= " + response.toString());
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, TAG + " ----->>>error= " + error.toString());
            }
        });

//2.2 Post
        StringRequest stringRequests = new StringRequest(Request.Method.POST,"https://www.test.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


        }
    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            //用HashMap来存储请求参数
            Map<String,String> map = new HashMap<String,String>();
            map.put("param1","value1");
            map.put("param2","value2");
            return map;
        }
    };


        //3 图片
//        BitmapCache bitmapCache = new BitmapCache();
//        ImageLoader imageLoader=new ImageLoader(mQueue,bitmapCache);
//        ImageLoader.ImageContainer imageContainer = imageLoader.get("http://images.cnblogs.com/cnblogs_com/yeshuwei/837768/o_u=733417015,367501698_fm=11_gp=0.jpg", new ImageLoader.ImageListener() {
//            @Override
//            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                Log.d(TAG, TAG + " ----->>>response= " + response.toString());
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(TAG, TAG + " ----->>>error= " + error.toString());
//            }
//        });
        //4 JsonObjectRequest和JsonArrayRequest
            jsonRequest();
        mQueue.add(stringRequest);
    }

    private void jsonRequest() {
        String urlWeather = "https://api.thinkpage.cn/v3/weather/now.json?key=rot2enzrehaztkdk&location=guangzhou&language=zh-Hans&unit=c";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlWeather,(String)null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d(TAG, TAG + " ----->>>jsonObject= " + jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, TAG + " ----->>>volleyError= " + volleyError.toString());
            }
        });
        mQueue.add(jsonObjectRequest);
    }

    public class BitmapCache implements ImageLoader.ImageCache {
//        private LruCache<String, Bitmap> mCache;
//        public BitmapCache() {
//            int maxSize = 10 * 1024 * 1024;
//            mCache = new LruCache<String, Bitmap>(maxSize) {
//                @Override
//                protected int sizeOf(String key, Bitmap bitmap) {
//                    return bitmap.getRowBytes() * bitmap.getHeight();
//                }
//            };
//        }
//        @Override
//        public Bitmap getBitmap(String url) {
//            return mCache.get(url);
//        }
//
//        @Override
//        public void putBitmap(String url, Bitmap bitmap) {
//            mCache.put(url, bitmap);
//        }



        private LruCache<String,Bitmap> mCache;
        public  BitmapCache(){
            int maxSize=10*1024*1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url,bitmap);
        }
    }
}
