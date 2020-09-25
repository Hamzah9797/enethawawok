package com.example.enethawawok.activities

import com.google.firebase.database.DatabaseReference

interface EnethawawokCallback {
    fun onSignout()
    fun onGetUserId(): String
    fun getUserDatabase(): DatabaseReference
    fun getChatDatabase(): DatabaseReference
    fun profileComplete()
    fun startActivityForPhoto()
}