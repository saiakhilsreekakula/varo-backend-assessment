/**
 * 
 */
package com.varo.assessment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
@Controller
public class UserController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AdressRepository addressRepo;
	
	/** 
	 * API Name - UpsertUser
	 * Method - POST
	 * Input - Input of type User class
	 * Output - Output of type UpsertUserResponse class
	 * Description - This API creates the user, if user is not present.
	 * It updates the user, if user is present.
	**/
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
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/** 
	 * API Name - IsUserExists
	 * Method - POST
	 * Input - Input of type User class
	 * Output - Output of type UserExistsResponse class
	 * Description - This API checks if the user is present in the records with the email of user.
	**/
	@PostMapping("isUserExists")
	public ResponseEntity<UserExistsResponse> isUserExists(@RequestBody User user) {
		UserExistsResponse response = new UserExistsResponse();
		response.setActiveUsersCount(userRepo.countByEmailAndIsActive(user.getEmail(), true));
		response.setNonActiveUsersCount(userRepo.countByEmailAndIsActive(user.getEmail(), false));
		return new ResponseEntity<UserExistsResponse>(response, HttpStatus.OK);
	}
	
	/** 
	 * API Name - ListUsers
	 * Method - GET
	 * Output - Output is the List of users of type User class
	 * Description - This API returns list of all the users present in the records.
	**/
	@GetMapping("listUsers")
	public ResponseEntity<List<User>> listUsers() {
		return new ResponseEntity<List<User>>(userRepo.findAll(), HttpStatus.OK);
	}
	
	/** 
	 * API Name - GetUserDetails
	 * Method - POST
	 * Input - Input of type User class
	 * Output - Output of type User class
	 * Description - This API gets the details of the user based on user id.
	**/
	@PostMapping("getUserDetails")
	public ResponseEntity<User> getUserDetails(@RequestBody User user) throws Exception {
		User resultUser = userRepo.findByUserId(user.getUserId());
		if(resultUser == null) {
			throw new Exception("No Data Found.");
		}
		return new ResponseEntity<User>(resultUser, HttpStatus.OK);
	}
	
	/** 
	 * API Name - DeleteUser
	 * Method - DELETE
	 * Input - Input of type User class
	 * Output - Output of type Map if the user is deleted.
	 * Description - This API gets the user based on user id and makes is_active flag as false.
	**/
	@DeleteMapping("deleteUser")
	public ResponseEntity<Map<String, String>> deleteUser(@RequestBody User user) throws Exception {
		Map<String, String> response = new HashMap<String, String>();
		User userToDeactivate = userRepo.findByUserId(user.getUserId());
		if(userToDeactivate == null) {
			throw new Exception("No Data Found.");
		}
		userToDeactivate.setIsActive(false);
		userRepo.save(userToDeactivate);
		response.put("status", "user has been deleted.");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/** 
	 * API Name - UpdateAddress
	 * Method - POST
	 * Input - Input of type AddressRequest class
	 * Output - Output of type Map if the user address is updated.
	 * Description - This API gets the user based on user id and updates his address as per the request.
	**/
	@PostMapping("updateAddress")
	public ResponseEntity<Map<String, String>> updateAddress(@Valid @RequestBody AddressRequest address) throws Exception {
		Map<String, String> response = new HashMap<String, String>();
		User user = userRepo.findByUserId(address.getUserId());
		if(user == null) {
			throw new Exception("No Data Found.");
		}
		user.setUserId(address.getUserId());
		Address addressToUpdate = user.getAddress();
		addressToUpdate.setAddressLine1(address.getAddressLine1());
		addressToUpdate.setAddressLine2(address.getAddressLine2());
		addressToUpdate.setCity(address.getCity());
		addressToUpdate.setState(address.getState());
		addressToUpdate.setZipCode(address.getZipCode());
		userRepo.save(user);
		response.put("status", "user address has been updated.");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
