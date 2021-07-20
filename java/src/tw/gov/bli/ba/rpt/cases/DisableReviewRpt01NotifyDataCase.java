package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.util.List;

/**
 * Case for 勞保失能年金給付受理編審清單 - 核定通知書
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01NotifyDataCase implements Serializable {
    
    private String subject; // 主旨
    private List<String> content; // 說明
    
    public DisableReviewRpt01NotifyDataCase(){
        
    }
    
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public List<String> getContent() {
        return content;
    }
    public void setContent(List<String> content) {
        this.content = content;
    }
    
    

}
