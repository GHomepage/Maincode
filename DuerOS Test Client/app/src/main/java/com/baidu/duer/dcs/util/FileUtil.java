
package com.baidu.duer.dcs.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtil {
    public static final String TEMP_POSTFIX = ".download";
    private static final String LOG_FILE = "LogAll.txt";
    private static final String APP_DIR = "/DCS";
    private static final String SPEAK = APP_DIR + "/Speak";
    private static final String ALERT = APP_DIR + "/Alert";
    private static final String ALARM_FILE = "alarms.json";

    private static String getSpeakDirPath() {
        String dirPath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + SPEAK;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        return dirPath;
    }

    private static String getAlertDirPath() {
        String dirPath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + ALERT;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        return dirPath;
    }

    public static File getSpeakFile() {
        String dirPath = getSpeakDirPath();
        if (TextUtils.isEmpty(dirPath)) {
            return null;
        }
        return new File(dirPath,
                "dcs_" + System.currentTimeMillis() + ".mp3" + TEMP_POSTFIX);
    }

    public static File getAlarmFile() {
        String dirPath = getAlertDirPath();
        if (TextUtils.isEmpty(dirPath)) {
            return null;
        }
        return new File(dirPath, ALARM_FILE);
    }

    public static String getLogFilePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + APP_DIR + File.separator + LOG_FILE;
    }

    public static void appendStrToFile(String content) {
        File file = new File(getLogFilePath());
        if (!file.isFile()) {
            file.delete();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(content);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileToString(String fileName) {
        String result = null;

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName), 8192);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void storeStrToFile(String fileName, String conent) {
        if (fileName == null || conent == null) {
            return;
        }
        File file = new File(fileName);
        File newFileDir = new File(file.getPath().replace(file.getName(), ""));
        if (!newFileDir.exists()) {
            newFileDir.mkdirs();
        }

        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, false)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}