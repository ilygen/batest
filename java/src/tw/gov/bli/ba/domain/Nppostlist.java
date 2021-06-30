package tw.gov.bli.ba.domain;

import java.io.Serializable;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 郵局代碼檔
 * 
 * @author Rickychi
 */

@Table("NPPOSTLIST")
public class Nppostlist implements Serializable {
    @PkeyField("POSTID")
    private String postId;// 郵局電腦代號
    private String postName;// 郵局名稱
    private String postBranch;// 郵局支局名稱

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostBranch() {
        return postBranch;
    }

    public void setPostBranch(String postBranch) {
        this.postBranch = postBranch;
    }
}
