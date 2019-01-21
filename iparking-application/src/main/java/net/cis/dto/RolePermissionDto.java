package net.cis.dto;

public class RolePermissionDto {
    private Long id;
    private RoleDto role;
    private FuncDto func;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public FuncDto getFunc() {
        return func;
    }

    public void setFunc(FuncDto func) {
        this.func = func;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
