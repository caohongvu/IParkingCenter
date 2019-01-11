package net.cis.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "role_permission")
public class RolePermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "func_id", referencedColumnName = "id")
    private FuncEntity func;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public FuncEntity getFunc() {
        return func;
    }

    public void setFunc(FuncEntity func) {
        this.func = func;
    }
}
