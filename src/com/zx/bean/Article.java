package com.zx.bean;


public class Article implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int id;//商品id
	private String title;//商品标题
	private String supplier;//供应商
	private double price;//价格
	private double discount;//折扣
	private String locality;//出产地
	private java.util.Date putawayDate;//上架时间
	private int storage;//库存
	private String image;//封面
	private String description;//描述信息
	private String typeCode;//商品类型
	private java.util.Date createDate;//创建时间
	private String disabled;//商品是否禁用   0代表 正常    1代表已下架

	/** setter and getter method */
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return this.title;
	}
	public void setSupplier(String supplier){
		this.supplier = supplier;
	}
	public String getSupplier(){
		return this.supplier;
	}
	public void setPrice(double price){
		this.price = price;
	}
	public double getPrice(){
		return this.price;
	}
	public void setDiscount(double discount){
		this.discount = discount;
	}
	public double getDiscount(){
		return this.discount;
	}
	public void setLocality(String locality){
		this.locality = locality;
	}
	public String getLocality(){
		return this.locality;
	}
	public void setPutawayDate(java.util.Date putawayDate){
		this.putawayDate = putawayDate;
	}
	public java.util.Date getPutawayDate(){
		return this.putawayDate;
	}
	public void setStorage(int storage){
		this.storage = storage;
	}
	public int getStorage(){
		return this.storage;
	}
	public void setImage(String image){
		this.image = image;
	}
	public String getImage(){
		return this.image;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return this.description;
	}
	public void setTypeCode(String typeCode){
		this.typeCode = typeCode;
	}
	public String getTypeCode(){
		return this.typeCode;
	}
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	public java.util.Date getCreateDate(){
		return this.createDate;
	}
	public void setDisabled(String disabled){
		this.disabled = disabled;
	}
	public String getDisabled(){
		return this.disabled;
	}
	
	//定义方法用于计算折后价格
	public double getDiscountPrice() {
		return this.price * this.discount;
	}

	
}