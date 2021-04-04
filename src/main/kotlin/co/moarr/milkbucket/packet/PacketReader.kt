package co.moarr.milkbucket.packet

import br.com.devsrsouza.ktmcpacket.PacketContent
import br.com.devsrsouza.ktmcpacket.PacketState
import br.com.devsrsouza.ktmcpacket.packets.client.Handshake
import br.com.devsrsouza.ktmcpacket.packets.client.status.Ping
import br.com.devsrsouza.ktmcpacket.packets.client.status.Request
import br.com.devsrsouza.ktmcpacket.packets.server.status.Pong
import br.com.devsrsouza.ktmcpacket.packets.server.status.ServerListPing
import br.com.devsrsouza.ktmcpacket.readClientPacket
import br.com.devsrsouza.ktmcpacket.utils.minecraft
import br.com.devsrsouza.ktmcpacket.writePacket
import io.ktor.utils.io.*

object PacketReader {
    @Suppress("UNCHECKED_CAST")
    suspend fun handleHandshake(read: ByteReadChannel, write: ByteWriteChannel) {
        // handle handshake
        read.minecraft.readClientPacket(PacketState.HANDSHAKE) as PacketContent.Found<Handshake>

        // handle request
        read.minecraft.readClientPacket(PacketState.STATUS) as PacketContent.Found<Request>
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun handlePing(read: ByteReadChannel, write: ByteWriteChannel, response: String) {
        write.minecraft.writePacket(PacketState.STATUS, ServerListPing(response))
        val ping = read.minecraft.readClientPacket(PacketState.STATUS) as PacketContent.Found<Ping>
        write.minecraft.writePacket(PacketState.STATUS, Pong(ping.packet.payload))
    }
}