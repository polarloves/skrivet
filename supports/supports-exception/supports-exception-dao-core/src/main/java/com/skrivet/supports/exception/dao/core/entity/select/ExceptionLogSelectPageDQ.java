package com.skrivet.supports.exception.dao.core.entity.select;

import com.skrivet.plugins.database.core.entity.BasicPageDQ;
	

public class ExceptionLogSelectPageDQ extends BasicPageDQ	{
	//接口
	private String interfaceTag;
	//异常信息
	private String exceptionMsg;
	//异常详情
	private String exceptionDetail;
	private String startTime;
	private String endTime;
	
	public String getInterfaceTag() 	{
		return this.interfaceTag;
	}

	public void setInterfaceTag(String interfaceTag)  {
		this.interfaceTag = interfaceTag;
	}
	
	public String getExceptionMsg() 	{
		return this.exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg)  {
		this.exceptionMsg = exceptionMsg;
	}
	
	public String getExceptionDetail() 	{
		return this.exceptionDetail;
	}

	public void setExceptionDetail(String exceptionDetail)  {
		this.exceptionDetail = exceptionDetail;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}