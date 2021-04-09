package com.bank.admin.controller.vo;

public class ApplicationVO {
	private int oid;
	private String name;
	private String color;
	private int price;
	
	

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ApplicationVO [oid=" + oid + ", name=" + name + ", color=" + color + ", price=" + price + "]";
	}

	

}
