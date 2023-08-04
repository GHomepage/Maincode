
package com.baidu.duer.dcs.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Closeable;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtil {
    private static final int JSON_INDENT = 4;
    private static String deviceUniqueId = "";

    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINESE);
        return format.format(new Date());
    }

    public static String formatToDataTime(long milliSeconds) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sd.format(milliSeconds);
    }


    public static Bundle decodeUrl(String query) {
        Bundle ret = new Bundle();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyAndValues = pair.split("=");
                if (keyAndValues != null && keyAndValues.length == 2) {
                    String key = keyAndValues[0];
                    String value = keyAndValues[1];
                    if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                        ret.putString(URLDecoder.decode(key), URLDecoder.decode(value));
                    }
                }
            }
        }
        return ret;
    }


    public static String encodeUrl(Bundle params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            String paramValue = params.getString(key);
            if (paramValue == null) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(key)).append("=").append(URLEncoder.encode(paramValue));
        }
        return sb.toString();
    }

    public static void showAlert(Context context, String title, String text) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private static long lastClickTime;


    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String formatJson(String json) {
        String formatted = "";
        if (json == null || json.length() == 0) {
            return formatted;
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jo = new JSONObject(json);
                formatted = jo.toString(JSON_INDENT);
            } else if (json.startsWith("[")) {
                JSONArray ja = new JSONArray(json);
                formatted = ja.toString(JSON_INDENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatted;
    }


    public static void closeQuietly(Closeable... closeables) {
        for (Closeable c : closeables) {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String getDeviceUniqueID() {
        // getCUID是DeviceId.getDeviceID()+"|"+DeviceId.getIMEI();
        if (TextUtils.isEmpty(deviceUniqueId)) {
            deviceUniqueId = StandbyDeviceIdUtil.getStandbyDeviceId(SystemServiceManager.getAppContext());
        }
        return deviceUniqueId;
    }
}