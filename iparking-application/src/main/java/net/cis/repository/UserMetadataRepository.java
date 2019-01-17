package net.cis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cis.jpa.entity.UserMetadataEntity;

public interface UserMetadataRepository extends JpaRepository<UserMetadataEntity, Integer>{

}
