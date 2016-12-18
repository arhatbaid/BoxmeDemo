package com.arhatbaid.boxmedemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.arhatbaid.boxmedemo.R;

public class AppHelper {

    public static void animation(View view, Context context, int animRes) {
        view.setAnimation(AnimationUtils.loadAnimation(context, animRes));
    }

    public static boolean isNetConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public static int getColorDrawable(int pos) {
        int[] colors = new int[]{R.drawable.circular_view_purple,
                R.drawable.circular_view_red,
                R.drawable.circular_view_green,
                R.drawable.circular_view_blue,
                R.drawable.circular_view_light_red};
        return colors[pos];
    }
}
