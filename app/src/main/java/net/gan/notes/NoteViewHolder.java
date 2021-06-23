package net.gan.notes;

import android.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import net.gan.notes.controller.ListNotesController;

public class NoteViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

    private final TextView nameTextView;
    private final TextView description_text_view;
    private final TextView dateCreationTextView;
    private NoteEntity noteEntity;

    public NoteViewHolder(@NonNull View itemView, @Nullable NotesAdapter.OnItemClickListener clickListener) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.name_text_view);
        description_text_view = itemView.findViewById(R.id.description_text_view);
        dateCreationTextView = itemView.findViewById(R.id.dateCreation_text_view);
        itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(noteEntity);
            }
        });
    }

    public void bind(NoteEntity noteEntity) {
        this.noteEntity = noteEntity;
        nameTextView.setText(noteEntity.name);
        description_text_view.setText(noteEntity.noteDescription);
        dateCreationTextView.setText(NoteEntity.convertLongToDate(noteEntity.dateCreation));

        itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), itemView);
            popupMenu.inflate(R.menu.item_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
            return true;
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        showAlertDialog();
        return true;
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(itemView.getContext())
                .setTitle(R.string.alert_title_delete_note)
                .setMessage(R.string.alert_message_delete_note)
                .setCancelable(false)
                .setPositiveButton(R.string.positive_button, (d, i) -> {
                    ((ListNotesController) itemView.getContext()).deleteNote(noteEntity);
                })
                .setNegativeButton(R.string.negative_button, (d, i) -> {
                })
                .setIcon(R.drawable.ic_baseline_delete_forever_24)
                .show();
    }

}