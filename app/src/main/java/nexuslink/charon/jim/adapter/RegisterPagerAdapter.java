package nexuslink.charon.jim.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/17 13:05
 * 修改人：Charon
 * 修改时间：2017/11/17 13:05
 * 修改备注：
 */

public class RegisterPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentList;

    public RegisterPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}
