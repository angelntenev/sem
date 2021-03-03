# As an HR advisor I want to view and employee's details so that the employee's promotion request can be supported.

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to view and employee's details so that the employee's* promotion request can be supported.*
### Scope

Company.

### Level

Primary task.

### Preconditions

We know the role.  Database contains employee´s details.

### Success End Condition

HR is able to get access to the employee´s details.

### Failed End Condition

No report is produced.

### Primary Actor

HR Advisor.

### Trigger

A request for finance information is sent to HR.

## MAIN SUCCESS SCENARIO

1. Finance request salary information for all employees.
2. HR advisor captures all employees to get salary information for.
3. HR advisor extracts current salary information of all employees.
4. HR advisor provides report to finance.

## EXTENSIONS

3. **No data**:
    1. HR advisor informs finance no information exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0