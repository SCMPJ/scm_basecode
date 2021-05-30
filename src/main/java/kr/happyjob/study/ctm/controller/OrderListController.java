package kr.happyjob.study.ctm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.happyjob.study.ctm.model.OrderListModel;
import kr.happyjob.study.ctm.service.OrderListService;

@Controller
@RequestMapping("/ctm")
public class OrderListController {
  @Autowired //묶어준다
  OrderListService orderListService;
  
  
  @RequestMapping("orderList.do")
  public String initOrderList(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
      HttpServletResponse response, HttpSession Session) throws Exception{
 
    return "ctm/orderList";
  }
  
  // 주문이력 화면 조회
  @RequestMapping("orderHisList.do")
  public String orderHisList(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) throws Exception{
    
    int currentPage = Integer.parseInt((String)paramMap.get("currentPage"));  // 현재 페이지 번호
    int pageSize    = Integer.parseInt((String)paramMap.get("pageSize"));      // 페이지 사이즈
    int pageIndex   = (currentPage -1)*pageSize;                  // 페이지 시작 row 번호

    paramMap.put("loginID", session.getAttribute("loginId"));
    paramMap.put("pageIndex", pageIndex);
    paramMap.put("pageSize", pageSize);
    
    // 주문이력 조회
    List<OrderListModel> orderHisListModel = orderListService.orderHisList(paramMap);
    model.addAttribute("orderHisListModel", orderHisListModel);
    
    // 주문이력 총 개수 조회
    int totalCount = orderListService.totalCntOrder(paramMap);
    model.addAttribute("totalOrder", totalCount);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("currentPageProduct",currentPage);  
    
    return "ctm/orderHisList";
  }  
  
  //반품 단건 조회
  @RequestMapping("selectRefund.do")
  @ResponseBody
  public Map<String, Object> selectRefund (Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) throws Exception{

    String result = "SUCCESS";
    String resultMsg = "조회 되었습니다.";
    
    OrderListModel refundInfoModel = orderListService.selectRefund(paramMap);
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("result", result);
    resultMap.put("resultMsg", resultMsg);
    resultMap.put("refundInfoModel", refundInfoModel);
    
    System.out.println(resultMap);
    return resultMap;
  }
  
  //반품 등록
  @RequestMapping("submitRefund.do")
  @ResponseBody
  public Map<String, Object> submitRefund (Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
    HttpServletResponse response, HttpSession session) throws Exception{
    
    String action = (String)paramMap.get("action");
    
    String result = "SUCCESS";
    String resultMsg = "";
    
    if("R".equals(action)){
      orderListService.submitRefund(paramMap);
      resultMsg = "반품신청이 완료되었습니다.";
    } else{
      result = "FALSE";
      resultMsg = "반품신청이 실패하였습니다.";
    }
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("result", result);
    resultMap.put("resultMsg", resultMsg);
    
    return resultMap;
  }
  
  //입금 단건 조회
  @RequestMapping("selectDeposit.do")
  @ResponseBody
  public Map<String, Object> selectDeposit (Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) throws Exception{

    String result = "SUCCESS";
    String resultMsg = "조회 되었습니다.";
    
    OrderListModel depositInfoModel = orderListService.selectDeposit(paramMap);
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("result", result);
    resultMap.put("resultMsg", resultMsg);
    resultMap.put("depositInfoModel", depositInfoModel);
    
    System.out.println(resultMap);
    return resultMap;
  }
  
  //입금 처리
  @RequestMapping("submitDeposit.do")
  @ResponseBody
  public Map<String, Object> submitDeposit (Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
    HttpServletResponse response, HttpSession session) throws Exception{
    
    String action = (String)paramMap.get("action");
    
    String result = "SUCCESS";
    String resultMsg = "";
    
    if("D".equals(action)){
      orderListService.submitDeposit(paramMap);
      resultMsg = "입금 처리되었습니다.";
    } else{
      result = "FALSE";
      resultMsg = "입금에 실패하였습니다.";
    }
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("result", result);
    resultMap.put("resultMsg", resultMsg);
    
    return resultMap;
  }
  
  //구매확정 단건 조회
  @RequestMapping("selectConfirm.do")
  @ResponseBody
  public Map<String, Object> selectConfirm (Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) throws Exception{

    String result = "SUCCESS";
    String resultMsg = "조회 되었습니다.";
    
    OrderListModel confirmInfoModel = orderListService.selectConfirm(paramMap);
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("result", result);
    resultMap.put("resultMsg", resultMsg);
    resultMap.put("confirmInfoModel", confirmInfoModel);
    
    System.out.println(resultMap);
    return resultMap;
  }
  
  //구매확정
  @RequestMapping("submitConfirm.do")
  @ResponseBody
  public Map<String, Object> submitConfirm (Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
    HttpServletResponse response, HttpSession session) throws Exception{
    
    String action = (String)paramMap.get("action");
    
    String result = "SUCCESS";
    String resultMsg = "";
    
    if("C".equals(action)){
      orderListService.submitConfirm(paramMap);
      resultMsg = "구매확정 처리되었습니다.";
    } else{
      result = "FALSE";
      resultMsg = "구매확정에 실패하였습니다.";
    }
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("result", result);
    resultMap.put("resultMsg", resultMsg);
    
    return resultMap;
  }
}