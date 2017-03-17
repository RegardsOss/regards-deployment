#!/usr/bin/env groovy

def cli = new CliBuilder(usage: 'install_registry.groovy [-h] -i <regards install directory> -n <microservice id> -t <microservice type>')
// Create the list of options.
cli.with {
  h longOpt: 'help', 'Show usage information'
  i longOpt: 'regards-installdir', args: 1, argName: 'installdir', 'regards install directory'
  n longOpt: 'regards-microservice-id', args: 1, argName: 'microservice-id', 'microservice id'
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
String microserviceType = options.t;
int id = Integer.valueOf(options.n);

import java.nio.file.Paths;
import java.nio.file.Path;
import fr.cnes.regards.deployment.izpack.utils.MicroserviceConfigListAccessor;
import fr.cnes.regards.deployment.izpack.utils.model.MicroserviceConfigList;

// Microservice Registry Configuration file
Path registryFilePath = Paths.get(regardsInstallDir, "config/" + microserviceType + "_config.xml");
MicroserviceConfigList microserviceConfigList = MicroserviceConfigListAccessor.readFromFile(registryFilePath);
println microserviceConfigList.get(id).toString();

System.exit(0);
