package org.manuel.teambuilting.players.model;

import java.util.Date;

/**
 * @author manuel.doncel.martos
 * @since 26-1-2017
 */
public interface TimeSlice {

	/**
	 * From date
	 * @return
	 */
	Date getFromDate();

    /**
     * To date
     * @return
     */
	Date getToDate();
}
