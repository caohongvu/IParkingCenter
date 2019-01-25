package net.cis.repository;

import net.cis.jpa.entity.FuncEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FuncRepository extends JpaRepository<FuncEntity, Long> {
	List<FuncEntity> findByStatus(Integer status);

	FuncEntity findOneByName(String name);

	@Query("SELECT f FROM FuncEntity f WHERE f.name = ?1 AND f.id <> ?2")
	FuncEntity findOneByNameAndIdNot(String name, Long id);

	List<FuncEntity> findByStatusAndParentIdIsNull(Integer status);
}
