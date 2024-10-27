package com.example.findmylocation_dipti_ict_04_05.model

import com.google.firebase.database.PropertyName


data class User_DIPTI_ICT_04_05(
    val userId:String,
    @get:PropertyName("DisplayName")
    @set:PropertyName("DisplayName")
    var displayname:String="",


    @get:PropertyName("email")
    @set:PropertyName("email")
    var email:String ="",

    @get:PropertyName("location")
    @set:PropertyName("location")
    var location:String =""

){
    constructor():this("","","")
}
