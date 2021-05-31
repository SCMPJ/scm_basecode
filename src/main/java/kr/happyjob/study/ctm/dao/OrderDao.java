package kr.happyjob.study.ctm.dao;

import java.util.List;
import java.util.Map;

import kr.happyjob.study.ctm.model.OrderModel;

public interface OrderDao {
  
  // 공급처 목록 조회
  public List<OrderModel> productList(Map<String, Object> paramMap) throws Exception;
  // 공급처 목록 갯수 조회
  public int totalCntProduct(Map<String, Object> paramMap) throws Exception;
  // 고객 단일 제품 구매  내역 INSERT
  public int insertOrder(Map<String, Object> paramMap) throws Exception;
  // 고객 단일 제품 장바구니 INSERT
  public int insertCart(Map<String, Object> paramMap) throws Exception;
}