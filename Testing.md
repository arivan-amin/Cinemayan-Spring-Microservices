# Testing Guide

### This project follows the Clean Code Manual for naming convention for unit and integration test methods.

## Purpose

To improve test readability, maintain consistency, and make it easier to understand what each test
is verifying at a glance.

# Testing Guide

## Test Naming Convention

All unit tests in this project follow a single, non-negotiable naming standard. Consistency across
the codebase is the goal.

---

### Standard

```
methodName_should{ExpectedBehavior}_when{Condition}
```

| Segment            | Rule                                             |
|--------------------|--------------------------------------------------|
| `methodName`       | The exact public method under test               |
| `should`           | Constant keyword, never omit, never substitute   |
| `ExpectedBehavior` | The observable outcome in camelCase              |
| `when`             | Constant keyword — never omit, never substitute  |
| `Condition`        | The specific scenario that produces this outcome |

---

### The Three Segments

A test name must answer three questions without the reader opening the body:

- **Which method** is being tested ?
- **What outcome** is expected ?
- **Under what condition** does this test occur ?

---

### Examples

**Always include the `when` clause.**
The `when` clause is what separates a test name from a test title. A name without it is incomplete
by definition.

```java
// Correct
void execute_shouldReturnPersistedStudio_whenNameDoesNotExist () {
}

// Wrong — no condition, forces reader to open the body
void execute_shouldReturnPersistedStudio () {
}
```

**Never use vague behavior or condition words.**
Words like `Valid`, `Invalid`, `Success`, `Failure`, `Error`, and `Exception` (unqualified) should
be avoided. They describe a category, not a specific outcome.

```java
// Correct
void execute_shouldThrowStudioAlreadyExistsException_whenNameIsAlreadyTaken () {
}

// Wrong — which exception? invalid according to which rule ?
void execute_shouldThrowException_whenInputIsInvalid () {
}
```

**Name the exact exception.**
If the behavior is throwing, the exception class name belongs in the test name. Reviewers and CI
reports must know which exception is expected without opening the test.

```java
// Correct
void execute_shouldThrowStudioAlreadyExistsException_whenNameIsAlreadyTaken () {
}

void findById_shouldThrowStudioNotFoundException_whenIdDoesNotExist () {
}

// Wrong — too generic
void execute_shouldThrowException_whenNameIsAlreadyTaken () {
}
```

**Match `methodName` exactly to the method under test.**
Do not paraphrase, abbreviate, or rename. If the method is `execute`, the test name starts with
`execute_`. This ties failure reports directly to source code.

```java
// Correct — method is literally named execute()
void execute_shouldReturnPersistedStudio_whenNameDoesNotExist () {
}

// Wrong — paraphrase of the method name
void createStudio_shouldReturnPersistedStudio_whenNameDoesNotExist () {
}
```

**`ExpectedBehavior` describes the return or side effect, not the internal steps.**
Tests assert outcomes visible to the caller, not implementation details. The behavior segment should
reflect what the caller observes.

```java
// Correct — caller-visible outcome
void execute_shouldReturnPaginatedStudiosList_whenFiltersApplied () {
}

// Wrong — internal implementation detail
void execute_shouldCallStorageFindAll_whenFiltersApplied () {
}
```

**Use `@DisplayName` for prose descriptions, not as a substitute for a good method name.**
`@DisplayName` is optional and not enforced.

```java
// Correct — method name is self-sufficient, DisplayName adds human-readable prose
@Test
@DisplayName ("should return the persisted studio when name does not already exist")
void execute_shouldReturnPersistedStudio_whenNameDoesNotExist () {
}

// Wrong — weak method name hidden behind DisplayName
@Test
@DisplayName ("should return the persisted studio when name does not already exist")
void testCreateStudio () {
}
```

### Anti-Patterns Reference

The following test method names are rejected:

| Anti-pattern                               | Why it is rejected                                       |
|--------------------------------------------|----------------------------------------------------------|
| `testCreateStudio()`                       | `test` prefix is redundant noise, carries no information |
| `shouldCreateStudio()`                     | Missing `methodName` and `when` clause                   |
| `shouldCreateStudioWithValidInput()`       | "Valid" is meaningless — valid according to what rule?   |
| `shouldThrowExceptionWhenInputIsInvalid()` | Vague exception, vague condition                         |
| `execute_shouldWork()`                     | "Work" is not a behavior                                 |
| `execute_shouldSucceed_whenOk()`           | "Succeed" and "Ok" carry no domain meaning               |
| `createStudio_happyPath()`                 | Informal, non-standard                                   |
| `createStudio_test1()`                     | Sequence numbers are just so wrong                       |

---

### Quick Reference Card

```
✅  execute_shouldReturnPersistedStudio_whenNameDoesNotExist
✅  execute_shouldThrowStudioAlreadyExistsException_whenNameIsAlreadyTaken
✅  findById_shouldThrowStudioNotFoundException_whenIdDoesNotExist
✅  search_shouldReturnEmptyPage_whenNoStudiosMatchQuery

❌  shouldCreateStudio
❌  shouldCreateStudioWithValidInput
❌  shouldThrowExceptionWhenInputIsInvalid
❌  testCreateStudio
❌  createStudio_happyPath
❌  execute_shouldWork
```
