package com.example.devintensive.model

import com.example.devintensive.extensions.humanizeDiff
import java.util.*

class ImageMessage(id:String,
                   from:User?,
                   chat:Chat,
                   isIncoming: Boolean = false,
                   date: Date = Date(),
                   var  image: String?
): BaseMessage(id, from, chat, isIncoming, date)
{
    override fun formatMessage(): String =
        "id:$id ${from?.firstName} ${if (isIncoming) "poluchil"
        else "otpravil"} izobrajeniye \"$image\" ${date.humanizeDiff()}"
}




