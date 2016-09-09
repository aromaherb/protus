package kr.co.protus.controllers.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.co.raon.commons.web.*;

@Controller
public class AdminController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@RequestMapping("/admin/mainpage.do")
	public String account_list(@RequestBody String requestBody,Model model, HttpServletRequest request) {
		logger.debug("requestBody=" + requestBody);
		Map<String,Object> pageInfo = new HashMap<String, Object>();	// Page ?†ïÎ≥? ?Ñ§?†ï
		pageInfo.put("firstPage", request.getRealPath("/") + "/WebContent/WEB-INF/views/admin/blank.jsp");
		long accesstime = System.currentTimeMillis();
		
		// Î™®Îç∏ ?Öã?åÖ
		SessionContext sessionContext = getSession();
		sessionContext.setAccesstime(String.valueOf(accesstime));
		model.addAttribute("pageInfo", 		pageInfo);
				
		return "layout/content_admin.admin_main";
	}
	
}
