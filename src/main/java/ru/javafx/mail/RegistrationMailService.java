
package ru.javafx.mail;

import java.io.IOException;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.javafx.entity.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.mail.MailException;
import ru.javafx.service.UserService;

@Service
@PropertySources({
	@PropertySource(value="classpath:mail.properties", ignoreResourceNotFound=true),
	@PropertySource(value="classpath:config/mail.properties", ignoreResourceNotFound=true)
})
public class RegistrationMailService {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private UserService userService;
    @Autowired
	private  JavaMailSender  javaMailSender;
    @Autowired
	private  Configuration  freemarkerConfiguration;
    @Autowired
    private HttpServletRequest servletRequest;
    
    @Value("${mail.default-encoding}")   
    private String defaultEncoding;
    
    @Value("${mail.from}")
    private String mailFrom;
    
    @Value("${mail.verification-link}")
    private String verificationLink;    
    
    public void sendHtmlMessage(User user) {
        try {                                  
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, defaultEncoding);           
            helper.setFrom(mailFrom);
			helper.setTo(user.getMail());
            helper.setReplyTo(user.getMail());
			helper.setSubject("Registration");        
			helper.setText(generateContent(user), true);          
			javaMailSender.send(msg); 
            
        } catch (MessagingException e) {
			logger.error("build email failed", e);
		} catch (MailException e) {
			logger.error("send email failed", e);
		}    
    }
    
    /**
	 * Freemarker html.
	 */
	private String generateContent(User user) throws MessagingException {

		try {
            String link = "http://" + servletRequest.getServerName() + ":" + servletRequest.getServerPort() + "/" + verificationLink + "/" + userService.createVerificationToken(user);
            //logger.info("confirmLink: {}", link);
            Map<String, String> context = new HashMap<>();
            context.put("userName", user.getUsername());
            context.put("confirmLink", link);
			Template template = freemarkerConfiguration.getTemplate("mailTemplate.ftl", defaultEncoding);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
            
		} catch (IOException e) {
			logger.error("FreeMarker template not exist", e);
			throw new MessagingException("FreeMarker template not exist", e);
		} catch (TemplateException e) {
			logger.error("FreeMarker process failed", e);
			throw new MessagingException("FreeMarker process failed", e);
		}
	}
    
	public void sendSimpleMessage(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
		mailMessage.setTo(user.getMail());
        mailMessage.setReplyTo(user.getMail());
		mailMessage.setSubject("Registration");
        String link = "http://" + servletRequest.getServerName() + ":" + servletRequest.getServerPort() + "/" + verificationLink + "/" + userService.createVerificationToken(user);
		mailMessage.setText("Hello " + user.getUsername() + 
            "\n Confirm registration: " +  link);
		javaMailSender.send(mailMessage);
	}
    
}
