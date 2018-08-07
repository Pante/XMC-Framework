<p align = "center">
  <img src = "https://i.imgur.com/TA6hOBq.png">
</p>


A collection of open source libraries & tools devloped by Karus Labs.

[![Travis-CI](https://travis-ci.org/Pante/Karus-Commons.svg?branch=master)](https://travis-ci.org/Pante/Karus-Commons)
[![Maintainability](https://api.codeclimate.com/v1/badges/d03deef9f37d3d90636d/maintainability)](https://codeclimate.com/github/Pante/Karus-Commons/maintainability)
[![Codecov](https://codecov.io/gh/Pante/Karus-Commons/branch/master/graph/badge.svg)](https://codecov.io/gh/Pante/Karus-Commons)
[![Stable Source Code](https://img.shields.io/badge/stable-branch-blue.svg)](https://github.com/Pante/Karus-Commons/tree/stable)

***
#### Karus-Commons
[![stable](https://img.shields.io/badge/stable-3.1.0--SNAPSHOT-blue.svg)](https://repo.karuslabs.com/#browse/browse/components:karus-commons:e67efc5804a3cb7a88b3526c0bd0b389)
[![maven](https://img.shields.io/maven-metadata/v/https/repo.karuslabs.com/repository/karus-commons/snapshots/com/karuslabs/commons/maven-metadata.xml.svg)](https://repo.karuslabs.com/#browse/browse/components:karus-commons:e67efc5804a3cb7a1c4a6d7ac5b49f2a)
[![javadoc](https://img.shields.io/badge/javadoc-3.1.0--SNAPSHOT-brightgreen.svg)](https://repo.karuslabs.com/repository/karus-commons-project/3.1.0-SNAPSHOT/commons/apidocs/overview-summary.html)
```XML
<repository>
  <id>karus-commons</id>
  <url>https://repo.karuslabs.com/repository/karus-commons/snapshots/</url>
</repository>

<dependencies>
  <dependency>
      <groupId>com.karuslabs</groupId>
      <artifactId>commons</artifactId>
      <version>3.1.0-SNAPSHOT</version>
  </dependency>
</dependencies>
```

_Annotation checkers_:
```XML
<annotationProcessors>
    <annotationProcessor>com.karuslabs.commons.command.annotation.checkers.CommandChecker</annotationProcessor>
    <annotationProcessor>com.karuslabs.commons.command.annotation.checkers.CompletionChecker</annotationProcessor>
    <annotationProcessor>com.karuslabs.commons.command.annotation.checkers.NamespaceChecker</annotationProcessor>
    <annotationProcessor>com.karuslabs.commons.locale.annotation.checkers.ResourceChecker</annotationProcessor>
</annotationProcessors>
```