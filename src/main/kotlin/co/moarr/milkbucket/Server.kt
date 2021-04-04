package co.moarr.milkbucket

import br.com.devsrsouza.ktmcpacket.utils.MinecraftVarIntEncoder
import co.moarr.milkbucket.config.Config
import co.moarr.milkbucket.config.ConfigManager
import co.moarr.milkbucket.packet.PacketReader
import co.moarr.milkbucket.packet.entities.ServerListDescription
import co.moarr.milkbucket.packet.entities.ServerListPlayers
import co.moarr.milkbucket.packet.entities.ServerListStatus
import com.google.gson.Gson
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory


object Server {
    val LOG = LoggerFactory.getLogger(this::class.java)
    val gson = Gson()

    lateinit var config: Config

    @JvmStatic
    fun main(args: Array<String>) {
        LOG.info("Starting MilkBucket...")
        val manager = ConfigManager("server.toml")
        config = manager.config

        runBlocking {
            val server = aSocket(ActorSelectorManager(Dispatchers.IO))
                .tcp()
                .bind(port = config.port)
            LOG.info("Started MilkBucket at ${server.localAddress}!")

            while (true) {
                val socket = server.accept()

                launch {
                    val input = socket.openReadChannel()
                    val output = socket.openWriteChannel()

                    val resp = ServerListStatus(
                        players = ServerListPlayers(
                            config.info.maxPlayers,
                            1
                        ),
                        description = ServerListDescription(
                            text = config.info.description
                        )
                    )

                    val respString = gson.toJson(resp).trimIndent()

                    PacketReader.handleHandshake(input, output)
                    PacketReader.handlePing(input, output, respString)
                }
            }
        }
    }
}