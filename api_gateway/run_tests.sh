#!/bin/bash
# run_tests.sh

# Define the source and destination directories
SOURCE_DIR="/app/build/reports"
DEST_DIR="/app/reports"

# Run the tests
./gradlew clean test --no-daemon --scan --stacktrace
TEST_EXIT_CODE=$?

# Create the destination directory if it doesn't exist
mkdir -p $DEST_DIR

# Move the reports
mv $SOURCE_DIR/* $DEST_DIR/

# Check the exit code and print the appropriate message
if [ $TEST_EXIT_CODE -ne 0 ]; then
  echo "Tests failed with exit code $TEST_EXIT_CODE"
  exit $TEST_EXIT_CODE
else
  echo "Tests passed and reports moved to $DEST_DIR"
  exit 0
fi
