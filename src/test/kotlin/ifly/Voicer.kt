package ifly

/**
 * Created by Administrator on 2017/6/19.
 */

import kotlinx.coroutines.experimental.delay
import org.junit.Test
import kotlinx.coroutines.experimental.runBlocking

class VoicerTest {
    @Test fun listeningTest () = runBlocking<Unit> {
        var voicer = Voicer()
        var words: String? = null

        voicer.listening { result -> words = result }
        //Thread.sleep(10000L)
        println(words)
        assert(!words.isNullOrEmpty())
    }

    @Test fun speakTest() = runBlocking<Unit> {
        var voicer = Voicer()
        var words = "这是一个测试！"

        voicer.speak(words, fun() {
            println("我说完了！")
        })

        println("语音测试结束了！")
    }
}
