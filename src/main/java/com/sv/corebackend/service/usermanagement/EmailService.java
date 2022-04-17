package com.sv.corebackend.service.usermanagement;

import com.sv.corebackend.entity.UserCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
//@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class EmailService implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void send(String to, String email) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(email,true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Confirm user creation");
            mimeMessageHelper.setFrom("aes.mail@anwargroup.net");
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
