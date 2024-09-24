# External Sorting Algorithms

This project is a Java-based application designed to sort large data sets from files using three different external sorting algorithms. It provides a simple user interface to select a file and apply the sorting method of your choice. The three sorting methods implemented are:

- **Polyphase Merge Sort**
- **Balanced Merge Sort**
- **External Radix Sort**

## Table of Contents
- [Overview](#overview)
- [Installation](#installation)
- [Usage](#usage)
- [Sorting Algorithms](#sorting-algorithms)
- [File Input/Output](#file-inputoutput)
- [Contributing](#contributing)
- [License](#license)

## Overview

The project is designed to handle large files that cannot be sorted entirely in memory. It uses external sorting algorithms to divide the data into smaller chunks that can be handled more efficiently, merging or rearranging them in the desired order.

## Installation

1. Clone the repository to your local machine.
   ```bash
   git clone https://github.com/your-username/external-sorting-algorithms.git

2. Open the project in your preferred IDE (e.g., IntelliJ IDEA).

3. Compile the project and ensure all dependencies are resolved.

4. Build the project:
    ```bash
    javac -d bin src/com/arturoar/tools/*.java

## Usage

After compiling the project, run the main class:
    ```bash
 java -cp bin com.arturoar.tools.Principal

Upon running, you'll be presented with a menu in the console with the following options:

Polyphase Merge Sort: Sorts the file using the Polyphase Merge Sort algorithm.
Balanced Merge Sort: Sorts the file using the Balanced Merge Sort algorithm.
External Radix Sort: Sorts the file using the External Radix Sort algorithm.
Exit program: Exits the program.

### Example
1. Select an option from the menu.
2. Choose a file using the file chooser dialog.
3. Enter any required parameters (e.g., block size for Polyphase Merge Sort).
4. The sorted file paths will be displayed upon completion.

## Sorting Algorithms
1. Polyphase Merge Sort
Polyphase Merge Sort is an external sorting algorithm that optimizes the number of merges by reducing the number of file handles used. It is designed for files that are too large to fit in memory, utilizing temporary files during the merge phase.

2. Balanced Merge Sort
Balanced Merge Sort divides the data into equal-sized blocks and sorts them using a merging process. The blocks are written to auxiliary files, and then the merge process consolidates the sorted blocks into the final sorted file.

3. External Radix Sort
This is a variant of Radix Sort adapted for large data sets stored in external memory (files). It sorts the data by processing the digits of the numbers, creating auxiliary files for each digit pass, and merging the results back.

## File Input/Output
The program requires a file in .txt format with a list of integers. After sorting, the results will be written back to new files, which are displayed on the console after sorting is complete.

## Contributing
If you would like to contribute to this project, feel free to fork the repository and submit a pull request. Contributions in the form of bug fixes, new features, and code improvements are welcome.

## License
This project is licensed under the MIT License. See the LICENSE file for details.
    ```bash
    You can copy this and save it as `README.md` in your repository! Let me know if you'd like any adjustments.
