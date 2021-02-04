# WoW Task List App
This was a for-fun project I had started to work more with Java as a back-end service. This is a dirt simple but working api/backend. If for whatever reason you want to use/expand it, it comes with no warrenty. Use at your own risk.

# API
API for the WoW app is run through src/api/API.java

Run in root directory so settings file is properly included with:

```sh
java -cp "lib/postgresql.jar:bin" api.API 9999
```

# javadoc Compiler Reminder
A simple reminder for myself on how to do basic javadoc compiling, so I don't have to spend 10 minutes digging through the docs

```sh
javadoc -d docs/ -sourcepath src/ -subpackages api:util
```
