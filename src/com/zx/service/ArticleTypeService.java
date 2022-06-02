/**
 * 
 */
package com.zx.service;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zx.bean.Article;
import com.zx.bean.ArticleType;
import com.zx.dao.ArticleDao;
import com.zx.dao.ArticleTypeDao;
import com.zx.util.pager.PagerModel;

import javafx.print.JobSettings;



public class ArticleTypeService {

	private ArticleTypeDao  typeDao = new ArticleTypeDao();
	
	/**
	 * @return
	 * 获取所有的一级类型
	 */
	public List<ArticleType> getAllFType() {
		List<ArticleType> types = typeDao.getAllFType();
		return types;
	}

	/**
	 * @param typeCode
	 * @return
	 * 根据一级商品类型获取对应的二级商品类型
	 */
	public List<ArticleType> getSecondTypesByFCode(String typeCode) {
		
		List<ArticleType> types = typeDao.getSecondTypesByFCode(typeCode+"%");
		return types;
	}

	//获取二级商品类型信息
	public String getAllSecondTypeByCode(String code) {
		List<ArticleType> types = typeDao.getSecondTypesByFCode(code+"%");
   
		Gson gson = new Gson();
		String jsonstr = gson.toJson(types);

		return jsonstr;
	}

	//获取所有的商品类型
	public List<ArticleType> getAllTypes(PagerModel pageModel) {
		// TODO Auto-generated method stub
		//查询总记录数
		int totalNum = typeDao.getTotalNum();
		//将总记录数封装至pageModel对象中
		pageModel.setTotalNum(totalNum);
		
		List<ArticleType> types = typeDao.getAllTypes(pageModel);

		return types;
	}

	//添加商品类型
	public void addArticleType(String parentCode, String name, String remark) {
		// TODO Auto-generated method stub
	     ArticleType type = this.calcType(parentCode, name, remark);
		//保存商品类型
		typeDao.saveType(type);
	}
	
	
	// 新增商品类型    工具方法
	public ArticleType calcType(String parentCode, String name, String remark) {
		
		ArticleType type = new ArticleType();
		type.setName(name);
		type.setRemark(remark);
		//
		if(parentCode == null || parentCode.equals("")) {
			//当前添加的是一级商品类型
			
			/*
			 * 获取最大的一级类型code    
			 * 0015 --->15  --->16  -->0016
			 * 0005 --->5   --->6   -->0006
			 * */
			String maxCode = typeDao.findMaxFcode();
			
			//计算当前的code
			int curCode = Integer.valueOf(maxCode) + 1;//16
			System.out.println("curCode:"+curCode);
			String strCurCode = String.valueOf(curCode); // "16"
			System.out.println("strCurCode:"+strCurCode);

			//进行补零操作
			for(int i=0;i<maxCode.length() - String.valueOf(curCode).length(); i++) {
				//进行补零操作
				strCurCode = "0" + strCurCode;
			}			
			System.out.println("strCurCode:"+strCurCode);

			type.setCode(strCurCode);
		}else {
			/***
			 *添加二级商品类型
			 *
			 * 00010008  ==》0008 ==》8 ==》9  ==》 00010009
			 * */
			
			//根据一级类型获取最大的二级类型   0017  00170001
			String maxSecode = typeDao.findMaxScode(parentCode);
			if(maxSecode == null || maxSecode.equals("")) {
				type.setCode(parentCode + "0001");
			}else {
				//进行截取   截取后四位
				String  code = maxSecode.substring(4); // 0008
				
				int curCode = Integer.valueOf(code) + 1;//9
				
				String strCurCode = String.valueOf(curCode); // "16"

				//进行补零操作
				for(int i=0;i<4 - String.valueOf(curCode).length(); i++) {
					//进行补零操作
					strCurCode = "0" + strCurCode;
				}
				
				type.setCode(parentCode + strCurCode);
			}
			
			
		}
		return type;
	}

	//根据商品类型的code获取商品类型
	public ArticleType getArticleType(String code) {
		  ArticleType type = typeDao.getArticleType(code);
		  return type;
	}

	//在未改变上级的前提下   更新商品类型的  name 以及  remark
	public void updateType(String name, String remark, String code) {
		// TODO Auto-generated method stub
		typeDao.updateType(name,remark,code);
	}

	/**
	 * 在改变上级的前提下   更新商品类型的  name 以及  remark
	 * 1、将当前的商品类型  挂在 用户选择的上级类型 下   进行添加操作
	 * 2、之前关联到该商品类型的商品的商品类型code需要发生变化
	 * 3、删除之前的商品类型
	 * */
	public void updateType2(String name, String remark, String code, String parentCode) {
		  
		 //计算好需要进行添加的  商品类型的数据
	     ArticleType type = this.calcType(parentCode, name, remark);
         
	     typeDao.updateType2(type,code, name, remark);
		
	}

	/*
	 * 删除商品类型
	 * 假设商品类型下没有  上架的商品则允许删除，否则不允许删除
	 * 
	 * */
	public void deleteArticle(String code) {
		
		 //判断商品类型下是否存在上架的商品信息
		 boolean flag = typeDao.findArticleByCode(code);
		 if(flag) {
			 //该商品类型下还存在上架的商品信息
			 throw new RuntimeException("删除失败:该商品类型下存在上架的商品信息，商品下架后才可删除该类型！");
		 }
		 
		 //进行   逻辑  删除商品类型信息
		 typeDao.deleteType(code);
	}

	

}
