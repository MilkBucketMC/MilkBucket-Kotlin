package co.moarr.milkbucket.leb128

import java.io.DataInputStream
import java.io.DataOutputStream
import kotlin.experimental.and
import kotlin.experimental.or

object VarInt {
    fun readVarInt(input: DataInputStream): Int {
        var numRead = 0
        var result = 0
        var read: Byte
        do {
            read = input.readByte()
            val value: Int = (read and 127).toInt()
            result = result or (value shl 7 * numRead)
            numRead++
            if (numRead > 5) {
                throw RuntimeException("VarInt is too big")
            }
        } while ((read and 128.toByte()).toInt() != 0)
        return result
    }

    fun writeVarInt(value: Int, output: DataOutputStream) {
        var value = value
        do {
            var temp = (value and 127).toByte()
            value = value ushr 7
            if (value != 0) {
                temp = temp or 128.toByte()
            }
            output.writeByte(temp.toInt())
        } while (value != 0)
    }

    fun readVarLong(input: DataInputStream): Long {
        var numRead = 0
        var result: Long = 0
        var read: Byte
        do {
            read = input.readByte()
            val value = (read and 127).toLong()
            result = result or (value shl 7 * numRead)
            numRead++
            if (numRead > 10) {
                throw RuntimeException("VarLong is too big")
            }
        } while ((read and 128.toByte()).toInt() != 0)
        return result
    }

    fun writeVarLong(value: Long, output: DataOutputStream) {
        var value = value
        do {
            var temp = (value and 127).toByte()
            value = value ushr 7
            if (value != 0L) {
                temp = (temp or 128.toByte())
            }
            output.writeByte(temp.toInt())
        } while (value != 0L)
    }

}