package com.doudui.rongegou.Order;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import static android.content.Context.CLIPBOARD_SERVICE;

public class CopyButtonLibrary {

    private ClipboardManager myClipboard;
    private ClipData myClip;
    private Context context;

    public CopyButtonLibrary(Context context) {
        this.context = context;
    }

    public void init(String text) {
        myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);

        if (!text.isEmpty()) {
            myClip = ClipData.newPlainText("text", text);
            myClipboard.setPrimaryClip(myClip);
            Toast.makeText(context, " 复制成功", Toast.LENGTH_SHORT).show();
        }
    }

}
