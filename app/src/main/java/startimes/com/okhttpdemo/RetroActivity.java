package startimes.com.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RetroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .build();
        BlogService service = retrofit.create(BlogService.class);


    }


    public interface BlogService {
        @GET("users/{user}/repos")
        Call<List<ResponseBody>> listRepos(@Path("user") String user);
    }
}
