package ru.skillbranch.devintensive.model

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User (

    val id : String,

    var firstName : String?,

    var lastName : String?,

    var avatar : String?,

    var rating : Int = 0,

    var respect : Int = 0,

    var lastVisit : Date? = Date(),

    var isOnline : Boolean = false

){



    companion object Factory {

        private var lastId : Int = -1

        fun makeUser(fullName: String?) : User {

            lastId++

            val (firstName, lastName) = Utils.parseFullName(fullName)

            return User(id = "$lastId", firstName = firstName, lastName = lastName, avatar = null)

        }



        fun makeUser(builder : Builder) : User {

            if (builder.id.all { it.isDigit() })

                lastId = builder.id.toInt()

            else

                lastId++

            return User(builder.id, builder.firstName, builder.lastName, builder.avatar, builder.rating,

                builder.respect, builder.lastVisit, builder.isOnline)

        }

    }



    data class Builder (

        var id : String = "${Factory.lastId + 1}",

        var firstName : String? = null,

        var lastName : String? = null,

        var avatar : String? = null,

        var rating : Int = 0,

        var respect : Int = 0,

        var lastVisit : Date? = Date(),

        var isOnline : Boolean = false

    ){

        fun id(id: String) = apply { this.id = id }

        fun firstName(firstName: String?) = apply { this.firstName = firstName }

        fun lastName(lastName: String?) = apply { this.lastName = lastName }

        fun avatar(avatar: String?) = apply { this.avatar = avatar }

        fun rating(rating: Int) = apply { this.rating = rating }

        fun respect(respect: Int) = apply { this.respect = respect }

        fun lastVisit(lastVisit: Date?) = apply { this.lastVisit = lastVisit }

        fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }

        fun build() = Factory.makeUser(this)

    }

}