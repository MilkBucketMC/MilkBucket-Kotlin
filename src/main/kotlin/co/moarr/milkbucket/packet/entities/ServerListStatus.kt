package co.moarr.milkbucket.packet.entities

data class ServerListStatus(val version: ServerListVersion = ServerListVersion(),
                            val players: ServerListPlayers = ServerListPlayers(),
                            val description: ServerListDescription = ServerListDescription()
)

data class ServerListVersion(val name: String = "1.15.2", val protocol: Int = 578)
data class ServerListPlayers(val max: Int = 4, val online: Int = 0, val sample: List<String> = listOf())
data class ServerListDescription(val text: String = "A Minecraft Server.") // TODO: make actual Chat object