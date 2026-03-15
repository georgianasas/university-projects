#ifndef ATM_H
#define ATM_H

#include "Collection.h"
#include "Tranzactie.h"
#include "Multime.h"
#include "comparators.h"

class ATM {
private:
    Collection<int> bancnote;
    Multime<Tranzactie>* tranzactii;

public:
    ATM(Comparator<Tranzactie> cmp);
    ~ATM();
    void adaugaBancnote(int valoare, int nr);
    void retrageSuma(int suma);
    void afisareBancnote();
    void afisareTranzactii();
    void afisareTranzactiiSortate(Comparator<Tranzactie> comparator);
};

#endif // ATM_H
