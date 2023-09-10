package com.stacklite.dev.stacklite_clone.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "blob_cobtainers")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlobContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ref", nullable = false)
    private String ref;

    @Column(name = "url", nullable = false)
    private String url;
}
