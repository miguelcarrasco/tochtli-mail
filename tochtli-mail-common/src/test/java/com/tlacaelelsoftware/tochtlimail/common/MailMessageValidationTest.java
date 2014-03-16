package com.tlacaelelsoftware.tochtlimail.common;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.fail;

/**
 * Unit test for simple App.
 */
public class MailMessageValidationTest {
    ValidatorHelper<MailMessage> validatorHelper = new ValidatorHelper<MailMessage>();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void validateWellFormedMessage() {
        MailMessage message = new MailMessage("mail@test.com", "first@mail.com,second@mail.com", "Some subject", "some text");

        try {
            validatorHelper.validate(message);
        } catch (Exception e) {
            fail("validate method should not throw any exception");
        }
    }

    @Test
    public void validateBlankFromAddressMessage() {
        MailMessage message = new MailMessage("", "first@mail.com,second@mail.com", "Some subject", "some text");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("may not be empty");
        validatorHelper.validate(message);
    }

    @Test
    public void validateBlankRecipients() {
        MailMessage message = new MailMessage("mail@test.com", "", "Some subject", "some text");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("may not be empty");
        validatorHelper.validate(message);
    }

    @Test
    public void validateBlankSubject() {
        MailMessage message = new MailMessage("mail@test.com", "first@gmail.com,second@gmail.com", "", "some text");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("may not be empty");
        validatorHelper.validate(message);
    }

    @Test
    public void validateBlankText() {
        MailMessage message = new MailMessage("mail@test.com", "first@gmail.com,second@gmail.com", "Some subject", "");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("may not be empty");
        validatorHelper.validate(message);
    }


    @Test
    public void fromAddressShouldBeAValidEmail(){
        MailMessage message = new MailMessage("notAMail.com", "first@gmail.com,second@gmail.com", "Some subject", "Some Text");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("not a well-formed email address");
        validatorHelper.validate(message);
    }
}
