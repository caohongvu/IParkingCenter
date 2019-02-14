package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.ParkingConfigEntity;

public interface ParkingConfigRepository extends JpaRepository<ParkingConfigEntity, Long> {
	ParkingConfigEntity findByConfigKey(String configKey);

	List<ParkingConfigEntity> findByParkingConfigTypeId(long configKey);

	@Query("SELECT config  FROM ParkingConfigEntity config WHERE 1=1 AND  (config.configKey LIKE CONCAT('%',:configKey,'%') OR :configKey is NULL) AND  (config.companyId =:company  OR :company is NULL) AND  (config.parkingConfigTypeId =:configType  OR :configType is NULL)")
	List<ParkingConfigEntity> findByParkingConfigs(@Param("configKey") String configKey, @Param("company") Long company,
			@Param("configType") Long configType);
}
