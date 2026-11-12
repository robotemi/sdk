- Release local artifact

```
./gradlew clean sdk:publishToMavenLocal
```

- Snapshot or official release from a machine that already has publishing credentials

```
./gradlew clean sdk:publish
```

- Official release from CI

Push a tag `v<major>.<minor>.<patch>` whose number matches `VERSION_NAME` in `gradle.properties`. The GitHub Actions workflow publishes that version to Maven Central and releases the deployment. Credentials are GitHub Actions secrets, not files in this repository.