package ifly

/**
 * Created by Administrator on 2017/6/18.
 */

import com.beust.klaxon.*
import com.iflytek.cloud.speech.*
import kotlinx.coroutines.experimental.delay

class Voicer {

    init {
        SpeechUtility.createUtility(SpeechConstant.APPID + "=593b6680")
    }

    // onResult  聆听结束的回调函数
    suspend fun listening(onResult: (String) -> Unit) {
        var mIat: SpeechRecognizer = SpeechRecognizer.createRecognizer()
        mIat.setParameter(SpeechConstant.DOMAIN, "iat")
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn")
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin")

        var recoginzerlistener = VoicerRecognizerListener()
        recoginzerlistener.OnResult = onResult

        println("开始聆听")
        mIat.startListening(recoginzerlistener)

        while (mIat.isListening) {
            delay(500L)
        }
    }

    class VoicerRecognizerListener(
            var results: MutableList<String> = mutableListOf(),
            var parser: Parser = Parser()) : RecognizerListener {

        var OnResult = fun(result: String) {}

        override fun onVolumeChanged(p0: Int) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onResult(result: RecognizerResult, isLast: Boolean) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            if (isLast) {
                OnResult(results.joinToString(""))
            } else {
                var builder = StringBuilder(result.resultString)
                var json = parser.parse(builder) as JsonObject
                var ws = json.array<JsonObject>("ws") as JsonArray<JsonObject>
                var word = ws.map {
                    var cws = it.array<JsonObject>("cw") as JsonArray<JsonObject>
                    cws.map { it.string("w") }.joinToString("")
                }.joinToString("")
                results.add(word)
            }
        }

        override fun onBeginOfSpeech() {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onEvent(p0: Int, p1: Int, p2: Int, p3: String?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onEndOfSpeech() {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onError(p0: SpeechError?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }


    suspend fun speak(word: String, OnResult: () -> Unit = fun() {}) {
        var mTts = SpeechSynthesizer.createSynthesizer()

        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan")
        mTts.setParameter(SpeechConstant.SPEED, "40")
        mTts.setParameter(SpeechConstant.VOLUME, "80")

        var mSynthesizerListener = VoicerSynthesizerListener()
        mSynthesizerListener.OnResult = OnResult

        mTts.startSpeaking(word, mSynthesizerListener)

        while (mTts.isSpeaking)
        {
            delay(500L)
        }
    }

    class VoicerSynthesizerListener :SynthesizerListener {

        var OnResult = fun() {}

        override fun onBufferProgress(p0: Int, p1: Int, p2: Int, p3: String?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSpeakBegin() {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSpeakProgress(p0: Int, p1: Int, p2: Int) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onEvent(p0: Int, p1: Int, p2: Int, p3: Int, p4: Any?, p5: Any?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSpeakPaused() {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSpeakResumed() {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onCompleted(error: SpeechError?) {
            OnResult()
        }

    }
}