/* 
 * WithdrawAccountingService.java  
 * 
 * version TODO
 *
 * 2016年11月14日 
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
 * @date 2016年11月14日 下午4:15:10
 * @since 
 */
public interface WithdrawAccountingService {

	/**
	 * 提现申请账务
	 * @param txnseqno 交易序列号
	 */
	public ResultBean withdrawApply(String txnseqno);
}
