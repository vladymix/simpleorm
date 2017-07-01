# SimpleOrm
========

```java
private static import MyContract.UserColumns.*;

private static class UserObject {
  @Column(NAME_FIELD)
  private String name;

  @Column(AGE_FIELD)
  private Integer age;

  @Column(IMAGE_PROFILE_FIELD)
  private byte[] imageProfile;

  @Column(ADMINISTRATOR_FIELD)
  private boolean administrator;
  
  @Column(IMAGE_PROFILE_FIELD)
  private Double rating;
  
  public UserObject(){
    // need for simple orm
  }

  // getters and setters
}
```
And then use SimpleOrm:

```java
SimpleOrm orm = new SimpleOrm();
SomeObject o = orm.fromCursor(c, SomeObject.class);
ContentValues values = orm.toContentValues(o);

// in case you'll iterate over the whole cursor
SomeObject o = new SomeObject();
do {
  orm.fromCursor(c, o);
} while (c.moveToNext());

Usage
-----
Just add the dependency to your `build.gradle`:

```groovy
dependencies {
    compile 'org.vladymix.simpleorm:microorm:0.1.0'
}
```
