package com.demo.bookstore.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Date;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, Date> {
    String validDate;
   /* @Override
    public void initialize(DateOfBirth constraintAnnotation) {
        validDate = constraintAnnotation.current();
    }
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        String[] splitDate = validDate.split("-");
        return date.isAfter(LocalDate.of(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.valueOf(splitDate[2])));
    }*/

    @Override
    public boolean isValid(final Date valueToValidate, final ConstraintValidatorContext context) {
        Calendar dateInCalendar = Calendar.getInstance();
        dateInCalendar.setTime(valueToValidate);

        return Calendar.getInstance().get(Calendar.YEAR) - dateInCalendar.get(Calendar.YEAR) >= 16;
    }
}