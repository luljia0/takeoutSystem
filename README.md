# takeoutSystem

### day03

#### Public field auto-population

auto populate the properties like createTime, updateUser when inserting or updating data in the database

1. a custom annotation `Autofill`, to identify the auto-poplulated methods 
2. Create a custom aspect `AutoFillAspect`to intercept annotated methods and assign values to common fields using reflection.
3. Add the  `Autofill`annotation to the `Mapper` method.

#### dynamic proxy

A dynamic proxy is a mechanism that allows you to create a proxy instance at runtime, typically to intercept method calls and add additional behavior (such as logging, transaction management, etc.) without modifying the original code.

#### replace alioss by aws s3

1. `S3Properties` to encapsulate s3 properties
2. config properties in application.yml and application-dev.yml
3. `S3Util` to handle the logic to upload, return the file path
4. `S3Configuration` to create an `S3Util` object as a bean when configuring
5. `CommonController`  to upload the file by calling the function of `S3Util`

Note that:

1. don;t forget to add permission policy for user to access s3 bucket
2. allow public access policy to get bucket so that you can get access to the file via url and display it in the frontend 



#### generated key return

add `generatedKeyReturn` and `keyProperty` property in SQL tags to return the generated key after inserting key 

### Day06 wechat login

1. Configue jwt properties for user authentication
2. configue wechat properties for user login
3. implemet controller, service and mapper
4. Update jwtInterceptor and webconfiguration
