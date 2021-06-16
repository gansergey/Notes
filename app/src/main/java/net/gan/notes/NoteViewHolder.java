package net.gan.notes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

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
    }

}

