package com.stacklite.dev.stacklite_clone.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stacklite.dev.stacklite_clone.Model.EmailTemplate;

public interface EmailTemplates extends JpaRepository<EmailTemplate, Integer> {

}
