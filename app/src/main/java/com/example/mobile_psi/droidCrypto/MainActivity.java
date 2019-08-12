package com.example.mobile_psi.droidCrypto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("droidcrypto");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickBtnSpeed(View v) {

        TestSpeedTask task = new TestSpeedTask();
        task.execute();
    }

    public void onClickBtn(View v) {
        TextView tv = (TextView) findViewById(R.id.text_message);
        EditText num_items = (EditText) findViewById(R.id.num_items);
        EditText ip = (EditText) findViewById(R.id.ip_address);
        EditText port = (EditText) findViewById(R.id.port);
        Spinner spin = (Spinner) findViewById(R.id.psi_type);


        TestAsyncTask task = new TestAsyncTask(tv, num_items, ip, port, spin);
        task.execute();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
}
