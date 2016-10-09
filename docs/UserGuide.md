# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `Do-Do Bird.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Do-Do Bird application.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > ![GUI](./images/UpdatedUI_041016.png)

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`see`**` tomorrow`:  see all tasks for tomorrow.
   * **`add`**` Meet with professor; CS1234; from 10/10/17 09:30; till 17:00;` :
     adds a task named `Meet with Professor` to the tasks list.
   * **`delete`**` 3` : deletes the task with ID #3.
   * **`exit`** : exits the app.
6. Refer to the [Features](#features) section below for details of each command.<br>


# Features

> **Command Format**
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

> **Date and Time Format**
> * **Date**
>   * 25/10/2017 or 25-10-2017
>   * 25 Oct 2017
>   * 25 October 2017
>   * tomorrow/yesterday/today/next monday
> * **Time**
>   * 24-hours format : 09:30
>   * 12-hours format : 09:30pm

## Viewing help : `help`
Shows the help page to user.<br>

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

## Adding a task: `add`
Adds a task to Do-Do Bird.<br>

Format:

* `add TASK_NAME [; a line of details]`
* `add TASK_NAME by DATE [tTIME] [; a line of details]`
* `add TASK_NAME on DATE [tTIME] [; a line of details]`
* `add TASK_NAME on DATE [tTIME] by DATE [tTime] [; a line of details]`


> Date and Time formats follow the above guidelines.

Examples:

* **`add`**` Meet with professor ; consultation for mid-terms`
* **`add`**` CS1010 Lab 4 by 10/10/2017`
* **`add`**` Amy's weddings on 25/10/17;`

## Seeing tasks : `see`
Shows a list of all tasks in Do-Do Bird.<br>

Format: `see`

## Searching tasks: `search`
Search tasks whose names or details contain any of the given keywords. <br>
Search for tasks before/after a time.<br>
Search for tasks on a particular date.<br>
Search for tasks with the specified tag.<br>
Search for tasks that are done or not done.<br>

Format:

* `search KEYWORD [MORE_KEYWORDS]`
* `search before DATE [tTIME]`
* `search after DATE [tTIME]`
* `search on DATE [tTIME]`
* `search from DATE [tTIME] till DATE [tTIME]`
* `search tag TAG`
* `search done`
* `search undone`

Examples:
* **`search`**` Party fun NIgHt OUTzz`
* **`search`**` before 25/10/17 t09:30`
* **`search`**` tag birthdays`

> * The search is case insensitive. e.g `meeting` will match `Meeting`.
> * The order of the keywords does not matter. e.g. `Meet Professor` will match `Professor Meet`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Meeting` will match `Meeting Professor`


## Marking tasks as done : `mark`
Marking a task in Do-Do Bird as completed.<br>

Format: `mark ID`

> The ID must be a positive integer 1, 2, 3, ...

Examples:

* **`search`**` tomorrow`<br>
  **`mark`**` 2`<br>
  Mark the task with `ID #2` in the Do-Do Bird as completed.

## Unmarking tasks : `unmark`
Unmark a task in Do-Do Bird to be uncompleted.<br>

Format: `unmark ID`

> The ID must be a positive integer 1, 2, 3, ...

Examples:

* **`search`**` tomorrow`<br>
**`unmark`**` 2`<br>
Mark the task with `ID #2` in the Do-Do Bird as uncompleted.

## Updating a task: `update`
Update an existing task inside Do-Do Bird.<br>

Format:

* `update ID [NEW_NAME] [on DATE [tTime]] [by DATE [tTime]] [; a line of new details]`
* `update ID [NEW_NAME] [-on DATE [tTime]] [-by DATE [tTime]] [-; a line of new details]`

> * Date and Time formats follow the above guidelines.
> * The ID must be a positive integer 1, 2, 3, ...
> * To remove any pre-existing optional fields, prefix a `-` to the field specifier.

Examples:

* **`search`**` tomorrow`<br>
  **`update`**` 2 on 14/10/17 by 18/10/17;` <br>
  Update the task with `ID #2` to reflect new dates/
* **`search`**` 25/10/17`<br>
  **`update`**` 3 -on 25/10/17;` <br>
  Update the task with `ID #3` to remove old on date.


## Deleting a task : `delete`
Deletes the specified task from the Do-Do Bird.<br>

Format: `delete ID`

> The ID **must be a positive integer** 1, 2, 3, ...

Examples:

* **`search`**` Tutorial`<br>
  **`delete`**` 1`<br>
  Deletes the task with `ID #1` in the Do-Do Bird.

## Tagging a task : `tag`
Tags the specified task with the specified tag.<br>

Format: `tag ID TAG`

> The ID **must be a positive integer** 1, 2, 3, ...

Examples:

* **`see`** <br>
**`tag`**` 2 Tutorial`<br>
Tags the task with `ID #2` with `Homework` tag

## Untagging a task : `untag`
Untags the specified task from the specified tag.<br>

Format: `untag ID TAG`

> The ID **must be a positive integer** 1, 2, 3, ...

Examples:

* **`see`** <br>
**`untag`**` 2 Tutorial`<br>
Untags the task with `ID #2` from `Homework` tag


## Undoing : `undo`
Undo the last operation.<br>

Format: `undo`

> Only undo one operation at most.

## Clearing all entries : `clear`
Clears all entries from the Do-Do Bird.<br>

Format: `clear`  

## Exiting the program : `exit`
Exits the program.<br>

Format: `exit`  

#### Saving the data
To-do list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Do-Do Bird folder.

## Command Summary

Command | Format  
-------- | :--------
Add | `add TASKNAME [PARAMETERS]`
Clear | `clear`
Delete | `delete ID`
Help | `help`
Mark | `mark ID`
Unmark | `unmark ID`
Quitting | `exit`
Search | `search [PARAMETERS]`
See | `see`
Tag | `tag ID TAG`
Untag | `untag ID TAG`
Undo | `undo`
Update | `update TASKNAME; [PARAMETERS;]`
