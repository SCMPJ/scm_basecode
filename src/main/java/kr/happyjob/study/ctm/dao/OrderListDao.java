package kr.happyjob.study.ctm.dao;

import java.util.List;
import java.util.Map;

import kr.happyjob.study.ctm.model.OrderListModel;

public interface OrderListDao {
  
  // 주문이력 조회
  public List<OrderListModel> orderHisList(Map<String, Object> paramMap) throws Exception;
  // 주문이력 갯수 조회
  public int totalCntOrder(Map<String, Object> paramMap) throws Exception;
  // 반품 단건 조회
  public OrderListModel selectRefund(Map<String, Object> paramMap) throws Exception;
  // 반품 등록
  public int submitRefund(Map<String, Object> paramMap) throws Exception;
  // 입금 단건 조회
  public OrderListModel selectDeposit(Map<String, Object> paramMap) throws Exception;
  // 입금 처리
  public int submitDeposit(Map<String, Object> paramMap) throws Exception;
  // 구매확정 단건 조회
  public OrderListModel selectConfirm(Map<String, Object> paramMap) throws Exception;
  // 구매확정
  public int submitConfirm(Map<String, Object> paramMap) throws Exception;
}