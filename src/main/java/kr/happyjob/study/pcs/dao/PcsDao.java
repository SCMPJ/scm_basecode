package kr.happyjob.study.pcs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.happyjob.study.pcs.model.PcsModel;

@Repository
public interface PcsDao {

  /** 그룹코드 목록 조회 */
  public static List<PcsModel> listComnGrpCod2(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return null;
  }
  
  /** 그룹코드 목록 카운트 조회 */
  public static int countListComnGrpCod2(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return 0;
  }
}