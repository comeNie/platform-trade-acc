package com.zlebank.zplatform.trade.acc.common.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.zlebank.zplatform.trade.acc.common.dao.TxnsLogDAO;
import com.zlebank.zplatform.trade.acc.common.dao.pojo.PojoTxnsLog;
import com.zlebank.zplatform.trade.acc.common.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.acc.common.utils.DateUtil;

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
@Repository("txnsLogDAO")
public class TxnsLogDAOImpl extends HibernateBaseDAOImpl<PojoTxnsLog> implements
		TxnsLogDAO {

	private static final Logger log = LoggerFactory
			.getLogger(TxnsLogDAOImpl.class);

	@Override
	@Transactional(readOnly = true)
	public PojoTxnsLog getTxnsLogByTxnseqno(String txnseqno) {
		Criteria criteria = getSession().createCriteria(PojoTxnsLog.class);
		criteria.add(Restrictions.eq("txnseqno", txnseqno));
		return (PojoTxnsLog) criteria.uniqueResult();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void updateAccBusiCodeAndFee(String txnseqno, String busicode,String txnFee){
		// TODO Auto-generated method stub
		String hql = "update PojoTxnsLog set accbusicode = ?,txnfee = ? where txnseqno = ? ";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, busicode);
		query.setParameter(1, Long.valueOf(txnFee));
		query.setParameter(2, txnseqno);
		int rows = query.executeUpdate();
		log.info("updateAccBusiCode() effect rows:" + rows);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void updateAppStatus(String txnseqno, String appOrderStatus,
			String appOrderinfo) {
		String hql = "update PojoTxnsLog set appordfintime = ?,apporderstatus = ?,apporderinfo = ? where txnseqno = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, DateUtil.getCurrentDateTime());
		query.setParameter(1, appOrderStatus);
		query.setParameter(2, appOrderinfo);
		query.setParameter(3, txnseqno);
		int rows = query.executeUpdate();
		log.info("updateAppStatus() effect rows:" + rows);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void updateTradeStatFlag(String txnseqno,
			TradeStatFlagEnum tradeStatFlagEnum) {
		String hql = "update PojoTxnsLog set tradestatflag = ? where txnseqno = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, tradeStatFlagEnum.getStatus());
		query.setParameter(1, txnseqno);
		int rows = query.executeUpdate();
		log.info("updateTradeStatFlag() effect rows:" + rows);

	}
	/**
	 * 
	 * @param txnsLog
	 * @return
	 */
	@Transactional(readOnly = true)
	public Long getTxnFee(PojoTxnsLog txnsLog){
        //交易序列号，扣率版本，业务类型，交易金额，会员号，原交易序列号，卡类型 
		SQLQuery sqlQuery = (SQLQuery) getSession().createSQLQuery("select FNC_GETFEES(?,?,?,?,?,?,?) as fee from dual").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Object[] paramates = new Object[]{txnsLog.getTxnseqno(),txnsLog.getFeever(),txnsLog.getBusicode(),txnsLog.getAmount(),txnsLog.getAccsecmerno(),txnsLog.getTxnseqnoOg(),txnsLog.getCardtype()};
		for(int i=0;i<paramates.length;i++){
			sqlQuery.setParameter(i, paramates[i]);
		}
		List<Map<String, Object>> feeList = sqlQuery.list();
        
        if(feeList.size()>0){
            if(StringUtils.isEmpty(feeList.get(0).get("FEE"))){
                return 0L;
            }else{
                return Long.valueOf(feeList.get(0).get("FEE")+"");
            }
            
        }
        return 0L;
       
    }
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void updateAppStatus(String txnseqno, String appOrderStatus,String appOrderinfo,String accBusiCode) {
		String hql = "update PojoTxnsLog set appordfintime = ?,apporderstatus = ?,apporderinfo = ?,accbusicode = ? where txnseqno = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, DateUtil.getCurrentDateTime());
		query.setParameter(1, appOrderStatus);
		query.setParameter(2, appOrderinfo);
		query.setParameter(3, accBusiCode);
		query.setParameter(4, txnseqno);
		int rows = query.executeUpdate();
		log.info("updateAppStatus() effect rows:" + rows);

	}
}
