package com.example.alcar.callyourmother;

/**
 * Created by abhasarya on 11/22/17.
 */

public class ContactModel {
    private String ID, firstName, lastName, phoneNumber, priority;

    public ContactModel(String firstName, String lastName, String phoneNumber, String priority) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.priority = priority;
    }

    public ContactModel(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.priority = null;
    }

    public ContactModel() {}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String toString() {
        return this.firstName + " " + this.lastName + " " + this.phoneNumber;
    }

    public boolean equals(ContactModel otherContact) {
        return this.firstName.equals(otherContact.firstName) &&
                this.lastName.equals(otherContact.lastName) &&
                this.phoneNumber.equals(otherContact.phoneNumber) &&
                this.priority.equals(otherContact.priority);
    }
}

