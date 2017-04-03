#!/usr/bin/env groovy

def cli = new CliBuilder(usage: 'install_registry.groovy [-h] -i <regards install directory> -c <configuration string> -t <microservice type>')
// Create the list of options.
cli.with {
  h longOpt: 'help', 'Show usage information'
  i longOpt: 'regards-installdir', args: 1, argName: 'installdir', 'regards install directory'
  c longOpt: 'regards-microservice-config', args: 1, argName: 'microservice-config', 'configuration string'
  t longOpt: 'regards-microservice-type', args: 1, argName: 'microservice-type', 'microservice type'
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

// TODO GBN : Faire la lecture précise des paramètres et notamment les asserts pour vérifier les paramètres 
String regardsInstallDir = options.i;
String xmlConfig = options.c;
String microserviceType = options.t;

import java.nio.file.Paths;
import java.nio.file.Path;

Thread.currentThread().getContextClassLoader().addURL((Paths.get(regardsInstallDir, "lib/regards-izpack-utils.jar").toUri().toURL()));
Thread.currentThread().getContextClassLoader().URLs.each{ println it }

println "Creation of Registry microservice configuration file"

// Print Config input
println "Xml input configuration : " + xmlConfig;

// Microservice Registry Configuration file
Path registryFilePath = Paths.get(regardsInstallDir, "config/" + microserviceType + "_config.xml");
Class.forName("fr.cnes.regards.deployment.izpack.utils.MicroserviceConfigListAccessor").writeToFile(xmlConfig, registryFilePath);

System.exit(0);
