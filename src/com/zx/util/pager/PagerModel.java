
package com.zx.util.pager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


public class PagerModel  {
	
	 private Integer pageIndex = 1;//当前页码    
	 private Integer pageSize = 9;//每页显示的记录数  
	 private Integer totalNum;//总记录数  
	 
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
	
	//计算查询的开始行号
	public int getStartSize() {
		return (this.pageIndex - 1) * this.pageSize;
	}
	 
}
