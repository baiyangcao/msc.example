package demo

import ifly.Voicer
import kotlinx.coroutines.experimental.*
import turing.TuringBot

/**
 * Created by Administrator on 2017/6/11.
 */

fun main(args:Array<String>) = runBlocking<Unit> {
    var voicer = Voicer()
    var bot = TuringBot()

    voicer.speak("嗨，我是小图！")

    var result = ""
    while (isActive && !result.contains("再见")) {
        voicer.listening { words -> result = words }
        var response = bot.talk(result)
        voicer.speak(response)
    }
}