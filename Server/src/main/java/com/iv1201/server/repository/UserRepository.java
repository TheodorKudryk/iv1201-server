package com.iv1201.server.repository;


import com.iv1201.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
/**
 *
 * @author theok
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    User findByEmail(String email);
}
