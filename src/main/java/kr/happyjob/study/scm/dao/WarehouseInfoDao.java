package kr.happyjob.study.scm.dao;

import java.util.List;
import java.util.Map;

import kr.happyjob.study.scm.model.WarehouseInfoModel;

public interface WarehouseInfoDao {
  //창고 목록 조회
  public List<WarehouseInfoModel> listWarehouse(Map<String, Object> paramMap) throws Exception;
  //창고 카운트
  public int totalCntWarehouse(Map<String, Object> paramMap) throws Exception;
  
  //제품 목록 조회
  public List<WarehouseInfoModel> listWarehouseProduct(Map<String, Object> paramMap) throws Exception;
  //제품 카운트
  public int totalCntProduct(Map<String, Object> paramMap) throws Exception;
  
  //창고 추가
  public int insertWarehouse(Map<String, Object> paramMap) throws Exception;
  //창고 수정
  public int updateWarehouse(Map<String, Object> paramMap) throws Exception;
  //창고 단건 조회
  public WarehouseInfoModel selectWarehouse(Map<String, Object> paramMap) throws Exception;
}
