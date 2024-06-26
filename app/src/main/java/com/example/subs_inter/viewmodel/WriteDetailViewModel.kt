package com.example.subs_inter.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.subs_inter.database.Note
import com.example.subs_inter.database.NoteRepository

class WriteDetailViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)
    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }
    fun update(note: Note) {
        mNoteRepository.update(note)
    }
    fun delete(note: Note) {
        mNoteRepository.delete(note)
    }
}