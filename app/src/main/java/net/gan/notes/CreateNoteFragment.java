package net.gan.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import net.gan.notes.controller.CreateNoteController;

public class CreateNoteFragment extends Fragment {

    private static final String NOTE_ARGS_KEY = "NOTE_ARGS_KEY";

    private EditText edTextName;
    private EditText edTextDescription;
    private EditText edTextDateCreation;
    private TextView textWarning;

    private AppCompatButton buttonSaveNote;

    @Nullable
    private NoteEntity note = null;

    public static CreateNoteFragment newInstance(@Nullable NoteEntity noteEntity) {
        CreateNoteFragment noteFragment = new CreateNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_ARGS_KEY, noteEntity);
        noteFragment.setArguments(args);
        return noteFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_note_fragment, container, false);
        edTextName = view.findViewById(R.id.edText_name);
        edTextDateCreation = view.findViewById(R.id.edText_dateCreation);
        edTextDescription = view.findViewById(R.id.edText_noteDescription);
        textWarning = view.findViewById(R.id.text_warning);
        buttonSaveNote = view.findViewById(R.id.button_save_note);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        note = getArguments() != null ? getArguments().getParcelable(NOTE_ARGS_KEY) : null;
        edTextDateCreation.setText(NoteEntity.convertLongToDate(NoteEntity.getCurrentDate()));
        fillNote(note);
        buttonSaveNote.setOnClickListener(v -> {
            if (edTextName.length() == 0) {
                textWarning.setText(getResources().getString(R.string.text_warning));
            } else {
                saveNote();
            }
        });
    }

    private void saveNote() {
        ((CreateNoteController) requireActivity()).saveNote(new NoteEntity(
                note == null ? NoteEntity.idGeneration() : note.id,
                edTextName.getText().toString(),
                note == null ? NoteEntity.getCurrentDate() : note.dateCreation,
                edTextDescription.getText().toString()
        ));
    }

    private void fillNote(@Nullable NoteEntity noteEntity) {
        if (noteEntity == null) return;
        edTextName.setText(noteEntity.name);
        edTextDateCreation.setText(NoteEntity.convertLongToDate(noteEntity.dateCreation));
        edTextDescription.setText(noteEntity.noteDescription);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof CreateNoteController)) {
            throw new IllegalStateException("Необходимо имплементировать CreateNoteController");
        }
    }
}
