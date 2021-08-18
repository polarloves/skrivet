package com.skrivet.supports.menu.dao.mongodb.template.template;

import com.skrivet.core.common.entity.BasicEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "skrivet_menu_template")
public class MenuTemplateTemplate extends BasicEntity {
	@Id
	@Field("ID")
	//主键编号
	private String id;
	@Field("TEMPLATE_NAME")
	//模板名称
	private String templateName;
	@Field("DEFAULT_TEMPLATE")
	//默认模板
	private String defaultTemplate;
	@Field("TEMPLATE_REMARK")
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
	
	public String getDefaultTemplate() 	{
		return this.defaultTemplate;
	}

	public void setDefaultTemplate(String defaultTemplate)  {
		this.defaultTemplate = defaultTemplate;
	}
	
	public String getTemplateRemark() 	{
		return this.templateRemark;
	}

	public void setTemplateRemark(String templateRemark)  {
		this.templateRemark = templateRemark;
	}
}