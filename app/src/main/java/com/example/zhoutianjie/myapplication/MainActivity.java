package com.example.zhoutianjie.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG","onCLick");
            }
        });
        // hookOnClickListener(button);
        hookBtn(button);
    }

//    private void hookOnClickListener(View view){
//        try {
//            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
//            getListenerInfo.setAccessible(true);
//            Object listenerInfo = getListenerInfo.invoke(view);
//            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
//            Field mOnClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
//            mOnClickListener.setAccessible(true);
//            View.OnClickListener originOnClickListener = (View.OnClickListener) mOnClickListener.get(listenerInfo);
//            View.OnClickListener hookedOnClickListener = new HookedOnClickListener(originOnClickListener);
//            mOnClickListener.set(listenerInfo, hookedOnClickListener);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    class HookedOnClickListener implements View.OnClickListener {
//        private View.OnClickListener origin;
//
//        HookedOnClickListener(View.OnClickListener origin) {
//            this.origin = origin;
//        }
//
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(MainActivity.this, "hook click", Toast.LENGTH_SHORT).show();
//            Log.i("TAG","Before click, do what you want to to.");
//            if (origin != null) {
//                origin.onClick(v);
//            }
//            Log.i("TAG","After click, do what you want to to.");
//        }
//    }

    private void hookBtn(View view){
        Class clz = View.class;
        try{
            Method method = clz.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            //得到listenerInfo
            Object listenerInfo = method.invoke(view);

            Class listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
            Field onClickListenerField = listenerInfoClz.getDeclaredField("mOnClickListener");

            HookonClickListener listener = new HookonClickListener();
            onClickListenerField.set(listenerInfo,listener);

        }catch (Exception e){

        }

    }

    class HookonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Log.i("TAG","onCLicKKk");
        }
    }

}
