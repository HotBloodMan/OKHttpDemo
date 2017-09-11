package startimes.com.okhttpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    private Button btnMain;
    private TextView tvMain;
    final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMain = (Button) findViewById(R.id.btn);
        tvMain = (TextView) findViewById(R.id.tv);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequest();
            }
        });
    }
    private void getRequest() {

        final Request request=new Request.Builder()
                .get()
                .tag(this)
                .url("http://www.wooyun.org")
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.i("WY","打印GET响应的数据：" + response.body().string());
                        print(response.body().string());
                    } else {
                        throw new IOException("Unexpected code " + response);
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
}
