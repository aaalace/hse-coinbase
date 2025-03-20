# hse-coinbase [ Software Design 2025 ]
#### Tazeev Almaz 235

###### MacOS/Linux
```bash
./gradlew build
java -jar build/libs/hse-coinbase.jar 
```

###### Windows
```bash
gradlew.bat build
java -jar build/libs/hse-coinbase.jar 
```

## Функционал
> Основные требования к функциональности модуля «Учет финансов»
> 1) Взаимодействия моделей доменной области ✅
> 2) Crud операции ✅

> Аналитика операций ✅
> 1) PnL аналитика ✅
> 2) Абсолютные объемы транзакций ✅
 
> Статистика - измерение времени работы различных пользовательских сценариев

## Принципы SOLID и GRASP
> SRP - любой класс, за исключением **HseCoinbaseApplication**

> OCP, LSP - **BaseRepository и его наследники**
 
> DIP - **AnalyticsServiceFactory**

---

> Low Coupling - /repository/* и **ModelCrudFacade**

> Protected Variations - **BaseRepository**
 
> Pure Fabrication - /service/*


## GoF паттерны

> Фасад - необходим в качестве единого места вызова CRUD операций для разных, но связанных моделей - **ModelCrudFacade**

> Декоратор - измерение времени работы различных пользовательских сценариев, необходим для выбора конкретных анализируемых методов - **ExecutionTimeAspect и ExecutionTimeAspect**
 
> Итератор - перебор консольных команд во время их регистрации - **CommandIterator**

> Строитель - создание объектов по доменной обл. - /domain/model/*
 
> Одиночка - логирование сессий, нет необходимости пересоздавать объект - **SessionLogger**
 
> Фабрика - выбор сервиса аналитики на основе запроса, необходимо для возможности удобного добавления новых аналитических параметров, что является частой практикой - **AnalyticsServiceFactory**