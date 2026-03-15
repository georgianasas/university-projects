#include "ATM.h"
#include "Iterator.h"
#include <iostream>
using namespace std;


ATM::ATM(Comparator<Tranzactie> cmp) {
    tranzactii = new Multime<Tranzactie>(cmp);
}

ATM::~ATM() {
    delete tranzactii;
}

void ATM::adaugaBancnote(int valoare, int nr) {
    for (int i = 0; i < nr; i++) {
        bancnote.add(valoare);
    }
}

void ATM::retrageSuma(int suma) {
    Collection<int> utilizate;
    int sumaRamasa = suma;
    int valori[] = {100, 50, 10, 5, 1};

    for (int i = 0; i < 5 && sumaRamasa > 0; i++) {
        while (sumaRamasa >= valori[i] && bancnote.nrOccurrences(valori[i]) > 0) {
            utilizate.add(valori[i]);
            if (!bancnote.remove(valori[i])) break;
            sumaRamasa -= valori[i];
        }
    }
    if (sumaRamasa == 0) {
        string data;
        cout << "Introdu data tranzactiei (YYYY-MM-DD): ";
        cin >> data;
        tranzactii->addtr(Tranzactie(tranzactii->size() + 1, suma, data, utilizate));
        cout << "Retragere realizata cu succes!" << endl;
    } else {
        cout << "Fonduri insuficiente!" << endl;
        for (int i = 0; i < 5; i++) {
            while (utilizate.nrOccurrences(valori[i]) > 0) {
                bancnote.add(valori[i]);
                utilizate.remove(valori[i]);
            }
        }
    }
}

void ATM::afisareBancnote() {
    cout << "Bancnote disponibile:" << endl;
    bancnote.afisare();
}

void ATM::afisareTranzactii() {
    Iterator<Tranzactie> it(*tranzactii);
    it.first();
    while (it.valid()) {
        it.current().afisare();
        it.next();
    }
}

void ATM::afisareTranzactiiSortate(Comparator<Tranzactie> comparator) {
    Multime<Tranzactie> sortate(comparator);
    Iterator<Tranzactie> it(*tranzactii);
    it.first();
    while (it.valid()) {
        sortate.addtr(it.current());
        it.next();
    }

    Iterator<Tranzactie> itSortate(sortate);
    itSortate.first();
    while (itSortate.valid()) {
        itSortate.current().afisare();
        itSortate.next();
    }
}
