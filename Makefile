YEAR=2022
AOC_SERVER_TIMEZONE=America/New_York
DAYS=$(shell seq 1 $(shell TZ=${AOC_SERVER_TIMEZONE} date +"%-d"))
INPUTS=$(patsubst %,inputs/day%.txt,$(DAYS))

# Grab this from your logged in web session
SESSION=<your-session-token>

inputs/day%.txt:
	curl -b "session=${SESSION}" https://adventofcode.com/${YEAR}/day/$*/input > $@

.PHONY: inputs
inputs: ${INPUTS}
