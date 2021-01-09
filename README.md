# datagokr-api-b090041-lrsrcldinfoservice-proxy-app

A proxy application for `http://apis.data.go.kr/B090041/openapi/service/LrsrCldInfoService`.

See [datagokr-api-b090041-lrsrcldinfoservice-client-spring](https://github.com/jinahya/datagokr-api-b090041-lrsrcldinfoservice-client-spring)
that this application uses.

## Application properties

See `application.yml.example`.

```yaml
datagokr:
  api:
    b090041:
      lrsrcldinfoservice:
        client:
          base-url: ... # defaults to http://.../LrsrCldInfoService
          connect-timeout-millis: ... # defaults to 20
          write-timeout-seconds: ... # defaults to 2
          read-timeout-seconds: ... # defaults to 10
          service-key: ... # REQUIRED
```

## Application endpoints

All endpoints `Accept`s `application/json` and `application/x-ndjson`.

|Method|URI|Notes|
|------|----|-----|
|`GET`|`/v2/lunar/{year}`|Reads all items in specified `{year}` in lunar calendar|
|`GET`|`/v2/lunar/{year}/{month}`|Reads all items in specified `{month}` in lunar calendar|
|`GET`|`/v2/lunar/{year}/{month}/{day}`|Reads an item of specified date in lunar calendar|
|`GET`|`/v2/lunar/{year}`|Reads all items in specified `{year}` in solar calendar|
|`GET`|`/v2/lunar/{year}/{month}`|`application/json`, `application/x-ndjson`|Reads all items in specified `{month}` in solar calendar|
|`GET`|`/v2/lunar/{year}/{month}/{day}`|`application/json`, `application/x-ndjson`|Reads an item of specified date in solar calendar|

