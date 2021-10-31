package com.example.recipeapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel (application: Application): AndroidViewModel(application) {
    private val repository: NoteRepository
    private val notes: LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        notes = repository.getNotes
    }

    fun readFromDB(): LiveData<List<Note>> {
            return notes
    }

    fun addNote(title: String, author: String, ingredients: String, instructions: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addNote(Note(0, title, author, ingredients, instructions))
            readFromDB()
        }
    }

    fun editNote(noteID: Int,newTitle: String,newAuthor:String, newIng:String, newInst:String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateNote(Note(noteID,newTitle,newAuthor,newIng,newInst))
            readFromDB()
        }
    }

    fun deleteNote(noteID: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteNote(Note(noteID, "","","",""))
            readFromDB()
        }
    }
}