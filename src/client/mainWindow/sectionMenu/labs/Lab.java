package client.mainWindow.sectionMenu.labs;

public class Lab {

    int status;
    enum LAB_STATUS {
        CREATED,    // Создана
        NO_ACCESS,  // Недоступна
        IN_ACCESS,  // Доступна
        IN_PROCESS, // На этапе выполнения
        WAIT_CHECK, // Ожидает проверки
        FIXING,     // На исправлении
        APPRECIATED // Оценена
    }

    String taskHelp;            // Теоретическая справка
    Test nullTest;              // Нуль-тест (входное)
    String taskFormulation;     // Постановка задачи
    String theoryBackground;    // Теоретическое обоснование
    String instrumentAlgorithm; // Алгоритм решения с инструментами
    Test finalTest;             // Завершающее тестирование
}
