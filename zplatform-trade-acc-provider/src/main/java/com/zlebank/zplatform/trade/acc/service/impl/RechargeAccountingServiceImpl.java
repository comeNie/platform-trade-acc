/* 
 * RechargeAccountingServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年11月18日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.acc.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.bean.enums.EntryEvent;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.trade.acc.bean.ResultBean;
import com.zlebank.zplatform.trade.acc.bean.TxnsLogBean;
import com.zlebank.zplatform.trade.acc.common.dao.TxnsLogDAO;
import com.zlebank.zplatform.trade.acc.common.enums.AccStatusEnum;
import com.zlebank.zplatform.trade.acc.common.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.acc.common.utils.DateUtil;
import com.zlebank.zplatform.trade.acc.enums.BusinessEnum;
import com.zlebank.zplatform.trade.acc.service.RechargeAccountingService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年11月18日 下午4:10:34
 * @since 
 */
@Service("rechargeAccountingService")
public class RechargeAccountingServiceImpl implements RechargeAccountingService {

	private static final Logger log = LoggerFactory.getLogger(RechargeAccountingServiceImpl.class);
	@Autowired
	private AccEntryService accEntryService;
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	/**
	 *
	 * @param txnsLogBean
	 * @return
	 */
	@Override
	public ResultBean rechargeAccounting(TxnsLogBean txnsLogBean) {
		ResultBean resultBean = null;
		log.info("交易:" + txnsLogBean.getTxnseqno() + "充值入账开始");
		
		BusinessEnum businessEnum = BusinessEnum.fromValue(txnsLogBean
				.getBusicode());
		if(businessEnum==BusinessEnum.RECHARGE){//现金账户充值
			resultBean = commonTradeAccounting(txnsLogBean);
		}else if(businessEnum==BusinessEnum.CHARGE_INDUSTRY){//行业账户充值
			//resultBean = industryTradeAccounting(txnsLogBean);
		}else if(businessEnum == BusinessEnum.BAIL_RECHARGE){
			//resultBean = commonTradeAccounting(txnsLogBean);
		}
		return resultBean;
	}
	/**
	 * @param txnsLogBean
	 * @return
	 */
	private ResultBean commonTradeAccounting(TxnsLogBean txnsLog) {
		ResultBean resultBean = null;
		try {
			/** 支付订单号 **/
			String payordno = txnsLog.getPayordno();
			/** 交易类型 **/
			String busiCode = txnsLog.getBusicode();
			/** 付款方会员ID **/
			String payMemberId = "";
			/** 收款方会员ID **/
			String payToMemberId = "";
			if (StringUtils.isEmpty(txnsLog.getAccsecmerno())) {
				payMemberId = txnsLog.getAccmemberid();//
				payToMemberId = txnsLog.getAccmemberid();
			} else {
				String memberId = txnsLog.getAccmemberid();
				payMemberId = memberId;
				payToMemberId = memberId;
			}
			/** 收款方父级会员ID **/
			String payToParentMemberId = "";
			/** 渠道 **/
			String channelId = txnsLog.getPayinst();// 支付机构代码
			/** 产品id **/
			String productId = "";
			/** 交易金额 **/
			BigDecimal amount = new BigDecimal(txnsLog.getAmount());
			/** 佣金 **/
			BigDecimal commission = new BigDecimal(txnsLog.getTradcomm());
			/** 手续费 **/
			BigDecimal charge = new BigDecimal(txnsLog.getTxnfee());
			/** 金额D **/
			BigDecimal amountD = new BigDecimal(0);
			/** 金额E **/
			BigDecimal amountE = new BigDecimal(0);
			// txnsLogService.updateAccBusiCode(txnseqno, busiCode);
			TradeInfo tradeInfo = new TradeInfo(txnsLog.getTxnseqno(), payordno, busiCode,
					payMemberId, payToMemberId, payToParentMemberId, channelId,
					productId, amount, commission, charge, amountD, amountE,
					false);
			tradeInfo.setPayordno(payordno);
			tradeInfo.setCoopInstCode(txnsLog.getAccfirmerno());
			log.info(JSON.toJSONString(tradeInfo));
			try {
				accEntryService.accEntryProcess(tradeInfo,
						EntryEvent.TRADE_SUCCESS);
				resultBean = new ResultBean("success");
			} catch (AccBussinessException e) {
				resultBean = new ResultBean(e.getCode(), e.getMessage());
				e.printStackTrace();
			} catch (AbstractBusiAcctException e) {
				resultBean = new ResultBean(e.getCode(), e.getMessage());
				e.printStackTrace();
			} catch (NumberFormatException e) {
				resultBean = new ResultBean("AE099", e.getMessage());
				e.printStackTrace();
			} catch (IllegalEntryRequestException e) {
				// TODO Auto-generated catch block
				resultBean = new ResultBean(e.getCode(), e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				resultBean = new ResultBean("", e.getMessage());
				e.printStackTrace();
			}
			log.info("交易:" + txnsLog.getTxnseqno() + "充值入账成功");
		} catch (NumberFormatException e) {
			resultBean = new ResultBean("T099", e.getMessage());
			e.printStackTrace();
		}
		if (txnsLog == null) {
			return resultBean;
		}
		if (resultBean.isResultBool()) {
			txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
			txnsLog.setApporderinfo("充值入账成功");
		} else {
			txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
			txnsLog.setApporderinfo(resultBean.getErrMsg());
		}
		txnsLogDAO.updateAppStatus(txnsLog.getTxnseqno(), txnsLog.getApporderstatus(),
				txnsLog.getApporderinfo(),BusinessEnum.RECHARGE.getBusiCode());
		txnsLogDAO.updateTradeStatFlag(txnsLog.getTxnseqno(),
				TradeStatFlagEnum.FINISH_ACCOUNTING);
		return resultBean;
	}

}
