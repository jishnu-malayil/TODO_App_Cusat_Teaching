package com.example.simpletodocusatteaching.di

import com.example.simpletodocusatteaching.util.TODO_COLLECTION
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class TodoModule {

    @Provides
    @Singleton
    fun provideFireStore(): CollectionReference = Firebase.firestore.collection(TODO_COLLECTION)
}