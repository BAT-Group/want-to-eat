package com.example.administrator.myapplication.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.BaseActivity;

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

    private int btn_type = NOT_START;

    Timer timer;

    public String foodStr = "盖浇饭 砂锅 大排档 米线 满汉全席 西餐 麻辣烫 自助餐 炒面 快餐 水果 西北风 馄饨 火锅 烧烤 泡面 速冻水饺 日本料理 涮羊肉 味千拉面 肯德基 面包 扬州炒饭 自助餐 茶餐厅 海底捞 咖啡 比萨 麦当劳 兰州拉面 沙县小吃 烤鱼 海鲜 铁板烧 韩国料理 粥 快餐 东南亚菜 甜点 农家菜 川菜 粤菜 湘菜 本帮菜 竹笋烤肉";

    public String[] foodArr;
    int cur = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar();

        dealList(foodStr);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null)
            timer.cancel();
    }

    private void setToolbar() {
        toolbar.setTitle("WantToEat");
        setSupportActionBar(toolbar);
    }

    public void dealList(String str){
        foodArr = str.split(" ");
    }

    TimerTask task;
    /**
     * 开始切换
     */
    public void startChange(){
        if (foodArr==null || foodArr.length < 1){
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
                            txtswitcher.setCurrentText(foodArr[cur]);
                            cur = (int) (Math.random() * foodArr.length);
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
        if (foodArr==null || foodArr.length < 1){
            return foodName;
        }
        int posi = (int) (Math.random() * foodArr.length);
        foodName = foodArr[posi];

        return foodName;
    }

}
