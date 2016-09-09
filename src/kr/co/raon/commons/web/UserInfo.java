package kr.co.raon.commons.web;

import java.io.Serializable;
 
public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = 4561712932150655498L;

	private String userId;
	private String userSeq;
	private String userName;
	private String userHpNum;
	private String userEmail;
	
	private String agreeYN;
	// 사용자 구분
	private String userType;
	// 사용자 권한
	private String userAuth;
	
	//사용자 아이디 얻기
	public String getUserId() {
		return userId;
	}
	
	//사용자 아이디 세팅
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}
	
	//사용자 이름 얻기
	public String getUserName() {
		return userName;
	}
	
	//사용자 이름 세팅
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	//사용자 휴대폰번호 얻기
	public String getUserHpNum() {
		return userHpNum;
	}
	
	//사용자 휴대폰번호 세팅
	public void setUserHpNum(String userHpNum) {
		this.userHpNum = userHpNum;
	}	
	
	//사용자 이메일 얻기
	public String getUserEmail() {
		return userEmail;
	}
	
	//사용자 이메일 세팅
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	//승인여부 얻기
	public String getAgreeYN() {
		return agreeYN;
	}
	
	//승인여부 세팅
	public void setAgreeYN(String agree_yn) {
		this.agreeYN = agree_yn;
	}
		
	//사용자 구분 얻기
	public String getUserType() {
		return userType;
	}
	
	//사용자 구분 세팅
	public void setUserType(String user_type) {
		this.userType = user_type;
	}
	
	//사용자 권한 얻기
	public String getUserAuth() {
		return userAuth;
	}
	
	//사용자 권한 세팅
	public void setUserAuth(String user_auth) {
		this.userEmail = user_auth;
	}

}
