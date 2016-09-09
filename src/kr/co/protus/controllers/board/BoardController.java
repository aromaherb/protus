package kr.co.protus.controllers.board;

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
public class BoardController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@RequestMapping("/board/board_list.do")
	public String board_list(@RequestBody String requestBody, Model model, HttpServletRequest request) {
		logger.debug("requestBody=" + requestBody);

		long accesstime = System.currentTimeMillis();

		SessionContext sessionContext = getSession();
		sessionContext.setAccesstime(String.valueOf(accesstime));

		return "community/board_list.admin_main";
	}

	@RequestMapping("/board/board_detail.do")
	public String board_detail(@RequestBody String requestBody, Model model, HttpServletRequest request) {
		logger.debug("requestBody=" + requestBody);

		long accesstime = System.currentTimeMillis();

		SessionContext sessionContext = getSession();
		sessionContext.setAccesstime(String.valueOf(accesstime));

		return "community/board_detail.admin_main";
	}

	@RequestMapping("/board/board_form.do")
	public String board_form(@RequestBody String requestBody, Model model, HttpServletRequest request) {
		logger.debug("requestBody=" + requestBody);

		long accesstime = System.currentTimeMillis();

		SessionContext sessionContext = getSession();
		sessionContext.setAccesstime(String.valueOf(accesstime));

		return "community/board_form.admin_main";
	}
}
