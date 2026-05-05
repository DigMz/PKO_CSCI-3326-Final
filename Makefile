.PHONY: all
all:
	./gradlew build run

.PHONY: compile run
compile:
	./gradlew build
run:
	./gradlew run
