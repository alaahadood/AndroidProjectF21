package my.dtu.androidprojectf21.logic

private var lives = 0
private var scores = 0


fun setLife(life: Int){
    lives = life
}

fun setScore(score: Int){
    scores = score
}

fun extraLife(life: Int){
    lives = life
    lives ++
}

fun minusLife(life: Int){
    lives = life
    lives --
}

fun extra1000(score: Int){
    scores = score
    scores += 1000
}


fun resetScore(score: Int){
    scores = score
    scores = 0
}


fun updateLives(): Int{
    return lives
}

fun updateScore(score: Int) {
    scores = score
}

fun getScore(): Int {
    return scores
}