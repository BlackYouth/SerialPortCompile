package com.glc.serialportcompile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android_serialport_api.SerialPort;

public class MainActivity extends AppCompatActivity {

    private SerialPort serialPort;
    private byte[] outputData = new byte[1024];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            serialPort = new SerialPort(new File("/dev/ttyS2"), 115200, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new ReadDataThrad().start();
    }

    class ReadDataThrad extends Thread{
        @Override
        public void run() {
            while (true) {
                long outTime = 1000;
                int flag = serialPort.readData(outputData, outTime);
                Log.e("TAG", "是否有数据：" + flag);
                if (flag > 0) {
                    Log.e("TAG", "读到数据：" + DataConversion.encodeHexString(outputData, flag));
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
