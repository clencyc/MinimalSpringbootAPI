package clency.dev.notetaking

import clency.dev.notetaking.dataSources.NoteRepository
import clency.dev.notetaking.model.Note
import clency.dev.notetaking.services.NoteService
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NoteServiceTest {
    private val repository = mockk<NoteRepository>()
    private val service = NoteService(repository)

    @Test
    fun `getNotes delegates to repository`() {
        val notes = listOf(Note(id = "1", title = "Groceries", description = "Groceries"))
        every { repository.getNotes() } returns notes.toMutableList()

        val result = service.getNotes()
        assertEquals(notes, result)
    }

    @Test
    fun `createNote delegates to repository`() {
        val note = Note(id = "1", title = "Groceries", description = "Groceries")
        every { repository.addNote(note) } returns note
        val result = service.createNote(note)

        assertEquals(note, result)
        verify(exactly = 1) { repository.addNote(note) }

    }

    @Test
    fun `delete delegates to repository`() {
        every { repository.deleteNote("1") } just Runs
        service.delete("1")
        verify(exactly = 1) { repository.deleteNote("1")}
    }
}