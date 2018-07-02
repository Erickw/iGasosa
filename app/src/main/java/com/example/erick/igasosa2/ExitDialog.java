package com.example.erick.igasosa2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class ExitDialog extends DialogFragment implements DialogInterface.OnClickListener{
    private ExitListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(!(context instanceof ExitListener)){
            throw new RuntimeException("Não é um ExitListener");
        }

        listener = (ExitListener)context;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog
                .Builder(getActivity());

        builder.setTitle("Deseja Sair?");
        builder.setPositiveButton("Sim",this);
        builder.setNegativeButton("Não",this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE && listener != null){
            listener.onExit();
        }
    }

    public interface ExitListener{

        public void onExit();
    }

}
