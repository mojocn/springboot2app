package springboot2app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(indexes = {@Index(name = "unique_mobile", columnList = "mobile", unique = true), @Index(name = "idx_role_id", columnList = "roleId", unique = false)})
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {

    @Size(min = 2, max = 20)
    @Column(columnDefinition = "varchar(20) default '' COMMENT '姓名'")
    private String name = "张三";

    @Email
    @NotNull(message = "邮件必须")
    private String email;

    @Pattern(regexp = "^[1][0-9]{10}$", message = "账号请输入11位手机号")
    @NotNull(message = "手机号必须")
    private String mobile;

    @Column(columnDefinition = "TINYINT UNSIGNED default 0 COMMENT '职位: 0-未知职位,1-省厅领导,2-干警'")
    private Integer jobTitle = 0;

    @Size(min = 6, message = "密码最少6位")
    @Column(columnDefinition = "char(255) default ''")
    private String password;

    private Integer roleId;

    private Timestamp expireAt = new Timestamp(System.currentTimeMillis() + 3600 * 1000 * 24 * 365 * 30);


    public Timestamp getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Timestamp expireAt) {
        this.expireAt = expireAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(Integer jobTitle) {
        this.jobTitle = jobTitle;
    }

    @JsonProperty
    public Integer getRoleId() {
        return this.roleId;
    }

    @JsonProperty
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            return;
        }
        this.password = new BCryptPasswordEncoder().encode(password);
    }

}