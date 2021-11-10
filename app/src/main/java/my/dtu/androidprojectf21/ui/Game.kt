package my.dtu.androidprojectf21.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import my.dtu.androidprojectf21.R
import my.dtu.androidprojectf21.databinding.FragmentGameBinding
import my.dtu.androidprojectf21.logic.*
import my.dtu.androidprojectf21.logic.getARandomWord

class Game : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var handler = Handler()

    private var lives: Int = 5
    private var scores: Int = 0

    private lateinit var wordToBeGuessedS: String
    private var points: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLife(lives)
        setScore(scores)
        updateLifeScore()
        getARandomWord()
        binding.wordToBeGuessed.text = hiddenWord()
    }


    override fun onStart() {
        super.onStart()

        // wheel spinner
        binding.apply {
            buttonSpinWheel.setOnClickListener {
                showSpinWheelUI()
                spinWheelFunction()
                delay()
                delayLogic()
            }
        }

    }


    private fun showSpinWheelUI() {
        binding.apply {
            linearLayout.visibility = View.GONE
            spinWheelAnim.visibility = View.VISIBLE
        }
    }


    private fun showGuesseUI() {
        binding.apply {
            Toast.makeText(activity, getSpinnerResult(), Toast.LENGTH_SHORT).show()
            spinWheelAnim.visibility = View.GONE
            buttonSpinWheel.visibility = View.GONE
            linearLayout.visibility = View.VISIBLE
            buttonGuesse.visibility = View.VISIBLE
            editTextLetter.visibility = View.VISIBLE
        }
    }

    private fun hideGuesseUI() {
        binding.apply {
            Toast.makeText(activity, getSpinnerResult(), Toast.LENGTH_SHORT).show()
            spinWheelAnim.visibility = View.GONE
            buttonSpinWheel.visibility = View.VISIBLE
            linearLayout.visibility = View.VISIBLE
            buttonGuesse.visibility = View.GONE
            editTextLetter.visibility = View.GONE
        }
    }

    private fun spinAgain() {
        binding.apply {
            buttonGuesse.visibility = View.GONE
            editTextLetter.visibility = View.GONE
            editTextLetter.setText("")
            buttonSpinWheel.visibility = View.VISIBLE
        }
    }

    private fun gameOver() {
        binding.apply {
            linearLayout.visibility = View.GONE
        }
    }

    private fun delay() {
        var wheelSpinner = Runnable {
            showGuesseUI()
        }
        handler.postDelayed(wheelSpinner, 2000)
    }


    private fun delayLogic() {
        var delay = Runnable {
            binding.apply {
                // activate wheel
                // extra 1000
                if (getSpinner() == 0) {
                    extra1000(scores)
                    scores = getScore()
                    binding.playerScore.text = "Score: $scores"
                    showGuesseUI()

                    buttonGuesse.setOnClickListener {
                        gameLogic()
                    }
                }

                // extra life
                if (getSpinner() == 1) {
                    extraLife(lives)
                    lives = updateLives()
                    binding.playerLives.text = "Lives: $lives"
                    showGuesseUI()
                    buttonGuesse.setOnClickListener {
                        gameLogic()
                    }
                }

                // minus life
                if (getSpinner() == 2) {
                    minusLife(lives)
                    lives = updateLives()
                    binding.playerLives.text = "Lives: $lives"
                    buttonSpinWheel.visibility = View.VISIBLE
                    hideGuesseUI()
                    if (lives == 0)
                        gameOver()
                }

                // reset score
                if (getSpinner() == 3) {
                    resetScore(scores)
                    scores = getScore()
                    binding.playerScore.text = "Score: $scores"
                    showGuesseUI()
                    buttonGuesse.setOnClickListener {
                        gameLogic()
                    }
                }
            }
        }
        handler.postDelayed(delay, 2000)
    }


    private fun updateLifeScore() {
        // update ui
        binding.apply {
            playerLives.text = "Lives: ${updateLives()}"
            playerScore.text = "Score: ${getScore()}"
        }
    }



    private fun getExtraPoints() {
        var x = Runnable {
            binding.extraPointsAnim.visibility = View.GONE
        }
        handler.postDelayed(x, 2000)
    }

    private fun won() {
        binding.gongAnim.visibility = View.VISIBLE
        var x = Runnable {
            binding.gongAnim.visibility = View.GONE
            findNavController().navigate(R.id.action_game_to_win)
        }
        handler.postDelayed(x, 4000)
    }


    private fun gameLogic(){
        binding.apply {
            if (editTextLetter.text.trim().toString().isNotEmpty()) {
                if (findLetter(editTextLetter.text.toString())) {
                    Toast.makeText(activity, getMessage(), Toast.LENGTH_LONG).show()
                    points = getCounter() * 1000
                    scores += points
                    updateScore(scores)
                    wordToBeGuessed.text = updateHiddenWord()
                    playerScore.text = "Score: ${getScore()} DKK"
                    extraPointsAnim.visibility = android.view.View.VISIBLE
                    getExtraPoints()
                    resetCounter()
                    if (!isWon())
                        won()
                    else {
                        spinAgain()
                    }
                } else if (!findLetter(editTextLetter.text.toString()) && lives == 1) {
                    minusLife(lives)
                    lives = updateLives()
                    binding.playerLives.text = "Lives: $lives"
                    buttonSpinWheel.visibility = View.VISIBLE
                    hideGuesseUI()
                    Toast.makeText(activity, "You've lost the game", Toast.LENGTH_SHORT).show()
                    gameOver()
                    var lost= Runnable{
                        findNavController().navigate(R.id.action_game_to_loss)
                    }
                    handler.postDelayed(lost, 2000)
                } else if (!findLetter(editTextLetter.text.toString())) {
                    minusLife(lives)
                    lives = updateLives()
                    binding.playerLives.text = "Lives: $lives"
                    buttonSpinWheel.visibility = View.VISIBLE
                    hideGuesseUI()
                    spinAgain()
                }
            }
        }
    }


}