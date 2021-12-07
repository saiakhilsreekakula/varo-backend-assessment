/**
 * 
 */
package com.varo.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varo.assessment.model.Address;
import com.varo.assessment.model.User;

/**
 * @author saiak
 *
 */
public interface AdressRepository extends JpaRepository<Address, Long> {
	Address findByUser(User user);

}
