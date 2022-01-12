package com.example.practice.service.impl;

import com.example.practice.entity.Member;
import com.example.practice.mapper.MemberMapper;
import com.example.practice.service.EmailService;
import com.sun.org.apache.xalan.internal.xslt.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;

    public static final Map<String, Object> keyMap = new HashMap<>();


    private MimeMessage createMessage(Map<String, String> map) throws MessagingException{
        MimeMessage  message = emailSender.createMimeMessage();
        String newKey = createKey();
        keyMap.put(map.get("email"), newKey);

        try {
            message.addRecipients(Message.RecipientType.TO, map.get("email"));//보내는 대상
            message.setSubject("KANBOO.GG 인증번호가 도착했습니다.");//제목
        } catch (MessagingException e) {
            throw new MessagingException("message Exception");
        }


        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요  KANBOO.GG입니다!!! </h1>";
        msgg+= "<br>";
        if(map.get("check").equals("sign")){
            msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
            msgg+= "<br>";
            msgg+= "<p>감사합니다!<p>";
            msgg+= "<br>";
            msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
            msgg+= "<h3 style='color:blue;'>회원가입 코드입니다.</h3>";
            msgg+= "<div style='font-size:130%'>";
            msgg+= "CODE : <strong>";
            msgg+= keyMap.get(map.get("email")) + "</strong><div><br/> ";
        } else if(map.get("check").equals("id")){
            Member member = memberMapper.selectMemberByEmail(map.get("email"));
            message.setSubject("KANBOO.GG에서 아이디가 도착했습니다.");

            msgg+= "<p>아이디 찾기<p>";
            msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
            msgg += "<h3 style='color:blue;'>회원 아이디입니다.</h3>";
            msgg+= "<div style='font-size:130%'>";
            msgg+= "아이디 : <strong>";
            msgg+= member.getMemId() + "</strong><div><br/> ";
        } else {
            String memPassword = passwordEncoder.encode(newKey);
            String memId = map.get("id");
            int resultOfUpdatePassword = memberMapper.updatePassword(memPassword, memId);
            int resultOfResetPassword = memberMapper.resetFailCount(memId);
            if( resultOfUpdatePassword != 0 && resultOfResetPassword != 0){
                message.setSubject("KANBOO.GG에서 임시비밀번호가 도착했습니다.");

                msgg+= "<p>임시비밀번호 발급<p>";
                msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
                msgg += "<h3 style='color:blue;'>임시비밀번호 입니다.</h3>";
                msgg+= "<div style='font-size:130%'>";
                msgg+= "임시비밀번호 : <strong>";
                msgg+= newKey + "</strong><div><br/> ";
            } else {
                message.setSubject("메시지 전송 실패");
                msgg += "<p>메세지 전송 실패</p>";
            }
        }
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        try {
            message.setFrom(new InternetAddress("ttky6807@gmail.com","KANBOO.GG"));//보내는 사람
        } catch (UnsupportedEncodingException e) {
            throw new MessagingException("unsupportedException");
        }
        return message;
    }



    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤
            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    @Override
    public void sendSimpleMessage(Map<String, String> map) throws MessagingException {
        MimeMessage message = null;
        try {
            message = createMessage(map);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new MessagingException();
        }
    }

}
