package com.example.demoapp.Utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationFormValidatorHelper implements TextWatcher {

    private TextInputEditText txtRegFirstName, txtRegLastName, txtRegEmail, txtRegPassword,
            txtRegOtherName,
            txtRegConfirm, txtRegAddress, txtRegPhoneNumber, txtRegDayOfBirth;
    private TextInputLayout txtRegFirstNameLayout, txtRegLastNameLayout, txtRegEmailLayout,
            txtRegPassLayout, txtRegConfirmLayout, txtRegAddressLayout, txtRegOtherNameLayout,
            txtRegPhoneNumberLayout, txtRegDayOfBirthLayout;

    private Button registerBtn;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonNam, radioButtonNu;

    // END OF NO ARGS CONSTRUCTOR.

    public RegistrationFormValidatorHelper(
            TextInputEditText txtRegFirstName,
            TextInputEditText txtRegLastName,
            TextInputEditText txtRegOtherName,
            RadioGroup radioGroupGender,
            RadioButton radioButtonNam,
            RadioButton radioButtonNu,
            TextInputEditText txtRegDayOfBirth,
            TextInputEditText txtRegAddress,
            TextInputEditText txtRegEmail,
            TextInputEditText txtRegPhoneNumber,
            TextInputEditText txtRegPassword,
            TextInputEditText txtRegConfirm,
            TextInputLayout txtRegFirstNameLayout,
            TextInputLayout txtRegLastNameLayout,
            TextInputLayout txtRegOtherNameLayout,
            TextInputLayout txtRegDayOfBirthLayout,
            TextInputLayout txtRegAddressLayout,
            TextInputLayout txtRegEmailLayout,
            TextInputLayout txtRegPhoneNumberLayout,
            TextInputLayout txtRegPassLayout,
            TextInputLayout txtRegConfirmLayout,
            Button registerBtn

    ) {
        this.txtRegFirstName = txtRegFirstName;
        this.txtRegLastName = txtRegLastName;
        this.txtRegOtherName = txtRegOtherName;
        this.radioGroupGender = radioGroupGender;
        this.radioButtonNam = radioButtonNam;
        this.radioButtonNu = radioButtonNu;
        this.txtRegDayOfBirth = txtRegDayOfBirth;
        this.txtRegAddress = txtRegAddress;
        this.txtRegEmail = txtRegEmail;
        this.txtRegPhoneNumber = txtRegPhoneNumber;
        this.txtRegPassword = txtRegPassword;
        this.txtRegConfirm = txtRegConfirm;
        this.txtRegFirstNameLayout = txtRegFirstNameLayout;
        this.txtRegLastNameLayout = txtRegLastNameLayout;
        this.txtRegOtherNameLayout = txtRegOtherNameLayout;
        this.txtRegDayOfBirthLayout = txtRegDayOfBirthLayout;
        this.txtRegAddressLayout = txtRegAddressLayout;
        this.txtRegEmailLayout = txtRegEmailLayout;
        this.txtRegPhoneNumberLayout = txtRegPhoneNumberLayout;
        this.txtRegPassLayout = txtRegPassLayout;
        this.txtRegConfirmLayout = txtRegConfirmLayout;
        this.registerBtn = registerBtn;
    }
    // END OF ALL ARGS CONSTRUCTOR.

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    // END OF BEFORE TEXT CHANGE.

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        txtRegPassLayout.setError(null);
        txtRegConfirmLayout.setError(null);
    }
    // END OF ON TEXT CHANGE METHOD.

    @Override
    public void afterTextChanged(Editable s) {
        String passwordField = txtRegPassword.getText()
                .toString()
                .trim();
        String confirmField = txtRegConfirm.getText()
                .toString()
                .trim();
        String firstName = txtRegFirstName.getText()
                                          .toString()
                                          .trim();
        String lastName = txtRegLastName.getText()
                                        .toString()
                                        .trim();
        String otherName = txtRegOtherName.getText()
                                          .toString()
                                          .trim();
        String dayOfBirth = txtRegDayOfBirth.getText()
                                            .toString()
                                            .trim();
        String phoneNumber = txtRegPhoneNumber.getText()
                                              .toString()
                                              .trim();
        String address = txtRegAddress.getText()
                                      .toString()
                                      .trim();
        String email = txtRegEmail.getText()
                                  .toString()
                                  .trim();
        String password = txtRegPassword.getText()
                                        .toString()
                                        .trim();
        String confirm = txtRegConfirm.getText()
                                      .toString()
                                      .trim();

        // Clear all previous errors
        txtRegFirstNameLayout.setError(null);
        txtRegLastNameLayout.setError(null);
        txtRegOtherNameLayout.setError(null);
        txtRegDayOfBirthLayout.setError(null);
        txtRegAddressLayout.setError(null);
        txtRegEmailLayout.setError(null);
        txtRegPhoneNumberLayout.setError(null);
        txtRegPassLayout.setError(null);
        txtRegConfirmLayout.setError(null);

        // Check each field and set errors appropriately
        if (firstName.isEmpty()) {
            txtRegFirstNameLayout.setError("First name cannot be empty!");
        }

        if (lastName.isEmpty()) {
            txtRegLastNameLayout.setError("Last name cannot be empty!");
        }

        if (otherName.isEmpty()) {
            txtRegOtherNameLayout.setError("Other name cannot be empty!");
        }
        if (dayOfBirth.isEmpty()) {
            txtRegDayOfBirthLayout.setError("Day of birth cannot be empty!");
        }
        if (phoneNumber.isEmpty()) {
            txtRegPhoneNumberLayout.setError("Phone number cannot be empty!");
        }

        if (address.isEmpty()) {
            txtRegAddressLayout.setError("Address cannot be empty!");
        }

        if (email.isEmpty()) {
            txtRegEmailLayout.setError("Email cannot be empty!");
        } else {
            // Validate email format only if it's not empty
            boolean isEmailValid = StringResourceHelper.regexEmailValidationPattern(email);
            if (!isEmailValid) {
                txtRegEmailLayout.setError("Enter a valid email address");
            }
        }

        if (password.isEmpty()) {
            txtRegPassLayout.setError("Password cannot be empty!");
        } else if(passwordField.length() < 6){
            txtRegPassLayout.setError("Password must be 6 characters or more");
        }else {
            txtRegPassLayout.setError(null);
        }

        if (confirm.isEmpty()) {
            txtRegConfirmLayout.setError("Confirm cannot be empty!");
        }

        if (!password.equals(confirm)) {
            txtRegPassLayout.setError("Passwords do not match!");
            txtRegConfirmLayout.setError("Passwords do not match!");
        }
        Pattern namePattern = Pattern.compile("^[a-zA-Z\\s]*$");

        Matcher firstNameMatcher = namePattern.matcher(firstName);
        if (!firstNameMatcher.matches()) {
            txtRegFirstNameLayout.setError("First name cannot contain special characters!");
        }

        Matcher lastNameMatcher = namePattern.matcher(lastName);
        if (!lastNameMatcher.matches()) {
            txtRegLastNameLayout.setError("Last name cannot contain special characters!");
        }

        Matcher otherNameMatcher = namePattern.matcher(otherName);
        if (!otherNameMatcher.matches()) {
            txtRegOtherNameLayout.setError("Other name cannot contain special characters!");
        }

        if (!dayOfBirth.isEmpty()) {
            try {
                LocalDate dateOfBirth = LocalDate.parse(dayOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                LocalDate currentDate = LocalDate.now();
                Period age = Period.between(dateOfBirth, currentDate);
                if (age.getYears() < 18) {
                    txtRegDayOfBirthLayout.setError("Your age must be 18 or older!");
                } else {
                    txtRegDayOfBirthLayout.setError(null);
                }
            } catch (DateTimeParseException e) {
                txtRegDayOfBirthLayout.setError("Invalid date format! Please enter date in yyyy-MM-dd format.");
            }
        } else {
            txtRegDayOfBirthLayout.setError("Day of birth cannot be empty!");
        }

        registerBtn.setEnabled(
                !firstName.isEmpty() && !lastName.isEmpty() && !otherName.isEmpty() &&
                        !dayOfBirth.isEmpty() && !address.isEmpty() && !email.isEmpty() &&
                        StringResourceHelper.regexEmailValidationPattern(email) &&
                        !phoneNumber.isEmpty() &&
                        password.length() >= 6 &&
                        password.equals(confirm)
        );
    }

}
