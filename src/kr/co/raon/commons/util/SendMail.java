package kr.co.raon.commons.util;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {   
	
	public static void main(String[] args){
        try{
            new SendMail();
        }catch(Exception e){
            System.out.println("Error");
        }
    }
	/**
	 * email 전송
	  * @Method Name : mailSenderChoiced
	  * @작성일 : 2016. 3. 8.
	  * @작성자 : redEye
	  * @변경이력 : 
	  * @Method 설명 :
	  * @param from
	  * @param to
	  * @param content
	  * @throws UnsupportedEncodingException
	 */
    public void mailSenderChoiced(String from, String to,String content) throws Exception {
    	 //String to = "clon_liamo@naver.com";   //받는 사람
		 //String from = "master@master.co.kr";   //보내는 사람
		 
		 
		 //String host = "smtp.gmail.com";     //Gmail: smtp.gmail.com
		 // String host = "mail.kpf.or.kr";     //kpf
		 String msgText = "";
		  
		 boolean debug = Boolean.valueOf("true").booleanValue();
		 Properties props = new Properties();		  
		 		
		 props.put("mail.transport.protocol", "smtp");
		 props.put("mail.smtp.starttls.enable", "true"); //Gmail의 경우 반드시 true로 세팅해야 한다.
		 props.put("mail.smtp.host", "smtp.gmail.com");
		 props.put("mail.smtp.auth","true");    //반드시 프로퍼티에 세팅되어 있어야 한다. 아니면 인증을 시도하지 않는다.
		 props.put("mail.smtp.port", "587");    //네이버는 587.
		 props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); 
		 
		 /*
		 props.put("mail.smtp.host","smtp.naver.com"); // 네이버 SMTP
		 props.put("mail.smtp.port", "587");
		 props.put("mail.smtp.starttls.enable", "true");
		 props.put("mail.smtp.auth", "true");
		 props.put("mail.smtp.debug", "true");		 
		 
		 props.put("mail.smtp.socketFactory.port", "465");
		 props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		 props.put("mail.smtp.socketFactory.fallback", "false");
		 */
		 Authenticator auth = new MyAuthentication();
		 //if (debug) props.put("mail.debug", "true"); 
		  
		 Session session = Session.getDefaultInstance(props, auth);
		 session.setDebug(true);
		 
		 try {
			   //네이버는 한번에 최대 100명에게 보낼수 있다.
			   /*
			   InternetAddress[] address = new InternetAddress[100];
			   for(int i=0; i<100; i++){
			    address[i] = new InternetAddress("clon_liamo@naver.com");
			   }
			   */
			   
			   //InternetAddress fromAddr = new InternetAddress(from);
			   /*
			    * 메일내용을 담을객체
			    * 제목
			    * 보내는사람
			    * 받는 사람
			    * 내용
			    */
			   Message msg = new MimeMessage(session);
			   //InternetAddress[] address = {new InternetAddress(to)};
			   msg.setSubject("TEST"); // 제목
			   
			   //닉네임(보내는사람) 세팅
			   Address fromAddr = new InternetAddress(from);			   
			   msg.setFrom(fromAddr);
			   
			   Address toAddr = new InternetAddress(to);
			   msg.addRecipient(Message.RecipientType.TO, toAddr); // 받는 사람
			  			   
			   //내용			  
			   msgText ="<a href='http://raonsnc.iptime.org/emrview/test.jsp?certification="+content+"'>Click</a>";   
			   msg.setContent(msgText, "text/html;charset=8859_1"); // 내용과 인코딩		   
			   
			   //setText(text, charset) //setText는 이미지가 안보내짐. 위의 setContent에 이미지를 보낼수 있다
			   //msg.setText(msgText);
			   
			   //메일 전송부분.
			   Transport.send(msg);
			      
		  }catch(MessagingException mex) {
		   mex.printStackTrace();
		   Exception ex = mex;
		 
		  }
		
	}
    
}