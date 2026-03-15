#ifndef UI_H
#define UI_H
#include"service.h"

class UI {
private:
    Service &service;
    void afiseaza_meniu();
    void afisare_toate();
public:
    UI(Service&service);
    void run();
};


#endif //UI_H
