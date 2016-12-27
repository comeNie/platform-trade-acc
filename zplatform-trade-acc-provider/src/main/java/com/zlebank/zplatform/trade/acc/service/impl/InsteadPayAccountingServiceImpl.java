/* 
 * InsteadPayAccountingServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年10月20日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.acc.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.bean.enums.EntryEvent;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.trade.acc.bean.ResultBean;
import com.zlebank.zplatform.trade.acc.bean.TxnsLogBean;
import com.zlebank.zplatform.trade.acc.common.dao.TxnsLogDAO;
import com.zlebank.zplatform.trade.acc.common.dao.pojo.PojoTxnsLog;
import com.zlebank.zplatform.trade.acc.common.enums.AccStatusEnum;
import com.zlebank.zplatform.trade.acc.common.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.acc.common.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.acc.enums.BusinessEnum;
import com.zlebank.zplatform.trade.acc.service.InsteadPayAccountingService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月20日 下午2:41:25
 * @since 
 */
@Service("insteadPayAccountingService")
public class InsteadPayAccountingServiceImpl implements InsteadPayAccountingService {
	
	private static final Logger log = LoggerFactory.getLogger(InsteadPayAccountingServiceImpl.class);
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	@Autowired
	private AccEntryService accEntryService;
	/**
	 *
	 * @param txnseqno
	 * @return
	 */
	@Override
	public ResultBean advancePaymentAccounting(String txnseqno) {
		ResultBean resultBean = null;
		try {
			PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(txnseqno);
			TradeInfo tradeInfo = new TradeInfo();
			tradeInfo.setTxnseqno(txnsLog.getTxnseqno());
			tradeInfo.setAmount(new BigDecimal(txnsLog.getAmount()));
			tradeInfo.setBusiCode(BusinessEnum.INSTEADPAY_REALTIME.getBusiCode());
			tradeInfo.setCharge(new BigDecimal(txnsLog.getTxnfee()));
			tradeInfo.setCommission(BigDecimal.ZERO);
			tradeInfo.setPayMemberId(txnsLog.getAccsecmerno());
			tradeInfo.setPayToMemberId(txnsLog.getAccmemberid());
			tradeInfo.setChannelId(ChannelEnmu.CMBCINSTEADPAY_REALTIME.getChnlcode());
			// 取合作机构号
			tradeInfo.setCoopInstCode(txnsLog.getAcccoopinstino());
			tradeInfo.setAccess_coopInstCode(txnsLog.getAccfirmerno());
			accEntryService.accEntryProcess(tradeInfo, EntryEvent.AUDIT_APPLY);
			txnsLogDAO.updateAccBusiCodeAndFee(txnseqno, BusinessEnum.INSTEADPAY_REALTIME.getBusiCode(), tradeInfo.getCharge().longValue()+"");
			resultBean = new ResultBean("success");
		} catch (AccBussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean(e.getCode(), e.getMessage());
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean(e.getCode(), e.getMessage());
		} catch (AbstractBusiAcctException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean(e.getCode(), e.getMessage());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("", e.getMessage());
		}
		return resultBean;
	}
	/**
	 *
	 * @param txnseqno
	 * @return
	 */
	@Override
	public ResultBean insteadPayAccounting(TxnsLogBean txnsLogBean) {
		BusinessEnum businessEnum = BusinessEnum.fromValue(txnsLogBean.getBusicode());
		ResultBean resultBean = null;
        log.info("交易:{},业务代码:{}",txnsLogBean.getTxnseqno(),businessEnum.getBusiCode());
      //产品消费账务处理
 		if(businessEnum==BusinessEnum.INSTEADPAY_REALTIME){
 			log.info("交易:"+txnsLogBean.getTxnseqno()+"开始实时代付账务");
 			resultBean = realTimeInsteadPayAccounting(txnsLogBean);
 			log.info("交易:"+txnsLogBean.getTxnseqno()+"结束实时代付账务");
 		}
		return null;
	}

	public ResultBean realTimeInsteadPayAccounting(TxnsLogBean txnsLogBean){
		ResultBean resultBean = null; 
		EntryEvent entryEvent = null;
		
		try {
			TradeInfo tradeInfo = new TradeInfo();
			tradeInfo.setTxnseqno(txnsLogBean.getTxnseqno());
			tradeInfo.setAmount(new BigDecimal(txnsLogBean.getAmount()));
			tradeInfo.setBusiCode(BusinessEnum.INSTEADPAY_REALTIME.getBusiCode());
			tradeInfo.setCharge(new BigDecimal(txnsLogBean.getTxnfee()));
			tradeInfo.setCommission(BigDecimal.ZERO);
			tradeInfo.setPayMemberId(txnsLogBean.getAccsecmerno());
			tradeInfo.setPayToMemberId(txnsLogBean.getAccmemberid());
			tradeInfo.setChannelId(ChannelEnmu.CMBCINSTEADPAY_REALTIME.getChnlcode());
			// 取合作机构号
			tradeInfo.setCoopInstCode(txnsLogBean.getAcccoopinstino());
			tradeInfo.setAccess_coopInstCode(txnsLogBean.getAccfirmerno());
			if(TradeStatFlagEnum.FINISH_SUCCESS==TradeStatFlagEnum.fromValue(txnsLogBean.getTradestatflag())){
				entryEvent = EntryEvent.TRADE_SUCCESS;
			}else{
				entryEvent = EntryEvent.TRADE_FAIL;
			}
			accEntryService.accEntryProcess(tradeInfo, entryEvent);
			resultBean = new ResultBean("00","交易成功");
			resultBean.setResultBool(true);
			log.info("交易:"+txnsLogBean.getTxnseqno()+"实时代付入账成功");
		} catch (AccBussinessException e) {
            resultBean = new ResultBean("AP05", e.getMessage());
            e.printStackTrace();
        } catch (AbstractBusiAcctException e) {
            resultBean = new ResultBean("AP05", e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            resultBean = new ResultBean("T099", e.getMessage());
            e.printStackTrace();
        }catch (RuntimeException e) {
			// TODO: handle exception
        	e.printStackTrace();
        	resultBean = new ResultBean("T000", e.getMessage());
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean(e.getCode(), e.getMessage());
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
		if(resultBean.isResultBool()){
			txnsLogBean.setApporderstatus(AccStatusEnum.Finish.getCode());
			if(entryEvent == EntryEvent.TRADE_SUCCESS){
				txnsLogBean.setApporderinfo("代付交易成功入账成功");
			}else if(entryEvent == EntryEvent.TRADE_FAIL){
				txnsLogBean.setApporderinfo("代付交易失败入账成功");
			}
			
        }else{
        	txnsLogBean.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
        	txnsLogBean.setApporderinfo(resultBean.getErrMsg());
        }
        txnsLogDAO.updateAppStatus(txnsLogBean.getTxnseqno(), txnsLogBean.getApporderstatus(), txnsLogBean.getApporderinfo(),BusinessEnum.INSTEADPAY_REALTIME.getBusiCode());
        txnsLogDAO.updateTradeStatFlag(txnsLogBean.getTxnseqno(), TradeStatFlagEnum.FINISH_ACCOUNTING);
		return resultBean;
	}
	/**
	 *
	 * @param txnseqno
	 * @return
	 */
	@Override
	public ResultBean reexchangeAccounting(String txnseqno) {
		log.info("交易:"+txnseqno+"开始实时代付退汇账务");
		ResultBean resultBean = null;
		PojoTxnsLog txnsLog = null;
		try {
			txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(txnseqno);
			TradeInfo tradeInfo = new TradeInfo();
			tradeInfo.setTxnseqno(txnsLog.getTxnseqnoOg());
			tradeInfo.setAmount(new BigDecimal(txnsLog.getAmount()));
			tradeInfo.setBusiCode(BusinessEnum.INSTEADPAY_REALTIME.getBusiCode());
			tradeInfo.setCharge(new BigDecimal(txnsLog.getTxnfee()));
			tradeInfo.setCommission(BigDecimal.ZERO);
			tradeInfo.setPayMemberId(txnsLog.getAccsecmerno());
			tradeInfo.setPayToMemberId(txnsLog.getAccmemberid());
			tradeInfo.setChannelId(ChannelEnmu.CMBCINSTEADPAY_REALTIME.getChnlcode());
			// 取合作机构号
			tradeInfo.setCoopInstCode(txnsLog.getAcccoopinstino());
			tradeInfo.setAccess_coopInstCode(txnsLog.getAccfirmerno());
			accEntryService.accEntryProcess(tradeInfo, EntryEvent.REFUND_EXCHANGE);
			resultBean = new ResultBean("00","交易成功");
			resultBean.setResultBool(true);
			log.info("交易:"+txnseqno+"实时代付退汇入账成功");
		} catch (AccBussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AbstractBusiAcctException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(resultBean.isResultBool()){
			txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
			txnsLog.setApporderinfo("代付交易退汇入账成功");
        }else{
        	txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
        	txnsLog.setApporderinfo(resultBean.getErrMsg());
        }
        txnsLogDAO.updateAppStatus(txnsLog.getTxnseqno(), txnsLog.getApporderstatus(), txnsLog.getApporderinfo());
        txnsLogDAO.updateTradeStatFlag(txnsLog.getTxnseqno(), TradeStatFlagEnum.FINISH_ACCOUNTING);
		return null;
	}
}
