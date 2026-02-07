package com.broomies.entity;

import com.broomies.enums.ProviderCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    @Enumerated(EnumType.STRING)
    private ProviderCategory category;

    private String bio;

    private Double hourlyRate;

    private Integer experienceYears;

    private Boolean isAvailable = true;

    private String skills; // Comma separated skills

    // Rating could be added here later
}
