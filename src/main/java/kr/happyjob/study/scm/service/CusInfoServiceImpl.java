package kr.happyjob.study.scm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.happyjob.study.scm.dao.CusInfoDao;
import kr.happyjob.study.scm.model.CusInfoModel;

@Service
public class CusInfoServiceImpl implements CusInfoService{
	
	@Autowired
	CusInfoDao cusInfoDao;

	@Override
	public List<CusInfoModel> cusList(Map<String, Object> paramMap) throws Exception {
		return cusInfoDao.cusList(paramMap);
	}

	@Override
	public int cusListCnt(Map<String, Object> paramMap) throws Exception {
		return cusInfoDao.cusListCnt(paramMap);
	}

	@Override
	public List<CusInfoModel> getCusDetailInfo(Map<String, Object> paramMap) throws Exception {
		return cusInfoDao.selectCusDetailInfo(paramMap);
	}

	@Override
	public Map<String, String> modifyActiveState(Map<String, Object> paramMap) throws Exception {
		
		String result = "";
		String resultMsg = "";
		
		int updateResult = cusInfoDao.updateActiveState(paramMap);
		
		if(updateResult == 1) {
			result = "SUCCESS";
			resultMsg = "고객 활성화 여부 변경을 완료하였습니다.";
		} else {
			result = "FAIL";
			resultMsg = "고객 활성화 여부 변경을 실패하였습니다.";
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", result);
		resultMap.put("resultMsg", resultMsg);
		
		return resultMap;
	}
  
}
