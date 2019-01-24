package net.cis.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import net.cis.jpa.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	@Query(value ="Select user from UserEntity user where (username LIKE CONCAT('%',:username,'%') OR :username is null) AND (fullname LIKE CONCAT('%',:fullname,'%') OR :fullname is null)")
	public List<UserEntity> getAll(@Param("username") String username,@Param("fullname") String fullname, Pageable pageable);
	
	public UserEntity findByUsername(String username);

}
