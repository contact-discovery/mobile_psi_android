package com.example.mobile_psi.droidCrypto;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.mobile_psi.droidCrypto.Networking.Channel;
import com.example.mobile_psi.droidCrypto.OT.IknpOTExtReceiver;
import com.example.mobile_psi.droidCrypto.OT.IknpOTExtSender;
import com.example.mobile_psi.droidCrypto.OT.NaorPinkas;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;

public class TestAsyncTask extends AsyncTask<Void, Void, Void> {

    private final String TAG = "NetworkTest";

    private TextView tv;
    private EditText ed_num_items;
    private EditText ed_ip;
    private EditText ed_port;
    private Spinner sp_psi_type;
    private String response;

    static {
        System.loadLibrary("droidcrypto");
    }

    public TestAsyncTask(TextView tv, EditText num_items, EditText ip, EditText port, Spinner type) {
        this.tv = tv;
        this.ed_num_items = num_items;
        this.ed_ip = ip;
        this.ed_port = port;
        this.sp_psi_type = type;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int num_items = 1024;
        int port = 8000;
        long type;
        String ip;
        try {
            num_items = Integer.valueOf(ed_num_items.getText().toString());
        } catch (Exception e) {
            num_items = 1024;
        }
        try {
            port = Integer.valueOf(ed_port.getText().toString());
        } catch (Exception e) {
            port = 8000;
        }
        try {
            ip = ed_ip.getText().toString();
        } catch (Exception e) {
            ip = "10.42.0.1";
        }
        try {
            type = sp_psi_type.getSelectedItemId();
        } catch (Exception e) {
            type = 0;
        }

        Log.v("PSI", String.valueOf(num_items) + " items.");
        response = testNative(ip, port, type, num_items);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        tv.setText(response);
    }

    private void testGC() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                InetSocketAddress sock = new InetSocketAddress("localhost", 12345);
//                Log.d(TAG, "starting channel creation");
//                Channel chan1 = new Channel(sock, Channel.ROLE.SERVER);
//                Log.d(TAG, "chan1 ready");
                garble(null);
            }
        }).start();

//        InetSocketAddress sock = new InetSocketAddress("localhost", 12345);
//        Log.d(TAG, "starting channel creation");
//        Channel chan2 = new Channel(sock, Channel.ROLE.CLIENT);
//        Log.d(TAG, "chan2 ready");
        evaluate(null);
    }

    private void testGCAES() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                InetSocketAddress sock = new InetSocketAddress("localhost", 12345);
//                Log.d(TAG, "starting channel creation");
//                Channel chan1 = new Channel(sock, Channel.ROLE.SERVER);
//                Log.d(TAG, "chan1 ready");
//                garbleAES(chan1);
                garbleAES(null);
            }
        }).start();

//        InetSocketAddress sock = new InetSocketAddress("localhost", 12345);
//        Log.d(TAG, "starting channel creation");
//        Channel chan2 = new Channel(sock, Channel.ROLE.CLIENT);
//        Log.d(TAG, "chan2 ready");
        long start = System.nanoTime();
        evaluateAES(null);
        long end = System.nanoTime();
//        Log.v(TAG, "recv: " + String.valueOf(chan2.getRecvBytes()) + ", sent: " + String.valueOf(chan2.getSentBytes()));
        response = "Time: " + String.valueOf(((double)end-start) /1000000000);
    }

    private void testGCLowMC() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                InetSocketAddress sock = new InetSocketAddress("localhost", 12345);
//                Log.d(TAG, "starting channel creation");
//                Channel chan1 = new Channel(sock, Channel.ROLE.SERVER);
//                Log.d(TAG, "chan1 ready");
                garbleLowMC(null);
            }
        }).start();

//        InetSocketAddress sock = new InetSocketAddress("localhost", 12345);
//        Log.d(TAG, "starting channel creation");
//        Channel chan2 = new Channel(sock, Channel.ROLE.CLIENT);
//        Log.d(TAG, "chan2 ready");
        long start = System.nanoTime();
        evaluateLowMC(null);
        long end = System.nanoTime();
//        Log.v(TAG, "recv: " + String.valueOf(chan2.getRecvBytes()) + ", sent: " + String.valueOf(chan2.getSentBytes()));
        response = "Time: " + String.valueOf(((double)end-start) /1000000000);
    }

    private void testOTE() {

        long start = System.nanoTime();
        new Thread(new Runnable() {
            @Override
            public void run() {
               IKNPRecv();
            }
        }).start();
        IKNPSend();
        long end = System.nanoTime();
        response = "Time: " + String.valueOf(((double)end-start) /1000000000);
    }

    private void testDotE() {

        long start = System.nanoTime();
        new Thread(new Runnable() {
            @Override
            public void run() {
                IKNPDotRecv();
            }
        }).start();
        IKNPDotSend();
        long end = System.nanoTime();
        response = "Time: " + String.valueOf(((double)end-start) /1000000000);
    }
    private void testOTEold() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InetSocketAddress sock = new InetSocketAddress("localhost", 12345);
                Log.d(TAG, "starting channel creation");
                Channel chan1 = new Channel(sock, Channel.ROLE.SERVER);
                Log.d(TAG, "chan1 ready");
                NaorPinkas sender = new NaorPinkas();
                int numOTs = 128;
                ByteBuffer mes = sender.send(numOTs, chan1);
//                Log.v(TAG, mes.toString());
//                for(int i = 0; i < numOTs; i++) {
//                    byte[] mes1 = new byte[16];
//                    mes.get(mes1);
//                    byte[] mes2 = new byte[16];
//                    mes.get(mes2);
//                    Log.v(TAG, "mes1: " + Utils.bytesToHex(mes1) + ", mes2: " + Utils.bytesToHex(mes2));
//                }
                IknpOTExtReceiver recv = new IknpOTExtReceiver(mes);
                Log.v(TAG, "IKNP recv init done!");
                Random r = new SecureRandom();
                int numOTEs = 1024*1024;
                byte[] choizes = new byte[numOTEs/8];
                r.nextBytes(choizes);
                ByteBuffer ote = recv.recv(choizes, chan1);
                Log.v(TAG, "IKNP recv done");
//                Log.v(TAG, mes.toString());
//                BitSet choices = BitSet.valueOf(choizes);
//                for(int i = 0; i < numOTEs; i++) {
//                    byte[] mes1 = new byte[16];
//                    ote.get(mes1);
//                    Log.v(TAG, String.valueOf(i) + ": mes1: " + Utils.bytesToHex(mes1) + ", ind: " + String.valueOf(choices.get(i)));
//                }

                recv.cleanup();

            }
        }).start();

        InetSocketAddress sock = new InetSocketAddress("localhost", 12345);
        Log.d(TAG, "starting channel creation");
        Channel chan2 = new Channel(sock, Channel.ROLE.CLIENT);
        Log.d(TAG, "chan2 ready");
        NaorPinkas recv = new NaorPinkas();
        Random r = new SecureRandom();
        int numOTs = 128;
        byte[] choize = new byte[numOTs/8];
        r.nextBytes(choize);
        long start = System.nanoTime();
        ByteBuffer mes = recv.recv(choize, chan2);
//        Log.v(TAG, mes.toString());
//        BitSet choices = BitSet.valueOf(choize);
//        for(int i = 0; i < numOTs; i++) {
//            byte[] mes1 = new byte[16];
//            mes.get(mes1);
//            Log.v(TAG, "mes1: " + Utils.bytesToHex(mes1) + ", ind: " + String.valueOf(choices.get(i)));
//        }
        IknpOTExtSender snd = new IknpOTExtSender(mes, choize);
        Log.v(TAG, "IKNP sender init done");
        int numOTEs = 1024*1024;
        ByteBuffer ote = snd.send(numOTEs, chan2);
        Log.v(TAG, "IKNP sender sent");
        long end = System.nanoTime();
        snd.cleanup();
//        Log.v(TAG, mes.toString());
//        for(int i = 0; i < numOTEs; i++) {
//            byte[] mes1 = new byte[16];
//            ote.get(mes1);
//            byte[] mes2 = new byte[16];
//            ote.get(mes2);
//            Log.v(TAG, String.valueOf(i) + ": mes1: " + Utils.bytesToHex(mes1) + ", mes2: " + Utils.bytesToHex(mes2));
//        }
        response = "Time: " + String.valueOf(((double)end-start) /1000000000);
    }



    private native void testSend(Channel chan);
    private native void testRecv(Channel chan);

    private native void garble(Channel chan);
    private native void evaluate(Channel chan);

    private native void garbleAES(Channel chan);
    private native void evaluateAES(Channel chan);

    private native void garbleLowMC(Channel chan);
    private native void evaluateLowMC(Channel chan);

    private native void IKNPSend();
    private native void IKNPRecv();

    private native void IKNPDotSend();
    private native void IKNPDotRecv();

    public native String testNative(String ip, long port, long type, long num_items);
}
