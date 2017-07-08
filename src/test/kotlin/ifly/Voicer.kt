package ifly

/**
 * Created by Administrator on 2017/6/19.
 */

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
}
