package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.UserSecurityEntity;

public interface UserSecurityRepository extends JpaRepository<UserSecurityEntity, String> {

	UserSecurityEntity findByUsername(String username);

}
