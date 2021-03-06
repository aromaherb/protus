package kr.co.protus.controllers.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.raon.commons.CommonProperty;
import kr.co.raon.commons.util.AesUtil;
import kr.co.raon.commons.util.RaonFunctions;
import kr.co.raon.commons.util.SendMail;
import kr.co.raon.commons.util.StringUtil;
import kr.co.raon.commons.web.AuthInfo;
import kr.co.raon.commons.web.BaseController;
import kr.co.raon.commons.web.PagingInfo;
import kr.co.raon.commons.web.SessionContext;
import kr.co.raon.commons.web.UserInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
  * @FileName : AuthController.java
  * @Project : VAS
  * @Date : 2016. 3. 7. 
  * @??±? : redEye
  * @λ³?κ²½μ΄? ₯ :
  * @?λ‘κ·Έ?¨ ?€λͺ? :  ?? κ΅¬λΆλ³? μ²λ¦¬
  */
@Controller
public class AuthController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	/**
	 *  λ‘κ·Έ?Έ ??΄μ§? ?΄?
	  * @Method Name : loginPage
	  * @??±?Ό : 2016. 3. 7.
	  * @??±? : redEye
	  * @λ³?κ²½μ΄? ₯ : 
	  * @Method ?€λͺ? :
	  * @param params
	  * @param model
	  * @return
	 */
	@RequestMapping(value = "/auth/login.do")
	public String login_form(@RequestParam Map<String, Object> params , Model model) {
		
		return "auth/login_form";
	}
	
	/**
	 * Logout
	  * @Method Name : log_out
	  * @??±?Ό : 2016. 3. 29.
	  * @??±? : redEye
	  * @λ³?κ²½μ΄? ₯ : 
	  * @Method ?€λͺ? :
	  * @param params
	  * @param model
	  * @return
	 */
	@RequestMapping(value = "/auth/logout.do")
	public String log_out(@RequestParam Map<String, Object> params , HttpSession session,Model model) {
		session.invalidate();
		return "auth/login_form";
	}

	/**
	 *  ??κ°?? ??΄μ§? ?΄?
	  * @Method Name : joinPage 
	  * @??±?Ό : 2016. 3. 7.
	  * @??±? : redEye
	  * @λ³?κ²½μ΄? ₯ : 
	  * @Method ?€λͺ? :
	  * @param params
	  * @param model
	  * @return ??΄μ§?
	 */
	@RequestMapping(value = "/auth/join_process.do")
	public String joinPage(@RequestParam Map<String, Object> params , Model model) {
		logger.error("########################");
		/*
		 * ??€?Έ ?? κ°???© ?°?΄?° Load
		 * ??€?Έ ?? κ°???© ?? Load
		 */
		mapper.setSqlMapper("User");
		mapper.setSqlId("TestStudentList");
		List<Map<String,Object>> studentList = mapper.list(params);
		
		/*
		 * ??€?Έ κ°μ¬ κ°???© ?°?΄?° Load
		 * ??€?Έ ?? κ°???© ?? Load
		 */
		mapper.setSqlMapper("User");
		mapper.setSqlId("TestTeacherList");
		List<Map<String,Object>> teacherList = mapper.list(params);
		
		/*
		 * ?? λͺ©λ‘? λΆλ¬?΄
		 */
		mapper.setSqlMapper("Introduce");
		mapper.setSqlId("TestInstitudeList");
		List<Map<String,Object>> institudeList = mapper.list(params);
		
		model.addAttribute("studentList", studentList);
		model.addAttribute("teacherList", teacherList);
		model.addAttribute("institudeList", institudeList);
		
		return "auth/join_form.only";
	}
	
	
	/**
	 *  λ‘κ·Έ?Έ μ²λ¦¬
	  * @Method Name : login_process
	  * @??±?Ό : 2016. 3. 7.
	  * @??±? : redEye
	  * @λ³?κ²½μ΄? ₯ : 
	  * @Method ?€λͺ? :
	  * @param params
	  * @param request
	  * @param model
	  * @return
	 */
	@RequestMapping(value = "/auth/login_process.do")
	public String login_process(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {

				
		String logid = StringUtil.object2Str(params.get("logemail"));
		String logpw = StringUtil.object2Str(params.get("logpw"));		
		
		String logpwDecode = StringUtil.object2Str(params.get("logpw"));  
		
		String adminYn = "";
		String user_name = "";
		String user_seq = "";
		String hp_num = ""; 
		String email = "";
		SessionContext sessionContext = getSession();
		
		/*
		 * ID,PASS κ°? κ³΅λ°±?΄ ??κ²½μ° λ§? ?°?΄?° λΆλ¬??? μ²λ¦¬
		 * ID,PASS κ°? κ³΅λ°±?Ό κ²½μ° λ‘κ·Έ?Έ μ°½μΌλ‘? ?΄?. ??¨ μ£Όμμ²λ¦¬ ??Έ 
		 */		
		if(logid!="" && logpw!="" ) {
			mapper.setSqlMapper("User");
			mapper.setSqlId("selectCntLogin");
			
			int existUserCnt = mapper.listCnt(params);
			
			if(existUserCnt>0){
				/*
				 * μΏΌλ¦¬?? ?½?΄?€? ??λ‘? λ¦¬λ?ΈνΈ1κ°? 
				 * 1κ°? ?°?΄?°κ°? ?΅?©?΄? ???? ?°?Ό? ??λΆ?λΆ?.
				 * ?Έ? ??± λ°? κ²°κ³Ό λ¦¬ν΄  
				 */				
    			mapper.setSqlMapper("User");
    			mapper.setSqlId("selectDataLogin");    				
    			
    			Map<String, Object> userdetail = mapper.detail(params);  			
    			
    			
    			if(userdetail.containsKey("EMAIL")){
    				email = StringUtil.object2Str(userdetail.get("EMAIL"));
    			}
    			
    			if(userdetail.containsKey("USER_SEQ")){
    				user_seq = StringUtil.object2Str(userdetail.get("USER_SEQ"));
    			}
    			
    			if(userdetail.containsKey("USER_NAME")){
    				user_name = StringUtil.object2Str(userdetail.get("USER_NAME"));
    			}
    			
    			if(userdetail.containsKey("HP_NUM")){
    				hp_num = StringUtil.object2Str(userdetail.get("HP_NUM"));
    			}
    			
    			
    			UserInfo userInfo = new UserInfo();
    			userInfo.setUserSeq(user_seq);
    			userInfo.setUserEmail(email);
    			userInfo.setUserName(user_name);
    			userInfo.setUserHpNum(hp_num);
    			
    			// κΆνκ΄?λ¦¬μ© μ½λ ?°
    			AuthInfo authInfo = new AuthInfo();
    			
    		    sessionContext.setAuthenticated(true);
    		    // ?₯? κ΄?λ¦¬μ YN μ²΄ν¬ ???¨
    		    sessionContext.setAdminYn("Y");
    		    
    		    sessionContext.setUserInfo(userInfo);
    		    sessionContext.setAuthInfo(authInfo);
    		   
    		   
    		    //////////////////////////////
    		    // ?₯? Login History ???₯ ???¨
    		    //////////////////////////////
    		    
    		    //if(!StringUtil.object2Str(userdetail.get("is_admin")).equals("U")){
    		    //	adminYn = "Y";
    		    //	sessionContext.setAdminYn("Y");
    		    //}else{
    		    // 	adminYn = "N";
    		    //}
    			
			} else if(existUserCnt == 0) {
				mapper.setSqlMapper("User");
				mapper.setSqlId("selectListCntTempPw");
				int tempExistUserCnt = mapper.listCnt(params);
				
				if(tempExistUserCnt > 0) {
					mapper.setSqlId("selectDataTempPw");
					Map<String, Object> userdetail = mapper.detail(params);
					
					if(userdetail.containsKey("EMAIL")){
	    				email = StringUtil.object2Str(userdetail.get("EMAIL"));
	    			}
	    			
	    			if(userdetail.containsKey("USER_SEQ")){
	    				user_seq = StringUtil.object2Str(userdetail.get("USER_SEQ"));
	    			}
	    			
	    			if(userdetail.containsKey("USER_NAME")){
	    				user_name = StringUtil.object2Str(userdetail.get("USER_NAME"));
	    			}
	    			
	    			if(userdetail.containsKey("HP_NUM")){
	    				hp_num = StringUtil.object2Str(userdetail.get("HP_NUM"));
	    			}
	    			
	    			
	    			UserInfo userInfo = new UserInfo();
	    			userInfo.setUserSeq(user_seq);
	    			userInfo.setUserEmail(email);
	    			userInfo.setUserName(user_name);
	    			userInfo.setUserHpNum(hp_num);
	    			
	    			// κΆνκ΄?λ¦¬μ© μ½λ ?°
	    			AuthInfo authInfo = new AuthInfo();
	    			
	    		    sessionContext.setAuthenticated(true);
	    		    // ?₯? κ΄?λ¦¬μ YN μ²΄ν¬ ???¨
	    		    sessionContext.setAdminYn("Y");
	    		    
	    		    sessionContext.setUserInfo(userInfo);
	    		    sessionContext.setAuthInfo(authInfo);
	    		   
	    		   
	    		    //////////////////////////////
	    		    // ?₯? Login History ???₯ ???¨
	    		    //////////////////////////////
	    		    
	    		    //if(!StringUtil.object2Str(userdetail.get("is_admin")).equals("U")){
	    		    //	adminYn = "Y";
	    		    //	sessionContext.setAdminYn("Y");
	    		    //}else{
	    		    // 	adminYn = "N";
	    		    //}
				}
			} else{
				return "redirect:/auth/login.do";
			}
				
		} else {
			return "redirect:/auth/login.do";
		}
		
		return "redirect:/admin/mainpage.do";
		
	}

    	
	
	/**
	 * ??κ°??? ? μ€λ³΅λ²νΌ ?΄λ¦?? κΈ°μ‘΄? ??κ°????΄ ?? ?΄λ©μΌ?΄ ??μ§? μ²΄ν¬
	  * @Method Name : user_exist_cnt
	  * @??±?Ό : 2016. 3. 18.
	  * @??±? : raon
	  * @λ³?κ²½μ΄? ₯ : 
	  * @Method ?€λͺ? :
	  * @param params
	  * @param request
	  * @param model
	 */
	@RequestMapping(value = "/user/user_exist_cnt.do")
    @ResponseBody	
	public Map<String, Object> user_exist_cnt(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
		// κ²°κ³Ό ??
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			/*
			 * κ°????΄ ?? ?΄λ©μΌ?΄ ??μ§? μ²΄ν¬
			 */
			mapper.setSqlMapper("User");
			mapper.setSqlId("selectUserExistCnt");
			int existCnt = mapper.listCnt(params);
			
			result.put("result_cnt", existCnt);
			result.put("result_cd", "200");
			result.put("result_status", "true");
		} catch (Exception e) {
			result.put("result_cnt", 0);
    		result.put("result_cd", -999);
    		result.put("result_status", "false");   
		} finally {
			//
		}
		
		return result;
	}
	
	
}