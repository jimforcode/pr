package cn.creatoo.model.system.dto;

/**
 * Created by Administrator on 2017/8/3 0003.
 */
public class PermisionRolesDto {
    private String permission;
    private  String roleName;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
