/*************************************************************************
 * 클 래 스 명 : HashMapHandler
 * 작   업  자 : 이진영
 * 작 업 일 자 : 2012.11.20
 * 설       명 : HashMap 대소문자 관련 handler
 * ---------------------------- 이력사항 --------------------------------
 * 순번  작성자   작성일자    변경사항
 * ---- --------  ----------- -------------------------    --------
 *    1 이진영    2012.11.20  최초작성
 **************************************************************************/

/**
* 기능 : HashMap 대소문자 제어 관련 모듈 <br>
*@author    이진영
*@version   HashMapHandler 1.0.0
*/
package kr.co.raon.commons.db;

public class HashMapHandler extends java.util.HashMap<String, Object> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7745687743018480753L;

	@Override
	public Object get(Object key) {
		return super.get(String.valueOf(key).toUpperCase());
	}

/*
 
 	@Override
	public Object get(Object key) {
		return super.get(String.valueOf(key).toUpperCase());
	}
	
	@Override
	public Object put(String key, Object value) {
		return super.put(key.toUpperCase(), value);
	}
*/	
}// end class