package com.pe.cardiac.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;

@Controller
public class IndexController {
	

    @Autowired private JavaMailSender mailSender;
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "redirect:/usuario/login";
	}
	
	@RequestMapping(path = "/email", method = RequestMethod.GET)
    public String sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Hello from Spring Boot Application");
        message.setTo("miguel.blas_03@hotmail.com");
        message.setFrom("Cardiac");
        message.setSubject("Prueba");
        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }

}
