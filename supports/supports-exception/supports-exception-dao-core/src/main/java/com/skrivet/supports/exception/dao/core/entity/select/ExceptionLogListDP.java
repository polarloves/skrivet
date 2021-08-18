package com.skrivet.supports.exception.dao.core.entity.select;

import com.skrivet.core.common.entity.BasicEntity;
	

public class ExceptionLogListDP extends BasicEntity {
	//主键
	private String id;
	//接口
	private String interfaceTag;
	//异常信息
	private String exceptionMsg;
	//报错时间
	private String happenTime;
	
	public String  getId() 	{
		return this.id;
	}

	public void setId(String id)  {
		this.id = id;
	}
	
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
	
	public String getHappenTime() 	{
		return this.happenTime;
	}

	public void setHappenTime(String happenTime)  {
		this.happenTime = happenTime;
	}
}