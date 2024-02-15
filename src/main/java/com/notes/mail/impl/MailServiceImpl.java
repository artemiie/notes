package com.notes.mail.impl;

import static com.notes.mail.MailTemplateConstants.*;

import com.notes.mail.MailService;
import com.notes.mail.MailType;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

  private final Configuration configuration;
  private final JavaMailSender mailSender;

  @Override
  @SneakyThrows
  public void sendEmail(final MailType type, final Properties properties) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    switch (type) {
      case LOGIN ->
          buildEmail(mimeMessage, properties, LOGIN_TEMPLATE, LOGIN_SUBJECT, "recipientName");
      case ACTIVATION ->
          buildEmail(
              mimeMessage,
              properties,
              ACTIVATION_TEMPLATE,
              ACTIVATION_SUBJECT,
              "recipientName",
              "activationToken");
      case RESTORE ->
          buildEmail(
              mimeMessage,
              properties,
              RESTORE_TEMPLATE,
              RESTORE_SUBJECT,
              "recipientName",
              "restoreToken");
    }

    mailSender.send(mimeMessage);
  }

  @SneakyThrows
  private void buildEmail(
      final MimeMessage mimeMessage,
      final Properties properties,
      String templateName,
      String subjectName,
      String... params) {

    Map<String, String> model = buildDataModel(properties, params);

    StringWriter writer = new StringWriter();
    configuration.getTemplate(templateName).process(model, writer);

    String emailContext = writer.getBuffer().toString();

    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
    helper.setSubject(subjectName);
    helper.setText(emailContext, true);
    helper.setTo(properties.getProperty("recipientEmail"));
  }

  private Map<String, String> buildDataModel(Properties properties, String... params) {
    Map<String, String> dataModel = new HashMap<>();
    for (String param : params) {
      dataModel.put(param, properties.getProperty(param));
    }
    return dataModel;
  }
}
