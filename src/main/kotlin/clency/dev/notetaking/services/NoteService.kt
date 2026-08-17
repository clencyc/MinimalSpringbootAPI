package clency.dev.notetaking.services

import clency.dev.notetaking.dataSources.NoteRepository
import clency.dev.notetaking.model.Note
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


//business logic is written here
@Service
class NoteService (
    private val repository: NoteRepository
) {

    fun getNotes(): List<Note> {
        return repository.getNotes()
    }

    fun delete(id: String) {
        repository.deleteNote(id)
    }

    fun createNote(note: Note): Note {
        return repository.addNote(note)
    }

    fun patchNote(note: Note): Note {
        return repository.patchNote(note)
    }
}