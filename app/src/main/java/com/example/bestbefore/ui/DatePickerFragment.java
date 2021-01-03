package com.example.bestbefore.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    /**
     * Input field for the date picker.
     */
    private TextView field;

    /**
     * Constructor for a new date picker dialog fragment.
     *
     * @param field Input field for the date picker.
     */
    public DatePickerFragment(TextView field) {
        this.field = field;
    }

    /**
     * If input {@link #field}'s content is in the preferred locale format, the date picker
     * parses the string and uses the date as default, else it uses today's date.
     *
     * @param savedInstanceState bundle containing saved state.
     * @return Date picker dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String inputDateString = field.getText().toString();
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();
        try {
            Date inputDate = android.text.format.DateFormat.getDateFormat(getActivity()).parse(inputDateString);
            calendar.setTime(inputDate);

            return new DatePickerDialog(getActivity(), this,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            calendar.setTime(today);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month,
                    day);
        }
    }

    /**
     * Sets the date to the input {@link #field}.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        Date input = calendar.getTime();
        LocalDate da = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        field.setText(da.format(ISO_LOCAL_DATE));
    }

}