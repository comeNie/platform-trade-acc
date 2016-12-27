/* 
 * BusinessEnum.java  
 * 
 * version TODO
 *
 * 2015年11月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.acc.enums;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月27日 上午11:45:57
 * @since
 */
public enum BusinessEnum {
	/**
	 * 消费-快捷
	 */
	CONSUMEQUICK("10000001"),
	/**
	 * 消费-账户
	 */
	CONSUMEACCOUNT("10000002"), 
	/**
	 *  消费-分账
	 */
	CONSUMESPLIT("10000004"),
	/**
	 *  消费-授信
	 */
	CREDIT_CONSUME("10000006"), 
	/**
	 *  充值
	 */
	RECHARGE("20000001"), 
	/**
	 *  保证金线上充值
	 */
	BAIL_RECHARGE("20000002"), 
	/**
	 *  保证金线下充值
	 */
	BAIL_RECHARGE_OFFLINE("20000003"), 
	/**
	 *  授信账户充值
	 */
	CREDIT_RECHARGE("20000004"), 
	/**
	 *  提现
	 */
	WITHDRAWALS("30000001"), 
	/**
	 *  退款-银行卡
	 */
	REFUND_BANK("40000001"), 
	/**
	 *  代付
	 */
	INSTEADPAY("70000001"), 
	/**
	 *  代付退汇
	 */
	INSTEADPAY_REFUND("70000001"), 
	/**
	 *  提现退汇
	 */
	WITHDRAWALS_REFUND("30000003"), 
	/**
	 * 实时代付
	 */
	INSTEADPAY_REALTIME("70000002"), 
	/**
	 *  退款退汇
	 */
	REFUND_REFUND("40000003"), 
	/**
	 *  授信账户退款
	 */
	CREDIT_REFUND("40000004"), 
	/**
	 *  代付退汇
	 */
	INSTEADPAY_SUCCESS("70000001"), 
	/**
	 *  提现退汇
	 */
	WITHDRAWALS_SUCCESS("30000001"), 
	/**
	 *  退款-账户
	 */
	REFUND_ACCOUNT("40000002"), 
	/**
	 *  手工充值
	 */
	CHARGE_OFFLINE("90000001"), 
	/**
	 *  产品-消费-快捷
	 */
	CONSUMEQUICK_PRODUCT("11000001"), 
	/**
	 *  产品-消费-账户
	 */
	CONSUMEACCOUNT_PRODUCT("11000002"), 
	/**
	 *  转账
	 */
	TRANSFER("50000001"),
	/**
	 *  保证金线提取
	 */
	BAIL_WITHDRAWALS("50000002"), 
	/**
	 *  消费行业
	 */
	CONSUME_INDUSTRY("10000005"), 
	/**
	 *  充值-行业
	 */
	CHARGE_INDUSTRY("20000005"), 
	/**
	 *  转账-行业
	 */
	TRANSFER_INDUSTRY("50000003"), 
	/**
	 *  提取-行业
	 */
	EXTRACT_INDUSTRY("50000004"), 
	/**
	 *  退款-行业
	 */
	REFUND_INDUSTRY("40000005"), 
	/**
	 *  未知
	 */
	UNKNOW("");
	private String busiCode;

	private BusinessEnum(String busiCode) {
		this.busiCode = busiCode;
	}

	public static BusinessEnum fromValue(String value) {
		for (BusinessEnum busi : values()) {
			if (busi.busiCode.equals(value)) {
				return busi;
			}
		}
		return UNKNOW;
	}

	public String getBusiCode() {
		return busiCode;
	}
}
