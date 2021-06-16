package net.gan.notes.controller;

import net.gan.notes.NoteEntity;

public interface ListNotesController {
    void createNote();
    void editNote(NoteEntity noteEntity);
}
