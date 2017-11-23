package nexuslink.charon.jim.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class MeFragment extends BaseFragment {
    private static MeFragment instance = new MeFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_me_main, null);
        return view;
    }

    public static MeFragment getInstance() {
        return instance;
    }
}
