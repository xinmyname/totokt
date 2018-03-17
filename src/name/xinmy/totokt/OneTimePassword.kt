package name.xinmy.totokt

import java.time.Instant

data class OneTimePassword(
    val value: Int,
    val expires: Long
) { 
    override fun toString(): String { 

        val valueText = String.format("%06d", value)
        val expiry = Instant.ofEpochSecond(0).plusSeconds(expires)
        return "$valueText - $expiry" }
} 
