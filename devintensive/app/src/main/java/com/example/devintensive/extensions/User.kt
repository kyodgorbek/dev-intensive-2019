package com.example.devintensive.extensions

import com.example.devintensive.model.User
import com.example.devintensive.model.UserView
import com.example.devintensive.utils.Utils


fun User.toUserView(): UserView{

    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status =  if(lastVisit == null) "Yesho ne raz nebil" else if(isOnline) "online" else "Posledniy raz bil ${lastVisit.humanizeDiff()}"

    return UserView(id,
        fullName = "$firstName $lastName",
        nickName = nickName,
        initials =initials ,
        avatar = avatar ,
        status = status)
}

