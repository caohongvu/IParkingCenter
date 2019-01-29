package net.cis.repository;

import net.cis.jpa.entity.SmsConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsConfigRepository extends JpaRepository<SmsConfigEntity, Long> {
    SmsConfigEntity findFirstByOrderByIdAsc();
}
