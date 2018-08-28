package com.example.administrator.myapplication.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

import static com.example.administrator.myapplication.ui.MainActivity.SP_NAME;

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

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        ButterKnife.bind(this);

        toolbar.setTitle("菜单");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

        sp = mContext.getSharedPreferences(SP_NAME,MODE_PRIVATE);
        editor = sp.edit();

        String str = sp.getString("saveFoodStr","");
        tagGroup.setTags(str.split("≠"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tag_editor_activity, menu);
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

    public void updateTags(CharSequence... tags){
        clearTags();
        String str = "";
        for (CharSequence tag :tags){
            if ("".equals(str))
                str += tag.toString();
            else{
                str += "≠";
                str += tag.toString();
            }
        }
        addTag(str);
    }

    public void addTag(String str) {
        editor.putString("saveFoodStr",str);
        editor.commit();
    }

    public void clearTags() {
        editor.putString("saveFoodStr","");
        editor.commit();
    }

}
