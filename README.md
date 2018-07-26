# MultiPart Demo

Demo application to demostrate how to handle a Multipart endpoint with JSON request content using Spring Boot 2.X.

## Running

```
gradle build bootRun
```

This is going to add the endpoint /upload that can be accessed like this.

Simple request without multipart.
```
http --print HBhb POST :8080/upload a=a b=b
```

Multipart request.
```
http --print hb -f POST :8080/upload request@request.json attach@build.gradle attach@settings.gradle
```