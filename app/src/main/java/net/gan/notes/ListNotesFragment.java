package net.gan.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import net.gan.notes.controller.ListNotesController;

import java.util.ArrayList;

public class ListNotesFragment extends Fragment {

    private Button createNoteButton;
    private RecyclerView recyclerView;
    private final ArrayList<NoteEntity> listNoteEntity = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //1.
        View view = inflater.inflate(R.layout.list_notes_fragment, container, false);
        //createNoteButton = view.findViewById(R.id.button_create_note);
        recyclerView = view.findViewById(R.id.recycler_list_notes);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //updateListNotes(listNoteEntity);//Нужно, чтобы при возврате перерисовать заметки
        NotesAdapter adapter = new NotesAdapter();
        adapter.setOnItemClickListener(item -> {
            ((ListNotesController) requireActivity()).editNote(item);
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter.setData(listNoteEntity);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //3.
        //Проверяем, что те кто будут пользоваться этим фрагментом должны имплементировать контроллер
        if (!(context instanceof ListNotesController)) {
            throw new IllegalStateException("Необходимо имплементировать ListNoteController");
        }
    }

    public void addNoteToList(NoteEntity noteEntity) {
        for (NoteEntity note : listNoteEntity) {
            if (noteEntity.id.equals(note.id)) {
                listNoteEntity.remove(note);
            }
        }
        listNoteEntity.add(noteEntity);
        // updateListNotes(listNoteEntity);
    }


}
