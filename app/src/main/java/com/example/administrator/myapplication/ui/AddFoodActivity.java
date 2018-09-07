package com.example.administrator.myapplication.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.BaseActivity;
import com.example.administrator.myapplication.db.DatabaseHelper;
import com.example.administrator.myapplication.db.EatReaderContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created with Android Studio
 * User: yuanxiaoru
 * Date: 2018/8/22.
 */

public class AddFoodActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tag_group)
    TagGroup tagGroup;

    DatabaseHelper dbhelper;

//    SharedPreferences sp;
//    SharedPreferences.Editor editor;

    List<String> foodList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        ButterKnife.bind(this);

        dbhelper = new DatabaseHelper(mContext);

        toolbar.setTitle("菜单");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

//
//        sp = mContext.getSharedPreferences(SP_NAME,MODE_PRIVATE);
//        editor = sp.edit();

//        String str = sp.getString("saveFoodStr","");
        getTags();

        tagGroup.setTags(foodList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_tag_editor_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_submit) {
//            tagGroup.submitTag();
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        updateTags(tagGroup.getTags());
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    public void getTags() {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor c = db.query(EatReaderContract.MenuEntry.TABLE_NAME,
                new String[]{EatReaderContract.MenuEntry.COLUMN_MENU_NAME}, null, null, null, null, null);
        while (c.moveToNext()) {
            String tag = c.getString(c.getColumnIndex(EatReaderContract.MenuEntry.COLUMN_MENU_NAME));
            foodList.add(tag);
        }
        c.close();
        db.close();
    }

    public void updateTags(CharSequence... tags){
        clearTags();
        for (CharSequence tag :tags){
            addTag(tag);
        }
    }

    public void addTag(CharSequence tag) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        values.put(EatReaderContract.MenuEntry.COLUMN_MENU_NAME, tag.toString());
        db.insert(EatReaderContract.MenuEntry.TABLE_NAME, null, values);
        db.close();
//        editor.putString("saveFoodStr",str);
//        editor.commit();
    }

    public void clearTags() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete(EatReaderContract.MenuEntry.TABLE_NAME, null, null);
        db.close();

//        editor.putString("saveFoodStr","");
//        editor.commit();
    }

}
