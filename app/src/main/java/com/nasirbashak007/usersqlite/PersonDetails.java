package com.nasirbashak007.usersqlite;

/**
 * Created by Nasir Basha K on 17-01-2018.
 */

public class PersonDetails {

    int id;
    String name;
    String email;
    byte[] imageUri;

    public PersonDetails(int id, String name, String email, byte[] imageUri) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUri = imageUri;
    }

    //only while retrival
    public PersonDetails() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageUri(byte[] imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImageUri() {
        return imageUri;
    }


}
