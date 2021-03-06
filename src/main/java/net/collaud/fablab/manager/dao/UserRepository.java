package net.collaud.fablab.manager.dao;

import java.util.List;
import java.util.Optional;
import net.collaud.fablab.manager.data.UserBalanceEO;
import net.collaud.fablab.manager.data.UserEO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author Gaetan Collaud <gaetancollaud@gmail.com>
 */
@Transactional
public interface UserRepository extends JpaRepository<UserEO, Long>{

	@Override
	@Query("SELECT u "
			+ " FROM UserEO u "
			+ " WHERE u.id=:id")
	UserEO findOne(@Param("id")Long id);
	
	
	@Query("SELECT u "
			+ " FROM UserEO u "
			+ " LEFT JOIN FETCH u.groups g "
			+ " LEFT JOIN FETCH u.membershipType mt "
			+ " LEFT JOIN FETCH u.subscriptions s "
			+ " WHERE u.id=:id")
	Optional<UserEO> findOneDetails(@Param("id")Long id);
	
	@Query("SELECT u "
			+ " FROM UserEO u "
			+ " LEFT JOIN FETCH u.groups g "
			+ " LEFT JOIN FETCH g.roles r"
			+ " LEFT JOIN FETCH u.subscriptions sub"
			+ " LEFT JOIN FETCH u.membershipType mt "
			+ " WHERE u.id=:id")
	Optional<UserEO> findOneByIdAndFetchRoles(@Param("id")Long id);
	
	@Query("SELECT u "
			+ " FROM UserEO u "
			+ " WHERE u.email=:login")
	Optional<UserEO> findByLoginOrEmail(@Param("login") String login);
	
	@Override
	@Query("SELECT DISTINCT u "
			+ " FROM UserEO u "
			+ " LEFT JOIN FETCH u.balance b "
			+ " LEFT JOIN FETCH u.membershipType mt "
			+ " LEFT JOIN FETCH u.subscriptions s "
			+ " WHERE u.enabled=1 ")
	List<UserEO> findAll();
	
	
	@Query("SELECT ub "
			+ " FROM UserBalanceEO ub "
			+ " WHERE ub.userId=:userId ")
	Optional<UserBalanceEO> getUserBalanceFromUserId(@Param("userId")Long userId);

	@Query("SELECT u "
			+ " FROM UserEO u "
			+ " WHERE u.rfid=:rfid")
	Optional<UserEO> findByRFID(@Param("rfid") String rfid);
	
}
