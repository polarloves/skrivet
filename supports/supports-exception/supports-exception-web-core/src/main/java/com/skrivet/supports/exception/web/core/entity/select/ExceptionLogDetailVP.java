package com.skrivet.supports.exception.web.core.entity.select;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
	
@ApiModel(description = "异常日志详情")
public class ExceptionLogDetailVP extends BasicEntity {
	@ApiModelProperty(value = "主键")
	//主键
	private String id;
	@ApiModelProperty(value = "接口")
	//接口
	private String interfaceTag;
	@ApiModelProperty(value = "异常信息")
	//异常信息
	private String exceptionMsg;
	@ApiModelProperty(value = "异常详情")
	//异常详情
	private String exceptionDetail;
	@ApiModelProperty(value = "报错时间")
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