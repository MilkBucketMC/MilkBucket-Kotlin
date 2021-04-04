package co.moarr.milkbucket.config


data class Config(val port: Int = 25565, val info: ServerConfig = ServerConfig())

data class ServerConfig(val maxPlayers: Int = 4, val description: String = "A Minecraft server.")