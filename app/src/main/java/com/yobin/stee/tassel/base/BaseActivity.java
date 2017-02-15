package com.yobin.stee.tassel.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yobin.stee.tassel.utils.ActivityCollector;
import com.yobin.stee.tassel.utils.LogUtils;

/**
 * Created by yobin_he on 2017/2/14.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    /*是否沉浸式状态栏*/
    private boolean isSetStatusBar = true;
    /*是否允许全屏*/
    private boolean mAllowFullScreen = true;
    /*是否禁止旋转屏幕*/
    private boolean isAllowScreenRoate = false;
    /*当前Activity渲染的视图View*/
    private View mContextView = null;
    /*是否输出日志消息*/
    private boolean isDebug;
    private String APP_NAME;
    private final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //用于打印继承BaseActivity的Activity的信息  ctrl + shift + r 找寻相关类
        LogUtils.i("--->>>BaseActivity", TAG);
        //用于快速退出程序
        ActivityCollector.addActivity(this);

        isDebug = MyApplication.isDebug;
        APP_NAME = MyApplication.APP_NAME;

        try{
            Bundle bundle = getIntent().getExtras();
            initParams(bundle);
//        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);

            if (mAllowFullScreen) {
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            }

            if (isSetStatusBar) {
                steepStatusBar();
            }
//        setContentView(mContextView);
            if(isAllowScreenRoate){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
//        initView(mContextView);
//            doBusiness(this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 初始化Bundle参数
     *
     * @param bundle
     */
    public abstract void initParams(Bundle bundle);
    /**
     * 沉浸式状态栏
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 绑定布局
     */
//    public abstract void bindLayout();

    /**
     * 初始化控件
     * @param mContextView
     */
//    protected abstract void initView(View mContextView);

    /**
     * 业务操作
     * @param
     */
//    protected abstract void doBusiness(Context mContext);

    /*view点击*/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if(fastClick()){
            widgetClick(v);
        }
    }

    /**
     * 防止快速点击
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if(System.currentTimeMillis() - lastClick <= 100){
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    public void startActivity(Class<?> aClass, Class<?> clz){
//        startActivity(clz,null);
//    }

    /**
     * 可以携带值的跳转
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(this,clz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    @SuppressWarnings("unchecked")
    public <T extends View>T $(int resId){
        return (T) super.findViewById(resId);
    }


    /**
     * 含有Bundle通过Class打开编辑界面
     * @param clz
     * @param bundle
     * @param requestCode
     */
    public void customStartActivityForResult(Class<?> clz,Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this,clz);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    /**
     * 是否设置全屏
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen){
        this.mAllowFullScreen = allowFullScreen;
    }
    /** * [是否设置沉浸状态栏] * * @param allowFullScreen */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }
    /** * [是否允许屏幕旋转] * * @param isAllowScreenRoate */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    /** * [日志输出] * * @param msg */
    protected void $Log(String msg) {
        if (isDebug) {
            LogUtils.i("---->>>"+APP_NAME+"::", msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}

//较好的启动activity的写法如下：
//
//        在边写自己的activity时，留下一个启动该activity的接口方法，将所需的参数封装进去，代码如下：
//
//public class SecondActivity extends BaseActivity {
//
//    public static void actionStart(Context context, String data1, String data2) {
//        Intent intent = new Intent(context, SecondActivity.class);
//        intent.putExtra("param1", data1);
//        intent.putExtra("param2", data2);
//        context.startActivity(intent);
//    }
//    ……
//}
//1
//        这样对所需参数就会一目了然，我们在启动这个activity时：
//
//        button.setOnClickListener(new OnClickListener() {
//@Override
//public void onClick(View v) {
//        SecondActivity.actionStart(FirstActivity.this, "data1", "data2");
//        }
//        });
