package startimes.com.okhttpdemo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Converter.Factory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class RetroActivity extends AppCompatActivity implements Callback<String> {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro);
        tv = (TextView) findViewById(R.id.tv_retro);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .addConverterFactory(new Factory(){
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, String>() {
                            @Override
                            public String convert(ResponseBody value) throws IOException {
                                return value.string();
                            }
                        };
                    }
                })
                .build();
        BlogService service = retrofit.create(BlogService.class);
        Call<String> listCall = service.getBaidu();
        listCall.enqueue(this);


    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        tv.setText(response.body().toString());
        Log.d("TAG","--------->>>>"+response.body().toString());
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        tv.setText("请求失败---》》"+call.request().url());
    }


    public interface BlogService {
        @GET("/") //网址子目录
        Call<String> getBaidu();
    }
}
