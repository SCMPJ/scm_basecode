package kr.happyjob.study.ctm.service;

import java.util.List;
import java.util.Map;

import kr.happyjob.study.ctm.model.OrderListModel;

public interface OrderListService {
  /* 주문이력 조회*/
  List<OrderListModel> orderHisList(Map<String, Object> paramMap) throws Exception;
  /* 주문이력 카운트 조회*/
  int totalCntOrder(Map<String, Object> paramMap) throws Exception;
  /* 반품 단건 조회*/
  public OrderListModel selectRefund(Map<String, Object> paramMap) throws Exception;
  /* 반품 등록*/
  int submitRefund(Map<String, Object> paramMap) throws Exception;
  /* 입금 단건 조회*/
  public OrderListModel selectDeposit(Map<String, Object> paramMap) throws Exception;
  /* 입금 등록*/
  int submitDeposit(Map<String, Object> paramMap) throws Exception;
  /* 구매확정 단건 조회*/
  public OrderListModel selectConfirm(Map<String, Object> paramMap) throws Exception;
  /* 구매확정*/
  int submitConfirm(Map<String, Object> paramMap) throws Exception;

}
