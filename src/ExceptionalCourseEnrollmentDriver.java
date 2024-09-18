// TODO File Header COMES HERE
// Be sure to credit the outside help section in the file header

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class implements the Exceptional Driver Application for cs300 Fall 2023 P05 Exceptional Course Enrollment System
 *
 */
public class ExceptionalCourseEnrollmentDriver {

  // welcome, good bye, and syntax error messages
  private static final String WELCOME_MSG = "--- Welcome to the Exceptional Course Enrollmant System! ----";
  private static final String GOOD_BYE_MSG = "---------- BYE! Thanks for using our App! ----------";
  private static final String SYNTAX_ERROR_MSG = "Syntax Error: Please enter a valid command!";
  private static final String NO_COURSE_ENROLLMANT_MSG =
      "Error: Create a new course enrollment first!";
  private static final String PATH = "saved_roster.txt";
  private static ArrayList<ExceptionalCourseEnrollment> enrollments = new ArrayList<ExceptionalCourseEnrollment>();
  /**
   * Main method that launches this driver application
   * 
   * @param args list of input arguments if any
   */
  public static void main(String[] args) {
    // run application
    System.out.println(WELCOME_MSG); // display welcome message
    // Create a scanner to read the user inputs
    Scanner scanner = new Scanner(System.in);
    // read and process user command lines
    processUserCommands(scanner);
    scanner.close();// close the scanner
    System.out.println(GOOD_BYE_MSG);// display good bye message
  }


  /**
   * Prints out the menu of this application
   */
  private static void displayMenu() {
    System.out.println("\n================================ MENU ===============================");
    System.out.println("Enter one of the following options:");
    System.out.println("[1 <course_name> <enrollment_capacity> <waitlist_capacity>] Create a new exceptional course enrollment");
    System.out.println("[2 <name>:<wisc_email>:<campus_ID>:boolean(true/false)] Enroll student");
    System.out.println("[3 <name>:<wisc_email>:<campus_ID>:boolean] Add student to waitlist");
    System.out.println("[4 <campus_ID>] Drop the course");
    System.out.println("[a <course index>] Switch to controlling course at index i");
    System.out.println("[5] Print roster");
    System.out.println("[6] Print waitlist");
    System.out.println("[7] Save roster");
    System.out.println("[8] Load roster");
    System.out.println("[9] Print all course enrollments");
    System.out.println("[e] Logout and EXIT");
    System.out.println("-----------------------------------------------------------------------");
  }


  /**
   * Reads and processes user command lines
   */
  private static void processUserCommands(Scanner scanner) {
    
    String promptCommandLine = "ENTER COMMAND: ";    
    String command = null; // variable to save the user command line

    // define an ExceptionalCourseEnrollment for the course enrollment, not yet initialized
    ExceptionalCourseEnrollment courseEnrollment = null;

    // read and process user command lines until the user quits the application
    do{
      
      displayMenu(); // display the main menu
      // read user command line
      System.out.print(promptCommandLine);
      command = scanner.nextLine();
      // blank command
      if(command == null || command.isBlank()) {
        System.out.println(SYNTAX_ERROR_MSG); // syntax error message
        continue; // go to the next iteration
      }
      
      // Exit the loop if the user command is 7 for Quit
      if(command.charAt(0) == 'b') {
        break; // exit the loop
      }
      
      // Create new course enrollment 
      if (command.charAt(0) == '1') {
        // [1 <enrollment_capacity> <waitlist_capacity>] Create a new course enrollment
        String[] parts = command.split(" ");
        if (parts.length != 4) {// syntax error
          System.out.println(SYNTAX_ERROR_MSG);
          continue;
        }
        // valid syntax: create lists
        try {
        	courseEnrollment = new ExceptionalCourseEnrollment(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        	System.out.println("Created course enrollment");
        }
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {
        	enrollments.add(courseEnrollment);
        }
        continue;
      }
      
      else { // any other type of command.
        // Check whether the course enrollment lists were created
        if (courseEnrollment == null || (courseEnrollment.deepCopyWaitlist() == null || courseEnrollment.deepCopyRoster() == null )) {
          System.out.println(NO_COURSE_ENROLLMANT_MSG);
          continue;
        }
        
        String[] parts;
        String name;
        String email;
        String campusID;
        boolean preReq;
        // process the user command line for the other options
        switch (command.charAt(0)) {
         
          // Enroll student
          case '2': // [2 <name>:<wisc_email>:<campus_ID>:boolean(true/false)] Enroll student
            // The boolean indicates whether the pre-requisites of the course are satisfied or not
        	parts = command.split(":");
        	name = parts[0].split(" ")[1];
        	email = parts[1];
        	campusID = parts[2];
        	preReq = Boolean.parseBoolean(parts[3]);
        	try {
        		StudentRecord student = new StudentRecord(name, email, campusID, preReq);
        		courseEnrollment.enrollOneStudent(student);
        	}
        	catch (Exception e) {
        		System.out.println(e.getMessage());
        	}
        	
            
            break;

          case '3': // [3 <name>:<wisc_email>:<campus_ID>:boolean] Add student to waitlist
        	parts = command.split(":");
          	name = parts[0].split(" ")[1];
          	email = parts[1];
          	campusID = parts[2];
          	preReq = Boolean.parseBoolean(parts[3]);
          	try {
          		StudentRecord student = new StudentRecord(name, email, campusID, preReq);
          		courseEnrollment.addWaitlist(student);
          	}
          	catch (Exception e) {
          		System.out.println(e.getMessage());
          	}
            break;

          case '4': // [4 <campus_ID>] Drop the course
        	parts = command.split(" ");
        	try {
        		StudentRecord student = courseEnrollment.searchById(parts[1], courseEnrollment.deepCopyRoster());
        		courseEnrollment.dropCourse(student);
        	}
        	catch (Exception e) {
        		System.out.println(e.getMessage());
        	}
            break;

          case '5': // [5] Print roster
            System.out.println(courseEnrollment.toString());
            break;

          case '6': // [6] Print waitlist
        	 courseEnrollment.printWaitlist();
            break;
           
          case '7': // [7] Save roster
        	  try {
        		  courseEnrollment.saveRoster(new File(PATH));
        		  System.out.println("Saved roster to saved_roster.txt");
        	  }
        	  catch (Exception e) {
        		  System.out.println(e.getMessage());
        	  }
             break;
             
          case '8': // [8] Load roster
        	 try {
        	   File roster = new File(PATH);
        		 courseEnrollment.loadRoster(roster);
        		 System.out.println("Loaded roster");
        	 }
        	 catch (Exception e) {
        		 System.out.println(e.getMessage());
        	 }
             break;
          case '9':
        	  try {
        		  for (int i = 0; i < enrollments.size(); i++) {
        			  ExceptionalCourseEnrollment ce = enrollments.get(i);
        			  if (ce != null) {
        				  System.out.println(ce.toString() + "\n");
        			  }
        		  }
        	  }
        	  catch (Exception e) {
        		  System.out.println(e.getMessage());
        	  }
        	  break;
          case 'a':
        	  try {
        		  parts = command.split(" ");
        		  courseEnrollment = enrollments.get(Integer.parseInt(parts[1]));
        		  System.out.println("Switched to course " + courseEnrollment.getName());
        	  }
        	  catch(Exception e){
        		  System.out.println(e.getMessage());
        	  }
        	  break;

          default:
            System.out.println(SYNTAX_ERROR_MSG); // Syntax Error

        }
      }
      
    }while(command.charAt(0) != 'e');
      
  }

}
