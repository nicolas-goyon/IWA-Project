package com.projet_iwa.ms_location;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {
    public static List<Date> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<Date> dates = new ArrayList<>();

        // Boucle entre la date de début et la date de fin
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            dates.add(java.sql.Date.valueOf(current));  // Convertir en Date si nécessaire
            current = current.plusDays(1);
        }

        return dates;
    }

}
