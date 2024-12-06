if [ ! -d "obj/" ]; then
    mkdir obj/
fi

javac -d obj/ GenStateMachine.java
cd obj/
java GenStateMachine $@
cd ..