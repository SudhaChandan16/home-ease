package com.broomies.entity;

import com.broomies.enums.ProviderCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ProviderCategory category;

    private String bio;

    private Double hourlyRate;

    private Integer experienceYears;

    private Boolean isAvailable = true;

    private String skills; // Comma separated skills

    // Rating could be added here later

    public Provider() {
    }

    public Provider(Long id, User user, ProviderCategory category, String bio, Double hourlyRate,
            Integer experienceYears, Boolean isAvailable, String skills) {
        this.id = id;
        this.user = user;
        this.category = category;
        this.bio = bio;
        this.hourlyRate = hourlyRate;
        this.experienceYears = experienceYears;
        this.isAvailable = isAvailable;
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProviderCategory getCategory() {
        return category;
    }

    public void setCategory(ProviderCategory category) {
        this.category = category;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id +
                ", category=" + category +
                ", bio='" + bio + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", experienceYears=" + experienceYears +
                ", isAvailable=" + isAvailable +
                ", skills='" + skills + '\'' +
                '}';
    }
}
