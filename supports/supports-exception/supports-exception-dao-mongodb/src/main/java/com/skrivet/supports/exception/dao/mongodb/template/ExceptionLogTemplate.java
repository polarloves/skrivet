package com.skrivet.supports.exception.dao.mongodb.template;

import com.skrivet.core.common.entity.BasicEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SKRIVET_EXCEPTION")
public class ExceptionLogTemplate extends BasicEntity {
	@Id
	//主键
	private String id;
	//接口
	private String interfaceTag;
	//异常信息
	private String exceptionMsg;
	//异常详情
	private String exceptionDetail;
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
	
	public String getExceptionDetail() 	{
		return this.exceptionDetail;
	}

	public void setExceptionDetail(String exceptionDetail)  {
		this.exceptionDetail = exceptionDetail;
	}
	
	public String getHappenTime() 	{
		return this.happenTime;
	}

	public void setHappenTime(String happenTime)  {
		this.happenTime = happenTime;
	}
}