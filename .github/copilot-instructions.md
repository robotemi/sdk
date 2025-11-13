---
applyTo: "sdk/src/main/aidl/com/robotemi/sdk/ISdkService.aidl,sdk/src/main/aidl/com/robotemi/sdk/ISdkServiceCallback.aidl"
---

# AIDL Interface Compatibility Rules

### For `sdk/src/main/aidl/com/robotemi/sdk/ISdkService.aidl` and `sdk/src/main/aidl/com/robotemi/sdk/ISdkServiceCallback.aidl`

**CRITICAL COMPATIBILITY REQUIREMENTS:**

1. **Adding New Interface Methods:**
   - ⚠️ **ALWAYS add new methods at the END of the interface**
   - ❌ **NEVER insert methods in the middle of existing methods**
   - This maintains binary compatibility with existing implementations

2. **Extending Existing Method Parameters:**
   - ⚠️ **ALWAYS add new parameters at the END of the method signature**
   - ❌ **NEVER insert parameters in the middle of existing parameters**
   - This ensures backward compatibility with existing callers

3. **Default Values for New Parameters:**
   - `int` parameters: default value is `0`
   - `String` parameters: default value is `null`
   - `boolean` parameters: default value is `false`
   - Always consider the impact of these defaults on existing functionality

### Examples:

✅ **CORRECT - Adding new method at the end:**
```aidl
interface ISdkService {
    // ... existing methods ...
    
    // NEW METHOD - added at the end
    void newMethod(in String parameter);
}
```

❌ **INCORRECT - Adding method in the middle:**
```aidl
interface ISdkService {
    void existingMethod1();
    void newMethod(in String parameter); // ❌ BREAKS COMPATIBILITY
    void existingMethod2();
}
```

✅ **CORRECT - Extending method parameters at the end:**
```aidl
// Before
void goTo(in String location, int backwards, int noBypass);

// After - new parameter added at the end
void goTo(in String location, int backwards, int noBypass, in String newParameter);
```

❌ **INCORRECT - Adding parameter in the middle:**
```aidl
// ❌ BREAKS COMPATIBILITY
void goTo(in String location, in String newParameter, int backwards, int noBypass);
```

### When Making Changes:

1. Always review the current method order before adding new methods
2. Consider the impact of default values on existing implementations
3. Test compatibility with existing applications
4. Document any breaking changes in release notes

These rules are essential for maintaining backward compatibility with existing Temi robot applications.
