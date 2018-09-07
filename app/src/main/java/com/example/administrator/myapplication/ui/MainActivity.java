package com.example.administrator.myapplication.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.BaseActivity;
import com.example.administrator.myapplication.db.DatabaseHelper;
import com.example.administrator.myapplication.db.EatReaderContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_name)
    TextView txt_name;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.txtswitcher)
    TextSwitcher txtswitcher;

    public static final int NOT_START = 0;//未开始随机
    public static final int STARTING = 1;//已经开始随机
    public static final String SP_NAME = "WANTTOEAT";

    private int btn_type = NOT_START;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Timer timer;

    public String foodStr = "盖浇饭≠砂锅≠大排档≠米线≠满汉全席≠西餐≠麻辣烫≠自助餐≠炒面≠快餐≠水果≠西北风≠馄饨≠火锅≠烧烤≠泡面≠速冻水饺≠日本料理≠涮羊肉≠味千拉面≠肯德基≠面包≠扬州炒饭≠自助餐≠茶餐厅≠海底捞≠咖啡≠比萨≠麦当劳≠兰州拉面≠沙县小吃≠烤鱼≠海鲜≠铁板烧≠韩国料理≠粥≠快餐≠东南亚菜≠甜点≠农家菜≠川菜≠粤菜≠湘菜≠本帮菜≠竹笋烤肉";

    public String[] foodArr;

    public List<Map<String,String>> foodList = new ArrayList<>();
    int cur = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar();

//        sp = mContext.getSharedPreferences(SP_NAME,MODE_PRIVATE);
//        editor = sp.edit();
//        editor.putString("defaultFoodStr",foodStr);
//        editor.commit();
//
//        String str = sp.getString("saveFoodStr","");
//        if (!"".equals(str)){
//            foodStr = str;
//        }
//
//        dealList(foodStr);

        getList();

        start.setOnClickListener(this);
        txtswitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView tv=new TextView(mContext);
                tv.setTextSize(36);
                tv.setTextColor(getResources().getColor(R.color.colorBlack));
                tv.setGravity(Gravity.CENTER);
                return tv;
            }
        });
        txtswitcher.setCurrentText("吃什么");
    }

    public void getList(){
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(EatReaderContract.MenuEntry.TABLE_NAME,
                new String[]{
                        EatReaderContract.MenuEntry.COLUMN_ID,
                        EatReaderContract.MenuEntry.COLUMN_MENU_NAME,
                        EatReaderContract.MenuEntry.COLUMN_IMAGE_PATH
                },null,null,null,null,null);
        foodList.clear();
        while (cursor.moveToNext()){
            Map<String,String> map = new HashMap<>();
            long id = cursor.getLong(cursor.getColumnIndex(EatReaderContract.MenuEntry.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(EatReaderContract.MenuEntry.COLUMN_MENU_NAME));
            String path = cursor.getString(cursor.getColumnIndex(EatReaderContract.MenuEntry.COLUMN_IMAGE_PATH));
            map.put("id",String.valueOf(id));
            map.put("menu_name",name);
            map.put("image_path",path);
            foodList.add(map);
        }
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null)
            timer.cancel();
    }

    private void setToolbar() {
        toolbar.setTitle("WantToEat");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(omcl);
    }

    Toolbar.OnMenuItemClickListener omcl = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.add:

                    Intent i = new Intent(MainActivity.this,AddFoodActivity.class);

                    startActivityForResult(i,0);

                    break;
            }
            return true;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK){
//            String str = sp.getString("saveFoodStr","");
//            if (!"".equals(str)){
//                foodStr = str;
//            }
//            foodArr = str.split("≠");

            getList();
        }
    }

//    public void dealList(String str){
//        foodArr = str.split("≠");
//        editor.putString("saveFoodStr",str);
//        editor.commit();
//    }

    TimerTask task;
    /**
     * 开始切换
     */
    public void startChange(){
        if (foodList == null || foodList.size() < 1){
            return;
        }
        if (timer == null){
            timer = new Timer();
        }
        if (task == null){
            task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cur = (int) (Math.random() * foodList.size());
                            txtswitcher.setCurrentText(foodList.get(cur).get("menu_name").toString());
                        }
                    });
                }
            };
        }
        if (timer!=null && task !=null) {
            timer.schedule(task, 0, 50);
        }
    }
    public void stopChange(){
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == start){
            if (btn_type == NOT_START){
                btn_type = STARTING;
                start.setText("停止");
                startChange();
            }else if (btn_type == STARTING){
                btn_type = NOT_START;
                start.setText("不喜欢，换一个");
                stopChange();
                String txt = getFoodName();
                if ("".equals(txt)){
                    Toast.makeText(mContext,"暂时没有找到吃的，请先添加候选菜单，或者检查候选菜单的格式是否正确",Toast.LENGTH_SHORT).show();
                }else {
                    txtswitcher.setCurrentText(txt);
//                    txt_name.setText(txt);
                }
            }
        }
    }

    public String getFoodName() {
        String foodName = "";
        if (foodList == null || foodList.size() < 1){
            return foodName;
        }
        int posi = (int) (Math.random() * foodList.size());
        foodName = foodList.get(posi).get("menu_name").toString();

        return foodName;
    }

}
