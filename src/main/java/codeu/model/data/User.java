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

import java.time.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class representing a registered user.
 */
public class User {
  private final UUID id;
  private final String name;
  private final String password;
  private final Instant creation;
  private final Integer age;
  private final String firstName;
  private final String lastName;
  private final String email;
  private final String phoneNum;
  private final String bio;
  private final String pictureURL;
  private final boolean superUser;
  private Integer rateLimit;
  private Integer messageCount;
  private Timer dailyResetTimer;

  /**
   * Constructs a new User.
   *
   * @param newId       the ID of this User
   * @param newName     the username of this User
   * @param newPassword the password of this User
   * @param newCreation the creation time of this User
   */
  public User(UUID newId, String newName, String newPassword, Instant newCreation) {

    this.id = newId;
    this.name = newName;
    this.password = newPassword;
    this.creation = newCreation;
    this.age = 0;
    this.firstName = "";
    this.lastName = "";
    this.email = "";
    this.phoneNum = "";
    this.bio = "";
    this.pictureURL = "";
    this.superUser = false;
    this.rateLimit = null; //for clarity
    this.messageCount = 0;
    scheduleMessageCountMidnightReset();
  }

  /**
   * Constructs a new User.
   *
   * @param newId         the ID of this User
   * @param newName       the username of this User
   * @param newPassword   the password of this User
   * @param newCreation   the creation time of this User
   * @param newAge        the age of this User
   * @param newFirstName  the first name of this User
   * @param newLastName   the last name of this User
   * @param newEmail      the email of this User
   * @param newPhoneNum   the phone number of this User
   * @param newBio        the bio of this User
   * @param newPictureURL the URL of the profile picture of this User
   * @param rateLimit     the rateLimit of the user
   */
  private User(UUID newId, String newName, String newPassword, Instant newCreation, Integer newAge, String newFirstName,
               String newLastName, String newEmail, String newPhoneNum, String newBio, String newPictureURL, boolean newSuperUser, Integer rateLimit, Integer messageCount) {
    this.id = newId;
    this.name = newName;
    this.password = newPassword;
    this.creation = newCreation;
    this.age = newAge;
    this.firstName = newFirstName;
    this.lastName = newLastName;
    this.email = newEmail;
    this.phoneNum = newPhoneNum;
    this.bio = newBio;
    this.pictureURL = newPictureURL;
    this.superUser = newSuperUser;
  }

  public static class Builder {
    private UUID nestedId;
    private String nestedName;
    private String nestedPassword;
    private Instant nestedCreation;
    private Integer nestedAge;
    private String nestedFirstName;
    private String nestedLastName;
    private String nestedEmail;
    private String nestedPhoneNum;
    private String nestedBio;
    private String nestedPictureURL;
    private boolean nestedSuperUser;
    private Integer nestedRateLimit;
    private Integer nestedMessageCount;


    public Builder(final UUID newId, final String newName, final String newPassword, final Instant newCreation) {
      this.nestedId = newId;
      this.nestedName = newName;
      this.nestedPassword = newPassword;
      this.nestedCreation = newCreation;
      this.nestedRateLimit = null;
      this.nestedMessageCount = 0;
    }

    public Builder setId(UUID newId) {
      this.nestedId = newId;
      return this;
    }

    public Builder setName(String newName) {
      this.nestedName = newName;
      return this;
    }

    public Builder setPassword(String newPassword) {
      this.nestedPassword = newPassword;
      return this;
    }

    public Builder setCreation(Instant newCreation) {
      this.nestedCreation = newCreation;
      return this;
    }

    public Builder setAge(Integer newAge) {
      this.nestedAge = newAge;
      return this;
    }

    public Builder setFirstName(String newFirstName) {
      this.nestedFirstName = newFirstName;
      return this;
    }

    public Builder setLastName(String newLastName) {
      this.nestedLastName = newLastName;
      return this;
    }

    public Builder setEmail(String newEmail) {
      this.nestedEmail = newEmail;
      return this;
    }

    public Builder setPhoneNum(String newPhoneNum) {
      this.nestedPhoneNum = newPhoneNum;
      return this;
    }

    public Builder setBio(String newBio) {
      this.nestedBio = newBio;
      return this;
    }

    public Builder setPictureURL(String newPictureURL) {
      this.nestedPictureURL = newPictureURL;
      return this;
    }

    public Builder setSuperUser(boolean newSuperUser) {
      this.nestedSuperUser = newSuperUser;
      return this;
    }

    public Builder setRateLimit(Integer nestedRateLimit) {
      this.nestedRateLimit = nestedRateLimit;
      return this;
    }

    public Builder setMessageCount(Integer nestedMessageCount) {
      this.nestedMessageCount = nestedMessageCount;
      return this;
    }

    public User createUser() {
      return new User(nestedId, nestedName, nestedPassword, nestedCreation, nestedAge, nestedFirstName, nestedLastName,
              nestedEmail, nestedPhoneNum, nestedBio, nestedPictureURL, nestedSuperUser, nestedRateLimit, nestedMessageCount);
    }
  }

  /**
   * Returns the ID of this User.
   */
  public UUID getId() {
    return id;
  }

  /**
   * Returns the username of this User.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the password of this User.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Returns the creation time of this User.
   */
  public Instant getCreationTime() {
    return creation;
  }

  /**
   * Returns the age of this User.
   */
  public Integer getAge() {
    return age;
  }

  /**
   * Returns the first name of this User.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Returns the last name of this User.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Returns the email of this User.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns the phone number of this User.
   */
  public String getPhoneNum() {
    return phoneNum;
  }

  /**
   * Returns the bio of this User.
   */
  public String getBio() {
    return bio;
  }

  /**
   * Returns the URL of the profile picture of this User.
   */
  public String getPictureURL() {
    return pictureURL;
  }

  /**
   * Returns the rate limit of this User.
   */
  public Integer getRateLimit() {
    return rateLimit;
  }

  /**
   * Returns the rate limit of this User.
   */
  public Integer getMessageCount() {
    return messageCount;
  }

  /**
   * Returns true if this user is superuser, false otherwise
   */
  public boolean isSuperUser() {
    return superUser;
  }

  /**
   * Sets a rate limit for the user
   */
  public void setRateLimit(Integer rate) {
    this.rateLimit = rate;
  }

  /**
   * Schedule a reset of messages to be done at midnight
   */
  private void scheduleMessageCountMidnightReset(){
    // Time on computer
    LocalDateTime localNow = LocalDateTime.now();
    ZoneId currentZone = ZoneId.of("America/New_York");

    // Compiled time of computer time and zone
    ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);

    // GetMidNight Time
    ZonedDateTime zDateTimeMidnight = zonedNow.withHour(0).withMinute(0).withSecond(0);

    // If midnight occurs on next day (almost always will), add day to time
    if(zonedNow.compareTo(zDateTimeMidnight) > 0)
      zDateTimeMidnight = zDateTimeMidnight.plusDays(1);

    // Time between now and midnight
    Duration duration = Duration.between(zonedNow, zDateTimeMidnight);
    long initalDelay = duration.getSeconds();

    // Not sure about if TimerTask and ScheduledExecutorService should be doing the scheduling job here
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(new TimerTask() {
                                    @Override
                                    public void run() {
                                      resetMessageCount();
                                    }
                                  }, initalDelay,
            24 * 60 * 60, TimeUnit.SECONDS);
  }

  /**
   * Removes any rate limit on the user
   */
  public void removeRateLimit() {
    this.rateLimit = null;
    //Consider resetting the message count after this operation
  }

  /**
   * Returns true if the user is able to send a message, false otherwise
   */
  public boolean canSendMessage() {
    return (rateLimit == null || messageCount < rateLimit);
  }

  /**
   * Increments the message count counter
   */
  public void incrementMessageCount() {
    messageCount++;
  }

  /**
   * Resets message count of the user
   */
  public void resetMessageCount() {
    messageCount = 0;
  }
}
