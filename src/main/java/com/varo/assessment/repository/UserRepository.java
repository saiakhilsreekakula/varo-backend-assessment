/**
 * 
 */
package com.varo.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varo.assessment.model.User;

/**
 * @author saiak
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	Long countByEmailAndIsActive(String email, Boolean isActive);
	
	User findByUserId(String userId);
}
