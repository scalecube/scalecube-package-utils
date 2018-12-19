package io.scalecube.app.decoration;

import io.scalecube.app.packages.PackageInfo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Logo {

  public static class LogoHeader {
    private static final String space = "        ";
    private String value;

    public LogoHeader(String value) {
      this.value = space + value;
    }

    public String value() {
      return this.value;
    }
  }

  /**
   * Configure the site to be displayed in the logo.
   *
   * @return this builder
   */
  public static Builder from(PackageInfo packageInfo) {
    return new Builder()
        .tagVersion(packageInfo.version())
        .group(packageInfo.groupId())
        .artifact(packageInfo.artifactId())
        .javaVersion(PackageInfo.java())
        .osType(PackageInfo.os())
        .pid(PackageInfo.pid())
        .hostname(PackageInfo.hostname())
        .dockerTag(PackageInfo.dockerTag())
        .website();
  }

  public static class Builder {
    private int index = 0;
    private int startAt = 5;
    private Map<Integer, LogoHeader> headers = new HashMap<>();
    private String logoResourceName = "logo";

    /**
     * Configure the logo ascii art resource name.
     *
     * @param value the resource name
     * @return this builder
     */
    public Builder logoResource(String value) {
      try {
        Logo.class.getClassLoader().getResource(value);
        this.logoResourceName = value;
      } catch (Exception ignoredException) {
        // TODO: handle exception
      }
      return this;
    }

    /**
     * Configure the ip to be displayed in the logo.
     *
     * @return this builder
     */
    public Builder ip(String value) {
      headers.put(
          startAt + headers.size() + 1, new LogoHeader("IP Address: #1".replaceAll("#1", value)));
      return this;
    }

    /**
     * Configure the tag version to be displayed in the logo.
     *
     * @return this builder
     */
    public Builder tagVersion(String value) {
      headers.put(
          startAt + headers.size() + 1,
          new LogoHeader("ScaleCube #1 is Running.".replaceAll("#1", value)));
      return this;
    }

    /**
     * Configure the group to be displayed in the logo.
     *
     * @return this builder
     */
    public Builder group(String value) {
      headers.put(
          startAt + headers.size() + 1, new LogoHeader("Group: #1".replaceAll("#1", value)));
      return this;
    }

    /**
     * Configure the artifact to be displayed in the logo.
     *
     * @return this builder
     */
    public Builder artifact(String value) {
      headers.put(
          startAt + headers.size() + 1, new LogoHeader("Artifact: #1".replaceAll("#1", value)));
      return this;
    }

    public Builder port(String value) {
      headers.put(startAt + headers.size() + 1, new LogoHeader("Port: #1".replaceAll("#1", value)));
      return this;
    }

    public Builder osType(String value) {
      headers.put(startAt + headers.size() + 1, new LogoHeader("OS: #1".replaceAll("#1", value)));
      return this;
    }

    public Builder pid(String value) {
      headers.put(startAt + headers.size() + 1, new LogoHeader("PID: #1".replaceAll("#1", value)));
      return this;
    }

    public Builder javaVersion(String value) {
      headers.put(startAt + headers.size() + 1, new LogoHeader("Java: #1".replaceAll("#1", value)));
      return this;
    }

    public Builder header(String header) {
      headers.put(startAt + headers.size() + 1, new LogoHeader(header));
      return this;
    }

    /**
     * Configure the host name to be displayed in the logo.
     *
     * @return this builder
     */
    public Builder hostname(String value) {
      headers.put(
          startAt + headers.size() + 1, new LogoHeader("Host Name: #1".replaceAll("#1", value)));
      return this;
    }

    /**
     * Configure the docker image tag to be displayed in the logo.
     *
     * @return this builder
     */
    public Builder dockerTag(String value) {
      if (value != null && !value.isEmpty()) {
        headers.put(
            startAt + headers.size() + 1, new LogoHeader("Docker Tag: #1".replaceAll("#1", value)));
      }
      return this;
    }

    /**
     * Configure a commit id to be displayed in the logo.
     *
     * @return this builder
     */
    public Builder commitId(String value) {
      if (value != null && !value.isEmpty()) {
        headers.put(
            startAt + headers.size() + 1, new LogoHeader("commit id: #1".replaceAll("#1", value)));
      }
      return this;
    }

    /**
     * Configure the site to be displayed in the logo.
     *
     * @return this builder
     */
    public Builder website() {
      headers.put(startAt + headers.size() + 2, new LogoHeader("http://scalecube.io"));
      headers.put(startAt + headers.size() + 2, new LogoHeader("https://github.com/scalecube"));
      return this;
    }

    /** draw the scalecube logo. */
    public void draw() {
      try {
        Files.lines(
                Paths.get(Logo.class.getClassLoader().getResource(this.logoResourceName).toURI()))
            .forEach(this::pln);
      } catch (IOException | URISyntaxException ignoredException) {
        for (int i = 0; i < startAt + headers.size(); i++) {
          pln("");
        }
      }
    }

    private void pln(String line) {
      LogoHeader logoHeader = headers.get(index);
      if (logoHeader != null) {
        System.out.println(line + logoHeader.value());
      } else {
        System.out.println(line);
      }
      index++;
    }
  }

  public static Builder builder() {
    return new Builder();
  }
}
