/* 
 * InsteadPayAccountingService.java  
 * 
 * version TODO
 *
 * 2016年10月20日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.acc.service;

import com.zlebank.zplatform.trade.acc.bean.ResultBean;
import com.zlebank.zplatform.trade.acc.bean.TxnsLogBean;

/**
 * 实时代付交易账务（交易前）
 *
 * @author guojia
 * @version
 * @date 2016年10月20日 下午2:36:52
 * @since 
 */
public interface InsteadPayAccountingService {

	/**
	 * 代付交易预付款账务
	 * @param txnseqno 交易序列号
	 * @return 结果bean
	 */
	public ResultBean advancePaymentAccounting(String txnseqno);
	
	
	/**
	 * 代付交易联机交易后账务
	 * @param txnsLogBean 交易日志bean
	 * @return 结果bean
	 */
	public ResultBean insteadPayAccounting(TxnsLogBean txnsLogBean);
	
	/**
	 * 退汇账务处理
	 * @param txnseqno 交易序列号
	 * @return
	 */
	public ResultBean reexchangeAccounting(String txnseqno);
}
