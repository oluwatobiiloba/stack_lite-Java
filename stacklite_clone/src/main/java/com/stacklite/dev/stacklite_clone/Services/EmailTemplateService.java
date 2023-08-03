package com.stacklite.dev.stacklite_clone.Services;

import com.stacklite.dev.stacklite_clone.Model.EmailTemplate;
import com.stacklite.dev.stacklite_clone.Repositories.EmailTemplatesRepo;
import com.stacklite.dev.stacklite_clone.handlers.NotFoundException;
import com.stacklite.dev.stacklite_clone.utils.AzureMailer;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class EmailTemplateService {


    private final EmailTemplatesRepo emailTemplateRepository;


    private final AzureMailer azureMailer;

    public EmailTemplateService(EmailTemplatesRepo emailTemplateRepository, AzureMailer azureMailer) {
        this.emailTemplateRepository = emailTemplateRepository;
        this.azureMailer = azureMailer;
    }

    public void sendEmailWithTemplate(Integer templateId, Map<String, String> constants, String recipient,
            String subject) {
        Optional<EmailTemplate> template = emailTemplateRepository.findById(templateId);
        if (template.isEmpty()) throw new NotFoundException("Email template not found");
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
