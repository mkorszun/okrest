[![Build Status](https://travis-ci.org/mkorszun/okrest.png?branch=master)](https://travis-ci.org/mkorszun/okrest)
[![Coverage Status](https://coveralls.io/repos/mkorszun/okrest/badge.png)](https://coveralls.io/r/mkorszun/okrest)
[![Dependency Status](https://www.versioneye.com/user/projects/531c9e8aec13759611000128/badge.png)](https://www.versioneye.com/user/projects/531c9e8aec13759611000128)

okrest - Android JSON / REST client
======

This is simple JSON / REST client built on top of [okhttp](http://square.github.io/okhttp/) and [Jackson JSON processor](https://github.com/FasterXML/jackson) dedicated for android development.

# Usage

## Maven

~~~xml
<dependency>
    <groupId>com.okrest</groupId>
    <artifactId>okrest</artifactId>
    <version>0.0.3</version>
</dependency>
...
<repositories>
    <repository>
        <id>plant_store_sdk_repo</id>
        <url>https://github.com/mkorszun/okrest/raw/master/repo</url>
    </repository>
</repositories>
~~~

## Gradle

~~~gradle
repositories {
    ...
    mavenRepo urls: "https://github.com/mkorszun/okrest/raw/master/repo"
    ...
}
...
compile('com.okrest:okrest:0.0.3')
~~~

# Examples

### Models

Models should be standard Jackson annotated classes, e.g:

~~~java
class SampleModel {
    @JsonProperty("id")
    private long id;
}
~~~

### JSON client

~~~java
JSONClient<SampleModel> client = new JSONClient<SampleModel>("URL", SampleModel.class);
SampleModel result = client.send(HTTPMethod.GET);
~~~

### REST client

~~~java
RESTClient<SampleModel> client = new RESTClient("RESOURCE_URL", SampleModel.class);
client.create(new SampleModel());
SampleModel result = client.read(666);
ArrayList<SampleModel> results = client.list();
~~~
