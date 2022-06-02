
package com.zx.util.pager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


public class Pager extends TagSupport {

	 private int pageIndex;//当前页码    1
	 private int pageSize;//每页显示的记录数  10
	 private int totalNum;//总记录数  999
	 private String submitUrl;//提交地址   list.action?pageIndex={0}
	 private String pageStyle = "yellow";

	/* 
	 * 
	 */
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		try {
			JspWriter jspWriter =  this.pageContext.getOut();
			
			//创建StringBuffer实例  用于拼装页码相关信息
			StringBuffer sbf = new StringBuffer();
			
			//定义跳转地址
			String jumpUrl = "";
			//判断总记录数是否大于零
			if(totalNum > 0){
				
				//计算总共的页码
				int totalPageNum = this.totalNum % this.pageSize == 0 ?this.totalNum / this.pageSize : this.totalNum / this.pageSize + 1;
				
				StringBuffer pager = new StringBuffer();
				//如果当前在第一页
				if(this.pageIndex == 1){
					pager.append("<span class='disabled'>上一页</span>");
					
					
					//计算中间页码
					calcMiddle(pager,totalPageNum);
					
					//如果总共就一页那么下一页也不能点击
					if(totalPageNum == 1){
						pager.append("<span class='disabled'>下一页</span>");
					}else{
						jumpUrl = this.submitUrl.replace("{0}", String.valueOf(this.pageIndex + 1));
						//可以点击下一页
						pager.append("<a href='"+jumpUrl+"'>下一页</a>");
					}
				
					//当前页码是尾页
				}else if(this.pageIndex == totalPageNum){
					
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(this.pageIndex - 1));
					//可以点击下一页
					pager.append("<a href='"+jumpUrl+"'>上一页</a>");
					
					
					//计算中间页码
					calcMiddle(pager,totalPageNum);
					
					
					pager.append("<span class='disabled'>下一页</span>");
					
					//当前页码在中间  当前既不是首页 也不是尾页
				}else{
					
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(this.pageIndex - 1));
					//可以点击下一页
					pager.append("<a href='"+jumpUrl+"'>上一页</a>");
					
					
					//计算中间页码
					calcMiddle(pager,totalPageNum);
					
					
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(this.pageIndex + 1));
					//可以点击下一页
					pager.append("<a href='"+jumpUrl+"'>下一页</a>");

				}
				
				sbf.append("<table align='center' class='"+pageStyle+"' style='width:100%;font-size:14px;'><tr><td>"+pager.toString()+"</td></tr>");
				//定义开始行号   每页显示10条 2页       总共 999条    当前是第100页   990  999
				int startSize = (this.pageIndex - 1) * this.pageSize + 1;//11
				
				//定义结束行号
				int endSize = this.pageIndex == totalPageNum? totalNum : this.pageIndex * this.pageSize;//1000
				
				sbf.append("<tr><td>总共<font color='red'>"+this.totalNum+"</font>条记录,当前显示"+startSize+"-"+endSize+"条记录</td></tr></table>");
				
			}else{
				
				sbf.append("<table align='center' style='width:100%;font-size:14px;'><tr><td>总共<font color='red'>0</font>条记录,当前显示0-0条记录</td></tr></table>");
				
			}
			
			
			jspWriter.write(sbf.toString());
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//
		return super.doStartTag();
	}


	/**
	 * @param pager
	 * 计算中间页码
	 */
	public void calcMiddle(StringBuffer pager,int totalPageNum) {
		// TODO Auto-generated method stub
		//定义跳转地址
		String jumpUrl = "";
		//如果总页码数小于等于10   1 2 3 4 5 6 7 8 9 10
		if(totalPageNum <= 10){
			for (int i = 1; i <= totalPageNum; i++) {
				
				//如果i等于当前页码  则不能点击
				if(i==this.pageIndex){
					pager.append("<span class='current'>"+i+"</span>");
				}else{
					//submitUrl： list.action?pageIndex={0}
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
					pager.append("<a href='"+jumpUrl+"'>"+i+"</a>");
				}
				
			}
		}else{
			
			//如果当前页码靠近首页   1 2 3 4 5 6 7 8 9  ... 100
			if(this.pageIndex <=8 ){
				
				for(int i=1;i<=9;i++){
					//如果i等于当前页码  则不能点击
					if(i==this.pageIndex){
						pager.append("<span class='current'>"+i+"</span>");
					}else{
						//submitUrl： list.action?pageIndex={0}
						jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
						pager.append("<a href='"+jumpUrl+"'>"+i+"</a>");
					}
				}
				
				//拼装 ...
				pager.append("...");
				
				//拼装尾页
				//submitUrl： list.action?pageIndex={0}
				jumpUrl = this.submitUrl.replace("{0}", String.valueOf(totalPageNum));
				pager.append("<a href='"+jumpUrl+"'>"+totalPageNum+"</a>");
			 
			 //当前页码靠近尾页   1 ... 91 92 93 94 95 96 97 98 99 100
			}else if(this.pageIndex + 8 >= totalPageNum){
				jumpUrl = this.submitUrl.replace("{0}", String.valueOf(1));
				pager.append("<a href='"+jumpUrl+"'>"+1+"</a>");
				
				//拼装 ...
				pager.append("...");
				
				for(int i=totalPageNum-9;i<=totalPageNum;i++){
					//如果i等于当前页码  则不能点击
					if(i==this.pageIndex){
						pager.append("<span class='current'>"+i+"</span>");
					}else{
						//submitUrl： list.action?pageIndex={0}
						jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
						pager.append("<a href='"+jumpUrl+"'>"+i+"</a>");
					}
				}
			 //当前页码靠近中间  1 ... 41 42 43 44 45 46 47 48 49 ... 100;
			}else{
				jumpUrl = this.submitUrl.replace("{0}", String.valueOf(1));
				pager.append("<a href='"+jumpUrl+"'>"+1+"</a>");
				//拼装 ...
				pager.append("...");
				
				for(int i=this.pageIndex - 4;i<=this.pageIndex + 4;i++){
					//如果i等于当前页码  则不能点击
					if(i==this.pageIndex){
						pager.append("<span class='current'>"+i+"</span>");
					}else{
						//submitUrl： list.action?pageIndex={0}
						jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
						pager.append("<a href='"+jumpUrl+"'>"+i+"</a>");
					}
				}
				
				
				//拼装 ...
				pager.append("...");
				
				//拼装尾页
				//submitUrl： list.action?pageIndex={0}
				jumpUrl = this.submitUrl.replace("{0}", String.valueOf(totalPageNum));
				pager.append("<a href='"+jumpUrl+"'>"+totalPageNum+"</a>");
			}
			
			
		}
		
		
	}


	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}


	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		System.out.println("pageIndex:"+pageIndex);
		this.pageIndex = pageIndex;
	}


	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}


	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	/**
	 * @return the totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}


	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}


	/**
	 * @return the submitUrl
	 */
	public String getSubmitUrl() {
		return submitUrl;
	}


	/**
	 * @param submitUrl the submitUrl to set
	 */
	public void setSubmitUrl(String submitUrl) {
		this.submitUrl = submitUrl;
	}


	/**
	 * @return the pageStyle
	 */
	public String getPageStyle() {
		return pageStyle;
	}


	/**
	 * @param pageStyle the pageStyle to set
	 */
	public void setPageStyle(String pageStyle) {
		this.pageStyle = pageStyle;
	}

	
	 
	
	   
}
