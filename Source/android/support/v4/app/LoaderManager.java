package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.p000a.C0008i;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class LoaderManager {

    public interface LoaderCallbacks {
        C0008i onCreateLoader(int i, Bundle bundle);

        void onLoadFinished(C0008i c0008i, Object obj);

        void onLoaderReset(C0008i c0008i);
    }

    public abstract void destroyLoader(int i);

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract C0008i getLoader(int i);

    public abstract C0008i initLoader(int i, Bundle bundle, LoaderCallbacks loaderCallbacks);

    public abstract C0008i restartLoader(int i, Bundle bundle, LoaderCallbacks loaderCallbacks);

    public static void enableDebugLogging(boolean z) {
        LoaderManagerImpl.DEBUG = z;
    }

    public boolean hasRunningLoaders() {
        return false;
    }
}
