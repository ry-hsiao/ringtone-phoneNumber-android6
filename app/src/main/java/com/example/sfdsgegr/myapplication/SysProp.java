package com.example.sfdsgegr.myapplication;

/**
 * Created by Ryan on 2017/3/31.
 */

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SysProp {
    private static final String TAG="SysProp";


    private static Method sysPropGet;
    private static Method sysPropSet;

    private SysProp() {
    }

    static {
        try {
            Class<?> S = Class.forName("android.os.SystemProperties");
            Method M[] = S.getMethods();
            for (Method m : M) {
                String n = m.getName();
                Log.i("RyanTest20170331","Method name:"+n);
                if (n.equals("get")) {
                    sysPropGet = m;
                } else if (n.equals("set")) {
                    sysPropSet = m;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static String get(String name, String default_value) {
        try {
            return (String) sysPropGet.invoke(null, name, default_value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return default_value;
    }

    public static void set(String name, String value) {
        try {
//            Log.i("tset20170331","0");
            sysPropSet.invoke(null, name, value);//sysPropSet.invoke(name, value);//sysPropSet.invoke(null, name, value);
//            Log.i("tset20170331","1");
        } catch (IllegalArgumentException e) {
//            Log.i("tset20170331","2");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
//            Log.i("tset20170331","3");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
//            Log.i("tset20170331","4");
            e.printStackTrace();
        }
    }
}