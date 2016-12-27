package com.zlebank.zplatform.trade.acc.common.dao;

import com.zlebank.zplatform.trade.acc.common.dao.pojo.PojoTxnsLog;
import com.zlebank.zplatform.trade.acc.common.enums.TradeStatFlagEnum;
/**
 * 
 * 基础数据库连接层DAO
 *
 * @author guojia
 * @version
 * @date 2015-6-26 上午10:09:10
 * @param T
 * @since
 */
public interface TxnsLogDAO extends BaseDAO<PojoTxnsLog>{

	/**
	 * 通过交易序列号获取交易信息
	 * @param txnseqno 交易序列号
	 * @return 交易日志pojo
	 */
	public PojoTxnsLog getTxnsLogByTxnseqno(String txnseqno);
	
	/**
	 * 更新交易受理业务代码和手续费
	 * @param txnseqno
	 * @param busicode
	 */
	public void updateAccBusiCodeAndFee(String txnseqno, String busicode,String txnFee) ;
	/**
     * 更新应用方（账务）处理结果信息
     * @param txnseqno
     * @param appOrderStatus
     * @param appOrderinfo
     */
    public void updateAppStatus(String txnseqno,String appOrderStatus,String appOrderinfo);
    
    /**
	 * 更新交易状态标记位
	 * @param txnseqno
	 * @param tradeStatFlagEnum
	 */
	public void updateTradeStatFlag(String txnseqno,TradeStatFlagEnum tradeStatFlagEnum);
	
	/**
	 * 获取交易手续费
	 * @param txnsLog
	 * @return
	 */
	@Deprecated
	public Long getTxnFee(PojoTxnsLog txnsLog);
	
	/**
	 * 更新交易账务结果和业务代码
	 * @param txnseqno
	 * @param appOrderStatus
	 * @param appOrderinfo
	 * @param accBusiCode
	 */
	public void updateAppStatus(String txnseqno, String appOrderStatus,String appOrderinfo,String accBusiCode);
}
