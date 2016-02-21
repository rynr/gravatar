gravatar
========

Simple class to generate Gravatar URL for a email-address.

Usage:
------

```java
import org.rjung.util.Gravatar;

class GravatarExample {
  public void main(String[] args) {
    System.out.println(Gravatar.forEmail("example@example.com").toUrl());
  }
}
```

There are other parameters you can set for the generation, like size,
[`Default` and `Rating`](https://en.gravatar.com/site/implement/images/).  
`Default` gives a alternative to the default gravatar-default-image, if no
gravatar is defined.  
To define a `Rating` or `Default`, add the information before you build the url
using `toUrl()`.  
If you need a `https`-URL, you can also set the protocol via `with(Protocol)`.
There are three Protocol-definitions, `HTTP` provides a `http://..`-url,
accordingly `HTTPS` provides the `https://..`-url. The default is `NONE`, the
url now starts with `://..`, a browser will choose the same protocol as
currently used.

```java
import org.rjung.util.Gravatar;
import org.rjung.util.gravatar.Default;
import org.rjung.util.gravatar.Protocol;
import org.rjung.util.gravatar.Rating;

class GravatarExample {
  public void main(String[] args) {
    System.out.println(
      Gravatar.forEmail("example@example.com")
          .with(Protocol.HTTPS)     // prepend https://
          .size(123)                // set the size to 123 pixel
          .defaultImage(Default.MM) // if not available show mystery man image
          .with(Rating.X)           // set rating to X
          .toUrl());
  }
}
```

Links:
------

 - [Info](https://rynr.github.io/gravatar/)
 - [API Doc](http://www.javadoc.io/doc/org.rjung.util/gravatar)
 - [Github](https://github.com/rynr/gravatar)
 - [Bugs](https://github.com/rynr/gravatar/issues)
 - [![Build Status](https://travis-ci.org/rynr/gravatar.svg?branch=master)](https://travis-ci.org/rynr/gravatar) [![Maven Central](https://img.shields.io/maven-central/v/org.rjung.util/gravatar.svg)](http://mvnrepository.com/artifact/org.rjung.util/gravatar) [![Github Releases](https://img.shields.io/github/downloads/rynr/gravatar/latest/total.svg)](https://github.com/rynr/gravatar/releases) [![codecov.io](https://codecov.io/github/rynr/gravatar/coverage.svg?branch=master)](https://codecov.io/github/rynr/gravatar?branch=master)

