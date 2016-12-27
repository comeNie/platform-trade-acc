package com.zlebank.zplatform.trade.acc.service.impl;


import java.math.BigDecimal;

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
import com.zlebank.zplatform.member.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.member.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.acc.bean.ResultBean;
import com.zlebank.zplatform.trade.acc.bean.TxnsLogBean;
import com.zlebank.zplatform.trade.acc.common.dao.TxnsLogDAO;
import com.zlebank.zplatform.trade.acc.common.dao.pojo.PojoTxnsLog;
import com.zlebank.zplatform.trade.acc.common.enums.AccStatusEnum;
import com.zlebank.zplatform.trade.acc.common.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.acc.enums.BusinessEnum;
import com.zlebank.zplatform.trade.acc.service.ConsumeAccountingService;

@Service("consumeAccountingService")
public class ConsumeAccountingServiceImpl implements ConsumeAccountingService {

	private static final Logger log = LoggerFactory.getLogger(ConsumeAccountingServiceImpl.class);
	@Autowired
	private AccEntryService accEntryService;
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	@Override
	public ResultBean consumeAccounting(TxnsLogBean txnsLogBean) {
		BusinessEnum businessEnum = BusinessEnum.fromValue(txnsLogBean.getBusicode());
		ResultBean resultBean = null;
        log.info("交易:{},业务代码:{}",txnsLogBean.getTxnseqno(),businessEnum.getBusiCode());
        //产品消费账务处理
 		if(businessEnum==BusinessEnum.CONSUMEQUICK_PRODUCT){
 			log.info("交易:"+txnsLogBean.getTxnseqno()+"开始产品消费账务");
 			//resultBean = productTradeAccounting(txnsLogBean);
 			log.info("交易:"+txnsLogBean.getTxnseqno()+"结束产品消费账务");
 		}else if(businessEnum==BusinessEnum.CONSUMEQUICK){//一般消费账务处理
			log.info("交易:"+txnsLogBean.getTxnseqno()+"开始一般消费账务");
 			resultBean = commonTradeAccounting(txnsLogBean);
 			log.info("交易:"+txnsLogBean.getTxnseqno()+"结束一般消费账务");
 		}else if(businessEnum==BusinessEnum.CONSUME_INDUSTRY){
 			log.info("交易:{}开始行业消费账务",txnsLogBean.getTxnseqno());
 			//resultBean=industryTradeAccounting(txnsLogBean);
 			log.info("交易:{}结束行业消费账务",txnsLogBean.getTxnseqno());
 		}
 		log.info("交易:"+txnsLogBean.getTxnseqno()+"消费入账结束");
		return resultBean;
	}
	/**
     * 一般消费交易账务
     * @param txnsLog
     * @return
     */
    public ResultBean commonTradeAccounting(TxnsLogBean txnsLog){
    	ResultBean resultBean = null;
    	String txnseqno = txnsLog.getTxnseqno();
    	String accBusiCode=null;
    	try {
            /**支付订单号**/
            String payordno = txnsLog.getPayordno();
            /**交易类型**/
            String busiCode = txnsLog.getBusicode();
            
            /**付款方会员ID**/
            String payMemberId = StringUtil.isNotEmpty(txnsLog.getAccmemberid())?txnsLog.getAccmemberid():"999999999999999";
            /**收款方会员ID**/
            String payToMemberId = StringUtil.isEmpty(txnsLog.getAccsecmerno())?txnsLog.getAccfirmerno():txnsLog.getAccsecmerno();
            /**收款方父级会员ID**/
            String payToParentMemberId=txnsLog.getAccfirmerno()+"";
            /**渠道**/
            String channelId = txnsLog.getPayinst();//支付机构代码

            /**手续费**/
            long txnfee = 0;
            if("99999999".equals(channelId)){
                busiCode = "10000002";
                accBusiCode = "10000002";
                payMemberId = txnsLog.getPayfirmerno();
                if (txnsLog.getTxnfee() != null) {
                    //txnfee = txnsLogDAO.getTxnFee(BeanCopyUtil.copyBean(PojoTxnsLog.class, txnsLog));
                }
            }else {
                busiCode = "10000001";
                accBusiCode = "10000001";
                if (txnsLog.getTxnfee() != null) {
                	//txnfee = txnsLogDAO.getTxnFee(BeanCopyUtil.copyBean(PojoTxnsLog.class, txnsLog));
                }
            }
            /**产品id**/
            String productId = "";
            /**交易金额**/
            BigDecimal amount = new BigDecimal(txnsLog.getAmount());
            /**佣金**/
            BigDecimal commission = new BigDecimal(txnsLog.getTradcomm()==null?0:txnsLog.getTradcomm());

            BigDecimal charge = new BigDecimal(txnfee);
            /**金额D**/
            BigDecimal amountD = new BigDecimal(0);
            /**金额E**/
            BigDecimal amountE = new BigDecimal(0);
            /** 分账标记**/
            boolean isSplit = false;
            if("10000004".equals(txnsLog.getBusicode())){
                isSplit = true;
            }
            //txnsLogDAO.updateAccBusiCodeAndFee(txnseqno, busiCode,txnfee+"");
            TradeInfo tradeInfo = new TradeInfo(txnseqno, payordno, busiCode, payMemberId, payToMemberId, payToParentMemberId, channelId, productId, amount, commission, charge, amountD, amountE, isSplit);
            tradeInfo.setCoopInstCode(txnsLog.getAccfirmerno());
            log.info(JSON.toJSONString(tradeInfo));
            accEntryService.accEntryProcess(tradeInfo,EntryEvent.TRADE_SUCCESS);
            resultBean = new ResultBean("00","交易成功");
            resultBean.setResultBool(true);
            log.info("交易:"+txnseqno+"消费入账成功");
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
        	resultBean = new ResultBean("T000", e.getMessage());
		}
        if(resultBean.isResultBool()){
            txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
            txnsLog.setApporderinfo("消费入账成功");
        }else{
            txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
            txnsLog.setApporderinfo(resultBean.getErrMsg());
        }
        txnsLogDAO.updateAppStatus(txnseqno, txnsLog.getApporderstatus(), txnsLog.getApporderinfo(),accBusiCode);
        txnsLogDAO.updateTradeStatFlag(txnseqno, TradeStatFlagEnum.FINISH_ACCOUNTING);
        return resultBean;
    }
    
   
}
