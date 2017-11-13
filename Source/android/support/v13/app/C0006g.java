package android.support.v13.app;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0006g implements Creator {
    C0006g() {
    }

    public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return new SavedState[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new SavedState(parcel);
    }
}
