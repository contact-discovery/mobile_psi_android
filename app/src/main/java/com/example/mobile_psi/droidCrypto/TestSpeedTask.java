package com.example.mobile_psi.droidCrypto;

import android.os.AsyncTask;

public class TestSpeedTask extends AsyncTask<Void, Void, Void> {

    static {
        System.loadLibrary("droidcrypto");
    }

    public TestSpeedTask() {
    }

    @Override
    protected Void doInBackground(Void... voids) {
        testSpeed();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }



    public native void testSpeed();
}
