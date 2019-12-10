package sample;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeInfo {

  /**
   * An employee's name.
   */
  private StringBuilder name;

  /**
   * The code given to an employee, which is the first letter of their first name
   * and their entire last name.
   * Format is as follows: Joe Smith = JSmith
   */
  private String code;

  /**
   * The employee's department identification number.
   */
  private String deptId;

  /**
   * The regex pattern to ensure the employee's department id applies the following format:
   * Aaaa##
   * where 'A' is any uppercase letter, 'a' is any lowercase letter, and '#' is any number.
   */
  private Pattern p;

  /**
   * A scanner object for gathering user input.
   */
  private Scanner in;

  /**
   * Default constructor.
   */
  EmployeeInfo() {
    in = new Scanner(System.in);
    p = Pattern.compile("[A-Z][a-z]{3}\\d\\d");
    setName();
    setDeptId();

    in.close();
  }

  /**
   * Returns the name of the employee.
   *
   * @return StringBuilder The name of the employee.
   */
  public StringBuilder getName() {
    return name;
  }

  /**
   * Sets the name of the employee. It does this by getting a name from the user,
   * then checking to see if the name has a space. If it doesn't, specifies the user
   * as a guest.
   */
  private void setName() {

    // Make a new StringBuilder from user input of their first and last name.
    StringBuilder name = new StringBuilder(inputName());

    // Check to see if the user inputted a first and last name - that is, if there
    // is a space in the name. If there is, create the proper employee code. If not,
    // set them as a guest.
    if (checkName(name)) {
      this.name = name;
      createEmployeeCode(name);
    } else {
      this.name = name;
      code = "guest";
    }
  }

  /**
   * Returns the employee code for the employee.
   *
   * @return String The employee code.
   */
  public String getCode() {
    return code;
  }

  /**
   * Gets the validated employee identification number.
   *
   * @return The validated employee identification number.
   */
  public String getId() {
    return deptId;
  }

  /**
   * Gets input from the user for their department identification number.
   *
   * @return String User input, i.e. the department identification number of the employee.
   */
  private String getDeptId() {

    System.out.println("Please enter your department ID: ");
    return in.nextLine();
  }

  /**
   *  Takes the user's input for a department id and runs it through a validator.
   *  If it passes, it retains the inputted employee department id.
   *  If it does not pass, it sets the department id to "None01".
   *  After validation, it encrypts the information before storing.
   */
  private void setDeptId() {
    String tempDeptId = getDeptId();

    if (!validId(tempDeptId)) {
      tempDeptId = "None01";
    }

    deptId = reverseString(tempDeptId);
  }

  /**
   * Gets input from the user for their first and last name.
   *
   * @return String The user input, i.e. the name of the employee.
   */
  private String inputName() {

    System.out.println("Please enter your first and last name: ");
    return in.nextLine();
  }

  /**
   * This method accepts a name as a String variable and ensure it is a first
   * and last name by checking to see if a space is in the string. If a space
   * exists, we can be reasonably sure the input is a first and last name.
   *
   * @param name The name of the employee.
   * @return boolean If true, name has a space.
   */
  private boolean checkName(StringBuilder name) {
    return name.indexOf(" ") != -1;
  }

  /**
   * Uses a first and last name to make an employee code which is the first letter
   * of the first name and the entire last name.
   * Formatting is as follows:
   * Joe Smith = JSmith
   *
   * @param name The name of the employee.
   */
  private void createEmployeeCode(StringBuilder name) {

    // Find the index of the space in the string, then add 1 so it points to
    // the index of first letter of the last name.
    int indexOfLastName = name.indexOf(" ") + 1;

    // Set the code to the first letter in the first name combined with the
    // entire last name.
    code = name.substring(0, 1) + name.substring(indexOfLastName);
  }

  /**
   * Validates the department identification number in the following format:
   * Aaaa##
   * where 'A' is any uppercase letter, 'a' is any lowercase letter, and '#' is any number.
   *
   * @param id The validated department identification number.
   * @return boolean True if the id.
   */
  private boolean validId(String id) {

    Matcher m = p.matcher(id);
    System.out.println(m.matches());
    return m.matches();
  }

  /**
   * Encrypts the department id before storing to the object, for security purposes.
   *
   * @param id The department id belonging to the employee.
   * @return String An encrypted version of the department id.
   */
  public String reverseString(String id) {

    // If the id is empty, then the recursion has finished. Return the id as is.
    if (id.isEmpty()) {
      return id;
    } else {

      // Else, if the id isn't empty, keep recursively calling.
      // This works by nesting calls which will occur on every letter of the id. It will
      // take the first character, put it at the end, and call itself recursively on the
      // rest of the string. By the end, there is nothing left in the rest of the string,
      // and the reversed string will be complete. The nest looks like this:
      //
      // Original word: Example
      // xample + E
      // ample + xE
      // mple + axE
      // ple + maxE
      // le + pmaxE
      // e + lpmaxE
      //  + elpmaxE - id.substring(1) is now empty, "elpmaxE" is returned.
      //
      return reverseString(id.substring(1)) + id.charAt(0);
    }
  }

  /**
   * Overrides the toString() method to return the employee code and department ID
   * in the following format:
   * Employee Code: (code), Department Id: (deptId)
   * where "code" is the code field and "deptId" is the deptId field of the employee.
   *
   * @return String containing the employee code and department ID.
   */
  @Override
  public String toString() {
    return String.format("Employee Code: %s, Department Id: %s", code, deptId);
  }
}

