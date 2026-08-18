package clency.dev.notetaking

import clency.dev.notetaking.controllers.NoteController
import clency.dev.notetaking.model.Note
import clency.dev.notetaking.services.NoteService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.MediaType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import tools.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status



@WebMvcTest(NoteController::class)
class NoteControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @MockkBean lateinit var service: NoteService
    @Autowired
    private lateinit var objectMapper: ObjectMapper


    @Test
    fun `GET notes returns 200 with the list of notes`() {
        val notes = listOf(Note(id = "1", title = "Groceries", description = "description"))
        every { service.getNotes() } returns notes
        mockMvc.get("/notes").andExpect {
            status { isOk() }
            jsonPath("$[0].title") { value("Groceries") }
        }
    }

    @Test
    fun `POST notes returns 200 with the created note`() {
        val note = Note(id = "1", title = "Groceries", description = "Buy milk")

        every { service.createNote(any()) } returns note

        // Fluent builder style chains methods with dots directly instead of code blocks
        mockMvc.perform(
            post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("Groceries"))
    }
    @Test
    fun `DELETE notes removes a note`() {
        every { service.delete("1") } just Runs

        mockMvc.delete("/notes/1").andExpect {
            status { isOk() }
        }
        verify(exactly = 1) { service.delete("1") }
    }

    @Test
    fun `DELETE notes returns 404 when note does not exist`() {
        every { service.delete("missing") } throws NoSuchElementException("No note found with id missing")

        mockMvc.delete("/notes/missing").andExpect {
            status { isNotFound() }
        }
    }
}