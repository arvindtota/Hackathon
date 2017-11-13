package android.support.v4.p001b;

import android.os.Parcel;
import android.os.Parcelable.ClassLoaderCreator;

final class C0046d implements ClassLoaderCreator {
    private final C0045c f56a;

    public C0046d(C0045c c0045c) {
        this.f56a = c0045c;
    }

    public final Object createFromParcel(Parcel parcel) {
        return this.f56a.createFromParcel(parcel, null);
    }

    public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return this.f56a.createFromParcel(parcel, classLoader);
    }

    public final Object[] newArray(int i) {
        return this.f56a.newArray(i);
    }
}
