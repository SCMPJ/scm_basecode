package kr.happyjob.study.ctm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.happyjob.study.common.uuidgenerator.Generator;
import kr.happyjob.study.ctm.model.OrderModel;
import kr.happyjob.study.ctm.service.OrderService;

@Controller
@RequestMapping("/ctm")
public class OrderController {
	
  @Autowired //묶어준다
  OrderService orderService;
  
  private final Logger logger = LogManager.getLogger(this.getClass());
	
  // Get class name for logger
  private final String className = this.getClass().toString();
	  
  
  @RequestMapping("order.do")
  public String initOrder(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
      HttpServletResponse response, HttpSession Session) throws Exception{
 
    return "ctm/order";
  }
  
  // 주문 화면 조회
  @RequestMapping("productList.do")
  public String productList(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) throws Exception{
    
    int currentPage = Integer.parseInt((String)paramMap.get("currentPage"));  // 현재 페이지 번호
    int pageSize    = Integer.parseInt((String)paramMap.get("pageSize"));      // 페이지 사이즈
    int pageIndex   = (currentPage -1)*pageSize;                  // 페이지 시작 row 번호
    
    paramMap.put("pageIndex", pageIndex);
    paramMap.put("pageSize", pageSize);
    
    // 제품 목록 조회
    List<OrderModel> productListModel = orderService.productList(paramMap);
    model.addAttribute("productListModel", productListModel);
    
    // 제품 총 개수 조회
    int totalCount = orderService.totalCntProduct(paramMap);
    model.addAttribute("totalProduct", totalCount);
    
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("currentPageProduct",currentPage);  
    
    return "ctm/productList";
  }
  
  // 주문페이지에서 한 품목을 구매하기 하였을 때 실행
  @RequestMapping("purchaseItem.do")
  @ResponseBody
  public Map<String, String> purchaseItem(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) throws Exception {
  
	logger.info("+ Start" + className + ".purchaseItem");
	
	String loginId = (String) session.getAttribute("loginId");
	Generator generator = new Generator();
	String uuid = generator.uuidGenerator();
	
	paramMap.put("loginId", loginId); // 로그인 아이디, 기업고객 아이디 가져올 때 쓰임
	paramMap.put("orderCode", uuid);  // order code 생성기
	logger.info("paramMap:" + paramMap);
	
	Map<String, String> resultMap = orderService.insertOrder(paramMap);
	
	logger.info("+ end " + className + ".purchaseItem");
	
	return resultMap;
	
  }
  
  // 주문페이지에서 한 품목을 장바구니 담기 하였을 때 실행  
  @RequestMapping("sendItemToCart.do")
  @ResponseBody
  public Map<String, String> sendItemToCart(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) throws Exception {
  
	logger.info("+ Start" + className + ".sendItemToCart");
	
	String loginId = (String) session.getAttribute("loginId");
	
	paramMap.put("loginId", loginId); // 로그인 아이디, 기업고객 아이디 가져올 때 쓰임
	logger.info("paramMap:" + paramMap);
	
	int productCount = Integer.parseInt((String) paramMap.get("productCount"));
	paramMap.put("productCount", productCount);
			
	Map<String, String> resultMap = orderService.insertCart(paramMap);
	
	logger.info("+ end " + className + ".sendItemToCart");
	
	return resultMap;
	
  }
}