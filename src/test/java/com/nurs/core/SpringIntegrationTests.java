package com.nurs.core;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.nurs.core.entity.Mail;
import com.nurs.core.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.MessagingException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class SpringIntegrationTests  {

	@Autowired
	private EmailService emailService;

	@Rule
	public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);



	@Test
	public void shouldSendSingleMail() throws Throwable {
		smtpServerRule.before();
		Mail mail = new Mail();
		mail.setFrom("no-reply@memorynotfound.com");
		mail.setTo("info@memorynotfound.com");
		mail.setSubject("Spring Mail Integration Testing with JUnit and GreenMail Example");
		mail.setContent("We show how to write Integration Tests using Spring and GreenMail.");

		emailService.sendSimpleMessage(mail);

		MimeMessage[] receivedMessages = smtpServerRule.getMessages();
		log.info(Arrays.toString(receivedMessages) + " - messages");
		assertEquals(1, receivedMessages.length);

		MimeMessage current = receivedMessages[0];

		assertEquals(mail.getSubject(), current.getSubject());
		assertEquals(mail.getTo(), current.getAllRecipients()[0].toString());
		assertTrue(String.valueOf(current.getContent()).contains(mail.getContent()));
		smtpServerRule.after();

	}



}
