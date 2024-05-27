package com.virtual_bank_g66.demo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

/**
 * Test class for AccountService.
 * <p>
 * This class contains unit tests for validating the AccountService functionalities including
 * account creation, password validation, and handling missing or duplicate account information.
 * </p>
 * 
 * @version 1.0 April 10th, 2024 Partition test the validity of the password
 * @version 2.0 April 24th, 2024 A series of tests to supplement account creation
 * @author Jiabo Tong
 * @author Kexing Zhang
 */

public class AccountServiceTest {

    private AccountService accountService;
    private Utils utils;
    private File tempUserInfoFile;
    private File tempMoneyInfoFile;



    @BeforeAll
    public static void initJFX() {
        JFXInitializer.initialize();
    }

    @BeforeEach
    public void setUp() throws IOException {
        utils = new Utils();
        tempUserInfoFile = File.createTempFile("userInfo", ".csv");
        tempMoneyInfoFile = File.createTempFile("moneyInfo", ".csv");
        accountService = new AccountService(tempUserInfoFile.getAbsolutePath(), tempMoneyInfoFile.getAbsolutePath(), utils);
    }


    // The following are black box testing for the whole process of creating an account

    /*
   1. Password Length
      Valid Length: Between five and nine characters.
      Invalid Length: Fewer than five characters and more than nine characters.
    */
    @Test
    public void testInvalidPasswordLength() {
        assertFalse(AccountService.isValid("abc1"));
        assertFalse(AccountService.isValid("1abc234567"));
    }

    /*
    2. Character Content
       Valid Characters: Contains only letters (a-z, A-Z) and digits (0-9).
       Invalid Characters: Contains special characters or spaces.
     */
    @Test
    public void testInvalidPasswordCharacters() {
        assertFalse(AccountService.isValid("abc!123"));
        assertFalse(AccountService.isValid("abcd efgh"));
    }

    /*
    3. Minimum Requirement
       Valid Minimum: Contains at least one letter and one digit.
       Invalid Minimum: All letters or all digits.
     */
    @Test
    public void testInvalidPasswordMinimumRequirements() {
        assertFalse(AccountService.isValid("abcdefg"));
        assertFalse(AccountService.isValid("1234567"));
    }

    /*
     4. Create Account with Missing Information
        Valid Information: All fields are provided.
        Invalid Information: Any field is missing.
    */
    @Test
    void testCreateAccountLockOfInfo() {
        assertFalse(accountService.createAccount("", "Jane Doe2", "pw12345", "jane@example.com"));
        assertFalse(accountService.createAccount("Child", "", "pw12345", "jane@example.com"));
        assertFalse(accountService.createAccount("Child", "Jane Doe2", "", "jane@example.com"));
        assertFalse(accountService.createAccount("Child", "Jane Doe2", "pw12345", ""));
    }

    /*
     5. Duplicate Account Creation
        Valid Case: No duplicate accounts.
        Invalid Case: Attempting to create an account with the same details again.
    */
    @Test
    void testCreateDuplicateAccount() {
        accountService.createAccount("Parent", "John Doe", "pw12345", "john@example.com");
        boolean result = accountService.createAccount("Parent", "John Doe", "pw12345", "john@example.com");
        assertFalse(result);
    }

    /*
     6. Create Account for Child Role
        Valid Case: Creating an account with "Child" role.
        Checks: Ensures moneyInfoFile exists.
    */
    @Test
    void testCreateAccountForChildRole() {
        boolean result = accountService.createAccount("Child", "Jane Doe2", "pw12345", "jane@example.com");
        assertTrue(result);
        File moneyInfoFile = new File(tempMoneyInfoFile.getAbsolutePath());
        assertTrue(moneyInfoFile.exists());
    }



    // The following is white box testing for the whole process of creating an account

    /*
        Create Account Success
        Valid Case: Creating accounts with "Parent" and "Child" roles.
    */
    @Test
    void testCreateAccountSuccess() {
        boolean result_Parent = accountService.createAccount("Parent", "Older John Doe", "pw12345", "john@example.com");
        boolean result_Child = accountService.createAccount("Child", "Yong John Doe", "pw12345", "john@example.com");
        assertTrue(result_Child && result_Parent);
    }

}
