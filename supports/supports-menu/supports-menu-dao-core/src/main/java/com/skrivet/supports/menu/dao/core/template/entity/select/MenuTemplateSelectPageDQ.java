package com.skrivet.supports.menu.dao.core.template.entity.select;

import com.skrivet.plugins.database.core.entity.BasicPageDQ;
	

public class MenuTemplateSelectPageDQ extends BasicPageDQ	{
	//模板名称
	private String templateName;
	//默认模板
	private String defaultTemplate;
	
	
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
}