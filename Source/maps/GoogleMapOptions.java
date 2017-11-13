package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.C0145R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.p016a.ag;

public final class GoogleMapOptions implements SafeParcelable {
    public static final C0755y CREATOR = new C0755y();
    private final int f4026a;
    private Boolean f4027b;
    private Boolean f4028c;
    private int f4029d;
    private CameraPosition f4030e;
    private Boolean f4031f;
    private Boolean f4032g;
    private Boolean f4033h;
    private Boolean f4034i;
    private Boolean f4035j;
    private Boolean f4036k;
    private Boolean f4037l;
    private Boolean f4038m;
    private Boolean f4039n;

    public GoogleMapOptions() {
        this.f4029d = -1;
        this.f4026a = 1;
    }

    GoogleMapOptions(int i, byte b, byte b2, int i2, CameraPosition cameraPosition, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8, byte b9, byte b10, byte b11) {
        this.f4029d = -1;
        this.f4026a = i;
        this.f4027b = ag.m5255a(b);
        this.f4028c = ag.m5255a(b2);
        this.f4029d = i2;
        this.f4030e = cameraPosition;
        this.f4031f = ag.m5255a(b3);
        this.f4032g = ag.m5255a(b4);
        this.f4033h = ag.m5255a(b5);
        this.f4034i = ag.m5255a(b6);
        this.f4035j = ag.m5255a(b7);
        this.f4036k = ag.m5255a(b8);
        this.f4037l = ag.m5255a(b9);
        this.f4038m = ag.m5255a(b10);
        this.f4039n = ag.m5255a(b11);
    }

    public static GoogleMapOptions m5154a(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, C0145R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_mapType)) {
            googleMapOptions.f4029d = obtainAttributes.getInt(C0145R.styleable.MapAttrs_mapType, -1);
        }
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_zOrderOnTop)) {
            googleMapOptions.f4027b = Boolean.valueOf(obtainAttributes.getBoolean(C0145R.styleable.MapAttrs_zOrderOnTop, false));
        }
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_useViewLifecycle)) {
            googleMapOptions.f4028c = Boolean.valueOf(obtainAttributes.getBoolean(C0145R.styleable.MapAttrs_useViewLifecycle, false));
        }
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_uiCompass)) {
            googleMapOptions.f4032g = Boolean.valueOf(obtainAttributes.getBoolean(C0145R.styleable.MapAttrs_uiCompass, true));
        }
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_uiRotateGestures)) {
            googleMapOptions.f4036k = Boolean.valueOf(obtainAttributes.getBoolean(C0145R.styleable.MapAttrs_uiRotateGestures, true));
        }
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_uiScrollGestures)) {
            googleMapOptions.f4033h = Boolean.valueOf(obtainAttributes.getBoolean(C0145R.styleable.MapAttrs_uiScrollGestures, true));
        }
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_uiTiltGestures)) {
            googleMapOptions.f4035j = Boolean.valueOf(obtainAttributes.getBoolean(C0145R.styleable.MapAttrs_uiTiltGestures, true));
        }
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_uiZoomGestures)) {
            googleMapOptions.f4034i = Boolean.valueOf(obtainAttributes.getBoolean(C0145R.styleable.MapAttrs_uiZoomGestures, true));
        }
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_uiZoomControls)) {
            googleMapOptions.f4031f = Boolean.valueOf(obtainAttributes.getBoolean(C0145R.styleable.MapAttrs_uiZoomControls, true));
        }
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_liteMode)) {
            googleMapOptions.f4037l = Boolean.valueOf(obtainAttributes.getBoolean(C0145R.styleable.MapAttrs_liteMode, false));
        }
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_uiMapToolbar)) {
            googleMapOptions.f4038m = Boolean.valueOf(obtainAttributes.getBoolean(C0145R.styleable.MapAttrs_uiMapToolbar, true));
        }
        if (obtainAttributes.hasValue(C0145R.styleable.MapAttrs_ambientEnabled)) {
            googleMapOptions.f4039n = Boolean.valueOf(obtainAttributes.getBoolean(C0145R.styleable.MapAttrs_ambientEnabled, false));
        }
        googleMapOptions.f4030e = CameraPosition.m5641a(context, attributeSet);
        obtainAttributes.recycle();
        return googleMapOptions;
    }

    final int m5155a() {
        return this.f4026a;
    }

    final byte m5156b() {
        return ag.m5254a(this.f4027b);
    }

    final byte m5157c() {
        return ag.m5254a(this.f4028c);
    }

    final byte m5158d() {
        return ag.m5254a(this.f4031f);
    }

    public final int describeContents() {
        return 0;
    }

    final byte m5159e() {
        return ag.m5254a(this.f4032g);
    }

    final byte m5160f() {
        return ag.m5254a(this.f4033h);
    }

    final byte m5161g() {
        return ag.m5254a(this.f4034i);
    }

    final byte m5162h() {
        return ag.m5254a(this.f4035j);
    }

    final byte m5163i() {
        return ag.m5254a(this.f4036k);
    }

    final byte m5164j() {
        return ag.m5254a(this.f4037l);
    }

    final byte m5165k() {
        return ag.m5254a(this.f4038m);
    }

    final byte m5166l() {
        return ag.m5254a(this.f4039n);
    }

    public final int m5167m() {
        return this.f4029d;
    }

    public final CameraPosition m5168n() {
        return this.f4030e;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        C0755y.m6069a(this, parcel, i);
    }
}
