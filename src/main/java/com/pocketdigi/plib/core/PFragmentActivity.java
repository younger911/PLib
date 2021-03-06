package com.pocketdigi.plib.core;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.pocketdigi.plib.util.RuntimeUtil;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * Created by fhp on 14-9-1.
 */
public abstract class PFragmentActivity extends FragmentActivity {
    HashSet<OnBackPressedListener> backPressedListeners;
    /**
     * Back按键事件是否已经被处理
     */
    protected boolean backProcessed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PApplication.getInstance().activityCreate(this);
        backPressedListeners=new HashSet<>();
        RuntimeUtil.readSetting();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerListenerOrReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterListerOrReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PApplication.getInstance().activityDestory(this);
    }
    /**
     * 注册监听器以及接收器(包括Event),在Fragment被隐藏或销毁时，会调用unregisterListerOrReceiver
     */
    protected void registerListenerOrReceiver(){};
    /**解注册监听器及接收器(包括Event)**/
    protected void unregisterListerOrReceiver(){};

    @Override
    public void onBackPressed() {
        notifyBackPressed();
    }

    public void addBackPressedListener(OnBackPressedListener listener) {
        backPressedListeners.add(listener);
    }
    public void removeBackPressedListener(OnBackPressedListener listener) {
        backPressedListeners.remove(listener);
    }
    private void notifyBackPressed() {
        backProcessed=false;
        for(OnBackPressedListener listener:backPressedListeners) {
            if(listener.onBackPressed()){
                backProcessed=true;
                break;
            }
        }
    }

}