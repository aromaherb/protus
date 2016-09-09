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
  * @?‘?„±? : redEye
  * @ë³?ê²½ì´? ¥ :
  * @?”„ë¡œê·¸?¨ ?„¤ëª? :  ?šŒ?› êµ¬ë¶„ë³? ì²˜ë¦¬
  */
@Controller
public class AuthController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	/**
	 *  ë¡œê·¸?¸ ?˜?´ì§? ?´?™
	  * @Method Name : loginPage
	  * @?‘?„±?¼ : 2016. 3. 7.
	  * @?‘?„±? : redEye
	  * @ë³?ê²½ì´? ¥ : 
	  * @Method ?„¤ëª? :
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
	  * @?‘?„±?¼ : 2016. 3. 29.
	  * @?‘?„±? : redEye
	  * @ë³?ê²½ì´? ¥ : 
	  * @Method ?„¤ëª? :
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
	 *  ?šŒ?›ê°??… ?˜?´ì§? ?´?™
	  * @Method Name : joinPage 
	  * @?‘?„±?¼ : 2016. 3. 7.
	  * @?‘?„±? : redEye
	  * @ë³?ê²½ì´? ¥ : 
	  * @Method ?„¤ëª? :
	  * @param params
	  * @param model
	  * @return ?˜?´ì§?
	 */
	@RequestMapping(value = "/auth/join_process.do")
	public String joinPage(@RequestParam Map<String, Object> params , Model model) {
		logger.error("########################");
		/*
		 * ?…Œ?Š¤?Š¸ ?•™?ƒ ê°??…?š© ?°?´?„° Load
		 * ?…Œ?Š¤?Š¸ ?•™?ƒ ê°??…?š© ?•™?› Load
		 */
		mapper.setSqlMapper("User");
		mapper.setSqlId("TestStudentList");
		List<Map<String,Object>> studentList = mapper.list(params);
		
		/*
		 * ?…Œ?Š¤?Š¸ ê°•ì‚¬ ê°??…?š© ?°?´?„° Load
		 * ?…Œ?Š¤?Š¸ ?•™?ƒ ê°??…?š© ?•™?› Load
		 */
		mapper.setSqlMapper("User");
		mapper.setSqlId("TestTeacherList");
		List<Map<String,Object>> teacherList = mapper.list(params);
		
		/*
		 * ?•™?› ëª©ë¡?„ ë¶ˆëŸ¬?˜´
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
	 *  ë¡œê·¸?¸ ì²˜ë¦¬
	  * @Method Name : login_process
	  * @?‘?„±?¼ : 2016. 3. 7.
	  * @?‘?„±? : redEye
	  * @ë³?ê²½ì´? ¥ : 
	  * @Method ?„¤ëª? :
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
		 * ID,PASS ê°? ê³µë°±?´ ?•„?‹ê²½ìš° ë§? ?°?´?„° ë¶ˆëŸ¬???„œ ì²˜ë¦¬
		 * ID,PASS ê°? ê³µë°±?¼ ê²½ìš° ë¡œê·¸?¸ ì°½ìœ¼ë¡? ?´?™. ?•˜?‹¨ ì£¼ì„ì²˜ë¦¬ ?™•?¸ 
		 */		
		if(logid!="" && logpw!="" ) {
			mapper.setSqlMapper("User");
			mapper.setSqlId("selectCntLogin");
			
			int existUserCnt = mapper.listCnt(params);
			
			if(existUserCnt>0){
				/*
				 * ì¿¼ë¦¬?—?„œ ?½?–´?˜¤?Š” ??ë¡? ë¦¬ë?¸íŠ¸1ê°? 
				 * 1ê°? ?°?´?„°ê°? ?†µ?•©?´?ƒ ?•„?‹ˆ?ƒ?— ?”°?¼?„œ ?Œ?—…ë¶?ë¶?.
				 * ?„¸?…˜ ?ƒ?„± ë°? ê²°ê³¼ ë¦¬í„´  
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
    			
    			// ê¶Œí•œê´?ë¦¬ìš© ì½œë ‰?„°
    			AuthInfo authInfo = new AuthInfo();
    			
    		    sessionContext.setAuthenticated(true);
    		    // ?–¥?›„ ê´?ë¦¬ì YN ì²´í¬ ?•„?š”?•¨
    		    sessionContext.setAdminYn("Y");
    		    
    		    sessionContext.setUserInfo(userInfo);
    		    sessionContext.setAuthInfo(authInfo);
    		   
    		   
    		    //////////////////////////////
    		    // ?–¥?›„ Login History ???¥ ?•„?š”?•¨
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
	    			
	    			// ê¶Œí•œê´?ë¦¬ìš© ì½œë ‰?„°
	    			AuthInfo authInfo = new AuthInfo();
	    			
	    		    sessionContext.setAuthenticated(true);
	    		    // ?–¥?›„ ê´?ë¦¬ì YN ì²´í¬ ?•„?š”?•¨
	    		    sessionContext.setAdminYn("Y");
	    		    
	    		    sessionContext.setUserInfo(userInfo);
	    		    sessionContext.setAuthInfo(authInfo);
	    		   
	    		   
	    		    //////////////////////////////
	    		    // ?–¥?›„ Login History ???¥ ?•„?š”?•¨
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
	 * ?šŒ?›ê°??…?• ?•Œ ì¤‘ë³µë²„íŠ¼ ?´ë¦??‹œ ê¸°ì¡´?— ?šŒ?›ê°??…?˜?–´ ?ˆ?Š” ?´ë©”ì¼?´ ?ˆ?Š”ì§? ì²´í¬
	  * @Method Name : user_exist_cnt
	  * @?‘?„±?¼ : 2016. 3. 18.
	  * @?‘?„±? : raon
	  * @ë³?ê²½ì´? ¥ : 
	  * @Method ?„¤ëª? :
	  * @param params
	  * @param request
	  * @param model
	 */
	@RequestMapping(value = "/user/user_exist_cnt.do")
    @ResponseBody	
	public Map<String, Object> user_exist_cnt(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
		// ê²°ê³¼ ?…‹?Œ…
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			/*
			 * ê°??…?˜?–´ ?ˆ?Š” ?´ë©”ì¼?´ ?ˆ?Š”ì§? ì²´í¬
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