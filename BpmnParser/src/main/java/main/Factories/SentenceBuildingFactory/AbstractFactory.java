package main.Factories.SentenceBuildingFactory;

// https://www.baeldung.com/java-abstract-factory-pattern
public interface AbstractFactory<T> {
        T get(String type) ;
}
