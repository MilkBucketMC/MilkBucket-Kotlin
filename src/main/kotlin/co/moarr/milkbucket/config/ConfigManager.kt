package co.moarr.milkbucket.config

import com.moandjiezana.toml.Toml
import com.moandjiezana.toml.TomlWriter
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.properties.Delegates
import kotlin.system.exitProcess

class ConfigManager(file: String){
    private var f : File by Delegates.notNull()
    var config: Config by Delegates.notNull()

    private val LOG = LoggerFactory.getLogger(this::class.java)

    init {
        this.f = File(file)
        if(!this.f.exists()) createConfig(file) else {
            this.config = Toml().read(this.f).to(Config::class.java)
        }
    }

    private fun createConfig(filename: String) {
        LOG.error("Creating configuration file with the name of $filename...")
        val writer = TomlWriter()
        writer.write(Config(), this.f)
        LOG.error("Created $filename, restart the application.")
        exitProcess(1)
    }
}