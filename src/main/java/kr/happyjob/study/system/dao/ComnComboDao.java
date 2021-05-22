package kr.happyjob.study.system.dao;

import java.util.List;
import java.util.Map;

import kr.happyjob.study.system.model.comcombo;

public interface ComnComboDao {

	/** 부서 목록 조회 */
	public List<comcombo> selectdeptlist(Map<String, Object> paramMap);
	
	/** 상세 계정 목록 조회 */
	public List<comcombo> selectacclist(Map<String, Object> paramMap);
	
	/** 거래처 목록 조회 */
	public List<comcombo> selectclientlist(Map<String, Object> paramMap);
	
	/** 제품  목록 조회 */
	public List<comcombo> selectproductlist(Map<String, Object> paramMap);
		
	/** 창고 담당자  목록 조회 */
	public List<comcombo> selectwhpersonlist(Map<String, Object> paramMap);
			
	/** 창고 목록 조회 */
	public List<comcombo> selectwhlist(Map<String, Object> paramMap);
	
	/** 창고 담당자 이름, LoginID 조회 */
	public List<comcombo> selectwhMnglist(Map<String, Object> paramMap);
	
}
