#!/usr/bin/env groovy
import java.nio.file.Files
import java.nio.file.Paths

def cli = new CliBuilder(usage: 'install_registry.groovy [-h]')

// Define the list of options
cli.with {
    h longOpt: 'help', 'Show usage information'
}

// Retrieve options
def options = cli.parse(args)

// End if options are incorrect
if (!options) return

// Show usage text when -h or --help option is used.
if (options.h) {
    cli.usage()
    return
}
// Create working dirs if they do not exist
if (!Files.exists(Paths.get("logs"))) {
    Files.createDirectory(Paths.get("logs"));
}
if (!Files.exists(Paths.get("run"))) {
    Files.createDirectory(Paths.get("run"));
}
if (!Files.exists(Paths.get("plugins"))) {
    Files.createDirectory(Paths.get("plugins"));
}

// Lets create some mandatory directories
// First lets read the properties file
Properties systemProperties = new Properties()
File systemPropertiesFile = new File(Paths.get("config", "system.properties").toUri())
systemPropertiesFile.withInputStream { systemProperties.load(it) }

// now, lets create some dirs only if corresponding microservice exists
Files.createDirectories(Paths.get(systemProperties."regards.microservices.workspace"))
if(Files.exists(Paths.get("bootstrap-storage.jar"))) {
    Files.createDirectories(Paths.get(systemProperties."regards.storage.cache"))
}
if(Files.exists(Paths.get("bootstrap-dam.jar"))) {
    Files.createDirectories(Paths.get(systemProperties."regards.dam.local.storage"))
}
System.exit(0);
