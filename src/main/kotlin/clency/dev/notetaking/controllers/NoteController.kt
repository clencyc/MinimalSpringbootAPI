package clency.dev.notetaking.controllers

import clency.dev.notetaking.model.Note
import clency.dev.notetaking.services.NoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// request mappping says we just map this request to this controller
@RestController
@RequestMapping("/notes")
class NoteController (
    private val noteservice: NoteService
) {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @GetMapping
    fun getNotes(): List<Note> {
        return noteservice.getNotes()
    }

    @DeleteMapping("/{noteId}")
    fun deleteNote(@PathVariable() noteId: String) {
        noteservice.delete(noteId)
    }

    @PostMapping
    fun createNote(@RequestBody note: Note): Note {
        return noteservice.createNote(note)
    }

    @PatchMapping
    fun patchNote(note: Note): Note {
        return noteservice.patchNote(note)
    }
}
