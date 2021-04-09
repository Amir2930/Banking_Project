package com.bank.admin.controller.vo;

public class CustomerTransactionDTO {

	private String txid;
	private String userid;
	private double amount;

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "CustomerTransactionDTO [txid=" + txid + ", userid=" + userid + ", amount=" + amount + "]";
	}
	
	

}
