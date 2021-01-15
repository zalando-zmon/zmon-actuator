![Maven Central](https://img.shields.io/maven-central/v/org.zalando.zmon/zmon-actuator.svg)

ZMON source code on GitHub is no longer in active development. Zalando will no longer actively review issues or merge pull-requests.

ZMON is still being used at Zalando and serves us well for many purposes. We are now deeper into our observability journey and understand better that we need other telemetry sources and tools to elevate our understanding of the systems we operate. We support the `OpenTelemetry <https://opentelemetry.io>`_ initiative and recommended others starting their journey to begin there.

If members of the community are interested in continuing developing ZMON, consider forking it. Please review the licence before you do.

Uses [Micrometer](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-metrics) for collecting core metrics (e.g. jvm, cpu, etc.) wherever applicable as well as custom metrics for your application.

## Security

Add security to your management endpoints or move management endpoints to a different non-public port.

```
management:
  port: 7979
```
## Usage

Add the following dependency to your spring-boot project.

### Maven Dependency

```
<dependency>
   <groupId>org.zalando.zmon</groupId>
   <artifactId>zmon-actuator</artifactId>
   <version>${version}</version>
</dependency>
```

You need to have the base actuator dependency in place, too.

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## Spring Boot with Jersey vs SpringMVC

See the [Jersey module provided](https://github.com/zalando/zmon-actuator/tree/master/zmon-actuator-jaxrs-jersey)
and the [example-application](https://github.com/zalando/zmon-actuator/tree/master/samples/zmon-actuator-jetty-jersey).

Important is to use at least the specified Jersey versions as defined in the POM. These will be included in spring boot 1.3+

## Metrics endpoint
Navigating to `/actuator/metrics` displays a list of available meter names. 
```
{
   "names":[
      "http.server.requests",
      "jvm.buffer.count",
      "jvm.buffer.memory.used",
      "jvm.buffer.total.capacity",
      "jvm.classes.loaded",
      "jvm.classes.unloaded",
      "jvm.gc.live.data.size",
      "jvm.gc.max.data.size",
      "jvm.gc.memory.allocated",
      "jvm.gc.memory.promoted",
      "jvm.gc.pause",
      "jvm.memory.committed",
      "jvm.memory.max",
      "jvm.memory.used",
      "jvm.threads.daemon",
      "jvm.threads.live",
      "jvm.threads.peak",
      "jvm.threads.states",
      "logback.events",
      "process.cpu.usage",
      "process.files.max",
      "process.files.open",
      "process.start.time",
      "process.uptime",
      "system.cpu.count",
      "system.cpu.usage",
      "system.load.average.1m",
      "tomcat.sessions.active.current",
      "tomcat.sessions.active.max",
      "tomcat.sessions.alive.max",
      "tomcat.sessions.created",
      "tomcat.sessions.expired",
      "tomcat.sessions.rejected",
      "zmon.response.200.GET.hello",
      "zmon.response.503.GET.hello"
   ]
}
```

You can drill down to view information about a particular meter by providing its name as a selector. For example: 
`/actuator/metrics/zmon.response.200.GET.hello` results in the following:

```
{
   "name":"zmon.response.200.GET.hello",
   "description":null,
   "baseUnit":"seconds",
   "measurements":[
      {
         "statistic":"COUNT",
         "value":67.0
      },
      {
         "statistic":"TOTAL_TIME",
         "value":0.406
      },
      {
         "statistic":"MAX",
         "value":0.294
      }
   ],
   "availableTags":[
      
   ]
}
```

## Release to Maven Central

    mvn clean release:prepare -Dresume=false

    mvn release:perform

## License

Copyright © 2015 Zalando SE

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
