/* 
 * RefundAccountingService.java  
 * 
 * version TODO
 *
 * 2016年11月11日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.acc.service;

import com.zlebank.zplatform.trade.acc.bean.ResultBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年11月11日 下午4:40:44
 * @since 
 */
public interface RefundAccountingService {

	/**
	 * 退款账务申请
	 * @param txnseqno 交易序列号
	 * @return 账务结果bean
	 */
	public ResultBean refundApply(String txnseqno);
}
