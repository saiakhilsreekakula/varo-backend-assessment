/**
 * 
 */
package com.varo.assessment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.varo.assessment.model.Address;
import com.varo.assessment.model.AddressRequest;
import com.varo.assessment.model.UpsertUserResponse;
import com.varo.assessment.model.User;
import com.varo.assessment.model.UserExistsResponse;
import com.varo.assessment.repository.AdressRepository;
import com.varo.assessment.repository.UserRepository;

/**
 * @author saiak
 *
 */
@RestController
public class UserController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AdressRepository addressRepo;
	
	@PostMapping("upsertUser")
	public ResponseEntity<UpsertUserResponse> upsertUser(@Valid @RequestBody User user) throws Exception {
		UpsertUserResponse response = new UpsertUserResponse();
		User existingUser = userRepo.findByUserId(user.getUserId());
		boolean userExists = existingUser != null;
		if(userExists) {
			Address existingUserAddress = existingUser.getAddress();
			existingUserAddress.setAddressLine1(user.getAddress().getAddressLine1());
			existingUserAddress.setAddressLine2(user.getAddress().getAddressLine2());
			existingUserAddress.setCity(user.getAddress().getCity());
			existingUserAddress.setState(user.getAddress().getState());
			existingUserAddress.setZipCode(user.getAddress().getZipCode());
			existingUser.setEmail(user.getEmail());
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setIsActive(user.getIsActive());
			existingUser.setPhoneNumber(user.getPhoneNumber());
			userRepo.save(existingUser);
		} else {
			user.getAddress().setUser(user);
			userRepo.save(user);
		}
		response.setStatus(userExists?"User Has Been Updated Successfully." : "User Has Been Created Successfully.");
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("isUserExists")
	public ResponseEntity<UserExistsResponse> isUserExists(@RequestBody User user) {
		UserExistsResponse response = new UserExistsResponse();
		response.setActiveUsersCount(userRepo.countByEmailAndIsActive(user.getEmail(), true));
		response.setNonActiveUsersCount(userRepo.countByEmailAndIsActive(user.getEmail(), false));
		return new ResponseEntity<UserExistsResponse>(response, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("listUsers")
	public ResponseEntity<List<User>> listUsers() {
		return new ResponseEntity<List<User>>(userRepo.findAll(), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("getUserDetails")
	public ResponseEntity<User> getUserDetails(@RequestBody User user) throws Exception {
		User resultUser = userRepo.findByUserId(user.getUserId());
		if(resultUser == null) {
			throw new Exception("No Data Found.");
		}
		return new ResponseEntity<User>(resultUser, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("deleteUser")
	public ResponseEntity deleteUser(@RequestBody User user) throws Exception {
		User userToDeactivate = userRepo.findByUserId(user.getUserId());
		if(userToDeactivate == null) {
			throw new Exception("No Data Found.");
		}
		userToDeactivate.setIsActive(false);
		userRepo.save(userToDeactivate);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("updateAddress")
	public ResponseEntity updateAddress(@Valid @RequestBody AddressRequest address) {
		User user = userRepo.findByUserId(address.getUserId());
		user.setUserId(address.getUserId());
		Address addressToUpdate = user.getAddress();
		addressToUpdate.setAddressLine1(address.getAddressLine1());
		addressToUpdate.setAddressLine2(address.getAddressLine2());
		addressToUpdate.setCity(address.getCity());
		addressToUpdate.setState(address.getState());
		addressToUpdate.setZipCode(address.getZipCode());
		userRepo.save(user);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
