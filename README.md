# soap-messenger

##### Wymagania: 
Do realizacji zadania potrzebna będzie wiedza o gniazdach, 
protokole HTTP, SOAP, XML i wdrażaniu aplikacji serwerowych. 
Choć kurs dotyczy głównie stosu technologii JAVA SE podczas
realizacji zadania można posiłkować się wiedzą z zakresu Java EE, 
w szczególności wiedzą o frameworku Apache Axis2 
(http://axis.apache.org/axis2/java/core/). 

##### Zadanie: 
Zaprojektuj architekturę rozproszonego systemu, 
w którym wymiana informacji bazuje na wykorzystaniu SOAP. 
Architektura ta może przyjąć formę "pierścienia", "gwiazdy", "łańcucha" 
lub ich kombinacji. Zaimplementuj węzły takiego systemu. 
Implementację można oprzeć się na gniazdach TCP/IP lub 
na frameworku Apache Axis/Axis2. System może służyć do rozsyłania 
komunikatów pomiędzy węzłami (adresowanych bezpośrednio do jakiegoś 
węzła lub rozsyłanych do wszystkich węzłów). Węzły uruchamiane są jako 
osobne aplikacje, na interfejsie których wyświetlane są wiadomości 
przychodzące oraz redagowane są wiadomości wychodzące. Do realizacji 
logiki przesyłania komunikatów należy wykorzystać możliwości, 
jakie dostarcza protokół SOAP (a więc pola części head), zaś sama treść 
komunikatów powinna być przenoszona w części transportowej (a więc w części 
body). Do realizacji zadania (gdy wykorzystuje się gniazda TCP/IP) można 
użyć pakiet javax.xml.soap.*. 

W szczególności architektura systemu może przyjąć postać jak na schemacie 
poniżej (strzałki pokazują kierunek przepływu komunikatów). 

Każdy z węzłów powinien być parametryzowany nazwą, numerami portów, 
oraz atrybutem mówiącym o jego umiejscowieniu w architekturze 
(tu: numerem warstwy). W tym konkretnym przypadku wiadomości wychodzące 
można adresować do wszystkich węzłów w tej sieci, do węzłów danej warstwy, 
do konkretnych węzłów. 


---------


Program's parameters:
- name (string)
- port (number)
- layer (number)
