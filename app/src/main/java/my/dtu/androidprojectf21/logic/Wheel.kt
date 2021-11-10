package my.dtu.androidprojectf21.logic

private val word = ArrayList<String>()
private var spinner = 0
private var message = ""

fun spinWheelFunction() {
    spinner = (0..3).random()
}

fun getSpinner(): Int{
    return spinner
}

fun getSpinnerResult(): String{
    if (spinner == 0)
        message = "+1000"
    if (spinner == 1)
        message = "Extra life"
    if (spinner == 2)
        message = "minus life"
    if (spinner == 3)
        message = "Reset Score"
    return message
}


