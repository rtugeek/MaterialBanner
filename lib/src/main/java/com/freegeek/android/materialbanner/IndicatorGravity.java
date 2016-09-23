package com.freegeek.android.materialbanner;

import android.view.Gravity;

/**
 * Created by leon on 2016/9/23.
 * Email:rtugeek@gmail.com
 */

public enum IndicatorGravity {

    LEFT,CENTER,RIGHT;
    public static IndicatorGravity valueOf(int i){
        switch (i%3){
            case 0:
                return LEFT;
            case 1:
                return CENTER;
        }
        return RIGHT;
    }

    public static int toGravity(IndicatorGravity gravity){
        switch (gravity){
            case LEFT:
                return Gravity.LEFT;
            case CENTER:
                return Gravity.CENTER;
        }
        return Gravity.RIGHT;
    }
}
