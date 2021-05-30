package kr.happyjob.study.ctm.model;

public class OrderListModel {
	// 제품 코드
	private String product_cd;
	// 제품명
	private String prod_nm;
	// 품목명
	private String l_ct_nm;
	// 상호명
	private String m_ct_nm;
	// 총 구매금액(부가세별도)
	private int amount;
	// 총 세금
	private int tax;
	// 주문 번호
	private String order_cd;
	// 주문 수량
	private int order_cnt;
	// 주문 날짜
	private String order_date;
	// 희망 배송 날짜
	private String want_receive_date;
	// 상태
	private String state;
	// 중분류 코드(for JOIN)
	private String m_ct_cd;
	// 대분류 코드(for JOIN)
	private String l_ct_cd;
	private String loginID;
	private String addr;
	private String refund_date;
	private String refund_cnt;
	private String refund_amt;
	private String refund_reason;
	private String STTcd;
	private String file_relative_path;
	private String request;
	private String total_amt;
	
	
	
	
  public String getTotal_amt() {
    return total_amt;
  }

  public void setTotal_amt(String total_amt) {
    this.total_amt = total_amt;
  }

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public String getFile_relative_path() {
    return file_relative_path;
  }

  public void setFile_relative_path(String file_relative_path) {
    this.file_relative_path = file_relative_path;
  }

  public String getSTTcd() {
    return STTcd;
  }

  public void setSTTcd(String sTTcd) {
    STTcd = sTTcd;
  }

  public String getRefund_date() {
    return refund_date;
  }

  public void setRefund_date(String refund_date) {
    this.refund_date = refund_date;
  }

  public String getRefund_cnt() {
    return refund_cnt;
  }

  public void setRefund_cnt(String refund_cnt) {
    this.refund_cnt = refund_cnt;
  }

  public String getRefund_amt() {
    return refund_amt;
  }

  public void setRefund_amt(String refund_amt) {
    this.refund_amt = refund_amt;
  }

  public String getRefund_reason() {
    return refund_reason;
  }

  public void setRefund_reason(String refund_reason) {
    this.refund_reason = refund_reason;
  }

  public String getAddr() {
    return addr;
  }

  public void setAddr(String addr) {
    this.addr = addr;
  }

  public String getLoginID() {
    return loginID;
  }

  public void setLoginID(String loginID) {
    this.loginID = loginID;
  }

  public String getL_ct_cd() {
		return l_ct_cd;
	}

	public void setL_ct_cd(String l_ct_cd) {
		this.l_ct_cd = l_ct_cd;
	}

	public String getM_ct_nm() {
		return m_ct_nm;
	}

	public void setM_ct_nm(String m_ct_nm) {
		this.m_ct_nm = m_ct_nm;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public String getOrder_cd() {
		return order_cd;
	}

	public void setOrder_cd(String order_cd) {
		this.order_cd = order_cd;
	}

	public int getOrder_cnt() {
		return order_cnt;
	}

	public void setOrder_cnt(int order_cnt) {
		this.order_cnt = order_cnt;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getWant_receive_date() {
		return want_receive_date;
	}

	public void setWant_receive_date(String want_receive_date) {
		this.want_receive_date = want_receive_date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProduct_cd() {
		return product_cd;
	}

	public void setProduct_cd(String product_cd) {
		this.product_cd = product_cd;
	}

	public String getProd_nm() {
		return prod_nm;
	}

	public void setProd_nm(String prod_nm) {
		this.prod_nm = prod_nm;
	}

	public String getL_ct_nm() {
		return l_ct_nm;
	}

	public void setL_ct_nm(String l_ct_nm) {
		this.l_ct_nm = l_ct_nm;
	}

	public String getM_ct_cd() {
		return m_ct_cd;
	}

	public void setM_ct_cd(String m_ct_cd) {
		this.m_ct_cd = m_ct_cd;
	}
}