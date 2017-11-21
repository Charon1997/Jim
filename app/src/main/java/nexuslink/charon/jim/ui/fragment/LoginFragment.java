package nexuslink.charon.jim.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.listener.OnViewPagerScroll;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/21 20:23
 * 修改人：Charon
 * 修改时间：2017/11/21 20:23
 * 修改备注：
 */

public class LoginFragment extends Fragment {
    @BindView(R.id.logo_login)
    ImageView logoLogin;
    @BindView(R.id.et_username_login_register)
    EditText etUsernameLoginRegister;
    @BindView(R.id.et_code_login_register)
    EditText etCodeLoginRegister;
    @BindView(R.id.btn_code_login_register)
    Button btnCodeLoginRegister;
    @BindView(R.id.et_password_login_register)
    EditText etPasswordLoginRegister;
    @BindView(R.id.et_password2_login_register)
    EditText etPassword2LoginRegister;
    @BindView(R.id.btn_send_login_register)
    Button btnSendLoginRegister;
    @BindView(R.id.tv_right_login_register)
    TextView tvRightLogonRegister;
    Unbinder unbinder;

    private OnViewPagerScroll scroll;

    public void setOnViewPagerScroll(OnViewPagerScroll scroll) {
        if (this.scroll == null) {
            this.scroll = scroll;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login_register, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_code_login_register, R.id.btn_send_login_register,
            R.id.tv_right_login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code_login_register:
                break;
            case R.id.btn_send_login_register:
                break;
            case R.id.tv_right_login_register:
                scroll.rightScroll();
                break;
            default:
                break;
        }
    }


    @OnClick(R.id.tv_right_login_register)
    public void onViewClicked() {
    }
}
