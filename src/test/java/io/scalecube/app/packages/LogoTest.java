package io.scalecube.app.packages;

import io.scalecube.app.decoration.Logo;
import org.junit.jupiter.api.Test;

public class LogoTest {

  PackageInfo packageInfo = new PackageInfo();

  @Test
  void verifyPackageInfo() throws Exception {
    
    Logo.builder()
        .tagVersion(packageInfo.name() + " " + packageInfo.version())
        .port("12345")
        .ip("192.168.100.101")
        .group(packageInfo.groupId())
        .artifact(packageInfo.artifactId())
        .javaVersion(packageInfo.java())
        .osType(packageInfo.os())
        .pid(packageInfo.pid())
        .hostname(packageInfo.hostname())
        .dockerTag("somesha256ofyourdocker")
        .commitId("somesha256ofyourcommitid")
        .website()
        .draw();
  }
  
  @Test
  void verifyLogo2() throws Exception {
    Logo
      .from(packageInfo)
      .dockerTag("Logo2")
      .logoResource("io/scalecube/app/logo2")
      .draw();
  }
}
