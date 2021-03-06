package kr.happyjob.study.scm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.happyjob.study.scm.model.GetWarehouseModel;
import kr.happyjob.study.scm.model.MainProductInfoModel;
import kr.happyjob.study.scm.model.MainProductModalModel;
import kr.happyjob.study.scm.service.MainProductInfoService;

@Controller
@RequestMapping("/scm")
public class MainProductInfoController {
  @Autowired
  MainProductInfoService mainProductInfoService;
  
  private static final Logger logger = LoggerFactory.getLogger(MainProductInfoController.class);
  private final String className = this.getClass().toString();
  
  @RequestMapping("mainProductInfo.do")
  public String initMainProductInfo(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession Session) throws Exception {
    
    logger.info("+ Start " + className + ".initMainProductInfo");
    logger.info("   - paramMap : " + paramMap);
    
    logger.info("+ End " + className + ".initMainProductInfo");
    
    return "scm/mainProductInfo";
  }
  
  // ์ ํ ์กฐํ
  @RequestMapping("listMainProduct.do")
  public String listWarehouse(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
    
    logger.info("+ Start " + className + ".listWarehouse");
    logger.info("   - paramMap : " + paramMap);
    
    int currentPage = Integer.parseInt((String) paramMap.get("currentPage")); // ํ์ฌ ํ์ด์ง ๋ฒํธ
    int pageSize = Integer.parseInt((String) paramMap.get("pageSize")); // ํ์ด์ง ์ฌ์ด์ฆ
    int pageIndex = (currentPage - 1) * pageSize; // ํ์ด์ง ์์ row ๋ฒํธ
    
    paramMap.put("pageIndex", pageIndex);
    paramMap.put("pageSize", pageSize);
    
    // ์ ํ ๋ชฉ๋ก ์กฐํ
    List<MainProductInfoModel> listMainProductModel = mainProductInfoService.listMainProduct(paramMap);
    model.addAttribute("listMainProductModel", listMainProductModel);
    
    // ์ ํ ๋ชฉ๋ก ์นด์ดํธ ์กฐํ
    int totalCount = mainProductInfoService.totalCntMainProduct(paramMap);
    model.addAttribute("totalMainProduct", totalCount);
    
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("currentPageMainProduct", currentPage);
    
    logger.info("+ End " + className + ".listMainProduct");
    
    return "scm/listMainProduct";
  }
  
  // ์ ํ์ ๋ณด ๊ด๋ฆฌ ์กฐํ
  @RequestMapping("selectMainProduct.do")
  @ResponseBody
  public Map<String, Object> selectMainProduct(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
    logger.info("+ Start " + className + ".selectMainProduct");
    logger.info("   - paramMap : " + paramMap);
    
    String result = "SUCCESS";
    String resultMsg = "์กฐํ ๋์์ต๋๋ค.";
    
    MainProductInfoModel mainProductInfoModel = mainProductInfoService.selectMainProduct(paramMap);
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("result", result);
    resultMap.put("resultMsg", resultMsg);
    resultMap.put("mainProductInfoModel", mainProductInfoModel);
    
    logger.info("+ End " + className + ".selectMainProduct");
    
    System.out.println(resultMap);
    return resultMap;
  }
  
  @RequestMapping("saveMainProduct.do")
  @ResponseBody
  public Map<String, Object> saveMainProduct(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
    logger.info("+ Start " + className + ".saveMainProduct");
    logger.info("   - paramMap : " + paramMap);
    
    String action = (String) paramMap.get("action");
    
    String result = "SUCCESS";
    String resultMsg = "";
    
    if ("I".equals(action)) {
      // ๋ฑ๋ก
      int saveResult = mainProductInfoService.insertMainProduct(paramMap, request);
      if (saveResult == 0) {
        result = "FAIL";
        resultMsg = "์ค๋ณต๋ ์ฝ๋์๋๋ค.";
      } else{
      resultMsg = "๋ฑ๋ก ์๋ฃ"; }
    } else if ("U".equals(action)) {
      // ์์ 
      mainProductInfoService.updateMainProduct(paramMap, request);
      resultMsg = "์์  ์๋ฃ";
    } else if ("D".equals(action)) {
      // ์ญ์ 
      mainProductInfoService.deleteMainProduct(paramMap);
      resultMsg = "์ญ์  ์๋ฃ";
    } else {
      result = "FALSE";
      resultMsg = "์ ์ฅ ์คํจ";
    }
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("result", result);
    resultMap.put("resultMsg", resultMsg);
    
    logger.info("+ End " + className + ".saveMainProduct");
    
    return resultMap;
  }
  
 //์ฐฝ๊ณ ์ ๋ณด ์กฐํ
  @RequestMapping("getWarehouseInfo.do")
  @ResponseBody
  public Map<String, Object> getWarehouseInfo(Model model, @RequestParam Map<String, Object> paramMap, 
      HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
    
    GetWarehouseModel warehouseInfo = mainProductInfoService.getWarehouseInfo(paramMap);
    
    model.addAttribute("warehouseInfo",warehouseInfo);
 
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("warehouseInfo", warehouseInfo);
    
    return resultMap;
  }
  
  // ์ ํ ์์ธ์ ๋ณด ์กฐํ
  @RequestMapping("mainProductModal.do")
  @ResponseBody
  public Map<String, Object> mainProductModal(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
    logger.info("+ Start " + className + ".mainProductModal");
    logger.info("   - paramMap : " + paramMap);
    
    String result = "SUCCESS";
    String resultMsg = "์กฐํ ๋์์ต๋๋ค.";
    
    MainProductModalModel mainProductModalModel = mainProductInfoService.mainProductModal(paramMap);
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("result", result);
    resultMap.put("resultMsg", resultMsg);
    resultMap.put("mainProductModalModel", mainProductModalModel);
    
    logger.info("+ End " + className + ".mainProductModal");
    
    System.out.println(resultMap);
    return resultMap;
  }
  

}
