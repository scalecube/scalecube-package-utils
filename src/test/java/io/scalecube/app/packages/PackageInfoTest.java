package io.scalecube.app.packages;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Information provider about the environment and the package of this instance.
 *
 */

public class PackageInfoTest {

  PackageInfo packageInfo = new PackageInfo();

  @Test
  void verifyPackageInfo() throws Exception {
    Assertions.assertNotNull(packageInfo.artifactId());
  }

}
