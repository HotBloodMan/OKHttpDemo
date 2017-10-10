package startimes.com.okhttpdemo;

import android.support.annotation.IntRange;

/**
 * Created by ${JT.L} on 2017/10/10.
 */

public interface ProgressListener {
    //定义接口，取值范围为0～100
    public void update(@IntRange(from = 0, to = 100) int progress);
}
