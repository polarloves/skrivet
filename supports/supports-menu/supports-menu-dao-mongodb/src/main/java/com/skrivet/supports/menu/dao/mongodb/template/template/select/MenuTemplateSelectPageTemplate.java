package com.skrivet.supports.menu.dao.mongodb.template.template.select;

import com.skrivet.plugins.database.mongodb.annotations.Query;
import com.skrivet.plugins.database.mongodb.annotations.Sortable;
import com.skrivet.plugins.database.mongodb.enums.MatchStyle;

public class MenuTemplateSelectPageTemplate {
	@Query(MatchStyle.LIKE)
	@Sortable
	//模板名称
	private String templateName;
}