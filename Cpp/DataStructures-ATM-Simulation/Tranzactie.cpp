#include "Tranzactie.h"
#include <iostream>
using namespace std;

Tranzactie::Tranzactie() : id(0), suma(0), data(""), bancnoteUtilizate() {}

Tranzactie::Tranzactie(int id, int suma, const string& data, Collection<int> bancnoteUtilizate)
    : id(id), suma(suma), data(data), bancnoteUtilizate(bancnoteUtilizate) {}

void Tranzactie::afisare() const {
    cout << "Tranzactie ID: " << id
         << ", Suma: " << suma
         << ", Data: " << data
         << ", Bancnote utilizate: ";
    bancnoteUtilizate.afisare();
}

int Tranzactie::getNumarBancnote() const {
    return bancnoteUtilizate.totalElements();
}
