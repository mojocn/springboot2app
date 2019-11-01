package springboot2app.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserLogin {

    @NotNull(message = "账号必须填")
    @Pattern(regexp = "^[1][0-9]{10}$", message = "账号请输入11位手机号") // 手机号
    private String mobile;

    @NotNull(message = "密码必须填")
    @Size(min = 6, max = 16, message = "密码6~16位")
    private String password;

    private boolean rememberMe;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

}