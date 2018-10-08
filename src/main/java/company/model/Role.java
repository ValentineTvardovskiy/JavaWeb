package company.model;

public class Role {

    private RoleName roleName;
    private User user;

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public static Role of(RoleName roleName) {
        return new Role(roleName);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum RoleName {
        USER, ADMIN
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
