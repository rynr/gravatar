gravatar
========

Simple class to generate Gravatar URL for a email-address.

Usage:
------

```java
import org.rjung.util.Gravatar;

class GravatarExample {
  public void main(String[] args) {
    System.out.println(Gravatar.getInstance().imageUrl(
        "example@example.com"));
  }
}
```

There are other parameters you can set for the generation, like size, [`Default` and `Rating`](https://en.gravatar.com/site/implement/images/).
`Default` gives a alternative to the default gravatar-default-image, if no gravatar is defined.
Currently no self-defined image is supported (will be fixed in [Issue#3](https://github.com/rynr/gravatar/issues/3)).
To define a `Rating` or `Default`, just use other `imageUrl`-methods providing these fields.

```java
import org.rjung.util.Gravatar;
import org.rjung.util.gravatar.Default;
import org.rjung.util.gravatar.Rating;

class GravatarExample {
  public void main(String[] args) {
    System.out.println(Gravatar.getInstance().imageUrl(
        "example@example.com", 123, Default.MONSTERID, Rating.X));
  }
}
```

Dependency
----------
Once it's commited to central maven, you can use the following dependency-definitions:

| framework |          |
| --------: | -------- |
| maven     | `<dependency><groupId>org.rjung.util</groupId><artifactId>gravatar</artifactId><version>0.1</version></dependency>` |
| ivy       | `<dependency org="org.rjung.util" name="gravatar" rev="0.1"/>` |
| grape     | `@Grab(group='org.rjung.util', module='gravatar', version='0.1')` |
| gradle    | `'org.rjung.util:gravatar:0.1'` |
| buildr    | `'org.rjung.util:gravatar:jar:0.1'` |
| sbt       | `libraryDependencies += "org.rjung.util" % "gravatar" % "0.1"` |
| leiningen | `[org.rjung.util/gravatar "0.1"]` |


Links:
------

 - [Info](https://rynr.github.io/gravatar/)
 - [Github](https://github.com/rynr/gravatar)
 - [Bugs](https://github.com/rynr/gravatar/issues)
 - [![Build Status](https://travis-ci.org/rynr/gravatar.svg?branch=master)](https://travis-ci.org/rynr/gravatar)

