package com.stacklite.dev.stacklite_clone.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class AzureMailer implements DisposableBean {

    Dotenv dotenv = Dotenv.load();
    private String comServiceConnectionString = dotenv.get("COMMUNICATION_SERVICES_CONNECTION_STRING");

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getConnectionString() {
        return comServiceConnectionString;
    }

    public EmailClient createClient() {
        AzureMailer az = new AzureMailer();
        String connectionStr = az.getConnectionString();
        return new EmailClientBuilder()
                .connectionString(connectionStr)
                .buildClient();
    }

    @Async
    public void sendEmail(String senderAddress, String recipientAddress, String subject, String body) {
        EmailClient emailClient = createClient();
        EmailMessage message = new EmailMessage();
        message.setSenderAddress(senderAddress)
                .setToRecipients(recipientAddress)
                .setSubject(subject).setBodyHtml(body);

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(message);
        PollResponse<EmailSendResult> response = poller.waitForCompletion();

        System.out.println("Operation Id: " + response.getValue().getId());
        logger.info("Operation Id: " + response.getValue().getId());
    }

    @Override
    public void destroy() throws Exception {

    }
}
