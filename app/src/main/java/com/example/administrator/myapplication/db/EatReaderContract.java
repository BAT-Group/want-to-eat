package com.example.administrator.myapplication.db;

import android.provider.BaseColumns;

/**
 * Created with Android Studio
 * User: yuanxiaoru
 * Date: 2018/9/7.
 */

public final class EatReaderContract {

    public EatReaderContract(){}

    public static abstract class MenuEntry implements BaseColumns{
        public static final String TABLE_NAME = "MENU";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_MENU_NAME = "MENU_NAME";
        public static final String COLUMN_IMAGE_PATH = "IMAGE_PATH";
    }
}
