package com.example.solicitarpermisoskotlin.retrofit.model

data class UserResponse(
    var id : Int = 0,
    var userName : String = "",
    var email : String = "",
    var address: Address = Address(),
    var phone : String = "",
    var webSite : String = "",
    var company: Company = Company()
){

}
