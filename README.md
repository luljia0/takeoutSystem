# takeoutSystem

### day03

#### Public field auto-population

auto populate the properties like createTime, updateUser when inserting or updating data in the database

1. a custom annotation `Autofill`, to identify the auto-poplulated methods 
2. Create a custom aspect `AutoFillAspect`to intercept annotated methods and assign values to common fields using reflection.
3. Add the  `Autofill`annotation to the `Mapper` method.

#### dynamic proxy

A dynamic proxy is a mechanism that allows you to create a proxy instance at runtime, typically to intercept method calls and add additional behavior (such as logging, transaction management, etc.) without modifying the original code.