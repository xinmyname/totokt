package name.xinmy.totokt

import java.time.Instant
import java.nio.ByteBuffer

class OneTimePasswordFactory(sharedSecret:ByteArray, ttlSeconds:Int = 30) { 

    val _sharedSecret = sharedSecret
    val _ttlSeconds = ttlSeconds

    constructor(sharedSecret:String, ttlSeconds:Int = 30) : this(sharedSecret.toByteArray(), ttlSeconds) {
    }

    fun generate(): OneTimePassword { 

        val to:Long = Instant.ofEpochSecond(0).getEpochSecond()
        val ti:Long = _ttlSeconds.toLong() 
        val un:Long = Instant.now().getEpochSecond()
        val tc = (un - to) / ti

        return OneTimePassword(
            HOTP(_sharedSecret, ByteBuffer.allocate(java.lang.Long.BYTES).putLong(tc).array()) % 1000000, 
            (tc + 1) * ti)
    } 

    private fun HOTP(key:ByteArray, counter:ByteArray): Int {
        // TODO: write this
        return 0
    }

    private fun XOR(dataIn:ByteArray, value:Byte): ByteArray {
        // TODO: Write this
        return dataIn
    }

    private fun Concatenate(dataIn1:ByteArray, dataIn2:ByteArray): ByteArray {
        // TODO: write this
        return dataIn1
    }

    private fun SHA1(dataIn:ByteArray): ByteArray {
        // TODO: write this
        return dataIn
    }

    private fun HMAC(key:ByteArray, message:ByteArray): ByteArray {
        // TODO: write this
        return message
    }

    private fun Truncate(dataIn:ByteArray): Int {
        // TODO: write this
        return 0
    }


} 
