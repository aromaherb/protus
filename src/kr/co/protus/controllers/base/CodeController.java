/***************************************************************************************************
 * ?��?���? : CodeController.java
 * ?��?��?�� : ?��?��?�� (jang1945@raonsolution.co.kr)
 * ?��?��?�� : 2016-7-6
 * ?��  ?�� : ( TB_CODE )?�� 콘트롤러 목록/?��?��?���?/Form ?��?��?���?/?��?��/?��?��/?��?��/?��체삭?�� 
 ***************************************************************************************************/
package kr.co.protus.controllers.base;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.co.raon.commons.util.StringUtil;
import kr.co.raon.commons.web.*;

@Controller
public class CodeController extends BaseController {

	/**
	 * 로그
	 */
	private static final Logger logger = LoggerFactory.getLogger(CodeController.class);
	
	/**
	 * ( TB_CODE ) 목록
	 * @param params	?��?��
	 * @param model		모델
	 * @return			tiles ?��?���?
	 */
	@RequestMapping(value = "/code/code_list.do")
	public String code_list(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
		try {
			mapper.setSqlMapper("Code");     // 매핑 ?��?��
	
			// ?��?��징정�?
			setCntPerPage("20");                        		// ?��?��?�� ?��?���? 마다 ?��?�� 개수 20개로 ?��?��
			if(params.get("page") == null) {
				setCurPage(1);             						// ?��?�� ?��?���? ?��?��
			}else {
				setCurPage(params.get("page"));         		// ?��?�� ?��?���? ?��?��
			}
	
			// ?��?��미터 ?��?��		
			params.put("page", 			getCurPage());
			params.put("start_of_page", ( getCurPage() - 1) * getCntPerPage() );		
			params.put("cnt_per_page", 	getCntPerPage());		
			
			// 목록 개수 구하기
			mapper.setSqlId("selectListCnt");
			int totalCnt = mapper.listCnt(params);
			setCntTotal(totalCnt);                      					// 목록 개수 ?��?�� 
			
			PagingInfo pagingInfo = getPagingInfo();						// Page Navigation ?��?��
			Map<String,Object> pageInfo = getPageInfo(pagingInfo);			// Page ?���? ?��?��
			pageInfo.put("search_word", 	params.get("search_word"));		// �??�� ?��?�� ?��?��
			pageInfo.put("search_word2", 	params.get("search_word2"));		// �??�� ?��?�� ?��?��
						
			// 목록
			mapper.setSqlId("selectList");
			List<Map<String, Object>> entities = mapper.list(params);
			
			// 모델 ?��?��
			model.addAttribute("entities", 		entities);
			model.addAttribute("pagingInfo",	pagingInfo);
			model.addAttribute("pageInfo", 		pageInfo);
		} catch (Exception e) {
            logger.error("Code 목록");
            logger.error(e.getMessage());
		} finally {
            //
		}
		return "code/code_list.admin_main";
	}

	/**
	 * ( TB_CODE ) Form ?���?
	 * @param params	?��?��
	 * @param model		모델
	 * @return			tiles ?��?���?
	 */
	@RequestMapping(value = "/code/code_form.do")
	public String code_form(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
		String method = StringUtil.object2Str(params.get("method"));
		
		String group_code = StringUtil.object2Str(params.get("group_code")); 
		
		String code = StringUtil.object2Str(params.get("code")); 
		
		try {
			// Page ?���?
			Map<String, Object> pageInfo = getPageInfo(params);
			pageInfo.put("method", 			method);			// 구분
			pageInfo.put("search_word", 	params.get("search_word"));		// �??�� ?��?�� ?��?��
			pageInfo.put("search_word2", 	params.get("search_word2"));		// �??�� ?��?�� ?��?��
			
			// 모델 ?��?��
			if( ( params.get("method").equals("M") ) || ( params.get("method").equals("V") ) ) {
				// ?��?��?�� ?��?�� ?���? �??��?���?
				mapper.setSqlMapper("Code");     // 매핑 ?��?��
				mapper.setSqlId("selectData");
				model.addAttribute("entity", mapper.detail(params));
			}
	
			model.addAttribute("pageInfo", pageInfo);
		} catch (Exception e) {
			logger.error("Code Form ?���?");
            logger.error(e.getMessage());
		} finally {
			//
		}
		if(method.equals("V")) {
			return "code/code_detail.admin_main";
		}else {
			return "code/code_form.admin_main";
		}
	}

	/**
     * ( TB_CODE ) ?��?��
     * @param params    ?��?��
     * @param model     모델
     * @return          결과 ?��?�� �?
     */ 
    @RequestMapping(value = "/code/code_save.do")
    @ResponseBody
    public Map<String, Object> code_save(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
        // 결과 ?��?���? ?��?��
        String method = StringUtil.object2Str(params.get("method"));
		if(method.equals("A")) {
			return code_insert(params,request,model);
		}else {
			return code_update(params,request,model);
		}
    }
    
    /**
     * ( TB_CODE ) ?��?��
     * @param params    ?��?��
     * @param model     모델
     * @return          결과 ?��?�� �?
     */ 
    @RequestMapping(value = "/base/code_insert.do")
    @ResponseBody
    public Map<String, Object> code_insert(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
        // 결과 ?��?���? ?��?��
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            mapper.setSqlMapper("Code");    // 매핑 ?��?��
            mapper.setSqlId("insert");
            
            int result_cnt  = 0;            // 결과 건수
            
            // Oracle Number, Double ?�� ?��?���? Float?�� ?��?��??�? �??��
            String[] floatField = {  };
            params = mapper.convMapForNumber(params, floatField);
            
            result_cnt = mapper.insert(params);
            
            result.put("result_cd", "200");
            result.put("result_cnt", result_cnt);
            result.put("result_msg", "Success");
        } catch (Exception e) {
            result.put("result_cd", "500");
            result.put("result_msg", e.getMessage());
            
            logger.error("Code ?��?��");
            logger.error(e.getMessage());           
        } finally {
            //
        }

        return result;
    }

	/**
     * ( TB_CODE ) ?��?��
     * @param params    ?��?��
     * @param model     모델
     * @return          결과 ?��?�� �?
     */ 
    @RequestMapping(value = "/code/code_update.do")
    @ResponseBody
    public Map<String, Object> code_update(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
        // 결과 ?��?���? ?��?��
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            mapper.setSqlMapper("Code");    // 매핑 ?��?��
            
            int result_cnt  = 0;            // 결과 건수
            
            // Number?�� ?��?���? Float?�� ?��?��??�? �??��
            String[] floatField = {  };
            params = mapper.convMapForNumber(params, floatField);
            
            mapper.setSqlId("update");
            result_cnt = mapper.update(params);
            
            result.put("result_cd", "200");
            result.put("result_cnt", result_cnt);
            result.put("result_msg", "Success");
        } catch (Exception e) {
            result.put("result_cd", "500");
            result.put("result_msg", e.getMessage());
            
            logger.error("Code ?��?��");
            logger.error(e.getMessage());           
        } finally {
            //
        }

        return result;
    }

   /**
     * ( TB_CODE ) ?��?��
     * @param params    ?��?��
     * @param model     모델
     * @return          결과 ?��?�� �?
     */ 
    @RequestMapping(value = "/code/code_delete.do")
    @ResponseBody
    public Map<String, Object> code_delete(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
        // 결과 ?��?���? ?��?��
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            mapper.setSqlMapper("Code");    // 매핑 ?��?��
            
            int result_cnt  = 0;            // 결과 건수
            
            // Oracle Number?�� or double?�� ?��?���? Float?�� ?��?��??�? �??��
            String[] floatField = {  };
            params = mapper.convMapForNumber(params, floatField);
            
            mapper.setSqlId("delete");
            result_cnt = mapper.delete(params);
            
            result.put("result_cd", "200");
            result.put("result_cnt", result_cnt);
            result.put("result_msg", "Success");
        } catch (Exception e) {
            result.put("result_cd", "500");
            result.put("result_msg", e.getMessage());
            
            logger.error("Code 삭제");
            logger.error(e.getMessage());           
        } finally {
            //
        }

        return result;
    }

	/**
	 * ( TB_CODE ) ?��?��/?��?��/?��?��/?��?��'Y'/?��?��'N'/?��체삭?��
	 * @param params	?��?��
	 * @param model		모델
	 * @return			결과 ?��?�� �?
	 */	
	@RequestMapping(value = "/base/code_process.do")
	@ResponseBody
	public Map<String, Object> code_process(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
		// 결과 ?��?���? ?��?��
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			mapper.setSqlMapper("Code");	// 매핑 ?��?��
			
			int result_cnt	= 0;			// 결과 건수
			
            // Number?�� ?��?���? Float?�� ?��?��??�? �??��
            String[] floatField = {  };
            params = mapper.convMapForNumber(params, floatField);
			
			result_cnt = mapper.process(params);
			
			result.put("result_cd", "200");
			result.put("result_cnt", result_cnt);
			result.put("result_msg", "Success");
		} catch (Exception e) {
			result.put("result_cd", "500");
			result.put("result_msg", e.getMessage());
			
            logger.error("Code ?��?��/?��?��/?��?��/?��?��'Y'/?��?��'N'/?��체삭?��");
            logger.error(e.getMessage());			
		} finally {
		    //
		}

		return result;
	}
}
