package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.CustomerCarEntity;

public interface CustomerCarRepository extends JpaRepository<CustomerCarEntity, Long> {
	@Query(value = "Select customer_car from CustomerCarEntity customer_car where (customer_car.numberPlate =:numberPlate) ORDER BY updatedAt DESC")
	public List<CustomerCarEntity> findCustomerCarByNumberPlate(@Param("numberPlate") String numnerPlate);

	@Query(value = "Select customer_car from CustomerCarEntity customer_car where (customer_car.numberPlate =:numberPlate AND customer_car.customer =:customer)")
	public CustomerCarEntity findCustomerCarByNumberPlateAndCustomer(@Param("numberPlate") String numnerPlate,
			@Param("customer") long customer);
}
