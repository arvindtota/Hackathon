package android.support.v4.p000a;

import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

final class C0022n extends FutureTask {
    final /* synthetic */ C0010k f46a;

    C0022n(C0010k c0010k, Callable callable) {
        this.f46a = c0010k;
        super(callable);
    }

    protected final void done() {
        try {
            C0010k.m37b(this.f46a, get());
        } catch (Throwable e) {
            Log.w("AsyncTask", e);
        } catch (ExecutionException e2) {
            throw new RuntimeException("An error occured while executing doInBackground()", e2.getCause());
        } catch (CancellationException e3) {
            C0010k.m37b(this.f46a, null);
        } catch (Throwable e4) {
            RuntimeException runtimeException = new RuntimeException("An error occured while executing doInBackground()", e4);
        }
    }
}
