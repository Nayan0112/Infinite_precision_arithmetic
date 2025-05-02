## Infinite Precision Arithmetic

This project implements arbitrary-precision arithmetic operations on integers and floating-point numbers. It supports basic mathematical operations such as addition, subtraction, multiplication, and division with infinite precision, allowing users to perform calculations on very large numbers.

## Features
- Supports arbitrary-precision integer and floating-point arithmetic.
- Operations include addition, subtraction, multiplication, and division.
- Built-in input validation to ensure proper number formatting.
- Can be executed through a Docker container.

## Project Structure
- **`src/`**: Contains Java source files for the project.
- **`myinfarith/MyInfArith.java`**: Main Java file that executes the arithmetic operations based on user input.
- **`Dockerfile`**: Contains instructions for building a Docker container to run the Java application.
- **`pom.xml`**: Maven build file for dependency management and project build configuration.

## Requirements
- **Java 17** or higher
- **Maven** for building the project (if not using Docker)
- **Docker** (optional, for running the project in a container)

## Installation

### Option 1: Running Without Docker

1. Clone this repository:

    ```bash
    git clone https://github.com/your-username/Infinite_precision_arithmetic.git
    cd Infinite_precision_arithmetic```
    

2. Build the project with Maven:

    ```bash
    mvn clean install```
    

3. Compile the Java files:

    ```bash
    javac -cp .:/target/classes myinfarith/MyInfArith.java```
    

4. Run the program:

    ```bash
    java -cp .:/target/classes myinfarith.MyInfArith <data type> <operation> <num1> <num2> ```
    

5. Example:
    
    ```bash
    java -cp .:/target/classes myinfarith.MyInfArith float add 10 
    ```

This will run the program with float data type and perform an addition operation on the numbers `10` and `5`.

### Option 2: Running With Docker

If you prefer to run the project in a Docker container, follow these steps:

1. Build the Docker image:

    ```bash
    docker build -t deleteme0/myinfarith .
    

2. Run the Docker container:

    ```bash
    docker run deleteme0/myinfarith <data type> <operation> <num1> <num2>
    

3. Example:

    ```bash
    docker run deleteme0/myinfarith float div 10 0
    

This will attempt to divide `10` by `0` and should raise an exception.

## Dockerfile Overview

- **Base Image**: `maven:3.9.4-eclipse-temurin-21` – Maven with Java 17.
- **Build Process**: Copies the project files, installs dependencies using Maven, and compiles the Java files.
- **Execution**: The entry point of the Docker container is set to run the `MyInfArith` class with user-supplied arguments.

## Example Input

The program accepts the following arguments:

1. **Data type**: `float` or `int`
2. **Operation**: `add`, `sub`, `mul`, `div`
3. **Number 1**: The first operand
4. **Number 2**: The second operand

Example command-line input:

bash
docker run deleteme0/myinfarith int add 10 20


### Expected Output

For the above example, the output will be:

bash
30

## Run with python script :
1. Here, 
    ```bash 
    python3 MyInfArith.py

The program expects input in the form of <DataType> <operation> <x> <y>
and it runs in an infinite loop for testing purposes and can be exitted by pressing Ctrl + C


## Error Handling

- **Division by Zero**: If you attempt to divide by zero, the program will throw an exception.
- **Invalid Input**: If an invalid number or operation is provided, an error message will be displayed.
- **Usage**: On invalid arguments or insufficient arguments 
