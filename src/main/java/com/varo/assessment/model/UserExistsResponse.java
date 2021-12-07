/**
 * 
 */
package com.varo.assessment.model;

/**
 * @author saiak
 *
 */
public class UserExistsResponse {

	private Long activeUsersCount;
	private Long nonActiveUsersCount;
	/**
	 * @return the activeUsersCount
	 */
	public Long getActiveUsersCount() {
		return activeUsersCount;
	}
	/**
	 * @param activeUsersCount the activeUsersCount to set
	 */
	public void setActiveUsersCount(Long activeUsersCount) {
		this.activeUsersCount = activeUsersCount;
	}
	/**
	 * @return the nonActiveUsersCount
	 */
	public Long getNonActiveUsersCount() {
		return nonActiveUsersCount;
	}
	/**
	 * @param nonActiveUsersCount the nonActiveUsersCount to set
	 */
	public void setNonActiveUsersCount(Long nonActiveUsersCount) {
		this.nonActiveUsersCount = nonActiveUsersCount;
	}
	
	
}
