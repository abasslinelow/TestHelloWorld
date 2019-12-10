package sample;

public class TestProductionLine {

  /**
   * A driver to test the EmployeeInfo class.
   * @param args Any arguments passed to the program.
   */
  public static void main(String[] args) {

    // Create a new object to store an employee's information.
    EmployeeInfo employee = new EmployeeInfo();

    // Display the name and code of the employee to show that input validation
    // works as intended. If a valid full name (i.e., a name with a space) is
    // given, the code will be set to the first letter of the first name and
    // the entire last name as one word. If a name without a space is given,
    // the code will be set to "guest".
    System.out.println(employee.toString());
  }
}
