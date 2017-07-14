package turing

/**
 * Created by Administrator on 2017/6/17.
 */

import com.beust.klaxon.*
import com.github.kittinunf.fuel.Fuel
import java.util.*

class TuringBot(
        val baseurl:String = "http://www.tuling123.com/openapi/api",
        val apikey:String = "22a3d4fe90ac0ea75392d3c3d1c81bbc"
) {
    var userid: UUID?
    var parser: Parser
    init {
        userid = UUID.randomUUID()
        parser = Parser()
    }

    fun talk(word:String):String {
        var text = ""
        var (_, _, result) = Fuel.post(baseurl, listOf("key" to apikey, "info" to word, "userid" to userid)).responseString()
        var json = parser.parse(StringBuilder(result.get())) as JsonObject
        var code = json.int("code")
        if(code == 100000) {
            text = json.string("text").orEmpty()
        } else {
            println(result)
            text = "抱歉！我还处理不了这样的结果"
        }

        return text
    }
}