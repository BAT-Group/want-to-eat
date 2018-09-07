package com.example.administrator.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with Android Studio
 * User: yuanxiaoru
 * Date: 2018/8/28.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_CREATE_MENU = "CREATE TABLE "+ EatReaderContract.MenuEntry.TABLE_NAME +"("
            + EatReaderContract.MenuEntry.COLUMN_ID +" INTEGER PRIMARY KEY autoincrement,"
            + EatReaderContract.MenuEntry.COLUMN_MENU_NAME +" TEXT,"
            + EatReaderContract.MenuEntry.COLUMN_IMAGE_PATH +" TEXT)";

    public static final String DATABASE_NAME = "eat.db";
    public static final int DB_VERSION = 1;

    public DatabaseHelper(Context context){
        this(context,DATABASE_NAME,null,DB_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_MENU);
        initData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void initData(SQLiteDatabase db){
        String foodStr = "盖浇饭≠砂锅≠大排档≠米线≠满汉全席≠西餐≠麻辣烫≠自助餐≠炒面≠快餐≠水果≠西北风≠馄饨≠火锅≠烧烤≠泡面≠速冻水饺≠日本料理≠涮羊肉≠味千拉面≠肯德基≠面包≠扬州炒饭≠自助餐≠茶餐厅≠海底捞≠咖啡≠比萨≠麦当劳≠兰州拉面≠沙县小吃≠烤鱼≠海鲜≠铁板烧≠韩国料理≠粥≠快餐≠东南亚菜≠甜点≠农家菜≠川菜≠粤菜≠湘菜≠本帮菜≠竹笋烤肉";

        String[] foodArr = foodStr.split("≠");

        ContentValues cv = new ContentValues();
        for (String s : foodArr){
            cv.put(EatReaderContract.MenuEntry.COLUMN_MENU_NAME,s);
            db.insert(EatReaderContract.MenuEntry.TABLE_NAME,null,cv);
        }

    }
}
