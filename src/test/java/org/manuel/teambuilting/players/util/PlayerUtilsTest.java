package org.manuel.teambuilting.players.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.teambuilting.players.model.TimeSlice;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author Manuel Doncel Martos
 * @since 06/04/2017.
 */
public class PlayerUtilsTest {

    private static PlayerUtils playerUtils;

    @BeforeEach
    public void beforeClass() {
        playerUtils = new PlayerUtils();
    }

    @Test
    public void testOverlappingForEntryOneFinishedBeforeEntryTwoToDateNull() {
        /*
		 * --|-----|-------- t
		 * E1_FD  E2_TD        entryOne
		 *
		 * -----------|----- t
		 *          E2_FD      entryTwo
		 */
        final Date fromDateOne = changeDate(new Date(), -4, Calendar.YEAR);
        final Date toDateOne = changeDate(new Date(), -2, Calendar.YEAR);

        final Date fromDateTwo = new Date();
        final Date toDateTwo = null;
        final boolean actual = playerUtils.isOverlapping(createTimeslice(fromDateOne, toDateOne), createTimeslice(fromDateTwo, toDateTwo));
        assertFalse(actual);
    }

    @Test
    public void testOverlappingForEntryOneFinishedBeforeEntryTwoToDateNotNull() {
        /*
		 * --|-----|--------  t
		 * E1_FD  E2_TD         entryOne
		 *
		 * -----------|----|- t
		 *          E2_FD E2_TD entryTwo
		 */
        final Date fromDateOne = changeDate(new Date(), -4, Calendar.YEAR);
        final Date toDateOne = changeDate(new Date(), -2, Calendar.YEAR);

        final Date toDateTwo =  new Date();
        final Date fromDateTwo =  changeDate(toDateTwo, -5, Calendar.MONTH);
        final boolean actual = playerUtils.isOverlapping(createTimeslice(fromDateOne, toDateOne), createTimeslice(fromDateTwo, toDateTwo));
        assertFalse(actual);
    }

    @Test
    public void testOverlappingForEntryTwoStartingAndFinishingAfterEntryOne() {
        /*
		 * --|-----|--------  t
		 * E1_FD  E2_TD         entryOne
		 *
		 * ----|----|------- t
		 *   E2_FD E2_TD        entryTwo
		 */
        final Date fromDateOne = changeDate(new Date(), -4, Calendar.YEAR);
        final Date toDateOne = changeDate(new Date(), -2, Calendar.YEAR);

        final Date fromDateTwo =  changeDate(fromDateOne, +2, Calendar.MONTH);
        final Date toDateTwo =  changeDate(toDateOne, +2, Calendar.MONTH);
        final boolean actual = playerUtils.isOverlapping(createTimeslice(fromDateOne, toDateOne), createTimeslice(fromDateTwo, toDateTwo));
        assertTrue(actual);
    }

    private Date changeDate(final Date date, final int number, final int calendarField ) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarField, number);
        return cal.getTime();
    }

    private TimeSlice createTimeslice(final Date fromDate, final Date toDate) {
        return new TimeSlice() {
            @Override
            public Date getFromDate() {
                return fromDate;
            }

            @Override
            public Date getToDate() {
                return toDate;
            }
        };
    }
}
