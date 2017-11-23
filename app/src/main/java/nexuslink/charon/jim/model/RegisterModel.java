package nexuslink.charon.jim.model;

import java.io.Serializable;

import nexuslink.charon.jim.contract.RegisterContract;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/17 11:24
 * 修改人：Charon
 * 修改时间：2017/11/17 11:24
 * 修改备注：
 */

public class RegisterModel implements RegisterContract.Model,Serializable {
    private String username;
    private String password;

    public RegisterModel() {
    }

    public RegisterModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
