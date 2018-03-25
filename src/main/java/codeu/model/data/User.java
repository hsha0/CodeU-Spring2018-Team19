// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/** Class representing a registered user. */
public class User {
    private final UUID id;
    private final String name;
    private final String password;
    private final Instant creation;
    private final Integer Age;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNum;
    private final String bio;

    /**
     * Constructs a new User.
     *
     * @param id
     *            the ID of this User
     * @param name
     *            the username of this User
     * @param password
     *            the password of this User
     * @param creation
     *            the creation time of this User
     */
    public User(UUID id, String name, String password, Instant creation) {
	this.id = id;
	this.name = name;
	this.password = password;
	this.creation = creation;
	
	/** Initializes other information for profile pages. */
	this.Age = 0;
	this.firstName = "First name";
	this.lastName = "Last name";
	this.email = "no-reply@example.com";
	this.phoneNum = "123-456-7890";
	this.bio = "Hello World!";
    }

    /** Returns the ID of this User. */
    public UUID getId() {
	return id;
    }

    /** Returns the username of this User. */
    public String getName() {
	return name;
    }

    /** Returns the password of this User. */
    public String getPassword() {
	return password;
    }

    /** Returns the creation time of this User. */
    public Instant getCreationTime() {
	return creation;
    }
    
    /** Returns the age of this User. */
    public Integer getAge() {
	return Age;
    }
    
    /** Returns the first name of this User. */
    public String getFirstName() {
	return firstName;
    }

    /** Returns the last name of this User. */
    public String getLastName() {
	return lastName;
    }

    /** Returns the email of this User. */
    public String getEmail() {
	return email;
    }

    /** Returns the phone number of this User. */
    public String getPhoneNum() {
	return phoneNum;
    }
    
    /** Returns the bio of this User. */
    public String getBio() {
	return bio;
    }
}
