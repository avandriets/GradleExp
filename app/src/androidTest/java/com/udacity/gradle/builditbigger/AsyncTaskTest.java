package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.support.v4.util.Pair;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class AsyncTaskTest extends ApplicationTestCase<Application> {

    String mRespondString = null;
    Exception mError = null;
    CountDownLatch signal = null;

    public AsyncTaskTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testGetDataTask() throws InterruptedException {

        EndpointsAsyncTask task = new EndpointsAsyncTask();
        task.setListener(new EndpointsAsyncTask.EndpointsGetTaskListener() {
            @Override
            public void onComplete(String jsonString, Exception e) {
                mRespondString = jsonString;
                mError = e;
                signal.countDown();
            }
        }).execute();

        signal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mRespondString));

    }
}