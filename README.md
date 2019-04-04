**test methods naming convention:**
every test method has three parts:

MethodName_StateUnderTest_ExpectedBehavior


**Data initialization:**
As a convention Spring runs every sql commands that placed in `import.sql`

**Exception handling:**
With `@ResponseStatus` annotation we can specify the status code of response

**HTTP methods, `PUT` vs `PATCH` :**
`PUT` used for updating entire of resource that addressed in URL.
 but when we need to update specified fields of one resource we use `PATCH`.
