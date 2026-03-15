#ifndef TRANZACTIE_H
#define TRANZACTIE_H

#include <string>
#include "Collection.h"

class Tranzactie {
private:
    int id;
    int suma;
    std::string data;
    Collection<int> bancnoteUtilizate;

public:
    Tranzactie(); // constructor default
    Tranzactie(int id, int suma, const std::string& data, Collection<int> bancnoteUtilizate);
    void afisare() const;
    int getSuma() const { return suma; }
    std::string getData() const { return data; }
    int getNumarBancnote() const;
};

#endif // TRANZACTIE_H
