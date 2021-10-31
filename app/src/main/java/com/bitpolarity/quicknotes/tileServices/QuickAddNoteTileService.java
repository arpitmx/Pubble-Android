package com.bitpolarity.quicknotes.tileServices;

import android.content.Intent;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bitpolarity.quicknotes.NoteEditorActivity;
import com.bitpolarity.quicknotes.intents.ReadLaterActivity;
import com.bitpolarity.quicknotes.modal;

@RequiresApi(api = Build.VERSION_CODES.N)
public class QuickAddNoteTileService extends TileService {

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        Toast.makeText(this, "Add note shortcut added!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        Toast.makeText(this,"Opening pubbl notes",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick() {
        super.onClick();
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        getQsTile().updateTile();
    }
}

