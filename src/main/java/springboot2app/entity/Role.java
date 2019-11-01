package springboot2app.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(indexes = {@Index(name = "unique_name", columnList = "name", unique = true)})
@EntityListeners(AuditingEntityListener.class)
public class Role extends BaseEntity {

    @Size(min = 2, max = 20)
    @NotNull(message = "角色名称必须")
    @Column(columnDefinition = "varchar(20) default '' COMMENT '角色名称'")
    private String name;

    @Column(columnDefinition = "TEXT COMMENT '角色权限,Method.URI逗号分隔字符串'")
    @NotNull(message = "权限必须勾选")
    private String permission;

    @Column(columnDefinition = "TINYINT UNSIGNED COMMENT 'used for jpa group count map only'")
    private Integer userCount = 0;

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}