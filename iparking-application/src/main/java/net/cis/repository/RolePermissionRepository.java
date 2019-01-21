package net.cis.repository;

import net.cis.jpa.entity.FuncEntity;
import net.cis.jpa.entity.RoleEntity;
import net.cis.jpa.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, Long> {
    List<RolePermissionEntity> findByRole(RoleEntity role);
    RolePermissionEntity findOneByRoleAndFunc(RoleEntity roleEntity, FuncEntity funcEntity);
}
