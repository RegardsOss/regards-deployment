#!/usr/bin/env groovy

def cli = new CliBuilder(usage: 'install_registry.groovy [-h]')
// Create the list of options.
cli.with {
  h longOpt: 'help', 'Show usage information'
}

def options = cli.parse(args)
if (!options) {
  return
}
// Show usage text when -h or --help option is used.
if (options.h) {
  cli.usage()
  return
}

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

// Create working dirs
Files.createDirectory(Paths.get("logs"));
Files.createDirectory(Paths.get("run"));

System.exit(0);
