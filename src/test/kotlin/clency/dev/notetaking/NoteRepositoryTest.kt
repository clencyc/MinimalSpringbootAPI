package clency.dev.notetaking

import clency.dev.notetaking.dataSources.NoteRepository
import clency.dev.notetaking.model.Note
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
class NoteRepositoryTest {
    private val repository = NoteRepository()

    @Test
    fun `addNote adds a note to the list`() {
        val note = Note(id="1", title="Groceries", description = "Milk and eggs")

        val result = repository.addNote(note)
        assertEquals(note, result)
        assertTrue(repository.getNotes().contains(note))
    }

    @Test
    fun `addNote throws when a note with the same id already exists`() {
        val note = Note(id="1", title = "Groceries", description = "Milk and eggs")
        repository.addNote(note)

        assertThrows<IllegalArgumentException> {
            repository.addNote(note.copy(title = "Different title"))

        }
    }

    @Test
    fun `deleteNote deletes the note`() {
        val note = Note(id="1", title = "Groceries", description = "Milk and eggs")
        repository.addNote(note)

        repository.deleteNote("1")
        repository

    }

    @Test
    fun `patchNote updates the note`() {
        val original = Note(id="1", title = "Groceries", description = "Milk and eggs")
        repository.addNote(original)

        val updated = original.copy(title = "Groceries v2")
        val result = repository.patchNote(updated)

        assertEquals(updated, result)
        assertEquals(1, repository.getNotes().count { it.id == "1" })
    }

    @Test
    fun `patchNote throws an exception when a note does not exist`() {
        val ghost = Note(id = "missing", title = "x", description = "Groceries")
        assertThrows<IllegalArgumentException> {
            repository.patchNote(ghost)
        }
    }
}