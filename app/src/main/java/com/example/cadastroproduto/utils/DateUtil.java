package com.example.cadastroproduto.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import com.example.cadastroproduto.MyApp;

public class DateUtil {
    public static String DataDMY() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String formattedDate = df.format(currentTime);

        return formattedDate;
    }

    public static Integer diaAtual() {
        Calendar newCalendar = Calendar.getInstance();
        return newCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer horaAtual() {
        Calendar newCalendar = Calendar.getInstance();
        return newCalendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String diaHoraAtual() {
        return String.format("%02d", diaAtual()) + String.format("%02d", horaAtual());
    }

    public static Date stringToDate(String sDateTime) throws Exception {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date dtIn = inFormat.parse(sDateTime);
        return dtIn;
    }

    public static Date stringToDate2(String sDateTime) throws Exception {
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date dtIn = inFormat.parse(sDateTime);
        return dtIn;
    }

    public static boolean dataServidorPosteriorADataAtual(Date dt) throws Exception {
        boolean dataServidorPosteriorADataAtual = false;
        String sDtUltAtz = ConfigSharedPreferences.getString(MyApp.getContext(), "cfgDtUltAtz");
        Date dataAtual = stringToDate2(sDtUltAtz);

        if (dt.compareTo(dataAtual) > 0) {
            dataServidorPosteriorADataAtual = true;  // Data do servidor posterior a data atual
        } else if (dt.compareTo(dataAtual) < 0) {
            dataServidorPosteriorADataAtual = false;  // Data do servidor anterior a data atual
        } else if (dt.compareTo(dataAtual) == 0) {
            dataServidorPosteriorADataAtual = false;  // Data do servidor igual a data atual
        }

        return dataServidorPosteriorADataAtual;
    }
}
