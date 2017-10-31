package startimes.com.okhttpdemo;
import android.app.Application;
import android.util.Log;

import com.github.mzule.activityrouter.annotation.Modules;

@Modules({"app"})
public class ModuleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void logActivityCreate(EventPool.ActivityNotify activityNotify) {
//        Log.d("ActivityCreate", activityNotify.activityName);
//    }
}