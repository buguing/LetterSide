package com.wellee.letterside;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AssetsUtils {

    public static List<String> readAssetsTxt(Context context, String fileName) {
        List<String> names = new ArrayList<>();
        Context ctx = context.getApplicationContext();
        InputStream inputStream = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            inputStream = ctx.getResources().getAssets().open(fileName);
            isr = new InputStreamReader(inputStream);
            reader = new BufferedReader(isr);
            String name;
            while ((name = reader.readLine()) != null) {
                names.add(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return names;
    }
}
