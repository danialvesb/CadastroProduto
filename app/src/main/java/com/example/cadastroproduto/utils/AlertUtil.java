package com.example.cadastroproduto.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

public abstract class AlertUtil {
    public static String DDM = "DDM Produtos";

    public static void showOKDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }

    public static void getConfirmDialog(Context mContext, String title, String msg, String positiveBtnCaption, String negativeBtnCaption, boolean isCancelable, final IAlertUtil target) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle(title).setMessage(msg).setIconAttribute(android.R.attr.alertDialogIcon).setCancelable(false).setPositiveButton(positiveBtnCaption, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                target.PositiveMethod(dialog, id);
            }
        }).setNegativeButton(negativeBtnCaption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                target.NegativeMethod(dialog, id);
            }
        });

        AlertDialog alert = builder.create();
        alert.setCancelable(isCancelable);
        alert.show();

        if (isCancelable) {
            alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    target.NegativeMethod(null, 0);
                }
            });
        }
    }

    public static void getSenhaDialog(final Context mContext, String title, String msg, String positiveBtnCaption, String negativeBtnCaption, boolean isCancelable, final IAlertUtil target) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIconAttribute(android.R.attr.alertDialogIcon);
        builder.setCancelable(false);

        final EditText input = new EditText(mContext);
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        builder.setView(input);

        builder.setPositiveButton(positiveBtnCaption, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String sSenhaRazao = "ddm" + DateUtil.diaHoraAtual();  // Exemplo: "itfad0815" se dia 08 às 15 horas
                String sSenha = input.getText().toString();
                String sSenhaAdm = ConfigSharedPreferences.getString(mContext, "cfgSenhaAdm");
                id = (sSenha.equals(sSenhaRazao) || sSenha.equals(sSenhaAdm) ? 1 : 0);
                target.PositiveMethod(dialog, id);
            }
        }).setNegativeButton(negativeBtnCaption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                target.NegativeMethod(dialog, id);
            }
        });

        AlertDialog alert = builder.create();
        alert.setCancelable(isCancelable);
        alert.show();

        if (isCancelable) {
            alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    target.NegativeMethod(null, 0);
                }
            });
        }
    }

}
