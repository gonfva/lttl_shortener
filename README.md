[![Build status](https://travis-ci.org/gonfva/lttl_shortener.svg?branch=master')](https://travis-ci.org/gonfva/lttl_shortener?branch=master)

# lttl shortener

A web address shortener written in Scala. It started as a coding exercise. 
Please see the LICENSE for copyright information.

![image of the project frontend](./example_lttl.png)

## Prerequisites

+ Java 8 (I usually develop with OpenJDK)
+ SBT
+ Docker (optional)

## How to build/run it

The better way to build is generate a docker image.

`sbt docker:publishLocal`

once you've built the project, you can run the container with

`docker run -d -p 9000:9000 lttl-web-address-shortener:1.0`

and then visit http://localhost:9000
