package net.dinglisch.android.tasker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import com.google.android.gms.maps.MapView;

public class MyMapView extends MapView {
    public boolean f5370a = false;
    public boolean f5371b = false;

    public MyMapView(Context context) {
        super(context);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    public final void m8609a(xb xbVar, Bundle bundle) {
        ng.m12719a("MMV", "doActivityOp: " + xbVar);
        switch (xa.f8930a[xbVar.ordinal()]) {
            case 1:
                if (this.f5370a && bundle == null) {
                    ng.m12719a("MMV", "doActivityOp: not creating, already created and no arg");
                    return;
                }
                m8607a(this, null, bundle);
                this.f5370a = true;
                this.f5371b = false;
                return;
            case 2:
                this.f5370a = false;
                m5174d();
                return;
            case 3:
                if (!this.f5370a) {
                    ng.m12719a("MMV", "doActivityOp: not resuming, not created yet");
                    return;
                } else if (this.f5371b) {
                    ng.m12719a("MMV", "doActivityOp: not resuming, already resumed");
                    return;
                } else {
                    m5171b();
                    this.f5371b = true;
                    return;
                }
            case 4:
                if (!this.f5370a) {
                    ng.m12719a("MMV", "doActivityOp: not pausing, not created yet");
                    return;
                } else if (this.f5371b) {
                    m5173c();
                    this.f5371b = false;
                    return;
                } else {
                    ng.m12719a("MMV", "doActivityOp: not pausing, already paused");
                    return;
                }
            case 5:
                if (this.f5370a) {
                    m5172b(bundle);
                    return;
                } else {
                    ng.m12719a("MMV", "doActivityOp: not saving instance, not created yet");
                    return;
                }
            case 6:
                m5175e();
                return;
            default:
                return;
        }
    }

    public static boolean m8608a(Activity activity) {
        try {
            new MapView(activity).m5170a(null);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static void m8607a(MapView mapView, Activity activity, Bundle bundle) {
        try {
            mapView.m5170a(bundle);
        } catch (Throwable e) {
            String message = e.getMessage();
            if (message == null || !message.startsWith("Attempt to invoke virtual method 'android.content.res.Configuration")) {
                ng.m12725b("MMV", "onCreate", e);
            } else {
                ng.m12719a("MMV", "try workaround for marshmallow ROM bug");
            }
            try {
                Window window = activity.getWindow();
                if (window != null) {
                    window.setFlags(16777216, 16777216);
                    mapView.m5170a(bundle);
                    return;
                }
                ng.m12730c("MMV", "couldn't apply marshmallow ROM bug workaround, no window");
            } catch (Exception e2) {
                ng.m12719a("MMV", "failure applying marshmallow map crash workaround");
            }
        }
    }
}
