package tw.gov.bli.common.domain;

import java.io.Serializable;

/**
 * Domain Object for 系統功能項目
 * 
 * @author Goston
 */
public class SystemFunction implements Serializable {
    private String itemId; // 系統項目代碼
    private String itemName; // 系統項目名稱
    private String url; // 系統項目 URL & 頁面程式代號
    private String urlDesc; // URL 描述 & 頁面程式描述

    public SystemFunction() {
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlDesc() {
        return urlDesc;
    }

    public void setUrlDesc(String urlDesc) {
        this.urlDesc = urlDesc;
    }

}
