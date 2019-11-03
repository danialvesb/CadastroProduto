package com.example.cadastroproduto.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigSharedPreferences {
    public static final String ID_PREF = "sae_consulta_precos";

    public static void setString(Context context, String chave, String valor) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(chave, valor);
        editor.commit();
    }

    public static String getString(Context context, String chave) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREF, 0);
        String s = sharedPreferences.getString(chave, "");

        return (s == null ? " " : s);
    }

    public static void setBoolean(Context context, String chave, Boolean valor) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(chave, valor);
        editor.commit();
    }

    public static Boolean getBoolean(Context context, String chave) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREF, 0);
        Boolean b = sharedPreferences.getBoolean(chave, true);
        return b;
    }
}
