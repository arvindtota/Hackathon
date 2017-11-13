package android.support.v4.view;

import android.view.View;
import android.view.View.OnClickListener;

final class C0095w implements OnClickListener {
    final /* synthetic */ PagerTabStrip f121a;

    C0095w(PagerTabStrip pagerTabStrip) {
        this.f121a = pagerTabStrip;
    }

    public final void onClick(View view) {
        this.f121a.a.setCurrentItem(this.f121a.a.getCurrentItem() + 1);
    }
}
