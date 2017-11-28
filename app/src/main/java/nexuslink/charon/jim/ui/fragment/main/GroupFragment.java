package nexuslink.charon.jim.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.adapter.FriendRvAdapter;
import nexuslink.charon.jim.ui.fragment.BaseFragment;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/23 15:53
 * 修改人：Charon
 * 修改时间：2017/11/23 15:53
 * 修改备注：
 */

public class GroupFragment extends BaseFragment {
    private static GroupFragment instance = new GroupFragment();
    @BindView(R.id.rv_group)
    RecyclerView rvGroup;
    Unbinder unbinder;
    private List<nexuslink.charon.jim.model.UserInfo> userList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_group_main, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFriend();
    }

    public static GroupFragment getInstance() {
        return instance;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initFriend() {

        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {

                if (0 == i) {
                    userList = new ArrayList<>();
                    for (int j = 0, size = list.size(); j < size; j++) {
                        String userName = list.get(j).getUserName();
                        long id = list.get(j).getUserID();
                        String nickName = list.get(j).getNickname();
                        String avatar = list.get(j).getAvatar();
                        nexuslink.charon.jim.model.UserInfo user = new nexuslink.charon.jim.model.UserInfo();
                        user.setUserID(id);
                        user.setUserName(userName);
                        user.setNickname(nickName);
                        user.setAvatarMediaID(avatar);
                        userList.add(user);
                        Log.d("tag", "gotResult: " + userList.get(j));
                    }

                    initRvAdapter();
                } else {
                    Toast.makeText(getContext(), "获取失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initRvAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvGroup.setLayoutManager(manager);
        rvGroup.setItemAnimator(new DefaultItemAnimator());
        FriendRvAdapter adapter = new FriendRvAdapter(userList, getContext());
        rvGroup.setAdapter(adapter);
    }
}
