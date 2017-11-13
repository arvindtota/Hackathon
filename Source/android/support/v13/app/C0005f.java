package android.support.v13.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class C0005f extends PagerAdapter {
    private final FragmentManager f10a;
    private FragmentTransaction f11b = null;
    private Fragment f12c = null;

    public abstract Fragment mo1615a(int i);

    public C0005f(FragmentManager fragmentManager) {
        this.f10a = fragmentManager;
    }

    public void startUpdate(ViewGroup viewGroup) {
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        if (this.f11b == null) {
            this.f11b = this.f10a.beginTransaction();
        }
        long j = (long) i;
        Fragment findFragmentByTag = this.f10a.findFragmentByTag(C0005f.m11a(viewGroup.getId(), j));
        if (findFragmentByTag != null) {
            this.f11b.attach(findFragmentByTag);
        } else {
            findFragmentByTag = mo1615a(i);
            this.f11b.add(viewGroup.getId(), findFragmentByTag, C0005f.m11a(viewGroup.getId(), j));
        }
        if (findFragmentByTag != this.f12c) {
            C0000a.m3a(findFragmentByTag, false);
            C0000a.m4b(findFragmentByTag, false);
        }
        return findFragmentByTag;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        if (this.f11b == null) {
            this.f11b = this.f10a.beginTransaction();
        }
        this.f11b.detach((Fragment) obj);
    }

    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment = (Fragment) obj;
        if (fragment != this.f12c) {
            if (this.f12c != null) {
                C0000a.m3a(this.f12c, false);
                C0000a.m4b(this.f12c, false);
            }
            if (fragment != null) {
                C0000a.m3a(fragment, true);
                C0000a.m4b(fragment, true);
            }
            this.f12c = fragment;
        }
    }

    public void finishUpdate(ViewGroup viewGroup) {
        if (this.f11b != null) {
            this.f11b.commitAllowingStateLoss();
            this.f11b = null;
            this.f10a.executePendingTransactions();
        }
    }

    public boolean isViewFromObject(View view, Object obj) {
        return ((Fragment) obj).getView() == view;
    }

    public Parcelable saveState() {
        return null;
    }

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    private static String m11a(int i, long j) {
        return "android:switcher:" + i + ":" + j;
    }
}
