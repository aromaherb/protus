package kr.co.raon.commons.web;

import java.io.Serializable;
 
public class AuthInfo implements Serializable {
	
	private static final long serialVersionUID = 4561712932150655498L;

	private String superUser	= "";			// super user : 시스템 전체 관리자
	private String authCode	= "";			// admin, user 구분
	
	
	/**
	 * 시스템 전체관리자(관리자) 
	 * @return String authCode 
	 */
	public String getAuthCode() {
		return authCode;
	}

	/**
	 * 시스템 전체관리자(관리자) 
	 * @return Nothing
	 */
	public void setAuthCode(String auth_code) {
		this.authCode = auth_code;
	}
	
	/**
	 * 시스템 전체관리자(관리자) 
	 * @return String superUser
	 */
	public String getSuperUser() {
		return superUser;
	}

	/**
	 * 시스템 전체관리자(관리자) 
	 * @return Nothing
	 */
	public void setSuperUser(String super_user) {
		this.superUser = super_user;
	}
}
