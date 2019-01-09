package net.cis.repository;

import net.cis.jpa.entity.FuncEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncRepository extends JpaRepository<FuncEntity, Long> {
}
