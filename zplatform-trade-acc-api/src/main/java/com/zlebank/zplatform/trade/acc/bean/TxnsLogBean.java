package com.zlebank.zplatform.trade.acc.bean;

import java.io.Serializable;

public class TxnsLogBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2896389835953257784L;
	private String txnseqno;
    private String txndate;
    private String txntime;
    private String apptype;
    private String busitype;
    private String busicode;
    private Long amount;
    private Long tradcomm;
    private Long txnfee;
    private String riskver;
    private String splitver;
    private String feever;
    private String prdtver;
    private String checkstandver;
    private String routver;
    private String pan;
    private String cardtype;
    private String cardinstino;
    private String inpan;
    private String incardtype;
    private String incardinstino;
    private String accordno;
    private String accordinst;
    private String accsecmerno;
    private String accfirmerno;
    private String accsettledate;
    private String accordcommitime;
    private String accordfintime;
    private String paytype;
    private String payordno;
    private String payinst;
    private String payfirmerno;
    private String paysecmerno;
    private String payordcomtime;
    private String payordfintime;
    private String payrettsnseqno;
    private String payretcode;
    private String payretinfo;
    private String appordno;
    private String appinst;
    private String appordcommitime;
    private String appordfintime;
    private String tradeseltxn;
    private String retcode;
    private String retinfo;
    private String tradestatflag;
    private String tradetxnflag;
    private String txncode;
    private String cashcode;
    private String relate;
    private String retdatetime;
    private String txnseqnoOg;
    private String notes;
    private String remarks;

    private String accmemberid;
    private String apporderstatus;
    private String apporderinfo;
    private String accbusicode;
    private String acccoopinstino;
    private String panName;
    private String productcode;
    private String groupcode;
	public String getTxnseqno() {
		return txnseqno;
	}
	public void setTxnseqno(String txnseqno) {
		this.txnseqno = txnseqno;
	}
	public String getTxndate() {
		return txndate;
	}
	public void setTxndate(String txndate) {
		this.txndate = txndate;
	}
	public String getTxntime() {
		return txntime;
	}
	public void setTxntime(String txntime) {
		this.txntime = txntime;
	}
	public String getApptype() {
		return apptype;
	}
	public void setApptype(String apptype) {
		this.apptype = apptype;
	}
	public String getBusitype() {
		return busitype;
	}
	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}
	public String getBusicode() {
		return busicode;
	}
	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getTradcomm() {
		return tradcomm;
	}
	public void setTradcomm(Long tradcomm) {
		this.tradcomm = tradcomm;
	}
	public Long getTxnfee() {
		return txnfee;
	}
	public void setTxnfee(Long txnfee) {
		this.txnfee = txnfee;
	}
	public String getRiskver() {
		return riskver;
	}
	public void setRiskver(String riskver) {
		this.riskver = riskver;
	}
	public String getSplitver() {
		return splitver;
	}
	public void setSplitver(String splitver) {
		this.splitver = splitver;
	}
	public String getFeever() {
		return feever;
	}
	public void setFeever(String feever) {
		this.feever = feever;
	}
	public String getPrdtver() {
		return prdtver;
	}
	public void setPrdtver(String prdtver) {
		this.prdtver = prdtver;
	}
	public String getCheckstandver() {
		return checkstandver;
	}
	public void setCheckstandver(String checkstandver) {
		this.checkstandver = checkstandver;
	}
	public String getRoutver() {
		return routver;
	}
	public void setRoutver(String routver) {
		this.routver = routver;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getCardinstino() {
		return cardinstino;
	}
	public void setCardinstino(String cardinstino) {
		this.cardinstino = cardinstino;
	}
	public String getInpan() {
		return inpan;
	}
	public void setInpan(String inpan) {
		this.inpan = inpan;
	}
	public String getIncardtype() {
		return incardtype;
	}
	public void setIncardtype(String incardtype) {
		this.incardtype = incardtype;
	}
	public String getIncardinstino() {
		return incardinstino;
	}
	public void setIncardinstino(String incardinstino) {
		this.incardinstino = incardinstino;
	}
	public String getAccordno() {
		return accordno;
	}
	public void setAccordno(String accordno) {
		this.accordno = accordno;
	}
	public String getAccordinst() {
		return accordinst;
	}
	public void setAccordinst(String accordinst) {
		this.accordinst = accordinst;
	}
	public String getAccsecmerno() {
		return accsecmerno;
	}
	public void setAccsecmerno(String accsecmerno) {
		this.accsecmerno = accsecmerno;
	}
	public String getAccfirmerno() {
		return accfirmerno;
	}
	public void setAccfirmerno(String accfirmerno) {
		this.accfirmerno = accfirmerno;
	}
	public String getAccsettledate() {
		return accsettledate;
	}
	public void setAccsettledate(String accsettledate) {
		this.accsettledate = accsettledate;
	}
	public String getAccordcommitime() {
		return accordcommitime;
	}
	public void setAccordcommitime(String accordcommitime) {
		this.accordcommitime = accordcommitime;
	}
	public String getAccordfintime() {
		return accordfintime;
	}
	public void setAccordfintime(String accordfintime) {
		this.accordfintime = accordfintime;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getPayordno() {
		return payordno;
	}
	public void setPayordno(String payordno) {
		this.payordno = payordno;
	}
	public String getPayinst() {
		return payinst;
	}
	public void setPayinst(String payinst) {
		this.payinst = payinst;
	}
	public String getPayfirmerno() {
		return payfirmerno;
	}
	public void setPayfirmerno(String payfirmerno) {
		this.payfirmerno = payfirmerno;
	}
	public String getPaysecmerno() {
		return paysecmerno;
	}
	public void setPaysecmerno(String paysecmerno) {
		this.paysecmerno = paysecmerno;
	}
	public String getPayordcomtime() {
		return payordcomtime;
	}
	public void setPayordcomtime(String payordcomtime) {
		this.payordcomtime = payordcomtime;
	}
	public String getPayordfintime() {
		return payordfintime;
	}
	public void setPayordfintime(String payordfintime) {
		this.payordfintime = payordfintime;
	}
	public String getPayrettsnseqno() {
		return payrettsnseqno;
	}
	public void setPayrettsnseqno(String payrettsnseqno) {
		this.payrettsnseqno = payrettsnseqno;
	}
	public String getPayretcode() {
		return payretcode;
	}
	public void setPayretcode(String payretcode) {
		this.payretcode = payretcode;
	}
	public String getPayretinfo() {
		return payretinfo;
	}
	public void setPayretinfo(String payretinfo) {
		this.payretinfo = payretinfo;
	}
	public String getAppordno() {
		return appordno;
	}
	public void setAppordno(String appordno) {
		this.appordno = appordno;
	}
	public String getAppinst() {
		return appinst;
	}
	public void setAppinst(String appinst) {
		this.appinst = appinst;
	}
	public String getAppordcommitime() {
		return appordcommitime;
	}
	public void setAppordcommitime(String appordcommitime) {
		this.appordcommitime = appordcommitime;
	}
	public String getAppordfintime() {
		return appordfintime;
	}
	public void setAppordfintime(String appordfintime) {
		this.appordfintime = appordfintime;
	}
	public String getTradeseltxn() {
		return tradeseltxn;
	}
	public void setTradeseltxn(String tradeseltxn) {
		this.tradeseltxn = tradeseltxn;
	}
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getRetinfo() {
		return retinfo;
	}
	public void setRetinfo(String retinfo) {
		this.retinfo = retinfo;
	}
	public String getTradestatflag() {
		return tradestatflag;
	}
	public void setTradestatflag(String tradestatflag) {
		this.tradestatflag = tradestatflag;
	}
	public String getTradetxnflag() {
		return tradetxnflag;
	}
	public void setTradetxnflag(String tradetxnflag) {
		this.tradetxnflag = tradetxnflag;
	}
	public String getTxncode() {
		return txncode;
	}
	public void setTxncode(String txncode) {
		this.txncode = txncode;
	}
	public String getCashcode() {
		return cashcode;
	}
	public void setCashcode(String cashcode) {
		this.cashcode = cashcode;
	}
	public String getRelate() {
		return relate;
	}
	public void setRelate(String relate) {
		this.relate = relate;
	}
	public String getRetdatetime() {
		return retdatetime;
	}
	public void setRetdatetime(String retdatetime) {
		this.retdatetime = retdatetime;
	}
	public String getTxnseqnoOg() {
		return txnseqnoOg;
	}
	public void setTxnseqnoOg(String txnseqnoOg) {
		this.txnseqnoOg = txnseqnoOg;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAccmemberid() {
		return accmemberid;
	}
	public void setAccmemberid(String accmemberid) {
		this.accmemberid = accmemberid;
	}
	public String getApporderstatus() {
		return apporderstatus;
	}
	public void setApporderstatus(String apporderstatus) {
		this.apporderstatus = apporderstatus;
	}
	public String getApporderinfo() {
		return apporderinfo;
	}
	public void setApporderinfo(String apporderinfo) {
		this.apporderinfo = apporderinfo;
	}
	public String getAccbusicode() {
		return accbusicode;
	}
	public void setAccbusicode(String accbusicode) {
		this.accbusicode = accbusicode;
	}
	public String getAcccoopinstino() {
		return acccoopinstino;
	}
	public void setAcccoopinstino(String acccoopinstino) {
		this.acccoopinstino = acccoopinstino;
	}
	public String getPanName() {
		return panName;
	}
	public void setPanName(String panName) {
		this.panName = panName;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getGroupcode() {
		return groupcode;
	}
	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}
    
}
