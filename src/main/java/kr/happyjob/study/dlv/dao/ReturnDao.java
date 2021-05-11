package kr.happyjob.study.dlv.dao;

import java.util.List;
import java.util.Map;

import kr.happyjob.study.dlv.model.DlvReturnDetailModel;
import kr.happyjob.study.dlv.model.DlvReturnModel;


public interface ReturnDao {
	// 반품 리스트 조회
	public List<DlvReturnModel> returnList(Map<String, Object> paramMap) throws Exception;
	// 반품 리스트 조회수 
	public int returnListCnt(Map<String, Object> paramMap) throws Exception;
	// 반품 상세페이지 조회
	public List<DlvReturnDetailModel> returnDetailList(Map<String, Object> paramMap) throws Exception;
}
