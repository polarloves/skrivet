package com.skrivet.supports.exception.web.core.entity.select;

import com.skrivet.web.core.entity.BasicPageVQ;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
	
@ApiModel(description = "异常日志分页查询参数")
public class ExceptionLogSelectPageVQ extends BasicPageVQ	{
	@ApiModelProperty(value = "接口")
	//接口
	private String interfaceTag;
	@ApiModelProperty(value = "异常信息")
	//异常信息
	private String exceptionMsg;
	@ApiModelProperty(value = "异常详情")
	//异常详情
	private String exceptionDetail;
	@ApiModelProperty(value = "开始时间")
	private String startTime;
	@ApiModelProperty(value = "结束时间")
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