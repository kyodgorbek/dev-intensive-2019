package ru.skillbranch.devintensive.utils

object Utils {



    private val translitMap: Map<Char, String> = hashMapOf(

        'а' to "a",

        'б' to "b",

        'в' to "v",

        'г' to "g",

        'д' to "d",

        'е' to "e",

        'ё' to "e",

        'ж' to "zh",

        'з' to "z",

        'и' to "i",

        'й' to "i",

        'к' to "k",

        'л' to "l",

        'м' to "m",

        'н' to "n",

        'о' to "o",

        'п' to "p",

        'р' to "r",

        'с' to "s",

        'т' to "t",

        'у' to "u",

        'ф' to "f",

        'х' to "h",

        'ц' to "c",

        'ч' to "ch",

        'ш' to "sh",

        'щ' to "sh'",

        'ъ' to "",

        'ы' to "i",

        'ь' to "",

        'э' to "e",

        'ю' to "yu",

        'я' to "ya"

    )



    fun parseFullName(fullName: String?) : Pair<String?, String?> {

        val processedString = fullName?.trim()

        if (processedString.isNullOrBlank())

            return null to null

        val parts : List<String>? = processedString.split("\\s+".toRegex())



        val firstName = parts?.getOrNull(0)

        val lastName = parts?.getOrNull(1)

        return firstName to lastName

    }



    fun transliteration(payload: String, divider: String = " "): String {

        var processedString = payload.trim()

        val charSet = processedString.toSet()

        for (char in charSet) {

            if (translitMap.containsKey(char.toLowerCase())) {

                processedString = if (char.isUpperCase())

                    processedString.replace("$char".toRegex(), translitMap.getValue(char.toLowerCase()).capitalize())

                else

                    processedString.replace("$char".toRegex(), translitMap.getValue(char))

            }

        }

        return processedString.replace(" ", divider)

    }



    fun toInitials(firstName: String?, lastName: String?) : String? {

        if (firstName?.trim().isNullOrBlank() && lastName?.trim().isNullOrBlank())

            return null

        val firstInitial = firstName?.trim()?.get(0)?.toUpperCase() ?: ""

        val secondInitial = lastName?.trim()?.get(0)?.toUpperCase() ?: ""

        return "$firstInitial$secondInitial"

    }

}