package startimes.com.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyActivity extends AppCompatActivity implements View.OnClickListener {
    public static String TAG= VolleyActivity.class.getSimpleName();
    private Button btnV;
    private RequestQueue mQueue;

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
        mQueue.add(stringRequest);
    }
}
