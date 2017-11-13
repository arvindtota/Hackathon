package android.support.v4.p001b;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0044b implements Creator {
    final C0045c f55a;

    public C0044b(C0045c c0045c) {
        this.f55a = c0045c;
    }

    public final Object createFromParcel(Parcel parcel) {
        return this.f55a.createFromParcel(parcel, null);
    }

    public final Object[] newArray(int i) {
        return this.f55a.newArray(i);
    }
}
