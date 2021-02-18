package com.adgvit.internals;

public class aboutusitem {
    String name;
    String linkedin;
    String github;
    String email;
    Integer image;
    public aboutusitem(Integer image, String name, String linkedin, String github, String email) {
        this.image = image;
        this.name = name;
        this.linkedin = linkedin;
        this.github = github;
        this.email = email;
    }

    public Integer getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public String getGithub() {
        return github;
    }

    public String getEmail() {
        return email;
    }
}
