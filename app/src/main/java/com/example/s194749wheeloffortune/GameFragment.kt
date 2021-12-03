package com.example.s194749wheeloffortune

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game.view.*
import kotlinx.android.synthetic.main.fragment_title.view.*


class GameFragment : Fragment() {
    var life = 5
    var hiddenword: String = ""
    var chosenword: String = ""
    var wheeloptions: String = ""
    var secretword: String = ""
    var score: Int = 0
    var newword: String = ""
    var guessedlet = ""

    val categories =
        mutableListOf("Category: Countries", "Category: Sports", "Category: Car manufacturers")
    val countries = mutableListOf("Spain", "Germany", "France")
    val sports = mutableListOf("Skating", "Hockey", "Bowling")
    val cars = mutableListOf("Honda", "Volkswagen", "Ford")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_game, container, false)

        binding.findViewById<TextView>(R.id.textView).text = "Lives = ${life}"
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.button2.setOnClickListener {

            spinthewheel()
            view.findViewById<TextView>(R.id.textView5).text = wheeloptions
            when (wheeloptions) {
                "extra life" -> {
                    life++
                    view.findViewById<TextView>(R.id.textView).text = "Lives = ${life}"
                }
                "bankrupt" -> {
                    life = 0
                    endgame(view)
                    view.findViewById<TextView>(R.id.textView6).text = "Score:$score"
                }
                "missed turn" -> {
                    life--
                    endgame(view)
                    view.findViewById<TextView>(R.id.textView).text = "Lives = ${life}"
                    // fun for hitting 0 and losing game
                    /* if(playerlost()){
                    navigate to end screen
                    }
                    */


                }
                "500", "1000", "2000" -> {
                    view.button2.visibility = View.GONE
                    view.button3.visibility = View.VISIBLE
                    view.editText.visibility = View.VISIBLE
                    view.button3.setOnClickListener {
                        var playerguess = view.findViewById<EditText>(R.id.editText).text.toString()
                        Log.d("playerguess",playerguess)
                        if (playerguess.length == 1) {
                            var pGuess = playerguess
                            Log.d("pGuess",pGuess)
                            if (chosenword.contains(pGuess, ignoreCase = true && !guessedlet.contains(pGuess))) {
                                guessedlet += pGuess
                                Log.d("guessed",guessedlet)
                                Log.d("chosenword",chosenword)
                                newSecret()
                                score += wheeloptions.toInt()
                                view.findViewById<TextView>(R.id.textView6).text = "Score:$score"

                                Log.d("secretword",secretword)
                                Log.d("newword",newword)
                                view.findViewById<TextView>(R.id.textView4).text = secretword
                                wingame(view)

                            }
                            else  if (!guessedlet.contains(pGuess)){
                                life--
                                view.findViewById<TextView>(R.id.textView).text = "Lives = ${life}"
                                endgame(view)
                            }
                            view.button3.visibility = View.GONE
                            view.editText.visibility = View.GONE
                            view.button2.visibility = View.VISIBLE
                        }
                    }

                }

            }
        }


            var text3 = view.findViewById<TextView>(R.id.textView3)
            text3.text = categories.random()
            when (text3.text.toString()) {
                "Category: Countries" -> hiddenword = countries.random()
                "Category: Sports" -> hiddenword = sports.random()
                "Category: Car manufacturers" -> hiddenword = cars.random()

            }
            chosenword = hiddenword
            repeat(chosenword.length) {
                secretword += "?"
            }

            view.findViewById<TextView>(R.id.textView4).text = secretword


        }
    private fun newSecret() {
        secretword = ""
        hiddenword.forEach {
                s -> secretword += (checkIfGuessed(s.toString()))
        }

        }
    private  fun wingame(view: View){
        if(secretword == chosenword){
            view.findNavController().navigate(R.id.action_gameFragment_to_wonGame)

        }
    }
    private fun endgame(view: View){
        if(life < 1 ){
            view.findNavController().navigate(R.id.action_gameFragment_to_lostgame)

        }
    }
    private fun checkIfGuessed(s: String) : String {
        return when (guessedlet.contains(s.toLowerCase())) {
            true -> s
            false -> "?"
        }
    }

        fun spinthewheel() {
            when ((0..19).random()) {
                0, 1, 2, 3, 4 -> wheeloptions = "500"
                5, 6, 7, 8, 9 -> wheeloptions = "1000"
                10, 11, 12, 13, 14 -> wheeloptions = "2000"
                15, 16 -> wheeloptions = "missed turn"
                17 -> wheeloptions = "bankrupt"
                18, 19 -> wheeloptions = "extra life"

            }
        }


    }






