/* 
 * RechargeAccountingService.java  
 * 
 * version TODO
 *
 * 2016年11月18日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.acc.service;

import com.zlebank.zplatform.trade.acc.bean.ResultBean;
import com.zlebank.zplatform.trade.acc.bean.TxnsLogBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年11月18日 下午4:08:57
 * @since 
 */
public interface RechargeAccountingService {

	/**
	 * 充值账务处理
	 * @param txnsLogBean 交易日志bean
	 * @return 交易结果bean
	 */
	public ResultBean rechargeAccounting(TxnsLogBean txnsLogBean);
}
