package com.whizkidzmedia.tiddlerjoy.Utilities;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.whizkidzmedia.tiddlerjoy.R;

/**
 * Created by Sourabh Dixit on 16-03-2016.
 */
public class DialogBox {

    public Context applicationContext;
    public String dialogMessage;
    public Dialog dialogBox;

    public DialogBox(Context c,String text)
    {
        this.applicationContext=c;
        this.dialogMessage=text;
        createDialog();

    }

    private void createDialog() {

        final Dialog dialog = new Dialog(applicationContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.custom_dialog_layout);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(this.dialogMessage);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        dialog.show();
        Button okBtn = (Button)dialog.findViewById(R.id.dialogButtonOK);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public Dialog getDialogBox()
    {
        return this.dialogBox;
    }
}
