package android.support.v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.p001b.C0043a;
import android.support.v4.p001b.C0045c;
import android.support.v4.p003d.C0051a;
import android.support.v4.view.p004a.C0060a;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewPager extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator COMPARATOR = new C00551();
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    private static final int[] LAYOUT_ATTRS = new int[]{16842931};
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private static final Interpolator sInterpolator = new C00562();
    private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();
    private int mActivePointerId = -1;
    private PagerAdapter mAdapter;
    private OnAdapterChangeListener mAdapterChangeListener;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    private int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable = new C00573();
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout = true;
    private float mFirstOffset = -3.4028235E38f;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mIgnoreGutter;
    private boolean mInLayout;
    private float mInitialMotionX;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private final ArrayList mItems = new ArrayList();
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset = Float.MAX_VALUE;
    private C0051a mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets = DEBUG;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit = 1;
    private OnPageChangeListener mOnPageChangeListener;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mRestoredCurItem = -1;
    private C0051a mRightEdge;
    private int mScrollState = 0;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private int mSeenPositionMax;
    private int mSeenPositionMin;
    private Method mSetChildrenDrawingOrderEnabled;
    private final ItemInfo mTempItem = new ItemInfo();
    private final Rect mTempRect = new Rect();
    private int mTopPageBounds;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    interface Decor {
    }

    final class C00551 implements Comparator {
        C00551() {
        }

        public final int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
            return itemInfo.position - itemInfo2.position;
        }
    }

    final class C00562 implements Interpolator {
        C00562() {
        }

        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * (((f2 * f2) * f2) * f2)) + 1.0f;
        }
    }

    class C00573 implements Runnable {
        C00573() {
        }

        public void run() {
            ViewPager.this.setScrollState(0);
            ViewPager.this.populate();
        }
    }

    class ItemInfo {
        Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;

        ItemInfo() {
        }
    }

    public class LayoutParams extends android.view.ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.LAYOUT_ATTRS);
            this.gravity = obtainStyledAttributes.getInteger(0, 48);
            obtainStyledAttributes.recycle();
        }
    }

    class MyAccessibilityDelegate extends C0058a {
        MyAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(ViewPager.class.getName());
        }

        public void onInitializeAccessibilityNodeInfo(View view, C0060a c0060a) {
            boolean z = true;
            super.onInitializeAccessibilityNodeInfo(view, c0060a);
            c0060a.m110a(ViewPager.class.getName());
            if (ViewPager.this.mAdapter == null || ViewPager.this.mAdapter.getCount() <= 1) {
                z = ViewPager.DEBUG;
            }
            c0060a.m111a(z);
            if (ViewPager.this.mAdapter != null && ViewPager.this.mCurItem >= 0 && ViewPager.this.mCurItem < ViewPager.this.mAdapter.getCount() - 1) {
                c0060a.m109a((int) FragmentTransaction.TRANSIT_ENTER_MASK);
            }
            if (ViewPager.this.mAdapter != null && ViewPager.this.mCurItem > 0 && ViewPager.this.mCurItem < ViewPager.this.mAdapter.getCount()) {
                c0060a.m109a((int) FragmentTransaction.TRANSIT_EXIT_MASK);
            }
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            switch (i) {
                case FragmentTransaction.TRANSIT_ENTER_MASK /*4096*/:
                    if (ViewPager.this.mAdapter == null || ViewPager.this.mCurItem < 0 || ViewPager.this.mCurItem >= ViewPager.this.mAdapter.getCount() - 1) {
                        return ViewPager.DEBUG;
                    }
                    ViewPager.this.setCurrentItem(ViewPager.this.mCurItem + 1);
                    return true;
                case FragmentTransaction.TRANSIT_EXIT_MASK /*8192*/:
                    if (ViewPager.this.mAdapter == null || ViewPager.this.mCurItem <= 0 || ViewPager.this.mCurItem >= ViewPager.this.mAdapter.getCount()) {
                        return ViewPager.DEBUG;
                    }
                    ViewPager.this.setCurrentItem(ViewPager.this.mCurItem - 1);
                    return true;
                default:
                    return ViewPager.DEBUG;
            }
        }
    }

    interface OnAdapterChangeListener {
        void onAdapterChanged(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2);
    }

    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);

        void onPageSelected(int i);
    }

    public interface PageTransformer {
        void transformPage(View view, float f);
    }

    class PagerObserver extends DataSetObserver {
        private PagerObserver() {
        }

        public void onChanged() {
            ViewPager.this.dataSetChanged();
        }

        public void onInvalidated() {
            ViewPager.this.dataSetChanged();
        }
    }

    public class SavedState extends BaseSavedState {
        public static final Creator CREATOR = C0043a.m52a(new C00591());
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        final class C00591 implements C0045c {
            C00591() {
            }

            public final SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public final SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, i);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel);
            if (classLoader == null) {
                classLoader = getClass().getClassLoader();
            }
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader);
            this.loader = classLoader;
        }
    }

    public class SimpleOnPageChangeListener implements OnPageChangeListener {
        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
        }

        public void onPageScrollStateChanged(int i) {
        }
    }

    class ViewPositionComparator implements Comparator {
        ViewPositionComparator() {
        }

        public int compare(View view, View view2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
            if (layoutParams.isDecor != layoutParams2.isDecor) {
                return layoutParams.isDecor ? 1 : -1;
            } else {
                return layoutParams.position - layoutParams2.position;
            }
        }
    }

    public ViewPager(Context context) {
        super(context);
        initViewPager();
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initViewPager();
    }

    void initViewPager() {
        setWillNotDraw(DEBUG);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = ap.m174a(viewConfiguration);
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new C0051a(context);
        this.mRightEdge = new C0051a(context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mFlingDistance = (int) (25.0f * f);
        this.mCloseEnough = (int) (2.0f * f);
        this.mDefaultGutterSize = (int) (f * 16.0f);
        ah.m142a((View) this, new MyAccessibilityDelegate());
        if (ah.m147c(this) == 0) {
            ah.m146b(this, 1);
        }
    }

    protected void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        super.onDetachedFromWindow();
    }

    private void setScrollState(int i) {
        boolean z = true;
        if (this.mScrollState != i) {
            this.mScrollState = i;
            if (i == 1) {
                this.mSeenPositionMax = -1;
                this.mSeenPositionMin = -1;
            }
            if (this.mPageTransformer != null) {
                if (i == 0) {
                    z = DEBUG;
                }
                enableLayers(z);
            }
            if (this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageScrollStateChanged(i);
            }
        }
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
            this.mAdapter.startUpdate((ViewGroup) this);
            for (int i = 0; i < this.mItems.size(); i++) {
                ItemInfo itemInfo = (ItemInfo) this.mItems.get(i);
                this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            this.mItems.clear();
            removeNonDecorViews();
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        PagerAdapter pagerAdapter2 = this.mAdapter;
        this.mAdapter = pagerAdapter;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mPopulatePending = DEBUG;
            this.mFirstLayout = true;
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                setCurrentItemInternal(this.mRestoredCurItem, DEBUG, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else {
                populate();
            }
        }
        if (this.mAdapterChangeListener != null && pagerAdapter2 != pagerAdapter) {
            this.mAdapterChangeListener.onAdapterChanged(pagerAdapter2, pagerAdapter);
        }
    }

    private void removeNonDecorViews() {
        int i = 0;
        while (i < getChildCount()) {
            if (!((LayoutParams) getChildAt(i).getLayoutParams()).isDecor) {
                removeViewAt(i);
                i--;
            }
            i++;
        }
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    void setOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        this.mAdapterChangeListener = onAdapterChangeListener;
    }

    public void setCurrentItem(int i) {
        this.mPopulatePending = DEBUG;
        setCurrentItemInternal(i, !this.mFirstLayout ? true : DEBUG, DEBUG);
    }

    public void setCurrentItem(int i, boolean z) {
        this.mPopulatePending = DEBUG;
        setCurrentItemInternal(i, z, DEBUG);
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    void setCurrentItemInternal(int i, boolean z, boolean z2) {
        setCurrentItemInternal(i, z, z2, 0);
    }

    void setCurrentItemInternal(int i, boolean z, boolean z2, int i2) {
        boolean z3 = DEBUG;
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(DEBUG);
        } else if (z2 || this.mCurItem != i || this.mItems.size() == 0) {
            if (i < 0) {
                i = 0;
            } else if (i >= this.mAdapter.getCount()) {
                i = this.mAdapter.getCount() - 1;
            }
            int i3 = this.mOffscreenPageLimit;
            if (i > this.mCurItem + i3 || i < this.mCurItem - i3) {
                for (int i4 = 0; i4 < this.mItems.size(); i4++) {
                    ((ItemInfo) this.mItems.get(i4)).scrolling = true;
                }
            }
            if (this.mCurItem != i) {
                z3 = true;
            }
            populate(i);
            scrollToItem(i, z, i2, z3);
        } else {
            setScrollingCacheEnabled(DEBUG);
        }
    }

    private void scrollToItem(int i, boolean z, int i2, boolean z2) {
        int max;
        ItemInfo infoForPosition = infoForPosition(i);
        if (infoForPosition != null) {
            max = (int) (Math.max(this.mFirstOffset, Math.min(infoForPosition.offset, this.mLastOffset)) * ((float) getWidth()));
        } else {
            max = 0;
        }
        if (z) {
            smoothScrollTo(max, 0, i2);
            if (z2 && this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(i);
            }
            if (z2 && this.mInternalPageChangeListener != null) {
                this.mInternalPageChangeListener.onPageSelected(i);
                return;
            }
            return;
        }
        if (z2 && this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(i);
        }
        if (z2 && this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(i);
        }
        completeScroll(DEBUG);
        scrollTo(max, 0);
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageTransformer(boolean z, PageTransformer pageTransformer) {
        int i = 1;
        if (VERSION.SDK_INT >= 11) {
            boolean z2;
            boolean z3 = pageTransformer != null ? true : DEBUG;
            if (this.mPageTransformer != null) {
                z2 = true;
            } else {
                z2 = false;
            }
            int i2 = z3 != z2 ? 1 : 0;
            this.mPageTransformer = pageTransformer;
            setChildrenDrawingOrderEnabledCompat(z3);
            if (z3) {
                if (z) {
                    i = 2;
                }
                this.mDrawingOrder = i;
            } else {
                this.mDrawingOrder = 0;
            }
            if (i2 != 0) {
                populate();
            }
        }
    }

    void setChildrenDrawingOrderEnabledCompat(boolean z) {
        if (this.mSetChildrenDrawingOrderEnabled == null) {
            try {
                this.mSetChildrenDrawingOrderEnabled = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[]{Boolean.TYPE});
            } catch (Throwable e) {
                Log.e(TAG, "Can't find setChildrenDrawingOrderEnabled", e);
            }
        }
        try {
            this.mSetChildrenDrawingOrderEnabled.invoke(this, new Object[]{Boolean.valueOf(z)});
        } catch (Throwable e2) {
            Log.e(TAG, "Error changing children drawing order", e2);
        }
    }

    protected int getChildDrawingOrder(int i, int i2) {
        if (this.mDrawingOrder == 2) {
            i2 = (i - 1) - i2;
        }
        return ((LayoutParams) ((View) this.mDrawingOrderedChildren.get(i2)).getLayoutParams()).childIndex;
    }

    OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public void setOffscreenPageLimit(int i) {
        if (i <= 0) {
            Log.w(TAG, "Requested offscreen page limit " + i + " too small; defaulting to 1");
            i = 1;
        }
        if (i != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = i;
            populate();
        }
    }

    public void setPageMargin(int i) {
        int i2 = this.mPageMargin;
        this.mPageMargin = i;
        int width = getWidth();
        recomputeScrollPosition(width, width, i, i2);
        requestLayout();
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    public void setPageMarginDrawable(Drawable drawable) {
        this.mMarginDrawable = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null ? true : DEBUG);
        invalidate();
    }

    public void setPageMarginDrawable(int i) {
        setPageMarginDrawable(getContext().getResources().getDrawable(i));
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return (super.verifyDrawable(drawable) || drawable == this.mMarginDrawable) ? true : DEBUG;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mMarginDrawable;
        if (drawable != null && drawable.isStateful()) {
            drawable.setState(getDrawableState());
        }
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((double) ((float) (((double) (f - 0.5f)) * 0.4712389167638204d)));
    }

    void smoothScrollTo(int i, int i2) {
        smoothScrollTo(i, i2, 0);
    }

    void smoothScrollTo(int i, int i2, int i3) {
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(DEBUG);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int i4 = i - scrollX;
        int i5 = i2 - scrollY;
        if (i4 == 0 && i5 == 0) {
            completeScroll(DEBUG);
            populate();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int width = getWidth();
        int i6 = width / 2;
        float distanceInfluenceForSnapDuration = (((float) i6) * distanceInfluenceForSnapDuration(Math.min(1.0f, (((float) Math.abs(i4)) * 1.0f) / ((float) width)))) + ((float) i6);
        int abs = Math.abs(i3);
        if (abs > 0) {
            width = Math.round(1000.0f * Math.abs(distanceInfluenceForSnapDuration / ((float) abs))) * 4;
        } else {
            width = (int) (((((float) Math.abs(i4)) / ((((float) width) * this.mAdapter.getPageWidth(this.mCurItem)) + ((float) this.mPageMargin))) + 1.0f) * 100.0f);
        }
        this.mScroller.startScroll(scrollX, scrollY, i4, i5, Math.min(width, MAX_SETTLE_DURATION));
        ah.m145b(this);
    }

    ItemInfo addNewItem(int i, int i2) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = i;
        itemInfo.object = this.mAdapter.instantiateItem((ViewGroup) this, i);
        itemInfo.widthFactor = this.mAdapter.getPageWidth(i);
        if (i2 < 0 || i2 >= this.mItems.size()) {
            this.mItems.add(itemInfo);
        } else {
            this.mItems.add(i2, itemInfo);
        }
        return itemInfo;
    }

    void dataSetChanged() {
        boolean z = (this.mItems.size() >= (this.mOffscreenPageLimit * 2) + 1 || this.mItems.size() >= this.mAdapter.getCount()) ? DEBUG : true;
        boolean z2 = DEBUG;
        int i = this.mCurItem;
        boolean z3 = z;
        int i2 = 0;
        while (i2 < this.mItems.size()) {
            int i3;
            boolean z4;
            int max;
            boolean z5;
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i2);
            int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.mItems.remove(i2);
                    i2--;
                    if (!z2) {
                        this.mAdapter.startUpdate((ViewGroup) this);
                        z2 = true;
                    }
                    this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
                    if (this.mCurItem == itemInfo.position) {
                        i3 = i2;
                        z4 = z2;
                        max = Math.max(0, Math.min(this.mCurItem, this.mAdapter.getCount() - 1));
                        z5 = true;
                    } else {
                        i3 = i2;
                        z4 = z2;
                        max = i;
                        z5 = true;
                    }
                } else if (itemInfo.position != itemPosition) {
                    if (itemInfo.position == this.mCurItem) {
                        i = itemPosition;
                    }
                    itemInfo.position = itemPosition;
                    i3 = i2;
                    z4 = z2;
                    max = i;
                    z5 = true;
                }
                z3 = z5;
                i = max;
                z2 = z4;
                i2 = i3 + 1;
            }
            i3 = i2;
            z4 = z2;
            max = i;
            z5 = z3;
            z3 = z5;
            i = max;
            z2 = z4;
            i2 = i3 + 1;
        }
        if (z2) {
            this.mAdapter.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (z3) {
            max = getChildCount();
            for (i2 = 0; i2 < max; i2++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i2).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.widthFactor = 0.0f;
                }
            }
            setCurrentItemInternal(i, DEBUG, true);
            requestLayout();
        }
    }

    void populate() {
        populate(this.mCurItem);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void populate(int r15) {
        /*
        r14 = this;
        r0 = 0;
        r1 = r14.mCurItem;
        if (r1 == r15) goto L_0x023e;
    L_0x0005:
        r0 = r14.mCurItem;
        r0 = r14.infoForPosition(r0);
        r14.mCurItem = r15;
        r1 = r0;
    L_0x000e:
        r0 = r14.mAdapter;
        if (r0 != 0) goto L_0x0013;
    L_0x0012:
        return;
    L_0x0013:
        r0 = r14.mPopulatePending;
        if (r0 != 0) goto L_0x0012;
    L_0x0017:
        r0 = r14.getWindowToken();
        if (r0 == 0) goto L_0x0012;
    L_0x001d:
        r0 = r14.mAdapter;
        r0.startUpdate(r14);
        r0 = r14.mOffscreenPageLimit;
        r2 = 0;
        r3 = r14.mCurItem;
        r3 = r3 - r0;
        r7 = java.lang.Math.max(r2, r3);
        r2 = r14.mAdapter;
        r8 = r2.getCount();
        r2 = r8 + -1;
        r3 = r14.mCurItem;
        r0 = r0 + r3;
        r9 = java.lang.Math.min(r2, r0);
        r3 = 0;
        r0 = 0;
        r2 = r0;
    L_0x003e:
        r0 = r14.mItems;
        r0 = r0.size();
        if (r2 >= r0) goto L_0x023b;
    L_0x0046:
        r0 = r14.mItems;
        r0 = r0.get(r2);
        r0 = (android.support.v4.view.ViewPager.ItemInfo) r0;
        r4 = r0.position;
        r5 = r14.mCurItem;
        if (r4 < r5) goto L_0x00b3;
    L_0x0054:
        r4 = r0.position;
        r5 = r14.mCurItem;
        if (r4 != r5) goto L_0x023b;
    L_0x005a:
        if (r0 != 0) goto L_0x0238;
    L_0x005c:
        if (r8 <= 0) goto L_0x0238;
    L_0x005e:
        r0 = r14.mCurItem;
        r0 = r14.addNewItem(r0, r2);
        r6 = r0;
    L_0x0065:
        if (r6 == 0) goto L_0x0189;
    L_0x0067:
        r5 = 0;
        r4 = r2 + -1;
        if (r4 < 0) goto L_0x00b7;
    L_0x006c:
        r0 = r14.mItems;
        r0 = r0.get(r4);
        r0 = (android.support.v4.view.ViewPager.ItemInfo) r0;
    L_0x0074:
        r3 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r10 = r6.widthFactor;
        r10 = r3 - r10;
        r3 = r14.mCurItem;
        r3 = r3 + -1;
        r12 = r3;
        r3 = r5;
        r5 = r12;
        r13 = r4;
        r4 = r2;
        r2 = r13;
    L_0x0084:
        if (r5 < 0) goto L_0x00eb;
    L_0x0086:
        r11 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1));
        if (r11 < 0) goto L_0x00bb;
    L_0x008a:
        if (r5 >= r7) goto L_0x00bb;
    L_0x008c:
        if (r0 == 0) goto L_0x00eb;
    L_0x008e:
        r11 = r0.position;
        if (r5 != r11) goto L_0x00b0;
    L_0x0092:
        r11 = r0.scrolling;
        if (r11 != 0) goto L_0x00b0;
    L_0x0096:
        r11 = r14.mItems;
        r11.remove(r2);
        r11 = r14.mAdapter;
        r0 = r0.object;
        r11.destroyItem(r14, r5, r0);
        r2 = r2 + -1;
        r4 = r4 + -1;
        if (r2 < 0) goto L_0x00b9;
    L_0x00a8:
        r0 = r14.mItems;
        r0 = r0.get(r2);
        r0 = (android.support.v4.view.ViewPager.ItemInfo) r0;
    L_0x00b0:
        r5 = r5 + -1;
        goto L_0x0084;
    L_0x00b3:
        r0 = r2 + 1;
        r2 = r0;
        goto L_0x003e;
    L_0x00b7:
        r0 = 0;
        goto L_0x0074;
    L_0x00b9:
        r0 = 0;
        goto L_0x00b0;
    L_0x00bb:
        if (r0 == 0) goto L_0x00d3;
    L_0x00bd:
        r11 = r0.position;
        if (r5 != r11) goto L_0x00d3;
    L_0x00c1:
        r0 = r0.widthFactor;
        r3 = r3 + r0;
        r2 = r2 + -1;
        if (r2 < 0) goto L_0x00d1;
    L_0x00c8:
        r0 = r14.mItems;
        r0 = r0.get(r2);
        r0 = (android.support.v4.view.ViewPager.ItemInfo) r0;
        goto L_0x00b0;
    L_0x00d1:
        r0 = 0;
        goto L_0x00b0;
    L_0x00d3:
        r0 = r2 + 1;
        r0 = r14.addNewItem(r5, r0);
        r0 = r0.widthFactor;
        r3 = r3 + r0;
        r4 = r4 + 1;
        if (r2 < 0) goto L_0x00e9;
    L_0x00e0:
        r0 = r14.mItems;
        r0 = r0.get(r2);
        r0 = (android.support.v4.view.ViewPager.ItemInfo) r0;
        goto L_0x00b0;
    L_0x00e9:
        r0 = 0;
        goto L_0x00b0;
    L_0x00eb:
        r2 = r6.widthFactor;
        r3 = r4 + 1;
        r0 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1));
        if (r0 >= 0) goto L_0x0186;
    L_0x00f5:
        r0 = r14.mItems;
        r0 = r0.size();
        if (r3 >= r0) goto L_0x0142;
    L_0x00fd:
        r0 = r14.mItems;
        r0 = r0.get(r3);
        r0 = (android.support.v4.view.ViewPager.ItemInfo) r0;
    L_0x0105:
        r5 = r14.mCurItem;
        r5 = r5 + 1;
    L_0x0109:
        if (r5 >= r8) goto L_0x0186;
    L_0x010b:
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r7 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1));
        if (r7 < 0) goto L_0x0146;
    L_0x0111:
        if (r5 <= r9) goto L_0x0146;
    L_0x0113:
        if (r0 == 0) goto L_0x0186;
    L_0x0115:
        r7 = r0.position;
        if (r5 != r7) goto L_0x0233;
    L_0x0119:
        r7 = r0.scrolling;
        if (r7 != 0) goto L_0x0233;
    L_0x011d:
        r7 = r14.mItems;
        r7.remove(r3);
        r7 = r14.mAdapter;
        r0 = r0.object;
        r7.destroyItem(r14, r5, r0);
        r0 = r14.mItems;
        r0 = r0.size();
        if (r3 >= r0) goto L_0x0144;
    L_0x0131:
        r0 = r14.mItems;
        r0 = r0.get(r3);
        r0 = (android.support.v4.view.ViewPager.ItemInfo) r0;
    L_0x0139:
        r12 = r2;
        r2 = r0;
        r0 = r12;
    L_0x013c:
        r5 = r5 + 1;
        r12 = r0;
        r0 = r2;
        r2 = r12;
        goto L_0x0109;
    L_0x0142:
        r0 = 0;
        goto L_0x0105;
    L_0x0144:
        r0 = 0;
        goto L_0x0139;
    L_0x0146:
        if (r0 == 0) goto L_0x0167;
    L_0x0148:
        r7 = r0.position;
        if (r5 != r7) goto L_0x0167;
    L_0x014c:
        r0 = r0.widthFactor;
        r2 = r2 + r0;
        r3 = r3 + 1;
        r0 = r14.mItems;
        r0 = r0.size();
        if (r3 >= r0) goto L_0x0165;
    L_0x0159:
        r0 = r14.mItems;
        r0 = r0.get(r3);
        r0 = (android.support.v4.view.ViewPager.ItemInfo) r0;
    L_0x0161:
        r12 = r2;
        r2 = r0;
        r0 = r12;
        goto L_0x013c;
    L_0x0165:
        r0 = 0;
        goto L_0x0161;
    L_0x0167:
        r0 = r14.addNewItem(r5, r3);
        r3 = r3 + 1;
        r0 = r0.widthFactor;
        r2 = r2 + r0;
        r0 = r14.mItems;
        r0 = r0.size();
        if (r3 >= r0) goto L_0x0184;
    L_0x0178:
        r0 = r14.mItems;
        r0 = r0.get(r3);
        r0 = (android.support.v4.view.ViewPager.ItemInfo) r0;
    L_0x0180:
        r12 = r2;
        r2 = r0;
        r0 = r12;
        goto L_0x013c;
    L_0x0184:
        r0 = 0;
        goto L_0x0180;
    L_0x0186:
        r14.calculatePageOffsets(r6, r4, r1);
    L_0x0189:
        r1 = r14.mAdapter;
        r2 = r14.mCurItem;
        if (r6 == 0) goto L_0x01e4;
    L_0x018f:
        r0 = r6.object;
    L_0x0191:
        r1.setPrimaryItem(r14, r2, r0);
        r0 = r14.mAdapter;
        r0.finishUpdate(r14);
        r0 = r14.mDrawingOrder;
        if (r0 == 0) goto L_0x01e6;
    L_0x019d:
        r0 = 1;
        r2 = r0;
    L_0x019f:
        if (r2 == 0) goto L_0x01ac;
    L_0x01a1:
        r0 = r14.mDrawingOrderedChildren;
        if (r0 != 0) goto L_0x01e9;
    L_0x01a5:
        r0 = new java.util.ArrayList;
        r0.<init>();
        r14.mDrawingOrderedChildren = r0;
    L_0x01ac:
        r3 = r14.getChildCount();
        r0 = 0;
        r1 = r0;
    L_0x01b2:
        if (r1 >= r3) goto L_0x01ef;
    L_0x01b4:
        r4 = r14.getChildAt(r1);
        r0 = r4.getLayoutParams();
        r0 = (android.support.v4.view.ViewPager.LayoutParams) r0;
        r0.childIndex = r1;
        r5 = r0.isDecor;
        if (r5 != 0) goto L_0x01d9;
    L_0x01c4:
        r5 = r0.widthFactor;
        r6 = 0;
        r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x01d9;
    L_0x01cb:
        r5 = r14.infoForChild(r4);
        if (r5 == 0) goto L_0x01d9;
    L_0x01d1:
        r6 = r5.widthFactor;
        r0.widthFactor = r6;
        r5 = r5.position;
        r0.position = r5;
    L_0x01d9:
        if (r2 == 0) goto L_0x01e0;
    L_0x01db:
        r0 = r14.mDrawingOrderedChildren;
        r0.add(r4);
    L_0x01e0:
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x01b2;
    L_0x01e4:
        r0 = 0;
        goto L_0x0191;
    L_0x01e6:
        r0 = 0;
        r2 = r0;
        goto L_0x019f;
    L_0x01e9:
        r0 = r14.mDrawingOrderedChildren;
        r0.clear();
        goto L_0x01ac;
    L_0x01ef:
        if (r2 == 0) goto L_0x01f8;
    L_0x01f1:
        r0 = r14.mDrawingOrderedChildren;
        r1 = sPositionComparator;
        java.util.Collections.sort(r0, r1);
    L_0x01f8:
        r0 = r14.hasFocus();
        if (r0 == 0) goto L_0x0012;
    L_0x01fe:
        r0 = r14.findFocus();
        if (r0 == 0) goto L_0x0231;
    L_0x0204:
        r0 = r14.infoForAnyChild(r0);
    L_0x0208:
        if (r0 == 0) goto L_0x0210;
    L_0x020a:
        r0 = r0.position;
        r1 = r14.mCurItem;
        if (r0 == r1) goto L_0x0012;
    L_0x0210:
        r0 = 0;
    L_0x0211:
        r1 = r14.getChildCount();
        if (r0 >= r1) goto L_0x0012;
    L_0x0217:
        r1 = r14.getChildAt(r0);
        r2 = r14.infoForChild(r1);
        if (r2 == 0) goto L_0x022e;
    L_0x0221:
        r2 = r2.position;
        r3 = r14.mCurItem;
        if (r2 != r3) goto L_0x022e;
    L_0x0227:
        r2 = 2;
        r1 = r1.requestFocus(r2);
        if (r1 != 0) goto L_0x0012;
    L_0x022e:
        r0 = r0 + 1;
        goto L_0x0211;
    L_0x0231:
        r0 = 0;
        goto L_0x0208;
    L_0x0233:
        r12 = r2;
        r2 = r0;
        r0 = r12;
        goto L_0x013c;
    L_0x0238:
        r6 = r0;
        goto L_0x0065;
    L_0x023b:
        r0 = r3;
        goto L_0x005a;
    L_0x023e:
        r1 = r0;
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.populate(int):void");
    }

    private void calculatePageOffsets(ItemInfo itemInfo, int i, ItemInfo itemInfo2) {
        float f;
        ItemInfo itemInfo3;
        float f2;
        int i2;
        int i3;
        int count = this.mAdapter.getCount();
        int width = getWidth();
        if (width > 0) {
            f = ((float) this.mPageMargin) / ((float) width);
        } else {
            f = 0.0f;
        }
        if (itemInfo2 != null) {
            width = itemInfo2.position;
            float f3;
            int i4;
            int i5;
            int i6;
            float pageWidth;
            if (width < itemInfo.position) {
                f3 = (itemInfo2.offset + itemInfo2.widthFactor) + f;
                i4 = 0;
                i5 = width + 1;
                while (i5 <= itemInfo.position && i4 < this.mItems.size()) {
                    itemInfo3 = (ItemInfo) this.mItems.get(i4);
                    while (i5 > itemInfo3.position && i4 < this.mItems.size() - 1) {
                        i4++;
                        itemInfo3 = (ItemInfo) this.mItems.get(i4);
                    }
                    i6 = i5;
                    f2 = f3;
                    i2 = i6;
                    while (i2 < itemInfo3.position) {
                        pageWidth = (this.mAdapter.getPageWidth(i2) + f) + f2;
                        i2++;
                        f2 = pageWidth;
                    }
                    itemInfo3.offset = f2;
                    f2 += itemInfo3.widthFactor + f;
                    width = i2 + 1;
                    f3 = f2;
                    i5 = width;
                }
            } else if (width > itemInfo.position) {
                i4 = this.mItems.size() - 1;
                f3 = itemInfo2.offset;
                i5 = width - 1;
                while (i5 >= itemInfo.position && i4 >= 0) {
                    itemInfo3 = (ItemInfo) this.mItems.get(i4);
                    while (i5 < itemInfo3.position && i4 > 0) {
                        i4--;
                        itemInfo3 = (ItemInfo) this.mItems.get(i4);
                    }
                    i6 = i5;
                    f2 = f3;
                    i2 = i6;
                    while (i2 > itemInfo3.position) {
                        pageWidth = f2 - (this.mAdapter.getPageWidth(i2) + f);
                        i2--;
                        f2 = pageWidth;
                    }
                    f2 -= itemInfo3.widthFactor + f;
                    itemInfo3.offset = f2;
                    width = i2 - 1;
                    f3 = f2;
                    i5 = width;
                }
            }
        }
        int size = this.mItems.size();
        f2 = itemInfo.offset;
        i2 = itemInfo.position - 1;
        this.mFirstOffset = itemInfo.position == 0 ? itemInfo.offset : -3.4028235E38f;
        this.mLastOffset = itemInfo.position == count + -1 ? (itemInfo.offset + itemInfo.widthFactor) - 1.0f : Float.MAX_VALUE;
        for (i3 = i - 1; i3 >= 0; i3--) {
            itemInfo3 = (ItemInfo) this.mItems.get(i3);
            while (i2 > itemInfo3.position) {
                f2 -= this.mAdapter.getPageWidth(i2) + f;
                i2--;
            }
            f2 -= itemInfo3.widthFactor + f;
            itemInfo3.offset = f2;
            if (itemInfo3.position == 0) {
                this.mFirstOffset = f2;
            }
            i2--;
        }
        f2 = (itemInfo.offset + itemInfo.widthFactor) + f;
        i2 = itemInfo.position + 1;
        for (i3 = i + 1; i3 < size; i3++) {
            itemInfo3 = (ItemInfo) this.mItems.get(i3);
            while (i2 < itemInfo3.position) {
                f2 += this.mAdapter.getPageWidth(i2) + f;
                i2++;
            }
            if (itemInfo3.position == count - 1) {
                this.mLastOffset = (itemInfo3.widthFactor + f2) - 1.0f;
            }
            itemInfo3.offset = f2;
            f2 += itemInfo3.widthFactor + f;
            i2++;
        }
        this.mNeedCalculatePageOffsets = DEBUG;
    }

    public Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        if (this.mAdapter != null) {
            savedState.adapterState = this.mAdapter.saveState();
        }
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            if (this.mAdapter != null) {
                this.mAdapter.restoreState(savedState.adapterState, savedState.loader);
                setCurrentItemInternal(savedState.position, DEBUG, true);
                return;
            }
            this.mRestoredCurItem = savedState.position;
            this.mRestoredAdapterState = savedState.adapterState;
            this.mRestoredClassLoader = savedState.loader;
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public void addView(View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        android.view.ViewGroup.LayoutParams layoutParams2;
        if (checkLayoutParams(layoutParams)) {
            layoutParams2 = layoutParams;
        } else {
            layoutParams2 = generateLayoutParams(layoutParams);
        }
        LayoutParams layoutParams3 = (LayoutParams) layoutParams2;
        layoutParams3.isDecor |= view instanceof Decor;
        if (!this.mInLayout) {
            super.addView(view, i, layoutParams2);
        } else if (layoutParams3 == null || !layoutParams3.isDecor) {
            layoutParams3.needsMeasure = true;
            addViewInLayout(view, i, layoutParams2);
        } else {
            throw new IllegalStateException("Cannot add pager decor view during layout");
        }
    }

    ItemInfo infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(view, itemInfo.object)) {
                return itemInfo;
            }
        }
        return null;
    }

    ItemInfo infoForAnyChild(View view) {
        while (true) {
            ViewPager parent = view.getParent();
            if (parent == this) {
                return infoForChild(view);
            }
            if (parent != null && (parent instanceof View)) {
                view = parent;
            }
        }
        return null;
    }

    ItemInfo infoForPosition(int i) {
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i2);
            if (itemInfo.position == i) {
                return itemInfo;
            }
        }
        return null;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onMeasure(int i, int i2) {
        int i3;
        setMeasuredDimension(getDefaultSize(0, i), getDefaultSize(0, i2));
        int measuredWidth = getMeasuredWidth();
        this.mGutterSize = Math.min(measuredWidth / 10, this.mDefaultGutterSize);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            LayoutParams layoutParams;
            int i5;
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams != null && layoutParams.isDecor) {
                    int i6 = layoutParams.gravity & 7;
                    int i7 = layoutParams.gravity & 112;
                    i3 = Integer.MIN_VALUE;
                    i5 = Integer.MIN_VALUE;
                    Object obj = (i7 == 48 || i7 == 80) ? 1 : null;
                    Object obj2 = (i6 == 3 || i6 == 5) ? 1 : null;
                    if (obj != null) {
                        i3 = 1073741824;
                    } else if (obj2 != null) {
                        i5 = 1073741824;
                    }
                    if (layoutParams.width != -2) {
                        i7 = 1073741824;
                        i3 = layoutParams.width != -1 ? layoutParams.width : paddingLeft;
                    } else {
                        i7 = i3;
                        i3 = paddingLeft;
                    }
                    if (layoutParams.height != -2) {
                        i5 = 1073741824;
                        if (layoutParams.height != -1) {
                            measuredWidth = layoutParams.height;
                            childAt.measure(MeasureSpec.makeMeasureSpec(i3, i7), MeasureSpec.makeMeasureSpec(measuredWidth, i5));
                            if (obj != null) {
                                measuredHeight -= childAt.getMeasuredHeight();
                            } else if (obj2 != null) {
                                paddingLeft -= childAt.getMeasuredWidth();
                            }
                        }
                    }
                    measuredWidth = measuredHeight;
                    childAt.measure(MeasureSpec.makeMeasureSpec(i3, i7), MeasureSpec.makeMeasureSpec(measuredWidth, i5));
                    if (obj != null) {
                        measuredHeight -= childAt.getMeasuredHeight();
                    } else if (obj2 != null) {
                        paddingLeft -= childAt.getMeasuredWidth();
                    }
                }
            }
        }
        this.mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824);
        this.mChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = DEBUG;
        i3 = getChildCount();
        for (i5 = 0; i5 < i3; i5++) {
            View childAt2 = getChildAt(i5);
            if (childAt2.getVisibility() != 8) {
                layoutParams = (LayoutParams) childAt2.getLayoutParams();
                if (layoutParams == null || !layoutParams.isDecor) {
                    childAt2.measure(MeasureSpec.makeMeasureSpec((int) (layoutParams.widthFactor * ((float) paddingLeft)), 1073741824), this.mChildHeightMeasureSpec);
                }
            }
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            recomputeScrollPosition(i, i3, this.mPageMargin, this.mPageMargin);
        }
    }

    private void recomputeScrollPosition(int i, int i2, int i3, int i4) {
        if (i2 <= 0 || this.mItems.isEmpty()) {
            ItemInfo infoForPosition = infoForPosition(this.mCurItem);
            int min = (int) ((infoForPosition != null ? Math.min(infoForPosition.offset, this.mLastOffset) : 0.0f) * ((float) i));
            if (min != getScrollX()) {
                completeScroll(DEBUG);
                scrollTo(min, getScrollY());
                return;
            }
            return;
        }
        int scrollX = (int) (((float) (i + i3)) * (((float) getScrollX()) / ((float) (i2 + i4))));
        scrollTo(scrollX, getScrollY());
        if (!this.mScroller.isFinished()) {
            this.mScroller.startScroll(scrollX, 0, (int) (infoForPosition(this.mCurItem).offset * ((float) i)), 0, this.mScroller.getDuration() - this.mScroller.timePassed());
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int max;
        this.mInLayout = true;
        populate();
        this.mInLayout = DEBUG;
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i7 = 0;
        int i8 = 0;
        while (i8 < childCount) {
            LayoutParams layoutParams;
            int measuredWidth;
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i9 = layoutParams.gravity & 112;
                    switch (layoutParams.gravity & 7) {
                        case 1:
                            max = Math.max((i5 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                            break;
                        case 3:
                            max = paddingLeft;
                            paddingLeft = childAt.getMeasuredWidth() + paddingLeft;
                            break;
                        case 5:
                            measuredWidth = (i5 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                            max = measuredWidth;
                            break;
                        default:
                            max = paddingLeft;
                            break;
                    }
                    int i10;
                    switch (i9) {
                        case 16:
                            measuredWidth = Math.max((i6 - childAt.getMeasuredHeight()) / 2, paddingTop);
                            i10 = paddingBottom;
                            paddingBottom = paddingTop;
                            paddingTop = i10;
                            break;
                        case 48:
                            measuredWidth = childAt.getMeasuredHeight() + paddingTop;
                            i10 = paddingTop;
                            paddingTop = paddingBottom;
                            paddingBottom = measuredWidth;
                            measuredWidth = i10;
                            break;
                        case 80:
                            measuredWidth = (i6 - paddingBottom) - childAt.getMeasuredHeight();
                            i10 = paddingBottom + childAt.getMeasuredHeight();
                            paddingBottom = paddingTop;
                            paddingTop = i10;
                            break;
                        default:
                            measuredWidth = paddingTop;
                            i10 = paddingBottom;
                            paddingBottom = paddingTop;
                            paddingTop = i10;
                            break;
                    }
                    max += scrollX;
                    childAt.layout(max, measuredWidth, childAt.getMeasuredWidth() + max, childAt.getMeasuredHeight() + measuredWidth);
                    measuredWidth = i7 + 1;
                    i7 = paddingBottom;
                    paddingBottom = paddingTop;
                    paddingTop = paddingRight;
                    paddingRight = paddingLeft;
                    i8++;
                    paddingLeft = paddingRight;
                    paddingRight = paddingTop;
                    paddingTop = i7;
                    i7 = measuredWidth;
                }
            }
            measuredWidth = i7;
            i7 = paddingTop;
            paddingTop = paddingRight;
            paddingRight = paddingLeft;
            i8++;
            paddingLeft = paddingRight;
            paddingRight = paddingTop;
            paddingTop = i7;
            i7 = measuredWidth;
        }
        for (max = 0; max < childCount; max++) {
            View childAt2 = getChildAt(max);
            if (childAt2.getVisibility() != 8) {
                layoutParams = (LayoutParams) childAt2.getLayoutParams();
                if (!layoutParams.isDecor) {
                    ItemInfo infoForChild = infoForChild(childAt2);
                    if (infoForChild != null) {
                        scrollX = ((int) (infoForChild.offset * ((float) i5))) + paddingLeft;
                        if (layoutParams.needsMeasure) {
                            layoutParams.needsMeasure = DEBUG;
                            childAt2.measure(MeasureSpec.makeMeasureSpec((int) (layoutParams.widthFactor * ((float) ((i5 - paddingLeft) - paddingRight))), 1073741824), MeasureSpec.makeMeasureSpec((i6 - paddingTop) - paddingBottom, 1073741824));
                        }
                        childAt2.layout(scrollX, paddingTop, childAt2.getMeasuredWidth() + scrollX, childAt2.getMeasuredHeight() + paddingTop);
                    }
                }
            }
        }
        this.mTopPageBounds = paddingTop;
        this.mBottomPageBounds = i6 - paddingBottom;
        this.mDecorChildCount = i7;
        this.mFirstLayout = DEBUG;
    }

    public void computeScroll() {
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.mScroller.getCurrX();
        int currY = this.mScroller.getCurrY();
        if (!(scrollX == currX && scrollY == currY)) {
            scrollTo(currX, currY);
            if (!pageScrolled(currX)) {
                this.mScroller.abortAnimation();
                scrollTo(0, currY);
            }
        }
        ah.m145b(this);
    }

    private boolean pageScrolled(int i) {
        if (this.mItems.size() == 0) {
            this.mCalledSuper = DEBUG;
            onPageScrolled(0, 0.0f, 0);
            if (this.mCalledSuper) {
                return DEBUG;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
        int width = getWidth();
        int i2 = this.mPageMargin + width;
        float f = ((float) this.mPageMargin) / ((float) width);
        int i3 = infoForCurrentScrollPosition.position;
        float f2 = ((((float) i) / ((float) width)) - infoForCurrentScrollPosition.offset) / (infoForCurrentScrollPosition.widthFactor + f);
        width = (int) (((float) i2) * f2);
        this.mCalledSuper = DEBUG;
        onPageScrolled(i3, f2, width);
        if (this.mCalledSuper) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    protected void onPageScrolled(int i, float f, int i2) {
        int paddingLeft;
        int paddingRight;
        int i3;
        if (this.mDecorChildCount > 0) {
            int scrollX = getScrollX();
            paddingLeft = getPaddingLeft();
            paddingRight = getPaddingRight();
            int width = getWidth();
            int childCount = getChildCount();
            i3 = 0;
            while (i3 < childCount) {
                int i4;
                View childAt = getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int max;
                    switch (layoutParams.gravity & 7) {
                        case 1:
                            max = Math.max((width - childAt.getMeasuredWidth()) / 2, paddingLeft);
                            i4 = paddingRight;
                            paddingRight = paddingLeft;
                            paddingLeft = i4;
                            break;
                        case 3:
                            max = childAt.getWidth() + paddingLeft;
                            i4 = paddingLeft;
                            paddingLeft = paddingRight;
                            paddingRight = max;
                            max = i4;
                            break;
                        case 5:
                            max = (width - paddingRight) - childAt.getMeasuredWidth();
                            i4 = paddingRight + childAt.getMeasuredWidth();
                            paddingRight = paddingLeft;
                            paddingLeft = i4;
                            break;
                        default:
                            max = paddingLeft;
                            i4 = paddingRight;
                            paddingRight = paddingLeft;
                            paddingLeft = i4;
                            break;
                    }
                    max = (max + scrollX) - childAt.getLeft();
                    if (max != 0) {
                        childAt.offsetLeftAndRight(max);
                    }
                } else {
                    i4 = paddingRight;
                    paddingRight = paddingLeft;
                    paddingLeft = i4;
                }
                i3++;
                i4 = paddingLeft;
                paddingLeft = paddingRight;
                paddingRight = i4;
            }
        }
        if (this.mSeenPositionMin < 0 || i < this.mSeenPositionMin) {
            this.mSeenPositionMin = i;
        }
        if (this.mSeenPositionMax < 0 || FloatMath.ceil(((float) i) + f) > ((float) this.mSeenPositionMax)) {
            this.mSeenPositionMax = i + 1;
        }
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(i, f, i2);
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(i, f, i2);
        }
        if (this.mPageTransformer != null) {
            paddingRight = getScrollX();
            i3 = getChildCount();
            for (paddingLeft = 0; paddingLeft < i3; paddingLeft++) {
                View childAt2 = getChildAt(paddingLeft);
                if (!((LayoutParams) childAt2.getLayoutParams()).isDecor) {
                    this.mPageTransformer.transformPage(childAt2, ((float) (childAt2.getLeft() - paddingRight)) / ((float) getWidth()));
                }
            }
        }
        this.mCalledSuper = true;
    }

    private void completeScroll(boolean z) {
        int scrollX;
        boolean z2 = this.mScrollState == 2 ? true : DEBUG;
        if (z2) {
            setScrollingCacheEnabled(DEBUG);
            this.mScroller.abortAnimation();
            scrollX = getScrollX();
            int scrollY = getScrollY();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (!(scrollX == currX && scrollY == currY)) {
                scrollTo(currX, currY);
            }
        }
        this.mPopulatePending = DEBUG;
        boolean z3 = z2;
        for (scrollX = 0; scrollX < this.mItems.size(); scrollX++) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(scrollX);
            if (itemInfo.scrolling) {
                itemInfo.scrolling = DEBUG;
                z3 = true;
            }
        }
        if (!z3) {
            return;
        }
        if (z) {
            ah.m143a((View) this, this.mEndScrollRunnable);
        } else {
            this.mEndScrollRunnable.run();
        }
    }

    private boolean isGutterDrag(float f, float f2) {
        return ((f >= ((float) this.mGutterSize) || f2 <= 0.0f) && (f <= ((float) (getWidth() - this.mGutterSize)) || f2 >= 0.0f)) ? DEBUG : true;
    }

    private void enableLayers(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            int i2;
            if (z) {
                i2 = 2;
            } else {
                i2 = 0;
            }
            ah.m141a(getChildAt(i), i2, null);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            this.mIsBeingDragged = DEBUG;
            this.mIsUnableToDrag = DEBUG;
            this.mActivePointerId = -1;
            if (this.mVelocityTracker == null) {
                return DEBUG;
            }
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            return DEBUG;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return DEBUG;
            }
        }
        switch (action) {
            case 0:
                float x = motionEvent.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                this.mLastMotionY = motionEvent.getY();
                this.mActivePointerId = C0090r.m256b(motionEvent, 0);
                this.mIsUnableToDrag = DEBUG;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = DEBUG;
                    populate();
                    this.mIsBeingDragged = true;
                    setScrollState(1);
                    break;
                }
                completeScroll(DEBUG);
                this.mIsBeingDragged = DEBUG;
                break;
            case 2:
                action = this.mActivePointerId;
                if (action != -1) {
                    action = C0090r.m255a(motionEvent, action);
                    float c = C0090r.m257c(motionEvent, action);
                    float f = c - this.mLastMotionX;
                    float abs = Math.abs(f);
                    float d = C0090r.m258d(motionEvent, action);
                    float abs2 = Math.abs(d - this.mLastMotionY);
                    if (f == 0.0f || isGutterDrag(this.mLastMotionX, f) || !canScroll(this, DEBUG, (int) f, (int) c, (int) d)) {
                        if (abs > ((float) this.mTouchSlop) && abs > abs2) {
                            this.mIsBeingDragged = true;
                            setScrollState(1);
                            this.mLastMotionX = f > 0.0f ? this.mInitialMotionX + ((float) this.mTouchSlop) : this.mInitialMotionX - ((float) this.mTouchSlop);
                            setScrollingCacheEnabled(true);
                        } else if (abs2 > ((float) this.mTouchSlop)) {
                            this.mIsUnableToDrag = true;
                        }
                        if (this.mIsBeingDragged && performDrag(c)) {
                            ah.m145b(this);
                            break;
                        }
                    }
                    this.mLastMotionX = c;
                    this.mInitialMotionX = c;
                    this.mLastMotionY = d;
                    this.mIsUnableToDrag = true;
                    return DEBUG;
                }
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                break;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = DEBUG;
        if (this.mFakeDragging) {
            return true;
        }
        if (motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) {
            return DEBUG;
        }
        if (this.mAdapter == null || this.mAdapter.getCount() == 0) {
            return DEBUG;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        float x;
        int a;
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.mScroller.abortAnimation();
                this.mPopulatePending = DEBUG;
                populate();
                this.mIsBeingDragged = true;
                setScrollState(1);
                x = motionEvent.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                this.mActivePointerId = C0090r.m256b(motionEvent, 0);
                break;
            case 1:
                if (this.mIsBeingDragged) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    a = (int) ad.m136a(velocityTracker, this.mActivePointerId);
                    this.mPopulatePending = true;
                    int width = getWidth();
                    int scrollX = getScrollX();
                    ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
                    setCurrentItemInternal(determineTargetPage(infoForCurrentScrollPosition.position, ((((float) scrollX) / ((float) width)) - infoForCurrentScrollPosition.offset) / infoForCurrentScrollPosition.widthFactor, a, (int) (C0090r.m257c(motionEvent, C0090r.m255a(motionEvent, this.mActivePointerId)) - this.mInitialMotionX)), true, true, a);
                    this.mActivePointerId = -1;
                    endDrag();
                    z = this.mRightEdge.m77c() | this.mLeftEdge.m77c();
                    break;
                }
                break;
            case 2:
                if (!this.mIsBeingDragged) {
                    a = C0090r.m255a(motionEvent, this.mActivePointerId);
                    float c = C0090r.m257c(motionEvent, a);
                    float abs = Math.abs(c - this.mLastMotionX);
                    x = Math.abs(C0090r.m258d(motionEvent, a) - this.mLastMotionY);
                    if (abs > ((float) this.mTouchSlop) && abs > x) {
                        this.mIsBeingDragged = true;
                        if (c - this.mInitialMotionX > 0.0f) {
                            x = this.mInitialMotionX + ((float) this.mTouchSlop);
                        } else {
                            x = this.mInitialMotionX - ((float) this.mTouchSlop);
                        }
                        this.mLastMotionX = x;
                        setScrollState(1);
                        setScrollingCacheEnabled(true);
                    }
                }
                if (this.mIsBeingDragged) {
                    z = performDrag(C0090r.m257c(motionEvent, C0090r.m255a(motionEvent, this.mActivePointerId))) | 0;
                    break;
                }
                break;
            case 3:
                if (this.mIsBeingDragged) {
                    scrollToItem(this.mCurItem, true, 0, DEBUG);
                    this.mActivePointerId = -1;
                    endDrag();
                    z = this.mRightEdge.m77c() | this.mLeftEdge.m77c();
                    break;
                }
                break;
            case 5:
                a = C0090r.m254a(motionEvent);
                this.mLastMotionX = C0090r.m257c(motionEvent, a);
                this.mActivePointerId = C0090r.m256b(motionEvent, a);
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                this.mLastMotionX = C0090r.m257c(motionEvent, C0090r.m255a(motionEvent, this.mActivePointerId));
                break;
        }
        if (z) {
            ah.m145b(this);
        }
        return true;
    }

    private boolean performDrag(float f) {
        boolean z;
        float f2;
        boolean z2 = true;
        boolean z3 = DEBUG;
        float f3 = this.mLastMotionX - f;
        this.mLastMotionX = f;
        float scrollX = ((float) getScrollX()) + f3;
        int width = getWidth();
        float f4 = ((float) width) * this.mFirstOffset;
        float f5 = ((float) width) * this.mLastOffset;
        ItemInfo itemInfo = (ItemInfo) this.mItems.get(0);
        ItemInfo itemInfo2 = (ItemInfo) this.mItems.get(this.mItems.size() - 1);
        if (itemInfo.position != 0) {
            f4 = itemInfo.offset * ((float) width);
            z = DEBUG;
        } else {
            z = true;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f2 = itemInfo2.offset * ((float) width);
            z2 = DEBUG;
        } else {
            f2 = f5;
        }
        if (scrollX < f4) {
            if (z) {
                z3 = this.mLeftEdge.m74a(Math.abs(f4 - scrollX) / ((float) width));
            }
        } else if (scrollX > f2) {
            if (z2) {
                z3 = this.mRightEdge.m74a(Math.abs(scrollX - f2) / ((float) width));
            }
            f4 = f2;
        } else {
            f4 = scrollX;
        }
        this.mLastMotionX += f4 - ((float) ((int) f4));
        scrollTo((int) f4, getScrollY());
        pageScrolled((int) f4);
        return z3;
    }

    private ItemInfo infoForCurrentScrollPosition() {
        float f;
        int width = getWidth();
        float scrollX = width > 0 ? ((float) getScrollX()) / ((float) width) : 0.0f;
        if (width > 0) {
            f = ((float) this.mPageMargin) / ((float) width);
        } else {
            f = 0.0f;
        }
        float f2 = 0.0f;
        float f3 = 0.0f;
        int i = -1;
        int i2 = 0;
        Object obj = 1;
        ItemInfo itemInfo = null;
        while (i2 < this.mItems.size()) {
            int i3;
            ItemInfo itemInfo2;
            ItemInfo itemInfo3 = (ItemInfo) this.mItems.get(i2);
            ItemInfo itemInfo4;
            if (obj != null || itemInfo3.position == i + 1) {
                itemInfo4 = itemInfo3;
                i3 = i2;
                itemInfo2 = itemInfo4;
            } else {
                itemInfo3 = this.mTempItem;
                itemInfo3.offset = (f2 + f3) + f;
                itemInfo3.position = i + 1;
                itemInfo3.widthFactor = this.mAdapter.getPageWidth(itemInfo3.position);
                itemInfo4 = itemInfo3;
                i3 = i2 - 1;
                itemInfo2 = itemInfo4;
            }
            f2 = itemInfo2.offset;
            f3 = (itemInfo2.widthFactor + f2) + f;
            if (obj == null && scrollX < f2) {
                return itemInfo;
            }
            if (scrollX < f3 || i3 == this.mItems.size() - 1) {
                return itemInfo2;
            }
            f3 = f2;
            i = itemInfo2.position;
            obj = null;
            f2 = itemInfo2.widthFactor;
            itemInfo = itemInfo2;
            i2 = i3 + 1;
        }
        return itemInfo;
    }

    private int determineTargetPage(int i, float f, int i2, int i3) {
        if (Math.abs(i3) <= this.mFlingDistance || Math.abs(i2) <= this.mMinimumVelocity) {
            if (this.mSeenPositionMin >= 0 && this.mSeenPositionMin < i && f < 0.5f) {
                i++;
            } else if (this.mSeenPositionMax < 0 || this.mSeenPositionMax <= i + 1 || f < 0.5f) {
                i = (int) ((((float) i) + f) + 0.5f);
            } else {
                i--;
            }
        } else if (i2 <= 0) {
            i++;
        }
        if (this.mItems.size() <= 0) {
            return i;
        }
        return Math.max(((ItemInfo) this.mItems.get(0)).position, Math.min(i, ((ItemInfo) this.mItems.get(this.mItems.size() - 1)).position));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int i = 0;
        int a = ah.m140a(this);
        if (a == 0 || (a == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
            int width;
            if (!this.mLeftEdge.m73a()) {
                a = canvas.save();
                i = (getHeight() - getPaddingTop()) - getPaddingBottom();
                width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float) ((-i) + getPaddingTop()), this.mFirstOffset * ((float) width));
                this.mLeftEdge.m72a(i, width);
                i = this.mLeftEdge.m75a(canvas) | 0;
                canvas.restoreToCount(a);
            }
            if (!this.mRightEdge.m73a()) {
                a = canvas.save();
                width = getWidth();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate((float) (-getPaddingTop()), (-(this.mLastOffset + 1.0f)) * ((float) width));
                this.mRightEdge.m72a(height, width);
                i |= this.mRightEdge.m75a(canvas);
                canvas.restoreToCount(a);
            }
        } else {
            this.mLeftEdge.m76b();
            this.mRightEdge.m76b();
        }
        if (i != 0) {
            ah.m145b(this);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int scrollX = getScrollX();
            int width = getWidth();
            float f = ((float) this.mPageMargin) / ((float) width);
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(0);
            float f2 = itemInfo.offset;
            int size = this.mItems.size();
            int i = itemInfo.position;
            int i2 = ((ItemInfo) this.mItems.get(size - 1)).position;
            int i3 = 0;
            int i4 = i;
            while (i4 < i2) {
                float f3;
                while (i4 > itemInfo.position && i3 < size) {
                    i3++;
                    itemInfo = (ItemInfo) this.mItems.get(i3);
                }
                if (i4 == itemInfo.position) {
                    f3 = (itemInfo.offset + itemInfo.widthFactor) * ((float) width);
                    f2 = (itemInfo.offset + itemInfo.widthFactor) + f;
                } else {
                    float pageWidth = this.mAdapter.getPageWidth(i4);
                    f3 = (f2 + pageWidth) * ((float) width);
                    f2 += pageWidth + f;
                }
                if (((float) this.mPageMargin) + f3 > ((float) scrollX)) {
                    this.mMarginDrawable.setBounds((int) f3, this.mTopPageBounds, (int) ((((float) this.mPageMargin) + f3) + 0.5f), this.mBottomPageBounds);
                    this.mMarginDrawable.draw(canvas);
                }
                if (f3 <= ((float) (scrollX + width))) {
                    i4++;
                } else {
                    return;
                }
            }
        }
    }

    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return DEBUG;
        }
        this.mFakeDragging = true;
        setScrollState(1);
        this.mLastMotionX = 0.0f;
        this.mInitialMotionX = 0.0f;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, 0.0f, 0.0f, 0);
        this.mVelocityTracker.addMovement(obtain);
        obtain.recycle();
        this.mFakeDragBeginTime = uptimeMillis;
        return true;
    }

    public void endFakeDrag() {
        if (this.mFakeDragging) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
            int a = (int) ad.m136a(velocityTracker, this.mActivePointerId);
            this.mPopulatePending = true;
            int width = getWidth();
            int scrollX = getScrollX();
            ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
            setCurrentItemInternal(determineTargetPage(infoForCurrentScrollPosition.position, ((((float) scrollX) / ((float) width)) - infoForCurrentScrollPosition.offset) / infoForCurrentScrollPosition.widthFactor, a, (int) (this.mLastMotionX - this.mInitialMotionX)), true, true, a);
            endDrag();
            this.mFakeDragging = DEBUG;
            return;
        }
        throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
    }

    public void fakeDragBy(float f) {
        if (this.mFakeDragging) {
            float f2;
            float f3;
            this.mLastMotionX += f;
            float scrollX = ((float) getScrollX()) - f;
            int width = getWidth();
            float f4 = ((float) width) * this.mFirstOffset;
            float f5 = ((float) width) * this.mLastOffset;
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(0);
            ItemInfo itemInfo2 = (ItemInfo) this.mItems.get(this.mItems.size() - 1);
            if (itemInfo.position != 0) {
                f2 = itemInfo.offset * ((float) width);
            } else {
                f2 = f4;
            }
            if (itemInfo2.position != this.mAdapter.getCount() - 1) {
                f3 = itemInfo2.offset * ((float) width);
            } else {
                f3 = f5;
            }
            if (scrollX >= f2) {
                if (scrollX > f3) {
                    f2 = f3;
                } else {
                    f2 = scrollX;
                }
            }
            this.mLastMotionX += f2 - ((float) ((int) f2));
            scrollTo((int) f2, getScrollY());
            pageScrolled((int) f2);
            MotionEvent obtain = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, this.mLastMotionX, 0.0f, 0);
            this.mVelocityTracker.addMovement(obtain);
            obtain.recycle();
            return;
        }
        throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int a = C0090r.m254a(motionEvent);
        if (C0090r.m256b(motionEvent, a) == this.mActivePointerId) {
            a = a == 0 ? 1 : 0;
            this.mLastMotionX = C0090r.m257c(motionEvent, a);
            this.mActivePointerId = C0090r.m256b(motionEvent, a);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = DEBUG;
        this.mIsUnableToDrag = DEBUG;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled != z) {
            this.mScrollingCacheEnabled = z;
        }
    }

    protected boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (i2 + scrollX >= childAt.getLeft() && i2 + scrollX < childAt.getRight() && i3 + scrollY >= childAt.getTop() && i3 + scrollY < childAt.getBottom()) {
                    if (canScroll(childAt, true, i, (i2 + scrollX) - childAt.getLeft(), (i3 + scrollY) - childAt.getTop())) {
                        return true;
                    }
                }
            }
        }
        if (z && ah.m144a(view, -i)) {
            return true;
        }
        return DEBUG;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return (super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent)) ? true : DEBUG;
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) {
            return DEBUG;
        }
        switch (keyEvent.getKeyCode()) {
            case 21:
                return arrowScroll(17);
            case 22:
                return arrowScroll(66);
            case 61:
                if (VERSION.SDK_INT < 11) {
                    return DEBUG;
                }
                if (C0086n.m244a(keyEvent)) {
                    return arrowScroll(2);
                }
                if (C0086n.m245a(keyEvent, 1)) {
                    return arrowScroll(1);
                }
                return DEBUG;
            default:
                return DEBUG;
        }
    }

    public boolean arrowScroll(int i) {
        boolean pageLeft;
        View findFocus = findFocus();
        if (findFocus == this) {
            findFocus = null;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, i);
        if (findNextFocus == null || findNextFocus == findFocus) {
            if (i == 17 || i == 1) {
                pageLeft = pageLeft();
            } else {
                if (i == 66 || i == 2) {
                    pageLeft = pageRight();
                }
                pageLeft = DEBUG;
            }
        } else if (i == 17) {
            r1 = getChildRectInPagerCoordinates(this.mTempRect, findNextFocus).left;
            r3 = getChildRectInPagerCoordinates(this.mTempRect, findFocus).left;
            if (findFocus == null || r1 < r3) {
                pageLeft = findNextFocus.requestFocus();
            } else {
                pageLeft = pageLeft();
            }
        } else {
            if (i == 66) {
                r1 = getChildRectInPagerCoordinates(this.mTempRect, findNextFocus).left;
                r3 = getChildRectInPagerCoordinates(this.mTempRect, findFocus).left;
                if (findFocus == null || r1 > r3) {
                    pageLeft = findNextFocus.requestFocus();
                } else {
                    pageLeft = pageRight();
                }
            }
            pageLeft = DEBUG;
        }
        if (pageLeft) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
        }
        return pageLeft;
    }

    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        Rect rect2;
        if (rect == null) {
            rect2 = new Rect();
        } else {
            rect2 = rect;
        }
        if (view == null) {
            rect2.set(0, 0, 0, 0);
            return rect2;
        }
        rect2.left = view.getLeft();
        rect2.right = view.getRight();
        rect2.top = view.getTop();
        rect2.bottom = view.getBottom();
        ViewPager parent = view.getParent();
        while ((parent instanceof ViewGroup) && parent != this) {
            ViewGroup viewGroup = parent;
            rect2.left += viewGroup.getLeft();
            rect2.right += viewGroup.getRight();
            rect2.top += viewGroup.getTop();
            rect2.bottom += viewGroup.getBottom();
            parent = viewGroup.getParent();
        }
        return rect2;
    }

    boolean pageLeft() {
        if (this.mCurItem <= 0) {
            return DEBUG;
        }
        setCurrentItem(this.mCurItem - 1, true);
        return true;
    }

    boolean pageRight() {
        if (this.mAdapter == null || this.mCurItem >= this.mAdapter.getCount() - 1) {
            return DEBUG;
        }
        setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    public void addFocusables(ArrayList arrayList, int i, int i2) {
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() == 0) {
                    ItemInfo infoForChild = infoForChild(childAt);
                    if (infoForChild != null && infoForChild.position == this.mCurItem) {
                        childAt.addFocusables(arrayList, i, i2);
                    }
                }
            }
        }
        if ((descendantFocusability == 262144 && size != arrayList.size()) || !isFocusable()) {
            return;
        }
        if (((i2 & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && arrayList != null) {
            arrayList.add(this);
        }
    }

    public void addTouchables(ArrayList arrayList) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                ItemInfo infoForChild = infoForChild(childAt);
                if (infoForChild != null && infoForChild.position == this.mCurItem) {
                    childAt.addTouchables(arrayList);
                }
            }
        }
    }

    protected boolean onRequestFocusInDescendants(int i, Rect rect) {
        int i2;
        int i3 = -1;
        int childCount = getChildCount();
        if ((i & 2) != 0) {
            i3 = 1;
            i2 = 0;
        } else {
            i2 = childCount - 1;
            childCount = -1;
        }
        while (i2 != childCount) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0) {
                ItemInfo infoForChild = infoForChild(childAt);
                if (infoForChild != null && infoForChild.position == this.mCurItem && childAt.requestFocus(i, rect)) {
                    return true;
                }
            }
            i2 += i3;
        }
        return DEBUG;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                ItemInfo infoForChild = infoForChild(childAt);
                if (infoForChild != null && infoForChild.position == this.mCurItem && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                    return true;
                }
            }
        }
        return DEBUG;
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return ((layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams)) ? true : DEBUG;
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }
}
