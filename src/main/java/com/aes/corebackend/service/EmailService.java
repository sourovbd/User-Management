package com.aes.corebackend.service;

import com.aes.corebackend.entity.UserCredential;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@NoArgsConstructor
public class EmailService implements EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    @Override
    public void send(String to, String email) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(email,true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Confirm user creation");
            mimeMessageHelper.setFrom("mdahad118@gmail.com");
            javaMailSender.send(mimeMessage);


        }catch (MessagingException e) {

        }
    }

    @Override
    public String buildEmailText(UserCredential userCredential) {
        String messagebody = "Your new password is: " + userCredential.getPassword() + ".";
        return messagebody;
    }
}
