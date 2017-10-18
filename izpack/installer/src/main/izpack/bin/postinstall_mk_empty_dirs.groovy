#!/usr/bin/env groovy

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

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

// Create working dirs if they do not exist
if(!Files.exists(Paths.get("logs"))) {
	Files.createDirectory(Paths.get("logs"));
}
if(!Files.exists(Paths.get("run"))) {
	Files.createDirectory(Paths.get("run"));
}
if(!Files.exists(Paths.get("plugins"))) {
	Files.createDirectory(Paths.get("plugins"));
}

System.exit(0);
