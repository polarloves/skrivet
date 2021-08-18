package com.skrivet.supports.menu.web.core.entity.item.add;

import com.skrivet.core.common.entity.BasicEntity;

import java.util.List;

public class TemplateItemAddVQ extends BasicEntity {
    private String templateId;
    private List<TemplateItem> items;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<TemplateItem> getItems() {
        return items;
    }

    public void setItems(List<TemplateItem> items) {
        this.items = items;
    }

    public static class TemplateItem extends BasicEntity {
        private String id;
        private String itemId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }
    }

}