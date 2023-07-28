package com.stacklite.dev.stacklite_clone.utils;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.PublicAccessType;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;

@Component
public class BlobUploader implements DisposableBean {

    Dotenv dotenv = Dotenv.load();

    private final String blobServiceAccountName = dotenv.get("AZURE_STORAGE_ACCOUNT_NAME");

    private final String blobServiceConnectionString = dotenv.get("AZURE_STORAGE_CONNECTION_STRING");

    private String getBlobServiceConnectionString(){
        return blobServiceConnectionString;
    }

    private String getBlobServiceAccountName(){
        return blobServiceAccountName;
    }

    public static BlobServiceClient GetBlobServiceClientConnectionString() {
        BlobUploader blob = new BlobUploader();

        System.out.println(blob.getBlobServiceAccountName() + " " + blob.blobServiceAccountName);
        return new BlobServiceClientBuilder()
                .endpoint(String.format("https://%s.blob.core.windows.net/",blob.getBlobServiceAccountName()))
                .connectionString(blob.getBlobServiceConnectionString())
                .buildClient();
    }

    public String uploadBlob(String containerName, InputStream data,long length, String fileName, String extension) {
        BlobServiceClient blobServiceClient = GetBlobServiceClientConnectionString();
        BlobContainerClient containerClient = blobServiceClient.createBlobContainerIfNotExists(containerName);
        containerClient.setAccessPolicy(PublicAccessType.BLOB, new ArrayList<>());
        BlobClient blobClient = containerClient.getBlobClient(fileName + extension);
        blobClient.upload(data,length,true);
        return blobClient.getBlobUrl();
    }


    @Override
    public void destroy() {

    }
}
