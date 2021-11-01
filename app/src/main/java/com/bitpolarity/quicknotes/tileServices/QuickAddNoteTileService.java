package com.bitpolarity.quicknotes.tileServices;

import android.content.Intent;
import android.os.Build;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bitpolarity.quicknotes.NoteEditor.NoteEditorActivity;


@RequiresApi(api = Build.VERSION_CODES.N)
public class QuickAddNoteTileService extends TileService {


    Intent intent;

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        Toast.makeText(this, "Add note shortcut added!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        intent = new Intent(this, NoteEditorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Toast.makeText(this,"Opening pubbl notes",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick() {
        super.onClick();
        startActivity(intent);
        getQsTile().updateTile();
    }
}

