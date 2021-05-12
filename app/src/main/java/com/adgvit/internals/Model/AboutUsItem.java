package com.adgvit.internals.Model;

public class AboutUsItem {
    String name;
    String linkedin;
    String github;
    String email;
    int image;

    public AboutUsItem(String name, String linkedin, String github, String email, int image) {
        this.name = name;
        this.linkedin = linkedin;
        this.github = github;
        this.email = email;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
