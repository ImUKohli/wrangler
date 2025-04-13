## Byte Size and Time Duration Parsers

This project has been enhanced to support parsing byte size and time duration units in recipes.

### Usage

- **Byte Size Parsing**: Supports units like `B`, `KB`, `MB`, `GB`, `TB`, `PB`.
  Example: `10KB`, `1.5MB`
- **Time Duration Parsing**: Supports units like `ns`, `ms`, `s`, `m`, `h`, `d`.
  Example: `150ms`, `2.1s`

### New Directive: `aggregate-stats`

Aggregates byte sizes and time durations across rows.

**Syntax**: