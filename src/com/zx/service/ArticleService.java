/**
 * 
 */
package com.zx.service;

import java.util.Date;
import java.util.List;

import com.zx.bean.Article;
import com.zx.dao.ArticleDao;
import com.zx.util.pager.PagerModel;


public class ArticleService {

	private ArticleDao articleDao = new ArticleDao();
	
	/**
	 * @return
	 * 获取所有的商品信息
	 */
	public List<Article> getAllArticle(String typeCode,String keyword,PagerModel pagerModel,String flag) {
		typeCode = typeCode + "%";
		keyword = keyword == null ? "%%" : "%"+keyword+"%";
		//查询总记录数
		int totalNum = articleDao.getTotalNum(typeCode,keyword,flag);
		//将总记录数封装至pageModel对象中
		pagerModel.setTotalNum(totalNum);
		List<Article> articles = articleDao.getAllArticle(typeCode,keyword,pagerModel,flag);
		
		return articles;
	}

	/**
	 * @param id
	 * @return
	 * 根据商品id获取商品信息
	 */
	public Article getArticleById(String id) {
		
		return articleDao.getArticleById(Integer.valueOf(id));
	}
	
	//保存商品信息
	public void saveArticle(String typeCode, String title, String supplier, String locality, String price,
			String storage, String description, String newFileName) {
		Article article = new Article();
		article.setTitle(title);
		article.setImage(newFileName);
		article.setCreateDate(new Date());
		article.setLocality(locality);
		article.setDescription(description);
		article.setTypeCode(typeCode);
		article.setStorage(Integer.valueOf(storage));
		article.setPrice(Double.valueOf(price));
		article.setSupplier(supplier);
		
		articleDao.save(article);
		
	}

	 //更新商品信息
	public void updateArticle(String id,String typeCode,String discount, String title, String supplier, String locality, String price,
			String storage, String description, String image) {
		
		Article article = new Article();
		article.setId(Integer.valueOf(id));
		article.setTitle(title);
		article.setImage(image);
		article.setCreateDate(new Date());
		article.setLocality(locality);
		article.setDescription(description);
		article.setTypeCode(typeCode);
		article.setStorage(Integer.valueOf(storage));
		article.setPrice(Double.valueOf(price));
		article.setSupplier(supplier);
		article.setDiscount(Double.valueOf(discount));
		articleDao.update(article);
	}

	//获取上架或下架标记
	public void removeOrPutArticleServlet(String id, String disabled) {
		// TODO Auto-generated method stub
		articleDao.removeOrPutArticleServlet(Integer.valueOf(id),disabled);
	}

}
