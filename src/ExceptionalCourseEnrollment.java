///////////////////////////////////////////////////////////////////////////////
//
// Title: The ExceptionalCourseEnrollment class creates a course with a roster and a waitlist. The 
//        user is able to add StudentRecord(s) to the waitlist and roster and save these lists. They
//        are also able to expand the roster, search for elements in the lists and reference these
//        values.
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
// Online Sources: https://docs.oracle.com/javase/8/docs/api/java/io/PrintWriter.html 
//                 -Referenced when creating saveRoster() method
//
///////////////////////////////////////////////////////////////////////////////
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;
import java.util.Scanner;

public class ExceptionalCourseEnrollment {
  /** Course name */
  private String courseName;
  /** Arraylist storing the records of students enrolled in this course */
  private ArrayList<StudentRecord> roster;
  /** enrollment capacity of this course enrollment */
  private int enrollmentCapacity;
  /** Arraylist storing records of students in the waitlist (not yet enrolled in the course) */
  private ArrayList<StudentRecord> waitlist;
  /** waitlist capacity */
  private int waitlistCapacity;


  /**
   * Constructor for ExceptionalCourseEnrollment. Initializes all the fields with the corresponding
   * inputs. The roster and waitlist arraylists must be empty.
   * 
   * @param courseName         the name of the course
   * @param enrollmentCapacity the capacity of the course roster (INCLUSIVE, between 15 and 250. That
   *                           is, 15 and 250 are allowed but 14 and 251 arent)
   * @param waitlistCapacity   the capacity of the waitlist (must be GREATER than 0 and LESS OR
   *                           EQUAL TO than the enrollmentCapacity)
   * @throws IllegalArgumentException with message "Course name must not be blank or empty" if
   *                                  course name is blank or empty
   * @throws IllegalArgumentException with message "Enrollment capacity must be between 15 and 250!"
   *                                  if enrollment capacity is not between 15 and 250, inclusive
   * @throws IllegalArgumentException with message "Waitlist capacity must be between 0 and
   *                                  enrollment capacity!" if waitlistCapacity is larger than
   *                                  enrollmentCapacity or less than zero
   */
  public ExceptionalCourseEnrollment(String courseName, int enrollmentCapacity,
      int waitlistCapacity) {
    // initialize the array lists for roster and waitlist
    roster = new ArrayList<StudentRecord>();
    waitlist = new ArrayList<StudentRecord>();
    
	// Setting the courseName 
    this.courseName = courseName;
    if(courseName == null || courseName.isBlank()) {
      throw new IllegalArgumentException("Course name must not be blank or empty");
    }
    
    // Setting the enrollmentCapacity
    this.enrollmentCapacity = enrollmentCapacity;
    if(enrollmentCapacity < 0 || enrollmentCapacity > 250) {
      throw new IllegalArgumentException("Enrollment capacity must be between 0 and 250!");
    }
    
    // Setting the waitlistCapacity
    this.waitlistCapacity = waitlistCapacity;
    if(waitlistCapacity <= 0 || waitlistCapacity > enrollmentCapacity) {
      throw new IllegalArgumentException("Waitlist capacity must be between 0 and enrollment capacity!");
    }
  }
    

  /**
   * Checks if the roster is full.
   *
   * @return true if the size of the roster is equal to the enrollment capacity, false otherwise.
   */
  public boolean isRosterFull() {
    if(roster != null) {
      return roster.size() == enrollmentCapacity;
    }
    return false;
  }

  /**
   * Checks if the waitlist is full.
   *
   * @return true if the size of the waitlist is equal to the waitlist capacity, false otherwise.
   */
  public boolean isWaitlistFull() {
    if(waitlist != null) {
      return waitlist.size() == waitlistCapacity;
    }
    return false;
  }

  /**
   * Checks if the course enrollment is closed. A course enrollment is considered closed if both the
   * roster and the waitlist are full.
   *
   * @return true if both the roster and the waitlist are full, false otherwise.
   */
  public boolean isCourseEnrollmentClosed() {
	  return isRosterFull() && isWaitlistFull();
  }

  /**
   * Getter for course name
   * 
   * @return string the name of the course
   */
  public String getName() {
	  return courseName;
  }

  /**
   * Returns a deep copy (NOT the deepest) of this course enrollment's roster
   * 
   * @return a deep copy of the roster, and null if roster is null
   */
  public ArrayList<StudentRecord> deepCopyRoster() {
	  if(roster == null) {
	    return null;
	  }
	  ArrayList<StudentRecord> returnList = new ArrayList<StudentRecord>();
	  for(int i = 0; i < roster.size(); i++) { // Looping through the roster
	    if(roster.get(i) != null) {
	      returnList.add(roster.get(i));
	    }
	  }
	  return returnList;
  }

  /**
   * Returns a deep copy (NOT the deepest) of this course enrollment's waitlist
   * 
   * @return a deep copy of the waitlist, and null if waitlist is null
   */
  public ArrayList<StudentRecord> deepCopyWaitlist() {
    if(waitlist == null) {
      return null;
    }
    ArrayList<StudentRecord> returnList = new ArrayList<StudentRecord>();
    for(int i = 0; i < waitlist.size(); i++) { // Looping through waitlist
      if(waitlist.get(i) != null) {
        returnList.add(waitlist.get(i));
      }
    }
    return returnList; 
  }

  /**
   * Expands the enrollment capacity of the course by the increase amount. Does not affect the
   * waitlist at all.
   * 
   * @param increase the non-negative amount to increase the capacity by
   * @throws IllegalArgumentException with message "Increase amount must be greater than zero!" if
   *                                  increase is not larger than zero
   */
  public void expandEnrollmentCapacity(int increase) throws IllegalArgumentException {
	  try {
	    // User cannot increase enrollment capacity by a negative or zero amount
	    if(increase <= 0) {
	      throw new IllegalArgumentException("Increase amount must be greater than zero!");
	    }
	  }
	  catch(IllegalArgumentException e) {
	    System.out.println(e.getMessage());
	  }
	  if(increase > 0) {
	    enrollmentCapacity += increase;
	  }
   
  }



  //PROVIDED METHOD
  /**
   * Prints the list of all the students in the waitlist of the course, with respect to the
   * following format.
   * 
   * Waitlist capacity: waitlist_capacity<BR>
   * 1. student1's string representation<BR>
   * 2. student2's string representation <BR>
   * 
   * Every entry must be in a newline. Each of the students records is printed in the format:
   * "order. name, email, campusID<BR>
   * 
   * where order starts at 1 for the student stored at index 0, name, email, and campusID represent
   * the name, email address, and campusID of the waitlisted student.
   * 
   * We assume all inputs are valid. If the waitlist is empty, you must print the capacity followed
   * by "The waitlist is empty." on a newline.
   */
  public void printWaitlist() {

    System.out.println("Waitlist capacity: " + this.waitlistCapacity);
    if (this.waitlist.isEmpty()) {
      System.out.println("The waitlist is empty.");
    } else {
      for (int i = 0; i < this.waitlist.size(); i++) {
        String waitlistString = (i + 1) + ". " + this.waitlist.get(i) + "\n";
        System.out.println(waitlistString);
      }
    }

  }


  /**
   * Returns the student record object that has an exact match with campusID in the list passed as
   * input. We assume that campusID values are unique.
   * 
   * @param campusID a string representing the campusID of a student.
   * @param list     an ArrayList of StudentRecords
   * @return StudentRecord record in list with an exact match with campusID.
   * @throws NoSuchElementException with message "No student record found!" if no match found in the
   *                                input list or if campusID is NOT valid.
   * 
   */
  public static StudentRecord searchById(String campusID, ArrayList<StudentRecord> list) {
	for(int i = 0; i < list.size(); i++) { // Looping through inputed list
      if(list.get(i).getCampusID() == campusID) {
  	    return list.get(i);
  	  }
  	}
	throw new NoSuchElementException("No student record found!"); // thrown if no campudID is found
  }

  /**
   * Appends (adds to the end) the student record to the waitlist if the waitlist has space, the
   * student isn't already on the waitlist, isn't already enrolled in the course, and they meet the
   * preReqs.
   * Prints student.getName() + " was successfully added to the waitlist." if successful
   * Throws exceptions described below.
   * 
   * @param student valid StudentRecord of student to be added
   * @throws IllegalArgumentException if the student is already on the waitlist with message "That
   *                                  student is already on the waitlist!"
   * @throws IllegalArgumentException if the student is already enrolled in the course with message
   *                                  "That student is already enrolled!"
   * @throws IllegalStateException    if the waitlist is full with the message "The waitlist is
   *                                  full!"
   * @throws IllegalStateException    if the student does not have satisfactory prerequisites with
   *                                  message "The prerequisities are not satisfied for that
   *                                  course!"
   */
  public void addWaitlist(StudentRecord student) {
    if(waitlist.contains(student)) {
      throw new IllegalArgumentException("That student is already on the waitlist!");
    }
    if(roster.contains(student)) {
      throw new IllegalArgumentException("That student is already enrolled!");
    }
    if(isWaitlistFull()) {
      throw new IllegalStateException("The waitlist is full!");
    }
    if(!student.isPrerequisiteSatisfied()) {
      throw new IllegalStateException("The prerequisites are not satisfied for that course!");
    }
    waitlist.add(student);
  }


  /**
   * Enrolls one student given their StudentRecord. Only enrolls the student if the following<br>
   * conditions are met, otherwise throws an appropriate error described below: <br>
   * - student is not already enrolled in the course <br>
   * - the course has space <br>
   * - the student has satisfied the prerequisities<br>
   * Prints student.getName() + " was successfully enrolled in this class." if the enrollment was
   * successful. <br>
   * Removes the student from the waitlist if they were on it.<br>
   * 
   * @param student StudentRecord for the student to add
   * @throws IllegalStateException with message "That student is already enrolled!" is the student
   *                               is already enrolled
   * @throws IllegalStateException with message "The course is full." if the course is full. The
   *                               course is considered full when the roster's size equals the
   *                               enrollment capacity.
   * @throws IllegalStateException with message "That student has not satisfied the prerequisites!"
   *                               if student does not have the appropriate prerequisities
   */
  public void enrollOneStudent(StudentRecord student) {
    if(roster != null && student != null && roster.contains(student)) {
      throw new IllegalStateException("That student is already enrolled!");
    }
    if(isRosterFull()) {
      throw new IllegalStateException("The course is full.");
    }
    if(!student.isPrerequisiteSatisfied()) {
      throw new IllegalStateException("That student has not satisfied the prerequisites!");
    }
    if(roster != null) {
      roster.add(student);
    }
    if(waitlist != null && waitlist.contains(student)) {
      waitlist.remove(student);
    }
    System.out.println(student.getName() + " was successfully enrolled in this class.");
  }



  /**
   * Removes a student from the roster based on a matching campusID
   * 
   * @param student the student's StudentRecord
   * @throws NoSuchElementException with message "There is no matching student in the roster!" if
   *                                the student is not in the roster
   */
  public void dropCourse(StudentRecord student) {
    // Make sure the student exists in the roster
    if(!roster.contains(student)) {
      throw new NoSuchElementException("There is no matching student in the roster!");
    }
    roster.remove(student);
  }


  // PROVIDED Method
  /**
   * Returns a String representation of this exceptional course enrollment The string should be of
   * the form: <BR>
   * Course Name: courseName<BR>
   * Number of enrolled students: number of enrolled students<BR>
   * 1. name, email, campusID, preReq <BR>
   * 2. name, email, campusID, preReq <BR>
   * ...<BR>
   * 
   * Every entry must be in a newline. Each of the students records is printed in the format:
   * "order. name, email, campusID, preReq"
   * 
   * where order represents index+1 of the student records in roster (orders are in the range
   * 1..size and NOT in the range 0..size-1), name, email, and campusID represent the name, email
   * address, and campusID of the enrolled student.
   * 
   * @return a String representation of this exceptional course enrollment
   */
//TODO Uncomment the below methods out after declaring the data fields
  @Override
  public String toString() {
    // Provided to students
    String rosterString = "";
    rosterString = rosterString + "Course Name: " + this.courseName + "\n";
    rosterString = rosterString + "Number of enrolled students: " + this.roster.size() + "\n";
    for (int i = 0; i < this.roster.size(); i++) {
      String studentString = this.roster.get(i).toString();
      rosterString = rosterString + (i + 1) + ". " + studentString + "\n";
    }
    return rosterString.trim();
  }

  //PROVIDED METHOD
  /**
   * Returns a string of the roster of the course, with the string representation of each
   * StudentRecord stored in the ArrayList roster in a separate line.
   * 
   * @return String representing the roster to the above specifications
   */
  public String rosterToString() {
    String rosterString = "";
    for (int i = 0; i < this.roster.size(); i++) { // Looping through roster
      rosterString += this.roster.get(i) + "\n";
    }
    return rosterString.trim();

  }

  /**
   * Saves the string representation of the roster to a file passed as input. Does this by calling
   * the rosterToString() method and writing the string to a file.
   * 
   * You can use a PrintWriter or a FileWriter to do this.
   * 
   * Catches and prints the message associated with any IOException that might be thrown.
   * 
   * @param file the path of the output file
   */
  public void saveRoster(File file) throws IOException{
    // Create a printwriter to write the file
    
    PrintWriter printwriter = null;
    try {
      // Make sure the file exists
      printwriter = new PrintWriter(file);
      
    }
    catch(IOException e) {
      System.out.println(e.getMessage());
    }
    
    // Prints all of the students in the roster to a file to save
    printwriter.println(rosterToString());
  }

  /**
   * Helper method to parse a line from a loaded roster and convert it to a StudentRecord object.
   * The line represents a String representation of a student. Extra whitespace at the beginning and
   * end of the line should be disregarded.
   * 
   * A String representation of a StudentRecord should be at the following format: <BR>
   * name, email, campusID, preReqValue
   * 
   * Where name represents the name of a student,<BR>
   * email represents the email address of a student,<BR>
   * campusID represents the campus ID of a student,<BR>
   * preReqValue should be parsable to a boolean telling whether the pre-requisites of the course
   * are satisfied.
   * 
   * 
   * @param line a string representing a student from a saved roster
   * @return StudentRecord the StudentRecord generated from that line
   * 
   * @throws DataFormatException if the line is not formatted correctly. A line is not correctly
   *                             formatted if it is not at the above format where name, email,
   *                             campusID, preReqValue are valid and separated by ", ".
   */
  private StudentRecord lineToRecord(String line) throws DataFormatException {
    String[] parts = line.trim().split(", ");
    if(parts.length != 4) { // Makes sure the line is in the valid format
      throw new DataFormatException();
    }
    
    // Prints in the format name, email, campusID, preReqSatisfied
    return new StudentRecord(parts[0].trim(), parts[1].trim(), parts[2].trim(), Boolean.parseBoolean(parts[3].trim()));
  }

  /**
   * Loads a roster in from a file. The file contains string representations of StudentRecords each
   * in a separate file.
   * 
   * Enrolls each student until the end of the file or the capacity of the roster is reached.
   * 
   * Catches FileNotFoundException and prints the message "Could not find that file!"
   * 
   * @throws IllegalStateException with message "The course capacity would be exceeded by loading
   *                               that student!" if the roster size would be exceeded after adding
   *                               that student.
   * @param rosterFile file object to read
   */
  public void loadRoster(File rosterFile) {
    // Scanner created to read the input file
    Scanner scanner = null;
    try{
      // Make sure the file exists
      scanner = new Scanner(rosterFile);
    }
    catch(FileNotFoundException e){
      System.out.println("Could not find that file!");
    }
    
    int i = 0; // Variable created to make sure the saved roster size is not greater than 
               // enrollmentCapacity
    while(scanner.hasNextLine()) { // If false, no more students in the roster and returns
      if(i > enrollmentCapacity) {
        throw new IllegalStateException();
      }
      try {
        enrollOneStudent(lineToRecord(scanner.nextLine()));
      } 
      catch (DataFormatException e) {
        e.getMessage();
      }
      catch(IllegalStateException e) {
        e.getMessage();
      }
      i++;
    }
  }
}
