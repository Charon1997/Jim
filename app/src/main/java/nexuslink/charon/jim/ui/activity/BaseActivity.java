package nexuslink.charon.jim.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/23 12:59
 * 修改人：Charon
 * 修改时间：2017/11/23 12:59
 * 修改备注：
 */

public abstract class BaseActivity extends AppCompatActivity {
    private View mContextView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = bindView();
        if (view == null) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(),null);
        } else {
            mContextView = view;
        }
        setContentView(mContextView);
        initData();
        initView();
        doSomething();
    }

    protected abstract int bindLayout();

    protected abstract View bindView();

    protected abstract void doSomething();

    protected abstract void initView();

    protected abstract void initData();


}
