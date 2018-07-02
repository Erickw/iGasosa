package com.example.erick.igasosa2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class DialogConfirmacao extends DialogFragment  implements DialogInterface.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Erro");
        builder.setMessage("Os dados inseridos estão em formatos invalidos");
        builder.setNeutralButton("ok", this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        String msg = null;
    }
}
