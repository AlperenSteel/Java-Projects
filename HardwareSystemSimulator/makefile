# Compiler
JAVAC = javac
JFLAGS = -d build -cp src
SRC_DIR = src
BUILD_DIR = build
DOCS_DIR = docs

# All Java source files
SOURCES = $(shell find $(SRC_DIR) -name "*.java")

# Default target
all: build

# Build class files
build:
	mkdir -p $(BUILD_DIR)
	$(JAVAC) $(JFLAGS) $(SOURCES)

# Run program with config and scenario
run:
	java -cp build Main.Main config.txt logs/

# Run program with redirected scenario input/output
run-scenario:
	java -cp build Main.Main config.txt logs/ < scenario1.txt > output.txt

# Generate JavaDoc
javadoc:
	mkdir -p $(DOCS_DIR)
	javadoc -d $(DOCS_DIR) -sourcepath $(SRC_DIR) -subpackages hwSystem:Main

# Clean class files and docs
clean:
	rm -rf $(BUILD_DIR)/*
	rm -rf $(DOCS_DIR)/*
	rm -rf logs/*

# Full rebuild
rebuild: clean all
