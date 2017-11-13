package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.p000a.C0008i;
import android.support.v4.p000a.C0018j;
import android.support.v4.p002c.C0047a;
import android.support.v4.p002c.C0049c;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

class LoaderManagerImpl extends LoaderManager {
    static boolean DEBUG = false;
    static final String TAG = "LoaderManager";
    FragmentActivity mActivity;
    boolean mCreatingLoader;
    final C0049c mInactiveLoaders = new C0049c();
    final C0049c mLoaders = new C0049c();
    boolean mRetaining;
    boolean mRetainingStarted;
    boolean mStarted;
    final String mWho;

    final class LoaderInfo implements C0018j {
        final Bundle mArgs;
        LoaderCallbacks mCallbacks;
        Object mData;
        boolean mDeliveredData;
        boolean mDestroyed;
        boolean mHaveData;
        final int mId;
        boolean mListenerRegistered;
        C0008i mLoader;
        LoaderInfo mPendingLoader;
        boolean mReportNextStart;
        boolean mRetaining;
        boolean mRetainingStarted;
        boolean mStarted;

        public LoaderInfo(int i, Bundle bundle, LoaderCallbacks loaderCallbacks) {
            this.mId = i;
            this.mArgs = bundle;
            this.mCallbacks = loaderCallbacks;
        }

        final void start() {
            if (this.mRetaining && this.mRetainingStarted) {
                this.mStarted = true;
            } else if (!this.mStarted) {
                this.mStarted = true;
                if (LoaderManagerImpl.DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Starting: " + this);
                }
                if (this.mLoader == null && this.mCallbacks != null) {
                    this.mLoader = this.mCallbacks.onCreateLoader(this.mId, this.mArgs);
                }
                if (this.mLoader == null) {
                    return;
                }
                if (!this.mLoader.getClass().isMemberClass() || Modifier.isStatic(this.mLoader.getClass().getModifiers())) {
                    if (!this.mListenerRegistered) {
                        this.mLoader.m19a(this.mId, this);
                        this.mListenerRegistered = true;
                    }
                    this.mLoader.m22d();
                    return;
                }
                throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.mLoader);
            }
        }

        final void retain() {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Retaining: " + this);
            }
            this.mRetaining = true;
            this.mRetainingStarted = this.mStarted;
            this.mStarted = false;
            this.mCallbacks = null;
        }

        final void finishRetain() {
            if (this.mRetaining) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Finished Retaining: " + this);
                }
                this.mRetaining = false;
                if (!(this.mStarted == this.mRetainingStarted || this.mStarted)) {
                    stop();
                }
            }
            if (this.mStarted && this.mHaveData && !this.mReportNextStart) {
                callOnLoadFinished(this.mLoader, this.mData);
            }
        }

        final void reportStart() {
            if (this.mStarted && this.mReportNextStart) {
                this.mReportNextStart = false;
                if (this.mHaveData) {
                    callOnLoadFinished(this.mLoader, this.mData);
                }
            }
        }

        final void stop() {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Stopping: " + this);
            }
            this.mStarted = false;
            if (!this.mRetaining && this.mLoader != null && this.mListenerRegistered) {
                this.mListenerRegistered = false;
                this.mLoader.m20a(this);
                this.mLoader.m25g();
            }
        }

        final void destroy() {
            String str;
            LoaderCallbacks loaderCallbacks = null;
            Object obj = null;
            while (true) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Destroying: " + this);
                }
                this.mDestroyed = true;
                boolean z = this.mDeliveredData;
                this.mDeliveredData = obj;
                if (this.mCallbacks != null && this.mLoader != null && this.mHaveData && z) {
                    if (LoaderManagerImpl.DEBUG) {
                        Log.v(LoaderManagerImpl.TAG, "  Reseting: " + this);
                    }
                    if (LoaderManagerImpl.this.mActivity != null) {
                        String str2 = LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause;
                        LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = "onLoaderReset";
                        str = str2;
                    } else {
                        Object obj2 = loaderCallbacks;
                    }
                    try {
                        this.mCallbacks.onLoaderReset(this.mLoader);
                    } finally {
                        loaderCallbacks = LoaderManagerImpl.this.mActivity;
                        if (loaderCallbacks != null) {
                            loaderCallbacks = LoaderManagerImpl.this.mActivity.mFragments;
                            loaderCallbacks.mNoTransactionsBecause = str;
                        }
                    }
                }
                this.mCallbacks = loaderCallbacks;
                this.mData = loaderCallbacks;
                this.mHaveData = obj;
                if (this.mLoader != null) {
                    if (this.mListenerRegistered) {
                        this.mListenerRegistered = obj;
                        this.mLoader.m20a(this);
                    }
                    this.mLoader.m27i();
                }
                if (this.mPendingLoader != null) {
                    this = this.mPendingLoader;
                } else {
                    return;
                }
            }
        }

        public final void onLoadComplete(C0008i c0008i, Object obj) {
            if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "onLoadComplete: " + this);
            }
            if (this.mDestroyed) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Ignoring load complete -- destroyed");
                }
            } else if (LoaderManagerImpl.this.mLoaders.m59a(this.mId) == this) {
                LoaderInfo loaderInfo = this.mPendingLoader;
                if (loaderInfo != null) {
                    if (LoaderManagerImpl.DEBUG) {
                        Log.v(LoaderManagerImpl.TAG, "  Switching to pending loader: " + loaderInfo);
                    }
                    this.mPendingLoader = null;
                    LoaderManagerImpl.this.mLoaders.m60a(this.mId, null);
                    destroy();
                    LoaderManagerImpl.this.installLoader(loaderInfo);
                    return;
                }
                if (!(this.mData == obj && this.mHaveData)) {
                    this.mData = obj;
                    this.mHaveData = true;
                    if (this.mStarted) {
                        callOnLoadFinished(c0008i, obj);
                    }
                }
                loaderInfo = (LoaderInfo) LoaderManagerImpl.this.mInactiveLoaders.m59a(this.mId);
                if (!(loaderInfo == null || loaderInfo == this)) {
                    loaderInfo.mDeliveredData = false;
                    loaderInfo.destroy();
                    LoaderManagerImpl.this.mInactiveLoaders.m62b(this.mId);
                }
                if (LoaderManagerImpl.this.mActivity != null && !LoaderManagerImpl.this.hasRunningLoaders()) {
                    LoaderManagerImpl.this.mActivity.mFragments.startPendingDeferredFragments();
                }
            } else if (LoaderManagerImpl.DEBUG) {
                Log.v(LoaderManagerImpl.TAG, "  Ignoring load complete -- not active");
            }
        }

        final void callOnLoadFinished(C0008i c0008i, Object obj) {
            String str;
            if (this.mCallbacks != null) {
                String str2;
                if (LoaderManagerImpl.this.mActivity != null) {
                    str2 = LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause;
                    LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = "onLoadFinished";
                    str = str2;
                } else {
                    str = null;
                }
                try {
                    if (LoaderManagerImpl.DEBUG) {
                        str2 = LoaderManagerImpl.TAG;
                        StringBuilder append = new StringBuilder("  onLoadFinished in ").append(c0008i).append(": ");
                        StringBuilder stringBuilder = new StringBuilder(64);
                        C0047a.m53a(obj, stringBuilder);
                        stringBuilder.append("}");
                        Log.v(str2, append.append(stringBuilder.toString()).toString());
                    }
                    this.mCallbacks.onLoadFinished(c0008i, obj);
                    this.mDeliveredData = true;
                } finally {
                    if (LoaderManagerImpl.this.mActivity != null) {
                        LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = str;
                    }
                }
            }
        }

        public final String toString() {
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append("LoaderInfo{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" #");
            stringBuilder.append(this.mId);
            stringBuilder.append(" : ");
            C0047a.m53a(this.mLoader, stringBuilder);
            stringBuilder.append("}}");
            return stringBuilder.toString();
        }

        public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            while (true) {
                printWriter.print(str);
                printWriter.print("mId=");
                printWriter.print(this.mId);
                printWriter.print(" mArgs=");
                printWriter.println(this.mArgs);
                printWriter.print(str);
                printWriter.print("mCallbacks=");
                printWriter.println(this.mCallbacks);
                printWriter.print(str);
                printWriter.print("mLoader=");
                printWriter.println(this.mLoader);
                if (this.mLoader != null) {
                    this.mLoader.mo12a(str + "  ", fileDescriptor, printWriter, strArr);
                }
                if (this.mHaveData || this.mDeliveredData) {
                    printWriter.print(str);
                    printWriter.print("mHaveData=");
                    printWriter.print(this.mHaveData);
                    printWriter.print("  mDeliveredData=");
                    printWriter.println(this.mDeliveredData);
                    printWriter.print(str);
                    printWriter.print("mData=");
                    printWriter.println(this.mData);
                }
                printWriter.print(str);
                printWriter.print("mStarted=");
                printWriter.print(this.mStarted);
                printWriter.print(" mReportNextStart=");
                printWriter.print(this.mReportNextStart);
                printWriter.print(" mDestroyed=");
                printWriter.println(this.mDestroyed);
                printWriter.print(str);
                printWriter.print("mRetaining=");
                printWriter.print(this.mRetaining);
                printWriter.print(" mRetainingStarted=");
                printWriter.print(this.mRetainingStarted);
                printWriter.print(" mListenerRegistered=");
                printWriter.println(this.mListenerRegistered);
                if (this.mPendingLoader != null) {
                    printWriter.print(str);
                    printWriter.println("Pending Loader ");
                    printWriter.print(this.mPendingLoader);
                    printWriter.println(":");
                    this = this.mPendingLoader;
                    str = str + "  ";
                } else {
                    return;
                }
            }
        }
    }

    LoaderManagerImpl(String str, FragmentActivity fragmentActivity, boolean z) {
        this.mWho = str;
        this.mActivity = fragmentActivity;
        this.mStarted = z;
    }

    void updateActivity(FragmentActivity fragmentActivity) {
        this.mActivity = fragmentActivity;
    }

    private LoaderInfo createLoader(int i, Bundle bundle, LoaderCallbacks loaderCallbacks) {
        LoaderInfo loaderInfo = new LoaderInfo(i, bundle, loaderCallbacks);
        loaderInfo.mLoader = loaderCallbacks.onCreateLoader(i, bundle);
        return loaderInfo;
    }

    private LoaderInfo createAndInstallLoader(int i, Bundle bundle, LoaderCallbacks loaderCallbacks) {
        try {
            this.mCreatingLoader = true;
            LoaderInfo createLoader = createLoader(i, bundle, loaderCallbacks);
            installLoader(createLoader);
            return createLoader;
        } finally {
            this.mCreatingLoader = false;
        }
    }

    void installLoader(LoaderInfo loaderInfo) {
        this.mLoaders.m60a(loaderInfo.mId, loaderInfo);
        if (this.mStarted) {
            loaderInfo.start();
        }
    }

    public C0008i initLoader(int i, Bundle bundle, LoaderCallbacks loaderCallbacks) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo loaderInfo = (LoaderInfo) this.mLoaders.m59a(i);
        if (DEBUG) {
            Log.v(TAG, "initLoader in " + this + ": args=" + bundle);
        }
        if (loaderInfo == null) {
            loaderInfo = createAndInstallLoader(i, bundle, loaderCallbacks);
            if (DEBUG) {
                Log.v(TAG, "  Created new loader " + loaderInfo);
            }
        } else {
            if (DEBUG) {
                Log.v(TAG, "  Re-using existing loader " + loaderInfo);
            }
            loaderInfo.mCallbacks = loaderCallbacks;
        }
        if (loaderInfo.mHaveData && this.mStarted) {
            loaderInfo.callOnLoadFinished(loaderInfo.mLoader, loaderInfo.mData);
        }
        return loaderInfo.mLoader;
    }

    public C0008i restartLoader(int i, Bundle bundle, LoaderCallbacks loaderCallbacks) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo loaderInfo = (LoaderInfo) this.mLoaders.m59a(i);
        if (DEBUG) {
            Log.v(TAG, "restartLoader in " + this + ": args=" + bundle);
        }
        if (loaderInfo != null) {
            LoaderInfo loaderInfo2 = (LoaderInfo) this.mInactiveLoaders.m59a(i);
            if (loaderInfo2 != null) {
                if (loaderInfo.mHaveData) {
                    if (DEBUG) {
                        Log.v(TAG, "  Removing last inactive loader: " + loaderInfo);
                    }
                    loaderInfo2.mDeliveredData = false;
                    loaderInfo2.destroy();
                } else if (loaderInfo.mStarted) {
                    if (loaderInfo.mPendingLoader != null) {
                        if (DEBUG) {
                            Log.v(TAG, "  Removing pending loader: " + loaderInfo.mPendingLoader);
                        }
                        loaderInfo.mPendingLoader.destroy();
                        loaderInfo.mPendingLoader = null;
                    }
                    if (DEBUG) {
                        Log.v(TAG, "  Enqueuing as new pending loader");
                    }
                    loaderInfo.mPendingLoader = createLoader(i, bundle, loaderCallbacks);
                    return loaderInfo.mPendingLoader.mLoader;
                } else {
                    if (DEBUG) {
                        Log.v(TAG, "  Current loader is stopped; replacing");
                    }
                    this.mLoaders.m60a(i, null);
                    loaderInfo.destroy();
                }
            } else if (DEBUG) {
                Log.v(TAG, "  Making last loader inactive: " + loaderInfo);
            }
            loaderInfo.mLoader.m26h();
            this.mInactiveLoaders.m60a(i, loaderInfo);
        }
        return createAndInstallLoader(i, bundle, loaderCallbacks).mLoader;
    }

    public void destroyLoader(int i) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        if (DEBUG) {
            Log.v(TAG, "destroyLoader in " + this + " of " + i);
        }
        int f = this.mLoaders.m66f(i);
        if (f >= 0) {
            LoaderInfo loaderInfo = (LoaderInfo) this.mLoaders.m65e(f);
            this.mLoaders.m63c(f);
            loaderInfo.destroy();
        }
        f = this.mInactiveLoaders.m66f(i);
        if (f >= 0) {
            loaderInfo = (LoaderInfo) this.mInactiveLoaders.m65e(f);
            this.mInactiveLoaders.m63c(f);
            loaderInfo.destroy();
        }
        if (this.mActivity != null && !hasRunningLoaders()) {
            this.mActivity.mFragments.startPendingDeferredFragments();
        }
    }

    public C0008i getLoader(int i) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo loaderInfo = (LoaderInfo) this.mLoaders.m59a(i);
        if (loaderInfo == null) {
            return null;
        }
        if (loaderInfo.mPendingLoader != null) {
            return loaderInfo.mPendingLoader.mLoader;
        }
        return loaderInfo.mLoader;
    }

    void doStart() {
        if (DEBUG) {
            Log.v(TAG, "Starting in " + this);
        }
        if (this.mStarted) {
            Throwable runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w(TAG, "Called doStart when already started: " + this, runtimeException);
            return;
        }
        this.mStarted = true;
        for (int a = this.mLoaders.m58a() - 1; a >= 0; a--) {
            ((LoaderInfo) this.mLoaders.m65e(a)).start();
        }
    }

    void doStop() {
        if (DEBUG) {
            Log.v(TAG, "Stopping in " + this);
        }
        if (this.mStarted) {
            for (int a = this.mLoaders.m58a() - 1; a >= 0; a--) {
                ((LoaderInfo) this.mLoaders.m65e(a)).stop();
            }
            this.mStarted = false;
            return;
        }
        Throwable runtimeException = new RuntimeException("here");
        runtimeException.fillInStackTrace();
        Log.w(TAG, "Called doStop when not started: " + this, runtimeException);
    }

    void doRetain() {
        if (DEBUG) {
            Log.v(TAG, "Retaining in " + this);
        }
        if (this.mStarted) {
            this.mRetaining = true;
            this.mStarted = false;
            for (int a = this.mLoaders.m58a() - 1; a >= 0; a--) {
                ((LoaderInfo) this.mLoaders.m65e(a)).retain();
            }
            return;
        }
        Throwable runtimeException = new RuntimeException("here");
        runtimeException.fillInStackTrace();
        Log.w(TAG, "Called doRetain when not started: " + this, runtimeException);
    }

    void finishRetain() {
        if (this.mRetaining) {
            if (DEBUG) {
                Log.v(TAG, "Finished Retaining in " + this);
            }
            this.mRetaining = false;
            for (int a = this.mLoaders.m58a() - 1; a >= 0; a--) {
                ((LoaderInfo) this.mLoaders.m65e(a)).finishRetain();
            }
        }
    }

    void doReportNextStart() {
        for (int a = this.mLoaders.m58a() - 1; a >= 0; a--) {
            ((LoaderInfo) this.mLoaders.m65e(a)).mReportNextStart = true;
        }
    }

    void doReportStart() {
        for (int a = this.mLoaders.m58a() - 1; a >= 0; a--) {
            ((LoaderInfo) this.mLoaders.m65e(a)).reportStart();
        }
    }

    void doDestroy() {
        int a;
        if (!this.mRetaining) {
            if (DEBUG) {
                Log.v(TAG, "Destroying Active in " + this);
            }
            for (a = this.mLoaders.m58a() - 1; a >= 0; a--) {
                ((LoaderInfo) this.mLoaders.m65e(a)).destroy();
            }
        }
        if (DEBUG) {
            Log.v(TAG, "Destroying Inactive in " + this);
        }
        for (a = this.mInactiveLoaders.m58a() - 1; a >= 0; a--) {
            ((LoaderInfo) this.mInactiveLoaders.m65e(a)).destroy();
        }
        this.mInactiveLoaders.m61b();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(NotificationCompat.FLAG_HIGH_PRIORITY);
        stringBuilder.append("LoaderManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        C0047a.m53a(this.mActivity, stringBuilder);
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i = 0;
        if (this.mLoaders.m58a() > 0) {
            printWriter.print(str);
            printWriter.println("Active Loaders:");
            String str2 = str + "    ";
            for (int i2 = 0; i2 < this.mLoaders.m58a(); i2++) {
                LoaderInfo loaderInfo = (LoaderInfo) this.mLoaders.m65e(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.mLoaders.m64d(i2));
                printWriter.print(": ");
                printWriter.println(loaderInfo.toString());
                loaderInfo.dump(str2, fileDescriptor, printWriter, strArr);
            }
        }
        if (this.mInactiveLoaders.m58a() > 0) {
            printWriter.print(str);
            printWriter.println("Inactive Loaders:");
            String str3 = str + "    ";
            while (i < this.mInactiveLoaders.m58a()) {
                loaderInfo = (LoaderInfo) this.mInactiveLoaders.m65e(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.mInactiveLoaders.m64d(i));
                printWriter.print(": ");
                printWriter.println(loaderInfo.toString());
                loaderInfo.dump(str3, fileDescriptor, printWriter, strArr);
                i++;
            }
        }
    }

    public boolean hasRunningLoaders() {
        int a = this.mLoaders.m58a();
        boolean z = false;
        for (int i = 0; i < a; i++) {
            int i2;
            LoaderInfo loaderInfo = (LoaderInfo) this.mLoaders.m65e(i);
            if (!loaderInfo.mStarted || loaderInfo.mDeliveredData) {
                i2 = 0;
            } else {
                i2 = 1;
            }
            z |= i2;
        }
        return z;
    }
}
