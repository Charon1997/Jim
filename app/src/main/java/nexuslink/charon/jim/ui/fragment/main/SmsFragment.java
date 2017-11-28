package nexuslink.charon.jim.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.ui.fragment.BaseFragment;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/23 15:52
 * 修改人：Charon
 * 修改时间：2017/11/23 15:52
 * 修改备注：
 */

public class SmsFragment extends BaseFragment {
    private static SmsFragment instance = new SmsFragment();
    @BindView(R.id.rv_sms)
    RecyclerView rvSms;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sms_main, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public static SmsFragment getInstance() {
        return instance;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
