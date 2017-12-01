JCC = javac

default: target

target:
	$(JCC) -g src/*.java -d bin/

run:
	java -cp "bin/" Main

clean:
	$(RM) bin/*.class
