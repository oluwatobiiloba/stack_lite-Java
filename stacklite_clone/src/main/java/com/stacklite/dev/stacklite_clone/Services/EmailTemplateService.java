package com.stacklite.dev.stacklite_clone.Services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacklite.dev.stacklite_clone.Model.EmailTemplate;
import com.stacklite.dev.stacklite_clone.Repositories.EmailTemplatesRepo;
import com.stacklite.dev.stacklite_clone.utils.AzureMailer;

@Service
public class EmailTemplateService {

    @Autowired
    private final EmailTemplatesRepo emailTemplateRepository;

    @Autowired
    private final AzureMailer azureMailer;

    public EmailTemplateService(EmailTemplatesRepo emailTemplateRepository, AzureMailer azureMailer) {
        this.emailTemplateRepository = emailTemplateRepository;
        this.azureMailer = azureMailer;
    }

    public void sendEmailWithTemplate(Integer templateId, Map<String, String> constants, String recipient,
            String subject) {
        // 1) Find email by template

        Optional<EmailTemplate> template = emailTemplateRepository.findById(templateId);

        if (template == null) {
            return;
        }

        // Convert template content to string
        String content = template.get().getHtmlContent();

        for (Map.Entry<String, String> entry : constants.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            content = content.replace("${" + key + "}", value);
        }

        azureMailer.sendEmail("Stacklite_Admin@2befcba4-7986-41ed-920a-5185024b5538.azurecomm.net", recipient,
                subject, content);

    }
}
