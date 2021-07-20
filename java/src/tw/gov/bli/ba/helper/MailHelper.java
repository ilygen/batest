package tw.gov.bli.ba.helper;

import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import tw.gov.bli.ba.util.DateUtility;


public class MailHelper {
    private static Log log = LogFactory.getLog(MailHelper.class);

    private static final String MAIL_ENCODING = "UTF-8";
    private static final String MAIL_HOST = "172.16.2.30";
    private static final String MAIL_FROM = "noreply@ms.bli.gov.tw" ;
    private VelocityEngine velocityEngine;

    
    /**
     * 發送勞保年金-產製媒體檔作業結果信
     * 
     * @param payCode
     * @param chkDate
     * @param result
     * @param email
     */
    public void sendMediaSendMail(final String payCode, final String chkDate, final String issuYm, final String email) {
    	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    	mailSender.setHost(PropertyHelper.getProperty("mailSender.host"));
    	mailSender.setDefaultEncoding(MAIL_ENCODING);
    	MimeMessagePreparator preparator = new MimeMessagePreparator() {
        public void prepare(MimeMessage mimeMessage) throws Exception {
                String subject = "&勞保年金線上產製媒體完成通知&";
                String payCodeString = "";
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setTo(email);
                message.setFrom(new InternetAddress(MAIL_FROM, "noreply", MAIL_ENCODING));
                message.setSubject(subject);
                if(StringUtils.equals(payCode, "L")){
                	payCodeString = "老年";
                } else if(StringUtils.equals(payCode, "S")){
                	payCodeString = "遺屬";
                } else {
                	payCodeString = "失能";
                }
                Map<String, String> map = new HashMap<String, String>();
                
                map.put("payCode", payCodeString);
                if(StringUtils.isNotBlank(chkDate)){
                	map.put("chkDate", DateUtility.changeDateType(chkDate));
                }
                if(StringUtils.isNotBlank(issuYm)){
                    map.put("issuYm",  DateUtility.changeWestYearMonthType(issuYm));
                }
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "tw/gov/bli/ba/template/produceMediaOnlineMail.vm", MAIL_ENCODING, map);
                message.setText(text, true);
        	}
        };
        mailSender.send(preparator);
    }
    
    /**
     * 發送勞保年金 - 線上月核定
     * 
     * @param payCode
     * @param chkDate
     * @param result
     * @param email
     */
    public void sendMonthMail(final String payCode, final String issuYm, final String chkDate, final String email) {
    	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    	mailSender.setHost(MAIL_HOST);
    	mailSender.setDefaultEncoding(MAIL_ENCODING);
    	MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                String subject = "&勞保年金線上月核定完成通知&";
                
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setTo(email);
                message.setFrom(new InternetAddress(MAIL_FROM, "noreply", MAIL_ENCODING));
                message.setSubject(subject);
                Map<String, String> map = new HashMap<String, String>();
                
                map.put("payCode", payCode);
                if(StringUtils.isNotBlank(issuYm)){
                	map.put("issuYm", issuYm);	
                }
                map.put("chkDate", chkDate);
                
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "tw/gov/bli/ba/template/produceMonthOnlineMail.vm", MAIL_ENCODING, map);
                message.setText(text, true);

            }
        };
        mailSender.send(preparator);
    }
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

}
