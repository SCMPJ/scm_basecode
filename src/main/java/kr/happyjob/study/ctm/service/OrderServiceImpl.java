package kr.happyjob.study.ctm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.happyjob.study.ctm.dao.OrderDao;
import kr.happyjob.study.ctm.model.OrderModel;

@Service
public class OrderServiceImpl implements OrderService {
  @Autowired //service에서는 controller,DAO와 연결되어 있다.
  OrderDao OrderDao;
  
  private final Logger logger = LogManager.getLogger(this.getClass());
  // Get class name for logger
  private final String className = this.getClass().toString();
  
  //공급처 목록 조회
  @Override
  public List<OrderModel> productList(Map<String, Object> paramMap) throws Exception {
    List<OrderModel> productList = OrderDao.productList(paramMap);
    return productList;
  }
  //공급처 목록 카운트
  @Override
  public int totalCntProduct(Map<String, Object> paramMap) throws Exception {
    int totalCntProduct = OrderDao.totalCntProduct(paramMap);
    return totalCntProduct;
  }
  // 고객 단일 제품 구매  내역 INSERT 
  @Override
  public Map<String, String> insertOrder(Map<String, Object> paramMap) throws Exception {
	logger.info("+ Start " + className + ".insertOrder");
	  
	String result = "";
	String resultMsg = "";
	  
	logger.info("====== 기업고객이 주문을 실행합니다. ======");
	int insertOrderResult = OrderDao.insertOrder(paramMap);
	
	if (insertOrderResult == 1) {
		result = "SUCCESS";
		resultMsg = "주문을 완료하였습니다.";
	} else {
		result = "FAIL";
		resultMsg = "주문을 실패하였습니다.";
	}
	
	Map<String, String> resultMap = new HashMap<String, String>();
	resultMap.put("result", result);
	resultMap.put("resultMsg", resultMsg);

	logger.info("+ end " + className + ".insertOrder");
    return resultMap;
  }
  // 고객 단일 제품 장바구니 테이블에 INSERT 
  @Override
  public Map<String, String> insertCart(Map<String, Object> paramMap) throws Exception {
	logger.info("+ Start " + className + ".insertCart");
	  
	String result = "";
	String resultMsg = "";
	  
	logger.info("====== 기업고객이 장바구니에 담습니다. ======");
	int insertCartResult = OrderDao.insertCart(paramMap);
	
	if (insertCartResult == 1) {
		result = "SUCCESS";
		resultMsg = "상품을 장바구니에 담았습니다.";
	} else {
		result = "FAIL";
		resultMsg = "장바구니에 동일한 제품이 이미 담겨있습니다.";
	}
	
	Map<String, String> resultMap = new HashMap<String, String>();
	resultMap.put("result", result);
	resultMap.put("resultMsg", resultMsg);

	logger.info("+ end " + className + ".insertCart");
    return resultMap;
  }
}