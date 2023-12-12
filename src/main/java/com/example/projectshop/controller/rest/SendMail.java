package com.example.projectshop.controller.rest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class SendMail {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/send-mail")
    public void sendMail() throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("vuttph25379@fpt.edu.vn", "Shop Gens-z");
        helper.setTo("vutt15032003@gmail.com");

        String subject = "Đây là liên kết để đặt lại mật khẩu của bạn";

        String content = "<p>Xin Chào,<p>" +
                "<p> Bạn đã yêu cầu đặt lại mật khẩu của mình.</p>" +
                "<p> Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn:</p>" +
                "<p><b><a href=" + "https://translate.google.com.vn/?hl=en&sl=en&tl=vi&op=translate" + ">" + "https://translate.google.com.vn/?hl=en&sl=en&tl=vi&op=translate</a><b></p>" +
                "<p> Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn chưa thực hiện yêu cầu";
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
        System.out.println("send mail success");
    }
}
