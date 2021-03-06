package io.scalecube.app.packages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/** Information provider about the environment and the package of this instance. */
public class PackageInfo {

  private final Properties properties = new Properties();

  /** Runtime Environment information provider. */
  public PackageInfo() {
    if (System.getenv("SC_HOME") != null) {
      String path = System.getenv("SC_HOME");

      try {
        FileInputStream in = new FileInputStream(new File(path, "package.properties"));
        properties.load(in);
      } catch (IOException exception) {
        System.out.println(
            "cannot open file: " + path + "package.properties cause:" + exception.getCause());
        defaultProps();
      }
    } else {
      InputStream stream = ClassLoader.getSystemResourceAsStream("package.properties");
      if (stream != null) {
        try {
          properties.load(stream);
          stream.close();
        } catch (IOException ioException) {
          defaultProps();
        }
      } else {
        defaultProps();
      }
    }
  }

  private void defaultProps() {
    properties.put("artifactId", "Development");
    properties.put("version", "Development");
    properties.put("groupId", "Development");
  }

  public String version() {
    return properties.getProperty("version");
  }

  public String groupId() {
    return properties.getProperty("groupId");
  }

  public String artifactId() {
    return properties.getProperty("artifactId");
  }

  public String name() {
    return properties.getProperty("name", artifactId());
  }

  public static String java() {
    return System.getProperty("java.version");
  }

  public static String os() {
    return System.getProperty("os.name");
  }

  /**
   * returns host name of the current running host.
   *
   * @return host name.
   */
  public static String hostname() {
    String result = getVariable("HOSTNAME", "unknown");
    if ("unknown".equals(result)) {
      return getHostName("unknown");
    } else {
      return result;
    }
  }

  private static String getHostName(String defaultValue) {
    String hostname = defaultValue;
    try {
      InetAddress addr = InetAddress.getLocalHost();
      hostname = addr.getHostName();
    } catch (UnknownHostException ex) {
      hostname = defaultValue;
    }
    return hostname;
  }

  /**
   * Process id of the jvm.
   *
   * @return the pid.
   */
  public static String pid() {
    String vmName = ManagementFactory.getRuntimeMXBean().getName();
    int pids = vmName.indexOf("@");
    String pid = vmName.substring(0, pids);
    return pid;
  }

  /** Returns API Gateway API. */
  public static int gatewayPort() {
    String port = getVariable("API_GATEWAY_PORT", "8081");
    return Integer.valueOf(port);
  }

  /**
   * Resolve seed address from environment variable or system property.
   *
   * @return seed address as string for example localhost:4801.
   */
  public static String[] seedAddress() {
    String list = getVariable("SC_SEED_ADDRESS", null);
    if (list != null && !list.isEmpty()) {
      String[] hosts = list.split(",");
      List<String> seedList =
          Arrays.asList(hosts)
              .stream()
              .filter(predicate -> !predicate.isEmpty())
              .map(mapper -> mapper.trim())
              .collect(Collectors.toList());
      return seedList.toArray(new String[seedList.size()]);
    } else {
      return null;
    }
  }

  /** Returns docker tag. */
  public static String dockerTag() {
    String tag = getVariable("DOCKER_TAG", "");
    return tag;
  }

  private static String getVariable(String name, String defaultValue) {
    if (System.getenv(name) != null) {
      return System.getenv(name);
    }
    if (System.getProperty(name) != null) {
      return System.getProperty(name);
    }
    return defaultValue;
  }
}
