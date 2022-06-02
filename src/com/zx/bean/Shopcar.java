package com.zx.bean;


public class Shopcar implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int buynum;
	private int userId;
	private int articleId;
	
	//定义Article用于封装商品信息
	private Article article;

	/** setter and getter method */
	public void setBuynum(int buynum){
		this.buynum = buynum;
	}
	public int getBuynum(){
		return this.buynum;
	}
	public void setUserId(int userId){
		this.userId = userId;
	}
	public int getUserId(){
		return this.userId;
	}
	public void setArticleId(int articleId){
		this.articleId = articleId;
	}
	public int getArticleId(){
		return this.articleId;
	}
	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}
	/**
	 * @param article the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
	}
	
	

}