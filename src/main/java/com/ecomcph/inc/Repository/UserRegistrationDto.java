package com.ecomcph.inc.Repository;


//Denne klasse bliver brugt til at overføre dataen igennem et objekt.
//Derfor navnet DTO - Data Transfer Object

public class UserRegistrationDto {
    // Opretter attributer til en User
    private String firstName;
    private String lastName;
    private String email;
    private String password;


    //Vi har en tom constructor, fordi at vi har ikke behov for at indsætte værdier.
    //Vi bruger ModelAttribute, som automatisk binder værdierne sammen med de Attributer vi har inde i denne klasse.
    public UserRegistrationDto(){

    }

    //Getters and setters
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}