package tw.gov.bli.common.domain;

import java.io.Serializable;

/**
 * Domain Object for 按鍵<br>
 * 代表使用者於畫面上所能執行的按鍵物件<br>
 * 
 * @author Goston
 */
public class UserButton implements Serializable {
    private String progId; // 頁面程式代號 - 螢幕編號
    private String tagId; // 按鍵編號 - 按鍵名稱

    public UserButton() {
    }

    public String getProgId() {
        return progId;
    }

    public void setProgId(String progId) {
        this.progId = progId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

}
