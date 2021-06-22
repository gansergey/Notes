package net.gan.notes;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.gan.notes.controller.CreateNoteController;
import net.gan.notes.controller.ListNotesController;

public class MainActivity extends AppCompatActivity implements ListNotesController,
        CreateNoteController {

    private static final String LIST_NOTES_FRAGMENT_TAG = "LIST_NOTES_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showNoteListFragment();
        initBottomBar();
    }

    private void showNoteListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                //Делаем TAG = LIST_NOTES_FRAGMENT_TAG, чтобы потом найти фрагмент
                .add(R.id.fragment_container, new ListNotesFragment(), LIST_NOTES_FRAGMENT_TAG)
                .commit();
    }

    private void showAboutAppFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, new AboutAppFragment())
                .commit();
    }

    private void showEditNote(@Nullable NoteEntity noteEntity) { //если может принимать значанение null надо помечать @Nullable
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, CreateNoteFragment.newInstance(noteEntity))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }


    @SuppressLint("NonConstantResourceId")
    private void initBottomBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_list_notes:
                            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            break;
                        case R.id.action_add_new_note:
                            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            createNote();
                            break;
                        case R.id.action_about:
                            showAboutAppFragment();
                            break;
                    }
                    return false;
                });
    }

    @Override
    public void createNote() {
        showEditNote(null);
    }

    @Override
    public void editNote(NoteEntity noteEntity) {
        showEditNote(noteEntity);
    }

    @Override
    public void deleteNote(NoteEntity noteEntity) {
        ListNotesFragment listNotesFragment = (ListNotesFragment) getSupportFragmentManager().findFragmentByTag(LIST_NOTES_FRAGMENT_TAG);
        if (listNotesFragment != null) {
            listNotesFragment.addNoteToList(noteEntity, true);
        }
    }

    @Override
    public void saveNote(NoteEntity noteEntity) {
        getSupportFragmentManager().popBackStack();//возвращаемся к фрагменту со списком заметок
        ListNotesFragment listNotesFragment = (ListNotesFragment) getSupportFragmentManager().findFragmentByTag(LIST_NOTES_FRAGMENT_TAG);
        if (listNotesFragment != null) {
            listNotesFragment.addNoteToList(noteEntity, false);
        }
    }
}