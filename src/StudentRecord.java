///////////////////////////////////////////////////////////////////////////////
//
// Title: The StudentRecord class creates a student with a name, email, campusID, and has a boolean
//        containing a boolean for if they completed the prerequisites. Most of the methods revolve
//        around getting and setting these values. 
//
// Course: CS 300 Fall 2023
//
// Author: Remington Reichmann
// Email: rreichmann@wisc.edu
// Lecturer: Mouna Kacem
//
///////////////////////////////////////////////////////////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////
import java.util.zip.DataFormatException;
public class StudentRecord {
  private String campusID;
  private String email;
  private boolean isPreReqSatisfied;
  private String name;
  /**
   * Constructor for a student record object. Assigns values to all fields.
   * 
   * @param name     the name of the student
   * @param email    the email of the student
   * @param campusID the campusID of the student
   * @param preReq   the boolean representing if the student satisfies the prerequisites
   * @throws DataFormatException with message "Bad name, email, or campusID!" if name or email or
   *                             campusID are NOT valid
   */
  public StudentRecord(String name, String email, String campusID, boolean preReq)
      throws DataFormatException {
    this.name = name;
    this.email = email;
    this.campusID = campusID;
    isPreReqSatisfied = preReq;
    
    // Checks to make sure all of the parameters are valid inputs
    if(!isValidName(name) || !isValidEmail(email) || !isValidCampusID(campusID)) {
      throw new DataFormatException("Bad name, email, or campusID!");
    }
  }

  /**
   * Validator method for a student's name
   * 
   * @param name the student's name
   * @return true if and only if the name is not null and not blank
   */
  public static boolean isValidName(String name) {
	return name != null && !name.isBlank();
	
  }

  /**
   * Validator method for a student's email
   * 
   * @param email the student's email
   * @return true if and only if the email is not null, has one @ symbol, ends with .edu, is between
   *         0 and 40 characters (EXCLUSIVE, that is, 0 and 40 are not allowed lengths but 1 and 39
   *         are), and has at least two characters before the @ symbol.
   */
  public static boolean isValidEmail(String email) {
	boolean notNull = email != null; // not null
	boolean containsSymbols = email.contains("@") && email.contains(".edu"); //contains @ and .edu
	boolean correctLength = email.length() > 0 && email.length() < 40; // valid length
	return notNull && containsSymbols && correctLength;
  }

  /**
   * Validator method for a student's id
   * 
   * @param campusID the student's campusID
   * @return true if and only if the campusID is not null and can be parsed to a long with
   *         10-digits. Extra leading and trailing whitespace should be disregarded.
   */
  public static boolean isValidCampusID(String campusID) {
	return campusID != null && campusID.trim().length() == 10;
  }

  /**
   * Getter method for a student's name
   * 
   * @return the student's name
   */
  public String getName() {
	return name;
  }

  /**
   * Getter method for a student's email
   * 
   * @return the student's email
   */
  public String getEmail() {
	return email;
  }

  /**
   * Getter method for a student's campusID
   * 
   * @return the student's campusID
   */
  public String getCampusID() {
	return campusID;
  }

  /**
   * Returns true if this student record satisfies the pre-requisites of the course
   * 
   * @return true if this student record satisfies the pre-requisites of the course
   */
  public boolean isPrerequisiteSatisfied() {
	return isPreReqSatisfied;
  }

  /**
   * Compared this StudentRecord to the specified object
   * 
   * @return true if anObject is instanceof StudentRecord and has the same campusID as this
   *         StudentRecord.
   */
  public boolean equals(Object other) {
    // makes sure inputted object is StudentRecord
	boolean instanceOf = other instanceof StudentRecord;
	
	String[] parts = null;
	boolean sameCampusID = false;
	if(instanceOf) { // makes sure that only StudentRecord objects will return true
	  parts = other.toString().trim().split(", ");
	  sameCampusID = campusID.equals(parts[2].trim());
	}
	return instanceOf && sameCampusID;
  }


  /**
   * Returns a string representation of this student record in the following format (comma followed
   * by a space ", " separated): <BR>
   * name, email, campusID, preReqValue
   * 
   * @return the string representation of a studentRecord as described above
   */
  @Override
  public String toString() {
	return name + ", " + email + ", " + campusID + ", " + isPreReqSatisfied;
  }
}
