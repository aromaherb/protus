
/***************************************************************************************************
 * 파일명 : NoticeController.java
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_NOTICE )의 콘트롤러 목록/상세정보/Form 상세정보/입력/수정/삭제/전체삭제 
 ***************************************************************************************************/
package kr.co.protus.controllers.protus;

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
public class NoticeController extends BaseController {

	/**
	 * 로그
	 */
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	/**
	 * ( TB_NOTICE ) 목록
	 * @param params	인자
	 * @param model		모델
	 * @return			tiles 템플릿
	 */
	@RequestMapping(value = "/notice/notice_list.do")
	public String notice_list(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
		try {
			mapper.setSqlMapper("Notice");     // 매핑 선언
	
			// 페이징정보
			setCntPerPage("20");                        		// 디폴트 페이지 마다 표시 개수 20개로 설정
			if(params.get("page") == null) {
				setCurPage(1);             						// 현재 페이지 설정
			}else {
				setCurPage(params.get("page"));         		// 현재 페이지 설정
			}
	
			// 파라미터 설정		
			params.put("page", 			getCurPage());
			params.put("start_of_page", ( getCurPage() - 1) * getCntPerPage() );		
			params.put("cnt_per_page", 	getCntPerPage());		
			
			// 목록 개수 구하기
			mapper.setSqlId("selectListCnt");
			int totalCnt = mapper.listCnt(params);
			setCntTotal(totalCnt);                      					// 목록 개수 설정 
			
			PagingInfo pagingInfo = getPagingInfo();						// Page Navigation 설정
			Map<String,Object> pageInfo = getPageInfo(pagingInfo);			// Page 정보 설정
			pageInfo.put("search_word", 	params.get("search_word"));		// 검색 인자 설정
						
			// 목록
			mapper.setSqlId("selectList");
			List<Map<String, Object>> entities = mapper.list(params);
			
			// 모델 셋팅
			model.addAttribute("entities", 		entities);
			model.addAttribute("pagingInfo",	pagingInfo);
			model.addAttribute("pageInfo", 		pageInfo);
		} catch (Exception e) {
            logger.error("Notice 목록");
            logger.error(e.getMessage());
		} finally {
            //
		}
		return "notice/notice_list.admin_main";
	}

	/**
	 * ( TB_NOTICE ) Form 정보
	 * @param params	인자
	 * @param model		모델
	 * @return			tiles 템플릿
	 */
	@RequestMapping(value = "/notice/notice_form.do")
	public String notice_form(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
		String method = StringUtil.object2Str(params.get("method"));
		
		String notice_seq = StringUtil.object2Str(params.get("notice_seq")); 
		
		try {
			// Page 정보
			Map<String, Object> pageInfo = getPageInfo(params);
			pageInfo.put("method", 			method);			// 구분
			pageInfo.put("search_word", 	params.get("search_word"));		// 검색 인자 설정
			
			// 모델 셋팅
			if( ( params.get("method").equals("M") ) || ( params.get("method").equals("V") ) ) {
				// 수정시 상세 정보 가져오기
				mapper.setSqlMapper("Notice");     // 매핑 선언
				mapper.setSqlId("selectData");
				model.addAttribute("entity", mapper.detail(params));
			}
	
			model.addAttribute("pageInfo", pageInfo);
		} catch (Exception e) {
			logger.error("Notice Form 정보");
            logger.error(e.getMessage());
		} finally {
			//
		}
		if(method.equals("V")) {
			return "notice/notice_detail.admin_main";
		}else {
			return "notice/notice_form.admin_main";
		}
	}

	/**
     * ( TB_NOTICE ) 입력
     * @param params    인자
     * @param model     모델
     * @return          결과 상태 값
     */ 
    @RequestMapping(value = "/notice/notice_save.do")
    @ResponseBody
    public Map<String, Object> notice_save(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
        // 결과 상태값 셋팅
        String method = StringUtil.object2Str(params.get("method"));
		if(method.equals("A")) {
			return notice_insert(params,request,model);
		}else {
			return notice_update(params,request,model);
		}
    }
    
    /**
     * ( TB_NOTICE ) 입력
     * @param params    인자
     * @param model     모델
     * @return          결과 상태 값
     */ 
    @RequestMapping(value = "/base/notice_insert.do")
    @ResponseBody
    public Map<String, Object> notice_insert(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
        // 결과 상태값 셋팅
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            mapper.setSqlMapper("Notice");    // 매핑 선언
            mapper.setSqlId("insert");
            
            int result_cnt  = 0;            // 결과 건수
            
            // Oracle Number, Double 형 필드를 Float형 데이타로 변환
            String[] floatField = {  };
            params = mapper.convMapForNumber(params, floatField);
            
            result_cnt = mapper.insert(params);
            
            result.put("result_cd", "200");
            result.put("result_cnt", result_cnt);
            result.put("result_msg", "Success");
        } catch (Exception e) {
            result.put("result_cd", "500");
            result.put("result_msg", e.getMessage());
            
            logger.error("Notice 입력");
            logger.error(e.getMessage());           
        } finally {
            //
        }

        return result;
    }

	/**
     * ( TB_NOTICE ) 수정
     * @param params    인자
     * @param model     모델
     * @return          결과 상태 값
     */ 
    @RequestMapping(value = "/notice/notice_update.do")
    @ResponseBody
    public Map<String, Object> notice_update(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
        // 결과 상태값 셋팅
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            mapper.setSqlMapper("Notice");    // 매핑 선언
            
            int result_cnt  = 0;            // 결과 건수
            
            // Number형 필드를 Float형 데이타로 변환
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
            
            logger.error("Notice 수정");
            logger.error(e.getMessage());           
        } finally {
            //
        }

        return result;
    }

   /**
     * ( TB_NOTICE ) 삭제
     * @param params    인자
     * @param model     모델
     * @return          결과 상태 값
     */ 
    @RequestMapping(value = "/notice/notice_delete.do")
    @ResponseBody
    public Map<String, Object> notice_delete(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
        // 결과 상태값 셋팅
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            mapper.setSqlMapper("Notice");    // 매핑 선언
            
            int result_cnt  = 0;            // 결과 건수
            
            // Oracle Number형 or double형 필드를 Float형 데이타로 변환
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
            
            logger.error("Notice 삭제");
            logger.error(e.getMessage());           
        } finally {
            //
        }

        return result;
    }

	/**
	 * ( TB_NOTICE ) 입력/수정/삭제/삭제'Y'/삭제'N'/전체삭제
	 * @param params	인자
	 * @param model		모델
	 * @return			결과 상태 값
	 */	
	@RequestMapping(value = "/base/notice_process.do")
	@ResponseBody
	public Map<String, Object> notice_process(@RequestParam Map<String, Object> params, HttpServletRequest request, Model model) {
		// 결과 상태값 셋팅
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			mapper.setSqlMapper("Notice");	// 매핑 선언
			
			int result_cnt	= 0;			// 결과 건수
			
            // Number형 필드를 Float형 데이타로 변환
            String[] floatField = {  };
            params = mapper.convMapForNumber(params, floatField);
			
			result_cnt = mapper.process(params);
			
			result.put("result_cd", "200");
			result.put("result_cnt", result_cnt);
			result.put("result_msg", "Success");
		} catch (Exception e) {
			result.put("result_cd", "500");
			result.put("result_msg", e.getMessage());
			
            logger.error("Notice 입력/수정/삭제/삭제'Y'/삭제'N'/전체삭제");
            logger.error(e.getMessage());			
		} finally {
		    //
		}

		return result;
	}
}
