# scalecube-app-utils

# Usage
``` java

Logo.builder().tagVersion(packageInfo.version())
        .port(String.valueOf(seed.cluster().address().port()))
        .ip(seed.cluster().address().host())
        .group(packageInfo.groupId())
        .artifact(packageInfo.artifactId())
        .javaVersion(packageInfo.java())
        .osType(packageInfo.os())
        .pid(packageInfo.pid())
        .hostname(packageInfo.hostname())
        .website().draw();
        
```