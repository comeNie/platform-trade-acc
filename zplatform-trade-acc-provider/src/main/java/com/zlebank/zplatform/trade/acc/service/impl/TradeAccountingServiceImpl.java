package com.zlebank.zplatform.trade.acc.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.member.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.trade.acc.bean.ResultBean;
import com.zlebank.zplatform.trade.acc.bean.TxnsLogBean;
import com.zlebank.zplatform.trade.acc.common.dao.TxnsLogDAO;
import com.zlebank.zplatform.trade.acc.common.dao.pojo.PojoTxnsLog;
import com.zlebank.zplatform.trade.acc.enums.BusiTypeEnum;
import com.zlebank.zplatform.trade.acc.service.ConsumeAccountingService;
import com.zlebank.zplatform.trade.acc.service.InsteadPayAccountingService;
import com.zlebank.zplatform.trade.acc.service.RechargeAccountingService;
import com.zlebank.zplatform.trade.acc.service.TradeAccountingService;

@Service("tradeAccountingService")
public class TradeAccountingServiceImpl implements TradeAccountingService {

	@Autowired
	private TxnsLogDAO txnsLogDAO;
	@Autowired
	private ConsumeAccountingService consumeAccountingService;
	@Autowired
	private InsteadPayAccountingService insteadPayAccountingService;
	@Autowired
	private RechargeAccountingService rechargeAccountingService;
	@Override
	public synchronized ResultBean accountingFor(String txnseqno) {
		ResultBean resultBean = null;
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(txnseqno);
		if(txnsLog==null){
			return null;
		}
		BusiTypeEnum busitype = BusiTypeEnum.fromValue(txnsLog.getBusitype());
		if(StringUtils.isEmpty(txnsLog.getApporderstatus())){
			TxnsLogBean txnsLogBean = BeanCopyUtil.copyBean(TxnsLogBean.class, txnsLog);
			if(busitype==BusiTypeEnum.consumption){
				resultBean = consumeAccountingService.consumeAccounting(txnsLogBean);
			}else if(busitype==BusiTypeEnum.charge){
				resultBean = rechargeAccountingService.rechargeAccounting(txnsLogBean);
			}else if(busitype==BusiTypeEnum.insteadPay){
				resultBean = insteadPayAccountingService.insteadPayAccounting(txnsLogBean);
			}
		}
		
		return resultBean;
	}
	/**
	 *
	 * @param txnseqno
	 * @return
	 */
	@Override
	public ResultBean accountingForSync(String txnseqno) {
		ResultBean resultBean = null;
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(txnseqno);
		if(txnsLog==null){
			return null;
		}
		BusiTypeEnum busitype = BusiTypeEnum.fromValue(txnsLog.getBusitype());
		if(StringUtils.isEmpty(txnsLog.getApporderstatus())){
			TxnsLogBean txnsLogBean = BeanCopyUtil.copyBean(TxnsLogBean.class, txnsLog);
			if(busitype==BusiTypeEnum.consumption){
				resultBean = consumeAccountingService.consumeAccounting(txnsLogBean);
			}else if(busitype==BusiTypeEnum.charge){
				resultBean = consumeAccountingService.consumeAccounting(txnsLogBean);
			}else if(busitype==BusiTypeEnum.insteadPay){
				resultBean = insteadPayAccountingService.insteadPayAccounting(txnsLogBean);
			}
		}
		
		return resultBean;
	}

}
