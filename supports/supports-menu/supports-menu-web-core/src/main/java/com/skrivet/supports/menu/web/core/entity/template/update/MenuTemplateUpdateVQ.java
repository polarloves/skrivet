package com.skrivet.supports.menu.web.core.entity.template.update;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.validator.constraints.Length;	
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;	
import com.skrivet.core.common.config.Regexps;
		
@ApiModel(description = "菜单模板修改的参数")
public class MenuTemplateUpdateVQ extends BasicEntity {
	@ApiModelProperty(value = "主键编号",required = true)
	@NotNull(message = "主键编号不能为空")
	//主键编号
	private String id;
	@NotNull(message = "模板名称不能为空")
	@Length(max = 255,min=0,message = "模板名称长度为0-255")
	@ApiModelProperty(value = "模板名称",required = true)
	//模板名称
	private String templateName;

	@Length(max = 255,min=0,message = "备注长度为0-255")
	@ApiModelProperty(value = "备注")
	//备注
	private String templateRemark;

	public String  getId() 	{
		return this.id;
	}

	public void setId(String id)  {
		this.id = id;
	}
	
	public String getTemplateName() 	{
		return this.templateName;
	}

	public void setTemplateName(String templateName)  {
		this.templateName = templateName;
	}

	public String getTemplateRemark() 	{
		return this.templateRemark;
	}

	public void setTemplateRemark(String templateRemark)  {
		this.templateRemark = templateRemark;
	}
}