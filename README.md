# Scala Fukuoka Exporter

# command line options

Name     | Description | Default | note
---------|-------------|----|----
org.jboss.resteasy.port | Address on which to expose metrics and web interface | 8081 |

e.g.

```
$ java -Dorg.jboss.resteasy.port=18081 -jar /path/to/scala_fukuoka_exporter.jar
```

# how to build

```
$ ./gradlew fatJar
```
