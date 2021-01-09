# datagokr-api-b090041-lrsrcldinfoservice-proxy-app

A proxy application for `http://apis.data.go.kr/B090041/openapi/service/LrsrCldInfoService`.

See [datagokr-api-b090041-lrsrcldinfoservice-client-spring](https://github.com/jinahya/datagokr-api-b090041-lrsrcldinfoservice-client-spring)
that this application uses.

## Application properties

See `application.yml.example`.

|Key|Default value|Description|
|---|-------------|-----------|
|`datagokr.api.b090041.lrsrcldinfoservice.client.base-url`|`http://.../LrsrCldInfoService`||
|`datagokr.api.b090041.lrsrcldinfoservice.client.connect-timeout-millis`|`2000`||
|`datagokr.api.b090041.lrsrcldinfoservice.client.write-timeout-seconds`|`2`||
|`client.datagokr.api.b090041.lrsrcldinfoservice.client.read-timeout-second`|`10`||
|`datagokr.api.b090041.lrsrcldinfoservice.client.service-key`||Provided by the service provider|

## Application endpoints

|Method|URI|Accept|Notes|
|------|----|------|-----|
|`GET`|`/v2/lunar/{year}`|`application/json`, `application/x-ndjson`|Reads all items in specified `{year}` in lunar calendar|
|`GET`|`/v2/lunar/{year}/{month}`|`application/json`, `application/x-ndjson`|Reads all items in specified `{month}` in lunar calendar|
|`GET`|`/v2/lunar/{year}/{month}/{day}`|`application/json`, `application/x-ndjson`|Reads an item of specified date in lunar calendar|
|`GET`|`/v2/lunar/{year}`|`application/json`, `application/x-ndjson`|Reads all items in specified `{year}` in solar calendar|
|`GET`|`/v2/lunar/{year}/{month}`|`application/json`, `application/x-ndjson`|Reads all items in specified `{month}` in solar calendar|
|`GET`|`/v2/lunar/{year}/{month}/{day}`|`application/json`, `application/x-ndjson`|Reads an item of specified date in solar calendar|

