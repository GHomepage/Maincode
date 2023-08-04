
package com.baidu.duer.dcs.util;

import android.app.Application;
import android.content.Context;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class SystemServiceManager {
    private static final String CONTEXT = "context";
    private Map<String, SoftReference<Object>> mSystemService;

    private SystemServiceManager() {
        mSystemService = new HashMap<>();
    }

    private Context getInnerCacheContext() {
        Context context = null;
        SoftReference<Object> object = mSystemService.get(CONTEXT);
        if (object != null) {
            context = (Context) object.get();
        }
        return context;
    }

    public static Context getAppContext() {
        return getInstance().getContext();
    }

    public Context getContext() {
        Context context = getInnerCacheContext();
        if (null != context) {
            return context;
        } else {
            synchronized (SystemServiceManager.class) {
                context = getInnerCacheContext();
                if (null != context) {
                    return context;
                }
                try {
                    Class clz = Class.forName("android.app.ActivityThread");
                    Method method = clz.getDeclaredMethod("currentApplication");
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    Application application = (Application) method.invoke(null);
                    if (null != application) {
                        context = application.getBaseContext();
                        if (context == null) {
                            context = application;
                        }
                        mSystemService.put(CONTEXT, new SoftReference<Object>(context));
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return context;
    }

    private static class SingletonHolder {
        private static SystemServiceManager instance = new SystemServiceManager();
    }

    public static SystemServiceManager getInstance() {
        return SingletonHolder.instance;
    }
}