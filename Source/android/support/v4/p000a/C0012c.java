package android.support.v4.p000a;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;

public class C0012c {
    public static boolean startActivities(Context context, Intent[] intentArr) {
        return C0012c.startActivities(context, intentArr, null);
    }

    public static boolean startActivities(Context context, Intent[] intentArr, Bundle bundle) {
        int i = VERSION.SDK_INT;
        if (i >= 16) {
            context.startActivities(intentArr, bundle);
            return true;
        } else if (i < 11) {
            return false;
        } else {
            context.startActivities(intentArr);
            return true;
        }
    }
}
