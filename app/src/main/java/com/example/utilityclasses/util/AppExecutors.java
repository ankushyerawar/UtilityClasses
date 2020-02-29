package com.example.utilityclasses.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/** The purpose of this is to make background processing easier and hassle less
 * There are three types of executors which are
 * DiskIO - make all you disk related operations on a DiskIO such as insert , update, delete from Database(Room, SqLite)
 * NetworkIO - make all network related operations on a NetworkIO such as API calls
 * MainThread - make all main thread Operation from MainThread such as update UI from a background thread
 * How to Use
 * 1) AppExecutors mAppExecutor = new AppExecutors();
 * 2)  mAppExecutors.diskIO().execute(new Runnable() {
 *             @Override
 *             public void run() {
 *                  //do your stuff
 *             }
 *         });
 * 3) same with others
 */
public class AppExecutors {

    private final Executor mDiskIO;

    private final Executor mNetworkIO;

    private final Executor mMainThread;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mNetworkIO = networkIO;
        this.mMainThread = mainThread;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
                new MainThreadExecutor());
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

}
