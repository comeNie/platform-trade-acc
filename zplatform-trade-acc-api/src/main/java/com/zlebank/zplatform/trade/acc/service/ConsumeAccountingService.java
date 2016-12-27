package com.zlebank.zplatform.trade.acc.service;

import com.zlebank.zplatform.trade.acc.bean.ResultBean;
import com.zlebank.zplatform.trade.acc.bean.TxnsLogBean;

/**
 * 消费类交易账务接口
 * @author guojia
 *
 */
public interface ConsumeAccountingService {

	public ResultBean consumeAccounting(TxnsLogBean txnsLogBean);
}
