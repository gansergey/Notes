package net.gan.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import net.gan.notes.controller.ListNotesController;

import java.util.ArrayList;

public class ListNotesFragment extends Fragment {

    private final static String NOTES_TABLE_NAME = "notes";

    private RecyclerView recyclerView;
    private final ArrayList<NoteEntity> listNoteEntity = new ArrayList<>();
    private NotesAdapter adapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //1.
        View view = inflater.inflate(R.layout.list_notes_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_list_notes);
        db = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        adapter = new NotesAdapter();
        adapter.setOnItemClickListener(item -> {
            ((ListNotesController) requireActivity()).editNote(item);
        });
        db.collection(NOTES_TABLE_NAME).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (listNoteEntity.size() == 0) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    listNoteEntity.add(documentSnapshot.toObject(NoteEntity.class));
                }
            }
            updateListNote();
        });
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

    public void addOrRemoveNoteToList(NoteEntity noteEntity, boolean deleteOrAdd) {
        for (NoteEntity note : listNoteEntity) {
            if (noteEntity.id.equals(note.id)) {
                listNoteEntity.remove(note);
                db.collection(NOTES_TABLE_NAME).document(note.id).delete();
                updateListNote();
                break;
            }
        }
        if (!deleteOrAdd) {
            listNoteEntity.add(noteEntity);
            db.collection(NOTES_TABLE_NAME)
                    .document(noteEntity.id)
                    .set(noteEntity);
        }
    }

    private void updateListNote() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter.setData(listNoteEntity);
        recyclerView.setAdapter(adapter);
    }

}
