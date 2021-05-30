package kr.happyjob.study.ctm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.happyjob.study.ctm.dao.OrderListDao;
import kr.happyjob.study.ctm.model.OrderListModel;

@Service
public class OrderListServiceImpl implements OrderListService {
  @Autowired //service에서는 controller,DAO와 연결되어 있다.
  OrderListDao OrderListDao;
  
  //주문이력 조회
  @Override
  public List<OrderListModel> orderHisList(Map<String, Object> paramMap) throws Exception {
    List<OrderListModel> orderHisList = OrderListDao.orderHisList(paramMap);
    return orderHisList;
  }
  //주문이력 카운트
  @Override
  public int totalCntOrder(Map<String, Object> paramMap) throws Exception {
    int totalCntOrder = OrderListDao.totalCntOrder(paramMap);
    return totalCntOrder;
  }
  //반품 단건 조회
  @Override
  public OrderListModel selectRefund(Map<String, Object> paramMap) throws Exception {
    OrderListModel selectRefund = OrderListDao.selectRefund(paramMap);
    return selectRefund;
  }
  //반품 등록
  @Override
  public int submitRefund(Map<String, Object> paramMap) throws Exception {
    return OrderListDao.submitRefund(paramMap);
  }
  //입금 단건 조회
  @Override
  public OrderListModel selectDeposit(Map<String, Object> paramMap) throws Exception {
    OrderListModel selectDeposit = OrderListDao.selectDeposit(paramMap);
    return selectDeposit;
  }
  //입금 등록
  @Override
  public int submitDeposit(Map<String, Object> paramMap) throws Exception {
    return OrderListDao.submitDeposit(paramMap);
  }
  //구매확정 단건 조회
  @Override
  public OrderListModel selectConfirm(Map<String, Object> paramMap) throws Exception {
    OrderListModel selectConfirm = OrderListDao.selectConfirm(paramMap);
    return selectConfirm;
  }
  //구매확정
  @Override
  public int submitConfirm(Map<String, Object> paramMap) throws Exception {
    return OrderListDao.submitConfirm(paramMap);
  }
}