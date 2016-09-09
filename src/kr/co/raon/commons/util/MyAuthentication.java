package kr.co.raon.commons.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthentication extends Authenticator {
	
	 PasswordAuthentication pa;
	 
	 public MyAuthentication(){
	     //smtp server의 아이디와 패스워드를 입력한다.
	     pa = new PasswordAuthentication("arparumlee@gmail.com","arplove99");
	     //pa = new PasswordAuthentication("koreapressFoundation@gmail.com","koreapressFoundation#mail#@$");
	 }

	 // 아래의 메소드는 시스템측에서 사용하는 메소드이다.
	 public PasswordAuthentication getPasswordAuthentication() {
	        return pa;
	 }

}
