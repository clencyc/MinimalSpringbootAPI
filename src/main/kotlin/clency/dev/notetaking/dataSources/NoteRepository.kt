package clency.dev.notetaking.dataSources

import clency.dev.notetaking.model.Note
import org.springframework.stereotype.Repository


@Repository
class NoteRepository {
    val mockNotes = mutableListOf<Note>(
        Note(
            title = "title",
            description = "description",
        )
    )

    fun getNotes(): MutableList<Note> = mockNotes

    fun deleteNote(noteId: String) {
        val tempNote = mockNotes.firstOrNull { it.id == noteId }
        if(tempNote != null ) {
            mockNotes.remove(tempNote)
        } else {
            throw NoSuchElementException("No note found with id $noteId")
        }
    }

    fun addNote(note: Note): Note {
        val tempNote = mockNotes.firstOrNull { it.id == note.id }
        if(tempNote == null) {
            mockNotes.add(note)
            return note
        } else {
            throw IllegalArgumentException("Cannot add note with id ${note.id}")
        }
    }


    fun patchNote(note: Note): Note {
        val tempNote = mockNotes.firstOrNull { it.id == note.id }
        if(tempNote != null) {
            mockNotes.remove(tempNote)
            mockNotes.add(note)
            return note
        } else {
            throw IllegalArgumentException("Cannot add note with id ${note.id}")
        }
    }
}

// POST, DELETE, PATCH