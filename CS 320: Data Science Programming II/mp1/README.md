# Machine Project 1: Review

## Overview

This machine project focuses on review of concepts needed to succeed in this course; it is
designed to get familiar with Python basics (or review them if you
took 220).

## Part 1: Python Control Flow

For part 1, you will focus on how to implement logic in Python and alter the control flow 
of code. This will mean working with if/elif/else-statements, while/for loops, creating and using functions, and using some built-in Python functions.

### Q1: What is the type of `11/7`?
Python offers many built-in functions that we will use throughout the course of this semester. Look at a list of them here to see which one can help you answer this question: https://docs.python.org/3/library/functions.html.

11 and 7 are ints, and so in most programming languages, dividing two ints will result in an int as well (1 after rounding down from 1.57). However, in Python, we get the mathematically correct answer of 1.57...

If we wanted to instead get an int from our division, we can use `//` instead of `/`. Try it out with `11/7` and `11//7` in jupyter.

### Q2: Is the length of `fruit_list` between 2 and 5?

Complete the code in accordance with the comment to get the correct answer. It may be helpful to review the [Python built-in functions](https://docs.python.org/3/library/functions.html) in Q1.

```python
fruit_list = ["apple", "pear", "banana", "watermelon"]
fruit_list_length = ???? # Don't hardcode this. Use a Python built-in

minimum = 2
maximum = 5
in_range = ???? # True if the number of fruits in fruit_list is inside the minimum-to-maximum range
in_range
```

### Q3: Ignoring case, how many of the files in `filepaths` end in `.csv`?

Complete the code below for your answer:

```python
filepaths = "TESTING_FILE1.C5V-TESTING_FILE2.CSV-TESTING_FILE3.CSV"
file_list = ????
csv_count = 0
for file in file_list:
    if ????:
        csv_count += 1
csv_count
```

### Requirement: `multiply` function

In a new cell, you will define a new function: `multiply(...)`.Your function will take two ints and return their product. For example, `multiply(2, 3)` should return 6. Users of this function should also be able to call it like `multiply(a=2, b=3)`. If only one argument is passed, the value should be multiplied by 2. For example, `multiply(4)` or `multiply(a=4)` should both return 8.

Python parameters may be filled with positions arguments, keyword arguments, or default arguments.  If this is unfamiliar, read the following:

1. https://docs.python.org/3/tutorial/controlflow.html#defining-functions
2. https://docs.python.org/3/tutorial/controlflow.html#more-on-defining-functions

In Python, indents are very important.  The code inside a function/if/loop is indented (Python doesn't use `{` and `{` to indicate this, as in Java and many other languages).

### Q4. What is `multiply(5)`?

### Requirement: `smart_multiply` function

In Python, lists can be created like `[item1, item2, item3, ...]` and can be indexed/sliced just like strings (strings and lists are both examples of Python *sequences*; by definition, you can index and slice any kind of sequence you encounter in Python).  This list contains just ints, but you're free to have a mix of types in Python lists.

In general, you can plug in a variable name and sequence into a `for` loop to run a piece of code for every entry in the sequence:

```python
for ???? in ????:
    # DO SOMETHING
```

More on `for` loops:
* https://docs.python.org/3/tutorial/controlflow.html#for-statements
* https://docs.python.org/3/tutorial/controlflow.html#break-and-continue-statements-and-else-clauses-on-loops

Write a function called `smart_multiply` that takes a list of numbers and returns their product. It should have the following features:
1. Ignore numbers that are negative or zero.
2. Once the product is above 100 (strictly larger than 100), all of the numbers that follow it, positive or negative, should be skipped.

Use `continue` to implement feature 1 and `break` to implement feature 2.

### Q5. What is `smart_multiply([4, -2, 0, 5, 5, 2, 7, -2])`

The answer should be 200. We should skip -2, 0, and -2 since they are less than or equal to 0. Additionally, 4 * 5 * 5 = 100, which is **not** strictly greater than 100, so we need to multiply by 2 to get 200 and then we skip 7 since 200 > 100.

## Part 2: Interacting with Python State

For part 2, you will focus on how to interact with python objects, especially those that are nested (i.e. list inside of a list, dict inside of list, etc.). We will look at how to
modify state, as well as how to extract state, and make copies of state that copy
data structures on different levels of depth.

### Requirement: lists and dicts:

Copy/paste the following:

```python
header = ["A", "B", "C"]

coord1 = {"x": 8, "y": 5}
coord2 = {"x": 9, "y": 2, "z" : 4}
coord3 = {"x": 3, "y": 1, "z" : 7}

rows = [
    [1, 6, 7, 8, coord1],
    [3, 4, 9, coord2],
    [5, 2, coord3],
]
```

Note that `rows` is a list of lists.  Each inner list contains some number of ints and one dict (dictionary).  For complicated nested structures like this, it's often helpful to visualize the stack of frames and heap of objects in PythonTutor: https://pythontutor.com/live.html#mode=edit.

You could copy the above to visualize it, or use the following link for your convenience:

https://pythontutor.com/visualize.html#code=header%20%3D%20%5B%22A%22,%20%22B%22,%20%22C%22%5D%0A%0Acoord1%20%3D%20%7B%22x%22%3A%208,%20%22y%22%3A%205%7D%0Acoord2%20%3D%20%7B%22x%22%3A%209,%20%22y%22%3A%202%7D%0Acoord3%20%3D%20%7B%22x%22%3A%203,%20%22y%22%3A%201%7D%0A%0Arows%20%3D%20%5B%0A%20%20%20%20%5B1,%206,%20coord1%5D,%0A%20%20%20%20%5B3,%204,%20coord2%5D,%0A%20%20%20%20%5B5,%202,%20coord3%5D,%0A%5D&cumulative=false&curInstr=7&heapPrimitives=nevernest&mode=display&origin=opt-frontend.js&py=3&rawInputLstJSON=%5B%5D&textReferences=false

Both lists and dicts contain values.  With lists, each value is associated with an index (integers starting from 0).  With dicts, each value is associated with a key specified by the programmers.  Keys are often strings, but they don't need to be.

Docs:
* https://docs.python.org/3/tutorial/datastructures.html#more-on-lists
* https://docs.python.org/3/tutorial/datastructures.html#dictionaries

### Q6: After inserting a "z" key in `coord1` (with `coord1["z"] = 6`), what is `rows`?

### Q7: What is the value associated with the "y" key of the dict in the last position of the last list in `rows`?

### Q8: What is `rows` after running the following?

Complete the following so that the first change via `v2` is NOT reflected in `rows`, but the second change via `v2` IS reflected in `rows`:

```python
import copy
v2 = ????
v2[0] = 404    # first change
v2[1][1] = 404 # second change
```

Relevant docs: https://docs.python.org/3/library/copy.html

To get a good intuition about the reference/shallow/deep copy, try stepping through the following slowly in PythonTutor:

```python
import copy
v1 = [[1], [], [2, 3]]
v2 = v1
v2 = copy.copy(v1)
v2 = copy.deepcopy(v1)
```

### Q9: If we imagine the list of lists structure referenced by `rows` as a table, with column names in `header`, what is the sum of values in the "A" column?

Note: the "A" column corresponds to the values at index in 0 of each list, but you are not allowed to hardcode 0 for this solution.  Instead, use `header.index(????)` to look up the position of "A" within the `header` list.

### Q10: What is `rows` after we sort it in-place by the "B" column, descending?

Docs:
* https://docs.python.org/3/howto/sorting.html#sorting-basics
* https://docs.python.org/3/howto/sorting.html#key-functions

### Q11: Say you're going on vacation to Canada with 400 US dollars; how many Canadian dollars can you get at the current exchange rate?

This site provides exchange rate information in JSON format: https://www.floatrates.com/json-feeds.html.  JSON is a simple format that can represent nested dicts and lists in files and web resources.

Download a copy of `usd.json` to the directory where your machine project is.  An easy way is to open a terminal, `cd` to the appropriate directory, then run `wget SOME_URL_HERE` to download the web resource.

Note: you can run shell commands in Jupyter, too, if you start the command with a `!` (to indicate it is not Python code).  If you do this, be sure to delete the cell after the download.  Otherwise you'll create too much traffic on the floatrates.com site, re-downloading the same thing every time you re-run your notebook.

You can read a file like this:

```python
f = open("usd.json")
data = f.read()
f.close()
```

Check the type of `data` and the first portion of it:

```python
print(type(data))
print(data[:300] + "...")
```

Even though the file contains a string that *could* be interpreted as JSON, Python won't *deserialize* it to Python dicts/lists automatically.  Instead of calling `.read()`, we need to use the `load` function in the `json` module:

https://docs.python.org/3/library/json.html#json.load

When reading documentation, start by focusing on parameters that can't take default arguments.

Access the exchange rate for Canadian dollars in the dictionary (key is "cad") and multiply it by the amount of US dollars you have to find out how many Canadian dollars you can get. You can round to the nearest 0.01 dollar (1 cent).

**Important**: Note the difference between `json.load()` and `json.loads()`. The first loads json data from a file, whereas the second loads json data from a Python string.

**Important**: Note the difference between `json.dump()` and `json.dumps()`. `json.dump()` serializes a Python object and writes it directly to a file-like object, on the other hand `json.dumps()` serializes a Python object and returns it as a JSON-formatted string.

*Note*: Since the exchange rate is constantly changing, your answer may not be the exact same as is in the answer key. We have implemented a buffer to allow a range of values which should include the correct answer, given the current exchange rate.

### Requirement: `convert_to_int` function:

In Python, we can cast strings that have an integer value into an int using `int(value)`. For example, `int("42")` will return `42` as an int and not a string.

Write a function named `convert_to_int` that will attempt to convert its input into an integer. Note that we will test it with invalid strings (e.g. `"not a number"`, `"forty-two"`) so your function should catch these errors, print `"Could not convert string to int."` and return `None`. For any other exceptions, your function should print `"Error with your input argument."` and return `None`.

How to catch exceptions: https://docs.python.org/3/tutorial/errors.html#handling-exceptions.

### Q12: What is `convert_to_int("320")`?

### Q13: What is `convert_to_int("ninety-nine")`?

*Note*: You may be able to pass the auto-grader with faulty code, as the auto-grader can not check print outputs. This grade will be part of the [manual grading process](./grading-guidelines.md).

### Q14: What is `convert_to_int([3, 2, 0])`?

## Part 3: Data Science using `pandas`

Each year, the University of Wisconsin's Office of the Registrar reports on the grades of students at the university in almost all classes. Unfortunately for us, this data is reported in a PDF format, which isn't super handy for data analysis in Python. You can view these reports here: https://registrar.wisc.edu/grade-reports/.

Fortunately, while creating the grade distribution visualization tool [Madgrades](https://madgrades.com/), former UW Badger Keenan Thompson created an [open-source tool for extracting the data](https://github.com/Madgrades/madgrades-extractor) in these PDFs into SQL and CSV files. We have done some basic manipulation of this data and provided a year-by-year breakdown of it in the `grade-data` directory for the years 2017 - 2022.

Create a dictionary called `grades` like this:

* **Key**: A year (`int`), corresponding to a year of data in the `grade-data` directory. Don't hardcode the years -- use `os.listdir` and extract the year from each filename (right after the last `-`). Check if the file is a CSV by checking if it ends with ".csv". Don't hardcode the years in the dictionary - use the year extracted from the file name as the key.
* **Value**: A pandas DataFrame corresponding to the CSV for that year. Use `set_index` (https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.set_index.html) to make `"course"` the index of the DataFrame.  This will let you easily look up course stats by name (instead of by row number) later.

### Q15: What are the keys in `grades`?

Answer with a sorted list in ascending order.

### Q16: What was the average GPA for COMP SCI 320 in 2022?

The answer is in the column with name `"gpa"` and row with course `"COMP SCI 320"`.  The hardcoding way to answer (not allowed) would be something like:

```python
df_2022 = grades[2022]
df_2022.iat[1228, 6] # iat works like df_2022.iloc[, 6], but is faster for one cell
```

Instead of hardcoding 1228 and 6, you can use "COMP SCI 320" (row index name) and "gpa" (column name).  When using names instead of positions, you just need to use `.at` or `.loc` (instead of `.iat` or `.iloc`).

### Q17: How many computer sciences classes were offered in 2022?

If we wanted a DataFrame of all of the psychology courses in 2016, we could do the following:

```python
df_2016 = grades[2016]
df_2016.loc[df_2016["course_subject"] == "Psychology"]
```

### Q18: How many students received an "A" in computer sciences courses in 2022?

The data will be found in the column `"a_count"`.

If `df` is a DataFrame, `df["some column name here"]` will extract an individual column as a Pandas Series.  A Pandas Series is like a list/dict hybrid.  You can use `.iat` to look up values by integer position (like you would with a list).  You can use `.at` to look up values by the Series' index, like you would with a dict.  Note the confusing terminology here: a Series' index is like a dict's key, and the "i" in "iat" does NOT refer to "index".

If you have a Pandas Series `s`, you can do various aggregations on it, like `.mean()`, `.sum()`, `.max()`, etc.

### Q19: Calculate the fraction of students who received an "A" in each computer sciences class in 2022. Then return the smallest value from this computation.

The total number of students to take a course is stored in the `"total"` column.

You can divide one Pandas Series by another on an element-wise basis like this: `s3 = s2 / s1`.  You can then compute `s3.min()`.  Or better, see if you can combine everything into a one-line computation.

### Q20: What fraction of students in computer sciences courses numbered 300 to 399 received an "A" in 2022?

Use the `"course_num"` column to filter based on the course numbers.

We can also look for multiple criterion using similar syntax. For example, if we wanted all Psychology courses or Biology courses from 2016, we could do the following:

```python
df_2016.loc[(df_2016["subject_name"] == "Psychology") | (df_2016["subject_name"] == "Biology")]
```

In this example, we need to use the `|` instead of `or`. If we wanted to look for all Psychology courses with a GPA of over 3.7, the following would work:

```python
df_2016.loc[(df_2016["subject_name"] == "Psychology") & (df_2016["gpa"] > 3.7)]
```

Answer with a `dict`, where the key is the course's name, and the value is the percentage of students that recieved an "A" in that course i.e. "0.01" for "1%". The resulting dictionary should look like this:

```python
{'COMP SCI 300': 0.4683698296836983,
 'COMP SCI 304': 0.8835616438356164,
 'COMP SCI 310': 0.345679012345679,
 'COMP SCI 319': 0.6567164179104478,
 'COMP SCI 320': 0.33044554455445546,
 'COMP SCI 352': 0.29277566539923955,
 'COMP SCI 354': 0.20095238095238097}
```

### Q21: Same question as Q20, but answer with a bar plot instead of a `dict`.

If you have a Series `s`, you can use `s.plot.bar()` or `s.plot.barh()`.  The labels of the x-axis and y-axis should be as shown in the below graph.

Example:

<img src="img/q21.png">

### Q22: How has the number of students taking COMP SCI 300 changed over recent years?

Answer with a plot like this: The labels of x-axis and y-axis should be as shown in the below graph.

<img src="img/q22.png">

### Q23: What is the relationship between number of students who receive an "A" and GPA of a course for 2022?

Answer with a plot where the x-axis is the total number of sutdents enrolled in a course and the y-axis is the number of students that earned an "A" in that course. The labels of x-axis and y-axis should be as shown in the below graph.

<img src="img/q23.png">
