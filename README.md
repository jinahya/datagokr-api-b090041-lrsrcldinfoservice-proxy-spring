# datagokr-api-b090041-lrsrcldinfoservice-proxy-app

A proxy application for `http://apis.data.go.kr/B090041/openapi/service/LrsrCldInfoService`.

See [datagokr-api-b090041-lrsrcldinfoservice-client-spring](https://github.com/jinahya/datagokr-api-b090041-lrsrcldinfoservice-client-spring)
that this application uses.

## Application properties

See `application.yml.example` and `ehcache-config.xml`.

### Database and Cache

This application requires database and cache. H2 and Ehcache are already added as dependencies. (
See `ehcache-config.xml` and tune if required.)

#### Cleansing

Old (and new) entries in the database are periodically purged.

```yaml
spring:
  cache:
    jcache:
      config: classpath:ehcache-config.xml
  datasource:
    driverClassName: org.h2.Driver
    password: password
    url: jdbc:h2:mem:testdb
    username: sa
  h2:
    console:
      enabled: true
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
```

### Client

You should provide at least the `...service-key` property.

```yaml
datagokr:
  api:
    b090041:
      lrsrcldinfoservice:
        client:
          base-url: ...               # defaults to http://.../LrsrCldInfoService
          connect-timeout-millis: ... # defaults to 2000
          write-timeout-seconds: ...  # defaults to 2
          read-timeout-seconds: ...   # defaults to 10
          service-key: ...            # REQUIRED
```

## Application endpoints

All endpoints `Accept`s `application/json` and `application/x-ndjson`.

|Method|URI|Notes|
|------|----|-----|
|`GET`|`/v2/lunar/{year}`|Reads all items in specified `{year}` in lunar calendar|
|`GET`|`/v2/lunar/{year}/{month}`|Reads all items in specified `{month}` in lunar calendar|
|`GET`|`/v2/lunar/{year}/{month}/{day}`|Reads items of specified date in lunar calendar|
|`GET`|`/v2/solar/{year}`|Reads all items in specified `{year}` in solar calendar|
|`GET`|`/v2/solar/{year}/{month}`|Reads all items in specified `{month}` in solar calendar|
|`GET`|`/v2/solar/{year}/{month}/{day}`|Reads items of specified date in solar calendar|


