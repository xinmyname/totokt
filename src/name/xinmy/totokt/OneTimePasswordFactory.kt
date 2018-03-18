package name.xinmy.totokt

import java.time.Instant
import java.nio.*
import java.security.MessageDigest
import java.io.ByteArrayOutputStream

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

        val byteBuffer = ByteBuffer.allocate(java.lang.Long.BYTES)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)

        return OneTimePassword(
            HOTP(_sharedSecret, byteBuffer.putLong(tc).array()) % 1000000, 
            (tc + 1) * ti)
    } 

    private fun HOTP(key:ByteArray, counter:ByteArray): Int {

        return Truncate(HMAC(key, counter)) and 0x7FFFFFFF
    }

    private fun XOR(dataIn:ByteArray, value:Byte): ByteArray {

        val dataOut = ByteArray(dataIn.size)

        for (i in 0 until dataIn.size) {
            dataOut[i] = (dataIn[i].toInt() xor value.toInt()).toByte()
        }

        return dataOut
    }

    private fun Concatenate(dataIn1:ByteArray, dataIn2:ByteArray): ByteArray {

        val outputStream = ByteArrayOutputStream()
        outputStream.write(dataIn1)
        outputStream.write(dataIn2)

        return outputStream.toByteArray()
    }

    private fun SHA1(dataIn:ByteArray): ByteArray {
        return MessageDigest.getInstance("SHA-1").digest(dataIn)
    }

    private fun HMAC(key:ByteArray, message:ByteArray): ByteArray {
        return Concatenate(SHA1(XOR(key, 0x5c)), Concatenate(XOR(key, 0x36), message))
    }

    private fun Truncate(dataIn:ByteArray): Int {

        val dataOut = ByteArray(4)
        val stride:Int = dataIn.size / 4
        val remnant:Int = dataIn.size % 4

        for (y in 0 until 4) {

            val o:Int = y * stride

            for (x in 0 until stride) {                
                dataOut[y] = (dataOut[y].toInt() xor dataIn[o + x].toInt()).toByte()
            }
        }

        for (x in 0 until remnant) {
            dataOut[x] = (dataOut[x].toInt() xor dataIn[stride*4 + x].toInt()).toByte()
        }

        val byteBuffer = ByteBuffer.wrap(dataOut)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)

        return byteBuffer.getInt()
    }
} 
