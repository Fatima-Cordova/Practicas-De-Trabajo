package com.example.solicitarpermisoskotlin.retrofit.model

data class UserResponse(
    var id : Int = 0,
    var name : String = "",
    var username : String = "",
    var email : String = "",
    var address: Address = Address(),
    var phone : String = "",
    var website : String = "",
    var company: Company = Company()
){

}
