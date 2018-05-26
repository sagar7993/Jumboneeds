package com.jumboneeds.services;

import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Service
public class MailService {
    private static final String TAG = "MailService : ";

    private JavaMailSender javaMailSender;
    private SimpleMailMessage simpleMailMessage;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void buildEmail(String[] to, String from, String replyTo, String subject, String body, String fileUrl, String fileName) {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
        MailService mailService = (MailService) context.getBean("mail");
        mailService.sendEmail(to, from, replyTo, subject, body, fileUrl, fileName);
    }

    private void sendEmail(String[] to, String from, String replyTo, String subject, String body, String fileUrl, String fileName) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setReplyTo(replyTo);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            if(!StringUtils.isEmpty(fileUrl) && !StringUtils.isEmpty(fileName)) {
                FileSystemResource file = new FileSystemResource(fileUrl);
                helper.addAttachment(fileName, file);
            }

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
    }

    public Boolean saveImage(String base64String, String fileName, String extension, String filePath) throws IOException {
        BufferedImage image; byte[] imageByte; BASE64Decoder decoder = new BASE64Decoder();
        imageByte = decoder.decodeBuffer(base64String);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis); bis.close();
        File outputFile = new File(filePath + fileName + "." + extension);

        return ImageIO.write(image, extension, outputFile);
    }

    public void sendStoreZipFile(){
        FileSystemResource file = new FileSystemResource(Constants.STORE_PATH + DateOperations.getDateStringForStore(DateOperations.getTodayStartDate()) + ".zip");

        String[] emailArray = {"tech@jumboneeds.com"};

        buildEmail(emailArray, Constants.FROM_EMAIL, Constants.REPLY_TO_EMAIL, Constants.STORE_PRINTS_SUBJECT + " " + DateOperations.getDateStringForAdmin(DateOperations.getTodayStartDate()), "", file.getPath(), file.getFilename());
    }

}