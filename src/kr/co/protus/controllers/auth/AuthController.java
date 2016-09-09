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
  * @?��?��?�� : redEye
  * @�?경이?�� :
  * @?��로그?�� ?���? :  ?��?�� 구분�? 처리
  */
@Controller
public class AuthController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	/**
	 *  로그?�� ?��?���? ?��?��
	  * @Method Name : loginPage
	  * @?��?��?�� : 2016. 3. 7.
	  * @?��?��?�� : redEye
	  * @�?경이?�� : 
	  * @Method ?���? :
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
	  * @?��?��?�� : 2016. 3. 29.
	  * @?��?��?�� : redEye
	  * @�?경이?�� : 
	  * @Method ?���? :
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
	 *  ?��?���??�� ?��?���? ?��?��
	  * @Method Name : joinPage 
	  * @?��?��?�� : 2016. 3. 7.
	  * @?��?��?�� : redEye
	  * @�?경이?�� : 
	  * @Method ?���? :
	  * @param params
	  * @param model
	  * @return ?��?���?
	 */
	@RequestMapping(value = "/auth/join_process.do")
	public String joinPage(@RequestParam Map<String, Object> params , Model model) {
		logger.error("########################");
		/*
		 * ?��?��?�� ?��?�� �??��?�� ?��?��?�� Load
		 * ?��?��?�� ?��?�� �??��?�� ?��?�� Load
		 */
		mapper.setSqlMapper("User");
		mapper.setSqlId("TestStudentList");
		List<Map<String,Object>> studentList = mapper.list(params);
		
		/*
		 * ?��?��?�� 강사 �??��?�� ?��?��?�� Load
		 * ?��?��?�� ?��?�� �??��?�� ?��?�� Load
		 */
		mapper.setSqlMapper("User");
		mapper.setSqlId("TestTeacherList");
		List<Map<String,Object>> teacherList = mapper.list(params);
		
		/*
		 * ?��?�� 목록?�� 불러?��
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
	 *  로그?�� 처리
	  * @Method Name : login_process
	  * @?��?��?�� : 2016. 3. 7.
	  * @?��?��?�� : redEye
	  * @�?경이?�� : 
	  * @Method ?���? :
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
		 * ID,PASS �? 공백?�� ?��?��경우 �? ?��?��?�� 불러???�� 처리
		 * ID,PASS �? 공백?�� 경우 로그?�� 창으�? ?��?��. ?��?�� 주석처리 ?��?�� 
		 */		
		if(logid!="" && logpw!="" ) {
			mapper.setSqlMapper("User");
			mapper.setSqlId("selectCntLogin");
			
			int existUserCnt = mapper.listCnt(params);
			
			if(existUserCnt>0){
				/*
				 * 쿼리?��?�� ?��?��?��?�� ??�? 리�?�트1�? 
				 * 1�? ?��?��?���? ?��?��?��?�� ?��?��?��?�� ?��?��?�� ?��?���?�?.
				 * ?��?�� ?��?�� �? 결과 리턴  
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
    			
    			// 권한�?리용 콜렉?��
    			AuthInfo authInfo = new AuthInfo();
    			
    		    sessionContext.setAuthenticated(true);
    		    // ?��?�� �?리자 YN 체크 ?��?��?��
    		    sessionContext.setAdminYn("Y");
    		    
    		    sessionContext.setUserInfo(userInfo);
    		    sessionContext.setAuthInfo(authInfo);
    		   
    		   
    		    //////////////////////////////
    		    // ?��?�� Login History ???�� ?��?��?��
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
	    			
	    			// 권한�?리용 콜렉?��
	    			AuthInfo authInfo = new AuthInfo();
	    			
	    		    sessionContext.setAuthenticated(true);
	    		    // ?��?�� �?리자 YN 체크 ?��?��?��
	    		    sessionContext.setAdminYn("Y");
	    		    
	    		    sessionContext.setUserInfo(userInfo);
	    		    sessionContext.setAuthInfo(authInfo);
	    		   
	    		   
	    		    //////////////////////////////
	    		    // ?��?�� Login History ???�� ?��?��?��
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
	 * ?��?���??��?��?�� 중복버튼 ?���??�� 기존?�� ?��?���??��?��?�� ?��?�� ?��메일?�� ?��?���? 체크
	  * @Method Name : user_exist_cnt
	  * @?��?��?�� : 2016. 3. 18.
	  * @?��?��?�� : raon
	  * @�?경이?�� : 
	  * @Method ?���? :
	  * @param params
	  * @param request
	  * @param model
	 */
	@RequestMapping(value = "/user/user_exist_cnt.do")
    @ResponseBody	
	public Map<String, Object> user_exist_cnt(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
		// 결과 ?��?��
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			/*
			 * �??��?��?�� ?��?�� ?��메일?�� ?��?���? 체크
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