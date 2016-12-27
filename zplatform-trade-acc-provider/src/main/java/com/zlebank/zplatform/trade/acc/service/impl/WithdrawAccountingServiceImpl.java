/* 
 * WithdrawAccountingServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年11月14日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.acc.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.bean.enums.EntryEvent;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.trade.acc.bean.ResultBean;
import com.zlebank.zplatform.trade.acc.common.dao.TxnsLogDAO;
import com.zlebank.zplatform.trade.acc.common.dao.pojo.PojoTxnsLog;
import com.zlebank.zplatform.trade.acc.common.enums.BusinessCodeEnum;
import com.zlebank.zplatform.trade.acc.service.WithdrawAccountingService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年11月14日 下午4:16:27
 * @since 
 */
@Service("withdrawAccountingService")
public class WithdrawAccountingServiceImpl implements WithdrawAccountingService {

	@Autowired
	private AccEntryService accEntryService;
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	/**
	 *
	 * @param txnseqno
	 */
	@Override
	public ResultBean withdrawApply(String txnseqno) {
		ResultBean resultBean = null;
		try {
			PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(txnseqno);
			TradeInfo tradeInfo = new TradeInfo();
			tradeInfo.setPayMemberId(txnsLog.getAccmemberid());
			tradeInfo.setPayToMemberId(txnsLog.getAccmemberid());
			tradeInfo.setAmount(new BigDecimal(txnsLog.getAmount()));
			tradeInfo.setCharge(new BigDecimal(txnsLog.getTxnfee()));
			tradeInfo.setTxnseqno(txnsLog.getTxnseqno());
			tradeInfo.setBusiCode(BusinessCodeEnum.WITHDRAWALS.getBusiCode());
			tradeInfo.setAccess_coopInstCode(txnsLog.getAccfirmerno());
			tradeInfo.setCoopInstCode(txnsLog.getAcccoopinstino());
			// 记录分录流水
			accEntryService.accEntryProcess(tradeInfo,EntryEvent.AUDIT_APPLY);
			resultBean = new ResultBean("success");
		} catch (AccBussinessException | IllegalEntryRequestException
				| AbstractBusiAcctException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
		}
		return resultBean;
	}

}
