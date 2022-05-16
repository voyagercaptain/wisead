package kr.wise.commons.handler;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class MailHandler {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 2021-06-16 sohyeonAn 추가
	 * 구글메일서버를 이용할 경우 아래 SMTP_USERNAME과 SMTP_PASSWORD를 변경해주세요.
	 */
	
	/**
	 * 프로퍼티로 빼려면 작업필요
	 * */
	//@Value("#{configure['wiseda.smtp.username']}")     
    private String SMTP_USERNAME = "dq@wise.co.kr";
	
	//@Value("#{configure['wiseda.smtp.password']}")     
	private String SMTP_PASSWORD = "wise1012!";
	
	//@Value("#{configure['wiseda.smtp.host']}")     
	private String HOST = "smtp.gmail.com";
	
	//@Value("#{configure['wiseda.smtp.port']}")     
	private int PORT = 465;
    
    private Properties props;
    private Session session;
    private MimeMessage msg;
    private Transport transport;
	    
    public MailHandler() {
	    this.props = new Properties();
	    props.setProperty("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.port", PORT); 
        props.put("mail.smtp.connectiontimeout", 30*1000);//30초
        props.put("mail.smtp.timeout", 30*1000);  

    	/**
    	 * 2021-06-16 sohyeonAn
    	 * 구글 메일서버를 이용할 경우 아래와 같은 설정이 필요합니다.(테스트용)
    	 * 세팅할 메일서버에 맞게 설정을 바꿔주세요.
    	 */
        // ssl를 사용할 경우 설정합니다.
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");
        // id와 password를 설정하고 session을 생성합니다.
        Session session = Session.getInstance(props, new Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
        	}
        });


        this.session = Session.getInstance(props);
        this.msg = new MimeMessage(session);
    }
    
	public void setSubject(String subject) throws MessagingException{
		msg.setSubject(subject);
	}
	
	public void setText(String htmlContent) throws MessagingException{
		msg.setContent(htmlContent, "text/html;charset=euc-kr");
	}
	
	public void setFrom(String email,String name) throws UnsupportedEncodingException,MessagingException{
		msg.setFrom(new InternetAddress(email, name));
	}
	
	public void setTo(String email) throws MessagingException{
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		
	}
	
	public int send() throws MessagingException{
		this.transport = session.getTransport();
		
		try {
			logger.debug("Sending...");
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            logger.debug("Email sent!");
            
            return 1;
		} catch (MessagingException  e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			logger.error("\nerror : \n{}\n",e.getMessage());
			logger.error("Email not sent!");
			return 0;
		}finally {
            transport.close();
        }
	}
		   
}
	

