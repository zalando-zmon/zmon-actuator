## Usage

Add the following dependency to your spring-boot project and remember to add security to your management endpoints or move management endpoints to a different non public port.

```
<dependency>
   <groupId>org.zalando.zmon</groupId>
   <artifactId>zmon-actuator</artifactId>
   <version>0.9.1</version>
</dependency>
```

## Result on /metrics endpoint

```
{  
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.count": 10,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.fifteenMinuteRate": 0.18076110580284566,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.fiveMinuteRate": 0.1518180485219247,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.meanRate": 0.06792011610723951,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.oneMinuteRate": 0.10512398137982051,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.snapshot.75thPercentile": 1173,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.snapshot.95thPercentile": 1233,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.snapshot.98thPercentile": 1282,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.snapshot.999thPercentile": 1282,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.snapshot.99thPercentile": 1282,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.snapshot.max": 1282,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.snapshot.mean": 1170,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.snapshot.median": 1161,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.snapshot.min": 1114,
  "zmon.response.200.GET.rest.api.v1.checks.all-active-check-definitions.snapshot.stdDev": 42,
}
```

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
