package android.support.v4.p000a;

import android.content.ComponentName;
import android.content.Intent;

class C0015f implements C0014e {
    C0015f() {
    }

    public Intent mo16a(ComponentName componentName) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(componentName);
        intent.addCategory("android.intent.category.LAUNCHER");
        return intent;
    }
}
