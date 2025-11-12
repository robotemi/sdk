---
applyTo: "**/*.aidl"
---

# GitHub Copilot Custom Instructions

## AIDL File Modification Guidelines

### Critical: Add New Methods and Properties at the End

When modifying AIDL (Android Interface Definition Language) files, **ALWAYS add new methods or properties at the end of the file**, after all existing methods/properties.

**Why this is important:**

- This ensures backward compatibility between older SDK versions and newer launcher versions
- AIDL interfaces are position-dependent - changing the order of existing methods can break compatibility
- Older SDK clients can still work with newer launcher versions if new methods are appended at the end
- This follows the Android AIDL best practice for maintaining interface stability

**Rules:**

1. **Never insert new methods in the middle** of an existing interface definition
2. **Never reorder existing methods** - maintain their current positions
3. **Always append new methods** at the end of the interface, before the closing brace
4. **Always append new properties** at the end of parcelable/data class definitions
5. When adding imports, add them at the end of the import section (maintain existing import order)

**Example:**

```aidl
// ✅ CORRECT - New method added at the end
interface ISdkService {
    // ... existing methods ...
    void existingMethod();

    // New method added here at the end
    void newMethod();
}

// ❌ INCORRECT - New method inserted in the middle
interface ISdkService {
    void existingMethod();
    void newMethod(); // ❌ Don't do this!
    void anotherExistingMethod();
}
```

**This applies to:**

- All `.aidl` files in the repository
- Interface definitions (methods)
- Parcelable definitions (properties)
- Any AIDL type modifications
