package kr.happyjob.study.ctm.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import kr.happyjob.study.ctm.model.CartModel;
import kr.happyjob.study.ctm.service.CartService;


@Controller
@RequestMapping("/ctm")

public class CartController {
	@Autowired
	CartService CartService;
	

	private final Logger logger = LogManager.getLogger(this.getClass());

	// Get class name for logger
	private final String className = this.getClass().toString();
	
	
	/** 장바구니 초기화면 호출   */
	@RequestMapping("cart.do")
	public String totalCartPrice(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		
		logger.info("+ Start " + className + ".init");
		
		// 장바구니 총 합계 금액 출력
		paramMap.put("loginID", session.getAttribute("loginId"));
		int totalPrice = CartService.totalCartPrice(paramMap);
		model.addAttribute("totalPrice", totalPrice);
		
		logger.info("   - paramMap : " + paramMap);
		
		logger.info("+ end " + className + ".init");
		
		return "ctm/cart"; /* 호출할 jsp 파일명 */
	}
	
	/** 장바구니 목록 조회  */
	@RequestMapping("listCart.do")
	public String listCart(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		
		logger.info("+ Start " + className + ".init");
		
		int currentPage = Integer.parseInt((String)paramMap.get("currentPage"));	// 현재 페이지 번호
		int pageSize    = Integer.parseInt((String)paramMap.get("pageSize"));	    // 페이지 사이즈
		int pageIndex   = (currentPage-1)*pageSize;								    // 페이지 시작 row 번호
				
		paramMap.put("pageIndex", pageIndex);
		paramMap.put("pageSize", pageSize);
		paramMap.put("loginID", (String) session.getAttribute("loginId")); // 로그인 아이디
		
		logger.info("   - paramMap : " + paramMap);
		
		// 공통 장바구니 목록 조회
		List<CartModel> listCartModel = CartService.listCart(paramMap);
		model.addAttribute("listCartModel", listCartModel);
		
		logger.info("+ end " + className + ".init");
		
		// 공통 장바구니 목록 카운트 조회
		int totalCount = CartService.countListCart(paramMap);
		model.addAttribute("totalCntCart", totalCount);
		
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPageCart",currentPage);
		
		return "ctm/cartList";
	}	
	
	 /** 장바구니 삭제  */
	  @RequestMapping("deleteCartItem.do")
	  @ResponseBody
	  public Map<String, Object> deleteCartItem(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			  HttpServletResponse response, HttpSession session) throws Exception {
	    
	    logger.info("+ Start " + className + ".deleteCartItem");
	    paramMap.put("loginID", (String) session.getAttribute("loginId")); // 로그인 아이디
		   
	    logger.info("   - paramMap : " + paramMap);
	    
	    String result = "SUCCESS";
	    String resultMsg = "삭제 되었습니다.";
	    
	    // 장바구니 삭제
	    CartService.deleteCartItem(paramMap);
	    
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("result", result);
	    resultMap.put("resultMsg", resultMsg);
	    
	    logger.info("+ End " + className + ".deleteCartItem");
	    
	    return resultMap;
	  }
	  
	  /** 장바구니 수량 변경  */
	  @RequestMapping("changeQty.do")
	  @ResponseBody
	  public Map<String, Object> changeQty(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			  HttpServletResponse response, HttpSession session) throws Exception {
	    
	    logger.info("+ Start " + className + ".changeQty");
	    logger.info("   - paramMap : " + paramMap);
	    
	    int shopping_cart_qty = Integer.parseInt((String)paramMap.get("shopping_cart_qty"));
	    
	    String result = "SUCCESS";
	    String resultMsg = "변경되었습니다.";
	    
	    // 장바구니 삭제
	    CartService.changeQty(paramMap);
	    
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("result", result);
	    resultMap.put("resultMsg", resultMsg);
	    
	    logger.info("+ End " + className + ".changeQty");
	    
	    return resultMap;
	  }
	  
	  /** 장바구니 주문  */
	  @RequestMapping("orderCartItem.do")
	  @ResponseBody
	  public Map<String, String> orderCartItem(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			  HttpServletResponse response, HttpSession session) throws Exception {
	    
	    logger.info("+ Start " + className + ".orderCartItem");
	    
	    paramMap.put("loginID", (String) session.getAttribute("loginId")); // 로그인 아이디
	    
	    logger.info("   - paramMap : " + paramMap);
	    
		ArrayList<Integer> checkMapperSuccessList = new ArrayList<Integer>(); 
	    
	    for (String key : paramMap.keySet()) {
	    	System.out.println(String.format("key -> %s, value -> %s",  key, paramMap.get(key)));
	    	if (key.substring(0, 3).equals("PRO")) {
	    		Map<String, Object> tempMap = new HashMap<String, Object>();
	    		tempMap.putAll(paramMap);
	    		tempMap.put("code", key.substring(7));
	    		tempMap.put("qty", paramMap.get(key));
	    		
	    		Generator generator = new Generator();
	    		String uuid = generator.uuidGenerator();
	    		
	    		tempMap.put("uuid", uuid);
	    		
	    		try {
					int orderResult = CartService.orderCartItem(tempMap);
					checkMapperSuccessList.add(orderResult);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    		try {
					int deleteResult = CartService.deleteOrderedCartItem(tempMap);
					checkMapperSuccessList.add(deleteResult);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    			
	    	}
	    };
	    
//	    int checkCnt =  Integer.parseInt((String)paramMap.get("checkCnt"));
//		
//		String cartOrderListPdcdArr = (String) paramMap.get("cartOrderListPdcdArr");
//		String[] cartOrderListPdcdArray = cartOrderListPdcdArr.split(",");
//		
//		paramMap.put("checkCnt", checkCnt);
//		paramMap.put("pdcdarr", "cartOrderListPdcdArray");
//		paramMap.put("loginID", (String) session.getAttribute("loginId")); // 로그인 아이디
//	    
//	    // 장바구니 주문
//	    CartService.orderCartItem(paramMap);
	    
	    String result = "";
	    String resultMsg = "";
	    
	    Map<String, String> resultMap = new HashMap<String, String>();
	    
	    if(checkMapperSuccessList.contains(0)) {
	    	result = "FAIL";
	    	resultMsg = "주문을 실패하였습니다.";
	    } else {
	    	result = "SUCCESS";
	    	resultMsg = "주문을 완료하였습니다.";
	    };
	    
	    resultMap.put("result", result);
	    resultMap.put("resultMsg", resultMsg);
	    
	    logger.info("Order & Delete CheckList: " + checkMapperSuccessList);
	    logger.info("+ End " + className + ".orderCartItem");
	    
	    return resultMap;
	  }
}
