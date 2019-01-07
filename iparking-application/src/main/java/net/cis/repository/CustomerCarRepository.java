package net.cis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.cis.jpa.entity.CustomerCarEntity;

public interface CustomerCarRepository extends JpaRepository<CustomerCarEntity, Long> {
	@Query(value = "Select customer_car from CustomerCarEntity customer_car where (customer_car.numberPlate =:numberPlate AND customer_car.verified = :verified)")
	public List<CustomerCarEntity> findCustomerCarByNumberPlate(@Param("numberPlate") String numnerPlate,@Param("verified") int verified);
	
	
	@Query(value = "Select customer_car from CustomerCarEntity customer_car where (customer_car.numberPlate =:numberPlate AND customer_car.customer =:customer)")
	public List<CustomerCarEntity> findCustomerCarByNumberPlateAndCustomer(@Param("numberPlate") String numnerPlate,@Param("customer") String customer);
}
