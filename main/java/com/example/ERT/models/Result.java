package com.example.ERT.models;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "results")
@Data
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ElementCollection
    @CollectionTable(name = "result_emotions_df", joinColumns = @JoinColumn(name = "result_id"))
    @Column(name = "emotionDf")
    private List<String> emotionsDf = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "result_values_df", joinColumns = @JoinColumn(name = "result_id"))
    @Column(name = "valuesDf")
    private List<Double> valuesDf = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "result_emotions_ml", joinColumns = @JoinColumn(name = "result_id"))
    @Column(name = "emotionsMl")
    private List<String> emotionsMl = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "result_values_ml", joinColumns = @JoinColumn(name = "result_id"))
    @Column(name = "valuesMl")
    private List<Double> valuesMl = new ArrayList<>();

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    private LocalDateTime dateOfCreated;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Result() {
    }

    public Result(Long id, List<String> emotionsDf, List<Double> valuesDf, List<String> emotionsMl, List<Double> valuesMl, String title, String description, String image, LocalDateTime dateOfCreated, User user) {
        this.id = id;
        this.emotionsDf = emotionsDf;
        this.valuesDf = valuesDf;
        this.emotionsMl = emotionsMl;
        this.valuesMl = valuesMl;
        this.title = title;
        this.description = description;
        this.image = image;
        this.dateOfCreated = dateOfCreated;
        this.user = user;
    }


    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }


    public String getImage() {
        return this.image;
    }

    public LocalDateTime getDateOfCreated() {
        return this.dateOfCreated;
    }

    public User getUser() {
        return this.user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    public void setUser(User user) {
        this.user = user;
    }


    protected boolean canEqual(final Object other) {
        return other instanceof Result;
    }

}

