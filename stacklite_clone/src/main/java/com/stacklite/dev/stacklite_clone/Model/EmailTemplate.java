package com.stacklite.dev.stacklite_clone.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "email_templates")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "html_content", nullable = false)
    private String htmlContent;
}
