package android.support.v13.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import java.util.ArrayList;

public class FragmentTabHost extends TabHost implements OnTabChangeListener {
    private final ArrayList f1a = new ArrayList();
    private FrameLayout f2b;
    private Context f3c;
    private FragmentManager f4d;
    private int f5e;
    private OnTabChangeListener f6f;
    private C0007h f7g;
    private boolean f8h;

    class SavedState extends BaseSavedState {
        public static final Creator CREATOR = new C0006g();
        String f0a;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.f0a = parcel.readString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.f0a);
        }

        public String toString() {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.f0a + "}";
        }
    }

    public FragmentTabHost(Context context) {
        super(context, null);
        m2a(context, null);
    }

    public FragmentTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m2a(context, attributeSet);
    }

    private void m2a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842995}, 0, 0);
        this.f5e = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        super.setOnTabChangedListener(this);
        if (findViewById(16908307) == null) {
            View linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            addView(linearLayout, new LayoutParams(-1, -1));
            View tabWidget = new TabWidget(context);
            tabWidget.setId(16908307);
            tabWidget.setOrientation(0);
            linearLayout.addView(tabWidget, new LinearLayout.LayoutParams(-1, -2, 0.0f));
            tabWidget = new FrameLayout(context);
            tabWidget.setId(16908305);
            linearLayout.addView(tabWidget, new LinearLayout.LayoutParams(0, 0, 0.0f));
            tabWidget = new FrameLayout(context);
            this.f2b = tabWidget;
            this.f2b.setId(this.f5e);
            linearLayout.addView(tabWidget, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    public void setup(Context context, FragmentManager fragmentManager) {
        super.setup();
        this.f3c = context;
        this.f4d = fragmentManager;
        m1a();
    }

    public void setup(Context context, FragmentManager fragmentManager, int i) {
        super.setup();
        this.f3c = context;
        this.f4d = fragmentManager;
        this.f5e = i;
        m1a();
        this.f2b.setId(i);
        if (getId() == -1) {
            setId(16908306);
        }
    }

    private void m1a() {
        if (this.f2b == null) {
            this.f2b = (FrameLayout) findViewById(this.f5e);
            if (this.f2b == null) {
                throw new IllegalStateException("No tab content FrameLayout found for id " + this.f5e);
            }
        }
    }

    public void setOnTabChangedListener(OnTabChangeListener onTabChangeListener) {
        this.f6f = onTabChangeListener;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTabTag = getCurrentTabTag();
        FragmentTransaction fragmentTransaction = null;
        for (int i = 0; i < this.f1a.size(); i++) {
            C0007h c0007h = (C0007h) this.f1a.get(i);
            c0007h.f16d = this.f4d.findFragmentByTag(c0007h.f13a);
            if (!(c0007h.f16d == null || c0007h.f16d.isDetached())) {
                if (c0007h.f13a.equals(currentTabTag)) {
                    this.f7g = c0007h;
                } else {
                    if (fragmentTransaction == null) {
                        fragmentTransaction = this.f4d.beginTransaction();
                    }
                    fragmentTransaction.detach(c0007h.f16d);
                }
            }
        }
        this.f8h = true;
        FragmentTransaction a = m0a(currentTabTag, fragmentTransaction);
        if (a != null) {
            a.commit();
            this.f4d.executePendingTransactions();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.f8h = false;
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.f0a = getCurrentTabTag();
        return savedState;
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCurrentTabByTag(savedState.f0a);
    }

    public void onTabChanged(String str) {
        if (this.f8h) {
            FragmentTransaction a = m0a(str, null);
            if (a != null) {
                a.commit();
            }
        }
        if (this.f6f != null) {
            this.f6f.onTabChanged(str);
        }
    }

    private FragmentTransaction m0a(String str, FragmentTransaction fragmentTransaction) {
        C0007h c0007h = null;
        int i = 0;
        while (i < this.f1a.size()) {
            C0007h c0007h2 = (C0007h) this.f1a.get(i);
            if (!c0007h2.f13a.equals(str)) {
                c0007h2 = c0007h;
            }
            i++;
            c0007h = c0007h2;
        }
        if (c0007h == null) {
            throw new IllegalStateException("No tab known for tag " + str);
        }
        if (this.f7g != c0007h) {
            if (fragmentTransaction == null) {
                fragmentTransaction = this.f4d.beginTransaction();
            }
            if (!(this.f7g == null || this.f7g.f16d == null)) {
                fragmentTransaction.detach(this.f7g.f16d);
            }
            if (c0007h != null) {
                if (c0007h.f16d == null) {
                    c0007h.f16d = Fragment.instantiate(this.f3c, c0007h.f14b.getName(), c0007h.f15c);
                    fragmentTransaction.add(this.f5e, c0007h.f16d, c0007h.f13a);
                } else {
                    fragmentTransaction.attach(c0007h.f16d);
                }
            }
            this.f7g = c0007h;
        }
        return fragmentTransaction;
    }
}
