package my.dtu.androidprojectf21.logic

import android.content.ContentValues.TAG
import android.util.Log

private val word = ArrayList<String>()
private var randomWord: String = ""

private var guessedLetter: String = ""

private var hiddenWordArrays = Array<String>(0) { "it = $it" }
private var hiddenWord: String = ""

private var message: String = ""
private var counter: Int = 0


fun createWords() {
    word.add("JAMES")
    word.add("ROBERT")
    word.add("JOHN")
    word.add("MICHAEL")
    word.add("WILLIAM")
    word.add("DAVID")
    word.add("RICHARD")
    word.add("JOSEPH")
    word.add("THOMAS")
    word.add("CHARLES")
    word.add("CHRISTOPHER")
}

fun getARandomWord(): String {
    createWords()
    var rnds = (0..10).random()
    randomWord = word.get(rnds)
    return randomWord
}

fun hiddenWord(): String {
    var wordToBeHidden = randomWord
    hiddenWordArrays = Array<String>(wordToBeHidden.length) { "it = $it" }

    for (i in wordToBeHidden.indices) {
        hiddenWordArrays[i] = "*"
    }
    val list: String = hiddenWordArrays.toList().toString()
        .replace("[", "")
        .replace("]", "")
        .replace(",", "")
    hiddenWord = list
    return list
}

fun findLetter(letter: String): Boolean{
    guessedLetter = letter
    var checker = false
    for (i in randomWord.indices){
        if (randomWord.get(i) == guessedLetter.get(0) && hiddenWordArrays[i] == "*"){
            checker = true
            message = "You guessed correctly. You get 1000 DKK"
            counter += 1
            Log.d(TAG, "findLetter: $counter")
//            break
        }
        else if (randomWord.get(i) == guessedLetter.get(0) && hiddenWordArrays[i] != "*"){
            checker = false
            message = "This letter has already been used!"
            break
        }else
            message = "This letter does not exist! You loss one life"
    }
    return checker
}

fun getMessage(): String {
    return message
}

fun getCounter(): Int{
    return counter
}

fun resetCounter() {
    counter = 0
}


fun updateHiddenWord(): String{
    for (i in randomWord.indices){
        if(randomWord.get(i) == guessedLetter.get(0) && hiddenWordArrays[i] == "*"){
            hiddenWordArrays[i] = guessedLetter.get(0).toString()
        }
    }
    return hiddenWordArrays.toList().toString()
        .replace("[", "")
        .replace("]", "")
        .replace(",", "")
}

fun isWon(): Boolean {
    return hiddenWordArrays.contains("*")
}
