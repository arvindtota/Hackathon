package com.google.android.gms.maps;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.C0724f;
import com.google.android.gms.p011a.C0147b;

public class MapView extends FrameLayout {
    private final C0746p f4040a;
    private C0682c f4041b;

    public MapView(Context context) {
        super(context);
        this.f4040a = new C0746p(this, context, null);
        setClickable(true);
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4040a = new C0746p(this, context, GoogleMapOptions.m5154a(context, attributeSet));
        setClickable(true);
    }

    public MapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4040a = new C0746p(this, context, GoogleMapOptions.m5154a(context, attributeSet));
        setClickable(true);
    }

    public MapView(Context context, GoogleMapOptions googleMapOptions) {
        super(context);
        this.f4040a = new C0746p(this, context, googleMapOptions);
        setClickable(true);
    }

    @Deprecated
    public final C0682c m5169a() {
        if (this.f4041b != null) {
            return this.f4041b;
        }
        this.f4040a.m6052f();
        if (this.f4040a.m438a() == null) {
            return null;
        }
        try {
            this.f4041b = new C0682c(((C0744n) this.f4040a.m438a()).m6049e().mo1120a());
            return this.f4041b;
        } catch (RemoteException e) {
            throw new C0724f(e);
        }
    }

    public final void m5170a(Bundle bundle) {
        this.f4040a.m439a(bundle);
        if (this.f4040a.m438a() == null) {
            C0147b.m436a((FrameLayout) this);
        }
    }

    public final void m5171b() {
        this.f4040a.m441b();
    }

    public final void m5172b(Bundle bundle) {
        this.f4040a.m442b(bundle);
    }

    public final void m5173c() {
        this.f4040a.m443c();
    }

    public final void m5174d() {
        this.f4040a.m444d();
    }

    public final void m5175e() {
        this.f4040a.m445e();
    }
}
